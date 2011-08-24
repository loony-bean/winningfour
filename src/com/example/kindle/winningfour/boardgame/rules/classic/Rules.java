package com.example.kindle.winningfour.boardgame.rules.classic;

import java.util.ArrayList;

import com.example.kindle.boardgame.GameEvent;
import com.example.kindle.boardgame.IBoard2D;
import com.example.kindle.boardgame.IGameEventListener;
import com.example.kindle.boardgame.IPiece;
import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.boardgame.IPosition2D;
import com.example.kindle.boardgame.IRules;
import com.example.kindle.boardgame.ITurn;
import com.example.kindle.boardgame.Position2D;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.boardgame.Board;
import com.example.kindle.winningfour.boardgame.Piece;
import com.example.kindle.winningfour.boardgame.Turn;

public class Rules implements IRules
{
	public Rules()
	{
		//this.turnValidator = new TurnValidator();
	}
	
	public void afterPlayerTurn(IBoard2D board)
	{
		int gameEvent = this.checkGameEvent(board);
		
		if (gameEvent != GameEvent.CONTINUE)
		{
			this.gameEventListener.onGameEvent(gameEvent);
		}
	}
	
	public int checkGameEvent(IBoard2D board)
	{
		int result = GameEvent.CONTINUE;
		
		// win
		ITurn turn = board.getLastTurn();
		IPlayer p = turn.getPiece().getPlayer();
		IPosition2D pos = new Position2D(turn.getPosition().x(), turn.getPosition().y());

		int max = 0;
		int counts[] = new int[4];
		counts[0] = this.countInRow(board, p, pos, 1, 0);
		counts[1] = this.countInRow(board, p, pos, 0, 1);
		counts[2] = this.countInRow(board, p, pos, 1, 1);
		counts[3] = this.countInRow(board, p, pos, 1, -1);

		for(int i = 0; i < counts.length; i++)
		{
			if (counts[i] > max)
			{
				max = counts[i];			
			}
		}

		// four pieces connected
		if (max >= 4)
		{
			result = GameEvent.WIN;
		}

		// drawn game
		if (board.getTurnsCount() >= board.getWidth() * board.getHeight())
		{
			result = GameEvent.DRAW;
		}
		
		return result;
	}

	private int countInRow(IBoard2D board, IPlayer player, IPosition2D pos, int incx, int incy)
	{
		return this.walk(board, player, pos,  incx,  incy) +
			   this.walk(board, player, pos, -incx, -incy) - 1;
	}

	private int walk(IBoard2D board, IPlayer player, IPosition2D pos, int incx, int incy)
	{
		int local = 0;
		int count = 0;
		IPosition2D p = new Position2D(pos.x(), pos.y());

		while (((Board) board).isPositionOnBoard(p))
		{
			IPiece piece = board.getPiece(p);
			if (piece != null && piece.getPlayer().equals(player))
			{
				local += 1;
				if (local > count)
				{
					count = local;
				}
			}
			else
			{
				local = 0;
				break;
			}
			
			p.adjust(incx, incy);
		}
		
		return count;
	}

	public void beforePlayerTurn(IBoard2D board)
	{
	}

	public void onSetup()
	{
	}

	public boolean isTurnAvailable(IBoard2D board, ITurn turn)
	{
		IPosition2D pos = turn.getPosition();
		if (board.isPositionOnBoard(pos))
		{
			if(board.getPiece(new Position2D(pos.x(), 0)) == null)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public ArrayList getAvailableTurns(IBoard2D board, IPlayer player)
	{
		ArrayList result = new ArrayList();

		ITurn last = board.getLastTurn();
		ITurn lastturn = new Turn(new Piece(player), last.getPosition());
		if(this.isTurnAvailable(board, lastturn))
		{
			result.add(lastturn);			
		}

		int inc = 1;
		boolean onboard = true;
		
		while(onboard)
		{
			onboard = false;
			Position2D left = new Position2D(last.getPosition().x() + inc, 0);
			Position2D right = new Position2D(last.getPosition().x() - inc, 0);
			ITurn leftturn = new Turn(new Piece(player), left);
			ITurn rightturn = new Turn(new Piece(player), right);
			
			if(this.isTurnAvailable(board, leftturn))
			{
				result.add(leftturn);
				onboard = true;
			}

			if(this.isTurnAvailable(board, rightturn))
			{
				result.add(rightturn);
				onboard = true;
			}
			
			inc += 1;
		}
		
		return result;
	}

	public int evaluate(IBoard2D board)
	{
		int event = checkGameEvent(board);
		
		if (event == GameEvent.WIN)
		{
			return 10;
		}
		else if (event == GameEvent.DRAW)
		{
			return 5;
		}
		
		return 0;
	}

	public boolean isEndGame(IBoard2D board)
	{
		return (this.checkGameEvent(board) != GameEvent.CONTINUE);
	}

	public void setEventListener(IGameEventListener gameEventListener)
	{
		this.gameEventListener = gameEventListener;
	}

	public void destroy()
	{
		App.log("Rules::destroy");

		this.gameEventListener = null;

		App.log("Rules::destroy done");
	}

	private IGameEventListener gameEventListener;
}
