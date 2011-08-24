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
	public ComputerPlayer(final Color color, final String name)
	{
		super(color, name);
	}

	public void think(IGameContext context)
	{
		App.log("ComputerPlayer::think");

		this.board = (IBoard2D) context.getBoard().clone();
		this.rules = context.getRules();
		this.players = context.getPlayers();
		
		this.pid = (this.players[0] == this) ? 0 : 1;

		ITurn best = null;
		int MAXVAL = 100;
        int maxscore = -MAXVAL;
        int score = 0;
        
        IBoard2D cloned = (IBoard2D) this.board.clone();
        Iterator i = this.rules.getAvailableTurns(cloned, this).iterator();
        while (i.hasNext())
        {
        	ITurn turn = (ITurn) i.next();

            ((Board) cloned).putPiece(new Piece(this), turn.getPosition().x());
            score = -this.negascout(cloned, depth-1, -MAXVAL, MAXVAL, this.nextPlayer(this.pid));
            cloned.undo();
            
            //App.log("D: " + depth + " P: " + turn.getPosition().x() + " S: " + score);
            //App.log("");

            if (score > maxscore && this.rules.isTurnAvailable(cloned, turn))
            {
                best = turn;
                maxscore = score;
            }
        }
        
        App.gamer.makeTurn(best);

		App.log("ComputerPlayer::think done");
	}

	private int nextPlayer(int pid)
	{
		int next = (pid == 0) ? 1 : 0;
		return next;
	}
	
	private int negascout(IBoard2D board, int depth, int alpha, int beta, int pid)
	{
		if (this.rules.isEndGame(board) || depth == 0)
		{
			return -this.rules.evaluate(board) - (this.depth - depth);
		}

		// (* initial window is (-beta, -alpha) *)
		int b = beta;
		boolean first = true;
		int score = -100;

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
            	alpha = score;
            }

            if (alpha >= beta)
            {
                // 'beta cut-off'
                return alpha;
            }
        	// (* set new null window *)
            b = alpha + 1;                                       
        }
        
        return alpha;
	}

	private IPlayer opponent(IPlayer player)
	{
		IPlayer opponent = (player == this.players[0]) ? players[1] : players[0];
		return opponent;
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
	
	private IBoard2D board;
	private IRules rules;
	private IPlayer[] players;
	private int pid;
    private final int depth = 6;

}
