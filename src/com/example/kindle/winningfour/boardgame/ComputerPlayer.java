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

public class ComputerPlayer extends Player
{
	public final static int MAXVAL = 100;
    public final static int DEPTH = 6;

	public ComputerPlayer(final Color color, final String name)
	{
		super(color, name);
		this.hash = new TranspositionTable();
	}

	public void think(IGameContext context)
	{
		App.log("ComputerPlayer::think");

		this.board = (IBoard2D) context.getBoard().clone();
		this.rules = context.getRules();
		this.players = context.getPlayers();
		
		this.pid = (this.players[0] == this) ? 0 : 1;

		ITurn best = null;
        int maxscore = -MAXVAL;
        int score = 0;
        
        IBoard2D cloned = (IBoard2D) this.board.clone();
        Iterator i = this.rules.getAvailableTurns(cloned, this).iterator();
        while (i.hasNext())
        {
        	ITurn turn = (ITurn) i.next();

            ((Board) cloned).putPiece(new Piece(this), turn.getPosition().x());
            score = -this.negascout(cloned, DEPTH-1, -MAXVAL, MAXVAL, this.nextPlayer(this.pid));
            cloned.undo();
            
            App.log("S: " + score);

            if (this.rules.isTurnAvailable(cloned, turn))
            {
                if (score > maxscore)
                {
                    best = turn;
                    maxscore = score;
                }
            }
        }
        
        cloned = null;

        App.gamer.makeTurn(best);

		App.log("ComputerPlayer::think done");
	}

	private int nextPlayer(int pid)
	{
		return (pid == 0) ? 1 : 0;
	}
	
	private int negascout(IBoard2D board, int depth, int alpha, int beta, int pid)
	{
		int score = -MAXVAL;
		int range = TranspositionTableItem.FailLow;

		score = this.hash.lookup(board.hashCode(), depth, alpha, beta);
		
		if (score != TranspositionTableItem.Unknown)
		{
			return score;
		}

		if (this.rules.isEndGame(board) || depth == 0)
		{
			return -this.rules.evaluate(board, depth);
		}

		// (* initial window is (-beta, -alpha) *)
		int b = beta;
		boolean first = true;

		IBoard2D cloned = (IBoard2D) board.clone();
		
        Iterator i = this.rules.getAvailableTurns(cloned, this.players[pid]).iterator();
        while (i.hasNext())
        {
        	ITurn turn = (ITurn) i.next();
        	((Board) cloned).putPiece(new Piece(this.players[pid]), turn.getPosition().x());

            score = -this.negascout(cloned, depth - 1, -b, -alpha, this.nextPlayer(pid));

            // (* check if null-window failed high *)
            if (alpha < score && score < beta && !first)
            {
            	// 'full re-search'
            	score = -this.negascout(cloned, depth - 1, -beta, -alpha, this.nextPlayer(pid));
            }
            
            //App.log("D: " + depth + " P: " + turn.getPosition().x() + " S: " + score);
            
            first = false;
            cloned.undo();
            
            if (score > alpha)
            {
            	range = TranspositionTableItem.ExactValue;
            	alpha = score;
            }

            if (alpha >= beta)
            {
                // 'beta cut-off'
            	range = TranspositionTableItem.FailHigh;
            	break;
            }
        	// (* set new null window *)
            b = alpha + 1;
        }
        
        cloned = null;
        
		this.hash.add(board.hashCode(), depth, alpha, range);
        return alpha;
	}

	public void interrupt()
	{
		App.log("ComputerPlayer::interrupt");
		
		App.log("ComputerPlayer::done");
	}

	protected void onKeyboard(KeyEvent event)
	{
		App.log("ComputerPlayer::onKeyboard" + event);
	}

	public void destroy()
	{
		App.log("ComputerPlayer::destroy");
		
		super.destroy();

		this.board.destroy();
		this.board = null;
		
		this.rules = null;
		this.players = null;
		
		this.hash.destroy();
		this.hash = null;

		App.log("ComputerPlayer::destroy done");
	}

	private IBoard2D board;
	private IRules rules;
	private IPlayer[] players;
	private int pid;
	private TranspositionTable hash;
}
