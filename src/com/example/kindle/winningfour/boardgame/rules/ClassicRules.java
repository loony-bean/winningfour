package com.example.kindle.winningfour.boardgame.rules;

import java.util.ArrayList;
import java.util.Iterator;

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
import com.example.kindle.winningfour.boardgame.TranspositionTable;
import com.example.kindle.winningfour.boardgame.TranspositionTableItem;

public class ClassicRules implements IRules
{
	public static final int MAX_PLAYERS = 2;
	public static final int MAX_WIDTH = 10;
	public static final int MAX_HEIGHT = 7;

	public static final int W_MAX = 10000;
	public static final int W_FALLOFF = 1;

	public static final int W_DRAW         = 1;
	public static final int W_THREAT_TWO   = 2;
	public static final int W_THREAT_THREE = 5;
	public static final int W_THREAT_ZUG   = 3;
	public static final int W_WIN          = W_MAX;

	public static final int B_MAJOR = 2;
	public static final int B_FORK  = 2;

	public ClassicRules()
	{
		this.hash = new TranspositionTable();
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
		ArrayList threats = this.walk(board, p, pos);

		Iterator iter = threats.iterator();
		while(iter.hasNext())
		{
			Threat threat = (Threat) iter.next();
			if (threat.count > max)
			{
				max = threat.count;
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

	private ArrayList walk(final IBoard2D board, final IPlayer player, final IPosition2D pos)
	{
		ArrayList threats = new ArrayList();
		
		int incs[][] = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};
		for (int c = 0; c < incs.length; c++)
		{
			Threat threat = this.countInRow(board, player, pos, incs[c][0], incs[c][1]);
			if (threat.count > 1)
			{
				threats.add(threat);
			}
		}

		return threats;
	}

	private Threat countInRow(final IBoard2D board, final IPlayer player, final IPosition2D pos, int incx, int incy)
	{
		Threat left  = this.countInRowOneDir(board, player, pos,  incx,  incy);
		Threat right = this.countInRowOneDir(board, player, pos, -incx, -incy);

		int count = left.count + right.count - 1;
		int type = left.type + right.type;

		return new Threat(count, type);
	}

	private Threat countInRowOneDir(final IBoard2D board, final IPlayer player, final IPosition2D pos, int incx, int incy)
	{
		int local = 0;
		int count = 0;
		int type = Threat.CLOSED;
		
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
				if (piece == null)
				{
					type = Threat.MINOR;
				}

				local = 0;
				break;
			}
			
			x += incx;
			y += incy;
		}

		return new Threat(count, type);
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
			result = W_WIN;
		}
		else if (event == GameEvent.DRAW)
		{
			result = W_DRAW;
		}
		else
		{
			TranspositionTableItem item = this.hash.lookup(board.hashCode(), depth, 0, 0);
			if (item == null)
			{
				ITurn last = board.getLastTurn();
				result = this.evaluateThreats(board, last.getPiece().getPlayer());
			}
			else
			{
				result = item.score;
			}
		}

		this.hash.add(board.hashCode(), depth, result, 0);

		return result - distance * W_FALLOFF;
	}

	private int evaluateThreats(final IBoard2D board, final IPlayer player)
	{
		int eval = 0;
		int width = board.getWidth();
		int height = board.getHeight();
		
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				ArrayList threats = this.walk(board, player, new Position2D(i, j));
				Iterator iter = threats.iterator();
				int threatsNumber = 0;
				int increment = 0;
				while (iter.hasNext())
				{
					Threat threat = (Threat) iter.next();
					if (threat.count == 3 && threat.type != Threat.CLOSED)
					{
						increment += W_THREAT_THREE;
						threatsNumber += 1;
						if (threat.type == Threat.MAJOR)
						{
							increment *= B_MAJOR;
						}
					}
					else if (threat.count == 2 && threat.type != Threat.CLOSED)
					{
						increment += W_THREAT_TWO;
						threatsNumber += 1;
						if (threat.type == Threat.MAJOR)
						{
							increment *= B_MAJOR;
						}
					}
				}
				
				if (threatsNumber > 2)
				{
					increment *= B_FORK;
				}
				
				eval += increment;
			}
		}

		return eval;
	}
	
	public boolean isEndGame(final IBoard2D board)
	{
		return (this.checkGameEvent(board) != GameEvent.CONTINUE);
	}

	public void setEventListener(final IGameEventListener gameEventListener)
	{
		this.gameEventListener = gameEventListener;
	}

	public int getMaxPlayers()
	{
		return MAX_PLAYERS;
	}

	public int getMaxWidth()
	{
		return MAX_WIDTH;
	}

	public int getMaxHeight()
	{
		return MAX_HEIGHT;
	}

	public void destroy()
	{
		App.log("Rules::destroy");

		this.gameEventListener = null;

		App.log("Rules::destroy done");
	}

	private IGameEventListener gameEventListener;
	private TranspositionTable hash;

	private class Threat
	{
		public static final int CLOSED = 0;
		public static final int MINOR  = 1;
		public static final int MAJOR  = 2;
		
		public Threat(int count, int type)
		{
			this.count = count;
			this.type = type;
		}
		
		int count;
		int type;
	}
}
