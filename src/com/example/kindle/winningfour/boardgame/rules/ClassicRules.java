package com.example.kindle.winningfour.boardgame.rules;

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
import com.example.kindle.winningfour.boardgame.ComputerPlayer;

public class ClassicRules implements IRules
{
	public static final int PLAYERS_MAX = 2;
	public static final int WIDTH_MAX = 10;
	public static final int HEIGHT_MAX = 7;

	public ClassicRules()
	{
	}
	
	public void afterPlayerTurn(final IBoard2D board)
	{
		int gameEvent = this.checkGameEvent(board);
		
		if (gameEvent != GameEvent.CONTINUE)
		{
			this.gameEventListener.onGameEvent(gameEvent);
		}
	}
	
	public int checkGameEvent(final IBoard2D board)
	{
		int result = GameEvent.CONTINUE;
		
		// win
		ITurn turn = board.getLastTurn();
		IPlayer p = turn.getPiece().getPlayer();
		IPosition2D pos = (IPosition2D) turn.getPosition().clone();

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

		if (max >= 4)
		{
			// four pieces connected
			result = GameEvent.WIN;
		}
		else if (board.getTurnsCount() >= board.getWidth() * board.getHeight())
		{
			// no turns left
			result = GameEvent.DRAW;
		}
		
		return result;
	}

	private int countInRow(final IBoard2D board, final IPlayer player, final IPosition2D pos, int incx, int incy)
	{
		return this.walk(board, player, pos,  incx,  incy) +
			   this.walk(board, player, pos, -incx, -incy) - 1;
	}

	private int walk(final IBoard2D board, final IPlayer player, final IPosition2D pos, int incx, int incy)
	{
		int local = 0;
		int count = 0;
		int x = pos.row();
		int y = pos.col();

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
				break;
			}
			
			x += incx;
			y += incy;
		}
		
		return count;
	}

	public void beforePlayerTurn(final IBoard2D board)
	{
	}

	public void onSetup()
	{
	}

	public boolean isTurnAvailable(final IBoard2D board, final ITurn turn)
	{
		if (turn != null)
		{
			IPosition2D pos = turn.getPosition();
			if (board.isPositionOnBoard(pos))
			{
				if(board.getPiece(new Position2D(pos.row(), 0)) == null)
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public ArrayList getAvailableTurns(final IBoard2D board, final IPlayer player)
	{
		ArrayList result = new ArrayList();

		ITurn best = null;
		ITurn last = board.getLastTurn();
		int lastx = (last != null) ? last.getPosition().row() : (board.getWidth() - 1)/2;
		best = ((Board) board).createTurn(player, lastx);
		
		if(this.isTurnAvailable(board, best))
		{
			result.add(best);			
		}

		int inc = 1;
		boolean onboard = true;

		while(onboard)
		{
			onboard = false;
			int[] candidates = {lastx + inc, lastx - inc};

			for (int i = 0; i < candidates.length; i++)
			{
				if (board.isPositionOnBoard(new Position2D(candidates[i], 0)))
				{
					ITurn t = ((Board) board).createTurn(player, candidates[i]);

					if(this.isTurnAvailable(board, t))
					{
						result.add(t);
					}

					onboard = true;
				}
			}
			
			inc += 1;
		}

		return result;
	}

	public int evaluate(final IBoard2D board, int depth)
	{
		int event = checkGameEvent(board);
		int distance = ComputerPlayer.DEPTH - depth;
		int result = 0;
		
		if (event == GameEvent.WIN)
		{
			result = 10;
		}
		else if (event == GameEvent.DRAW)
		{
			result = 5;
		}
		
		return result - distance;
	}

	public boolean isEndGame(final IBoard2D board)
	{
		return (this.checkGameEvent(board) != GameEvent.CONTINUE);
	}

	public void setEventListener(final IGameEventListener gameEventListener)
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
