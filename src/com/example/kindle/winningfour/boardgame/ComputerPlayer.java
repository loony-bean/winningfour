package com.example.kindle.winningfour.boardgame;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import com.example.kindle.boardgame.IBoard2D;
import com.example.kindle.boardgame.IGameContext;
import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.boardgame.IRules;
import com.example.kindle.boardgame.ITurn;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.AppResources;

public class ComputerPlayer extends Player
{
	public final static int MAXVAL = 10000;
    public final static int DEPTH = 5;
    public final static int MINTIME = 1000;

	public ComputerPlayer(final Color color)
	{
		super(color);

		App.log("ComputerPlayer::create");

		//this.hash = new TranspositionTable();
		
		this.contextReady = new Object();
		this.workerReady = new Object();
		this.ready = false;
		this.worker = new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					App.log("ComputerPlayer::worker started");
					
					while (!ComputerPlayer.this.worker.isInterrupted())
					{
						App.log("ComputerPlayer::worker entered");
						ComputerPlayer.this.waitForTurn();
						ComputerPlayer.this.makeBestTurn();
					}
				}
				catch(InterruptedException e)
				{
					App.log("ComputerPlayer::worker interrupted");
					Thread.currentThread().interrupt();
				}
			}
		}, "AI");
		

		this.worker.start();
		
		App.log("ComputerPlayer::create done");
	}
	
	public void think(final IGameContext context)
	{
		App.log("ComputerPlayer::think");

		this.board = (IBoard2D) context.getBoard().clone();
		this.rules = context.getRules();
		this.players = context.getPlayers();

		synchronized (this.workerReady)
		{
			try
			{
				while(!this.ready)
				{
					this.workerReady.wait(50);
				}
				
				App.log("ComputerPlayer::worker wait ended");
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		synchronized (this.contextReady)
		{
			App.log("ComputerPlayer::think notify AI worker");
			this.contextReady.notifyAll();
		}

		App.log("ComputerPlayer::think done");
	}

	private void started()
	{
		synchronized (this.workerReady)
		{
			App.log("ComputerPlayer::workerReady notify");
			this.ready = true;
			this.workerReady.notifyAll();
		}
	}

	private void waitForTurn() throws InterruptedException
	{
		synchronized (this.contextReady)
		{
			this.started();

			App.log("ComputerPlayer::waitForTurn");
			this.contextReady.wait();
		}
	}
	
	private void makeBestTurn()
	{
		App.log("ComputerPlayer::makeBestTurn");

		turnTime = System.currentTimeMillis();

		this.pid = (this.players[0] == this) ? 0 : 1;

		ITurn best = null;
		// TODO: read from rules
        int maxscore = -MAXVAL;
        int score = 0;
        boolean empty = true;
        
        //IBoard2D cloned = (IBoard2D) this.board.clone();
        Iterator i = this.rules.getAvailableTurns(this.board, this).iterator();
        while (i.hasNext())
        {
        	ITurn turn = (ITurn) i.next();

        	ITurn last = board.getLastTurn();
        	this.board.turn(turn);
            
        	score = -this.negascout(this.board, DEPTH-1, -MAXVAL, MAXVAL, this.nextPlayer(this.pid));
            
        	this.board.undo();
        	((Board) this.board).setLastTurn(last);

            App.log("S: " + score);

            if (empty)
            {
            	best = turn;
            	empty = false;
            }

            if (score > maxscore)
            {
                best = turn;
                maxscore = score;
            }
            
            if (score == MAXVAL - 1)
            {
            	break;
            }
        }

        //cloned = null;

        if(empty)
        {
        	App.error(App.bundle.getString(AppResources.KEY_ERROR_NO_TURNS));
        }
        else
        {
			long delta = System.currentTimeMillis() - turnTime;
			if (delta < MINTIME)
			{
				try
				{
					Thread.sleep(MINTIME - delta, 0);
				} catch (InterruptedException e)
				{
					// no danger
				}
			}
			turnTime = System.currentTimeMillis();

			App.gamer.makeTurn(best);
        }

		App.log("ComputerPlayer::makeBestTurn done");
	}

	private int nextPlayer(int pid)
	{
		return (pid == 0) ? 1 : 0;
	}

	private int negascout(final IBoard2D board, int depth, int alpha, int beta, int pid)
	{
		int score = -MAXVAL;
		//int range = TranspositionTableItem.FailLow;

		//score = this.hash.lookup(board.hashCode(), depth, alpha, beta);
		
		//if (score != TranspositionTableItem.Unknown)
		//{
		//	return score;
		//}

		if (depth == 0 || this.rules.isEndGame(board))
		{
			return -this.rules.evaluate(board, depth);
		}

		// (* initial window is (-beta, -alpha) *)
		int b = beta;
		boolean first = true;

		//IBoard2D cloned = (IBoard2D) board.clone();
		
        Iterator i = this.rules.getAvailableTurns(board, this.players[pid]).iterator();
        while (i.hasNext())
        {
        	ITurn turn = (ITurn) i.next();
        	ITurn last = board.getLastTurn();
        	board.turn(turn);

            score = -this.negascout(board, depth - 1, -b, -alpha, this.nextPlayer(pid));

            // (* check if null-window failed high *)
            if (alpha < score && score < beta && !first)
            {
            	// 'full re-search'
            	score = -this.negascout(board, depth - 1, -beta, -alpha, this.nextPlayer(pid));
            }
            
            //App.log("D: " + depth + " P: " + turn.getPosition().x() + " S: " + score);
            
            first = false;
            board.undo();
            ((Board) board).setLastTurn(last);
            
            if (score > alpha)
            {
            	//range = TranspositionTableItem.ExactValue;
            	alpha = score;
            }

            if (alpha >= beta)
            {
                // 'beta cut-off'
            	//range = TranspositionTableItem.FailHigh;
            	break;
            }
        	// (* set new null window *)
            b = alpha + 1;
        }
        
        //cloned = null;
        
		//this.hash.add(board.hashCode(), depth, alpha, range);
        return alpha;
	}

	public void interrupt() throws InterruptedException
	{
		App.log("ComputerPlayer::interrupt");
		App.log("ComputerPlayer::done");
	}

	protected void onKeyboard(final KeyEvent event)
	{
		App.log("ComputerPlayer::onKeyboard" + event);
	}

	public void destroy()
	{
		App.log("ComputerPlayer::destroy");

		super.destroy();

		if (this.board != null)
		{
			this.board.destroy();
			this.board = null;
		}
		
		this.rules = null;
		this.players = null;

		//this.hash.destroy();
		//this.hash = null;
		
		this.worker.interrupt();
		try
		{
			this.worker.join();
		}
		catch (InterruptedException e)
		{
			// doesn't matter, we are going to destroy the class
			App.log("ComputerPlayer::destroy worker interrupt ecxeption");
		}
		this.worker = null;

		this.contextReady = null;
		this.workerReady = null;

		App.log("ComputerPlayer::destroy done");
	}

	private IBoard2D board;
	private IRules rules;
	private IPlayer[] players;
	private int pid;
	//private TranspositionTable hash;
	private Thread worker;
	private Object contextReady;
	private Object workerReady;
	private boolean ready;
	private long turnTime;
}
