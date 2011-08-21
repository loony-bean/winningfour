package com.example.kindle.winningfour.boardgame.rules.classic;

import com.example.kindle.boardgame.GameEvent;
import com.example.kindle.boardgame.IBoard2D;
import com.example.kindle.boardgame.IGameEventListener;
import com.example.kindle.boardgame.IPiece;
import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.boardgame.IRules;
import com.example.kindle.boardgame.ITurn;
import com.example.kindle.boardgame.ITurnValidator;
import com.example.kindle.boardgame.Position2D;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.boardgame.Board;

public class Rules implements IRules
{
	public Rules()
	{
		this.turnValidator = new TurnValidator();
	}
	
	public void afterPlayerTurn(IBoard2D b)
	{
		// win
		ITurn turn = b.getLastTurn();
		IPlayer p = turn.getPiece().getPlayer();
		int x = turn.getPosition().x();
		int y = turn.getPosition().y();

		int max = 0;
		int counts[] = new int[4];
		counts[0] = this.countInRow(b, p, x, y, 1, 0);
		counts[1] = this.countInRow(b, p, x, y, 0, 1);
		counts[2] = this.countInRow(b, p, x, y, 1, 1);
		counts[3] = this.countInRow(b, p, x, y, 1, -1);

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
			this.gameEventListener.onGameEvent(GameEvent.WIN);
			return;
		}

		// drawn game
		if (b.getTurnsCount() >= b.getWidth() * b.getHeight())
		{
			this.gameEventListener.onGameEvent(GameEvent.DRAW);
			return;
		}
	}

	private int countInRow(IBoard2D board, IPlayer player, int x, int y, int incx, int incy)
	{
		return this.walk(board, player, x, y, incx, incy) + this.walk(board, player, x, y, -incx,  -incy) - 1;
	}

	private int walk(IBoard2D board, IPlayer player, int x, int y, int incx, int incy)
	{
		int local = 0;
		int count = 0;

		while (((Board) board).isPositionOnBoard(new Position2D(x, y)))
		{
			IPiece piece = board.getPiece(new Position2D(x, y));
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
			}
			
			x += incx;
			y += incy;
		}
		
		return count;
	}

	public void beforePlayerTurn()
	{
	}

	public ITurnValidator getTurnValidator()
	{
		return turnValidator;
	}

	public void onSetup()
	{
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

	// TODO: remove turn validator
	private ITurnValidator turnValidator;
	private IGameEventListener gameEventListener;
}
