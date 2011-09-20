package com.example.kindle.winningfour.boardgame;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;

import com.example.kindle.boardgame.Board2DItemType;
import com.example.kindle.boardgame.IBoard2D;
import com.example.kindle.boardgame.IBoard2DItem;
import com.example.kindle.boardgame.IPiece;
import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.boardgame.IPosition2D;
import com.example.kindle.boardgame.ITurn;
import com.example.kindle.boardgame.Position2D;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.boardgame.rules.ClassicRules;

public class Board implements IBoard2D
{
	public Board(final Dimension size)
	{
		this.board = new Piece[size.width][size.height];

		for (int i = 0; i < this.board.length; i++)
		{
			for (int j = 0; j < this.board[i].length; j++)
			{
				this.setPiece(null, new Position2D(i, j));
			}
		}

		this.turnsCount = 0;
		this.lastTurn = null;
		this.hashCode = 0;
	}

	public Board(final IBoard2D board)
	{
		this.board = new Piece[board.getWidth()][board.getHeight()];

		for (int i = 0; i < this.board.length; i++)
		{
			for (int j = 0; j < this.board[i].length; j++)
			{
				IPosition2D pos = new Position2D(i, j);
				this.setPiece(board.getPiece(pos), pos);
			}
		}

		this.turnsCount = board.getTurnsCount();
		
		ITurn last = board.getLastTurn();
		if (last != null)
		{
			this.lastTurn = new Turn(new Piece(last.getPiece().getPlayer()), last.getPosition());
		}

		this.hashCode = board.hashCode();
	}

	public static void initHash()
	{
		// TODO: untie from classic rules
		Random rand = new Random();
		for (int p = 0; p < ClassicRules.MAX_PLAYERS; p++)
		{
			for (int x = 0; x < ClassicRules.MAX_WIDTH; x++)
			{
				for (int y = 0; y < ClassicRules.MAX_HEIGHT; y++)
				{
					Board.zobrist[p][x][y] = rand.nextInt();
				}
			}
		}
		
		Board.zobristMove = rand.nextInt();
	}

	static public void clearHash()
	{
		Board.zobrist = null;
	}

	public ArrayList getItems()
	{
		ArrayList result = new ArrayList();
		for (int j = this.board[0].length - 1; j >= 0 ; j--)
		{
			for (int i = 0; i < this.board.length; i++)
			{
				IPosition2D pos = new Position2D(i, j);
				int type = Board2DItemType.NORMAL;
				if (this.lastTurn != null && this.lastTurn.getPosition().equals(pos))
				{
					type = Board2DItemType.LAST;
				}
				IBoard2DItem item = new BoardItem(this.board[i][j], pos, type);
				result.add(item);
			}
		}

		return result;
	}

	public IPiece getPiece(final IPosition2D position)
	{
		return this.getPiece(position.x(), position.y());
	}

	public IPiece getPiece(int x, int y)
	{
		return this.board[x][y];
	}

	public void setPiece(final IPiece piece, final IPosition2D position)
	{
		this.board[position.x()][position.y()] = (Piece)piece;

		if (piece != null)
		{
			Turn turn = new Turn(piece, (IPosition2D) position.clone());
			this.lastTurn = turn;
			
			this.hashCode ^= this.getTurnHash(turn);
			this.hashCode ^= Board.zobristMove;
		}
		
		this.turnsCount += 1;
	}

	public void putPiece(final IPiece piece, int x)
	{
		this.turn(this.createTurn(piece.getPlayer(), x));
	}

	public void turn(ITurn turn)
	{
		if (turn != null)
		{
			this.setPiece(turn.getPiece(), turn.getPosition());
		}
	}

	public int getTurnHash(ITurn turn)
	{
		int p = turn.getPiece().getPlayer().getColor() == Color.black ? 1 : 0;
		int x = turn.getPosition().x();
		int y = turn.getPosition().y();

		return Board.zobrist[p][x][y];
	}
	
	public void undo()
	{
		if (this.lastTurn != null)
		{
			//this.hashCode ^= lastTurn.hashCode();
			this.hashCode ^= this.getTurnHash(lastTurn);
			this.hashCode ^= Board.zobristMove;

			IPosition2D pos = this.lastTurn.getPosition();
			this.board[pos.x()][pos.y()] = null;
			this.lastTurn = null;
			this.turnsCount -= 1;
		}
	}
	
	public int getWidth()
	{
		return this.board.length;
	}

	public int getHeight()
	{
		return this.board[0].length;
	}

	public Dimension getSize()
	{
		return new Dimension(this.getWidth(), this.getHeight());
	}

	public boolean isPositionOnBoard(final IPosition2D pos)
	{
		return this.isPositionOnBoard(pos.x(), pos.y());
	}

	public boolean isPositionOnBoard(int x, int y)
	{
		return (x >= 0 && x < this.getWidth() &&
				y >= 0 && y < this.getHeight());
	}

	public ITurn createTurn(IPlayer player, int x)
	{
		ITurn result = null;
		
		for (int y = this.board[x].length - 1; y >= 0; y--)
		{
			if (this.board[x][y] == null)
			{
				result = new Turn(new Piece(player), new Position2D(x, y));
				break;
			}
		}
		
		return result;
	}

	public ITurn getLastTurn()
	{
		return this.lastTurn;
	}

	public void setLastTurn(ITurn turn)
	{
		this.lastTurn = (Turn) turn;
	}
	
	public int getTurnsCount()
	{
		return this.turnsCount;
	}

	public Object clone()
	{
		return new Board(this);
	}
	
	public int hashCode()
	{
		return hashCode;
	}

	public void destroy()
	{
		App.log("Board::destroy");

		for (int i = 0; i < this.board.length; i++)
		{
			for (int j = 0; j < this.board[i].length; j++)
			{
				this.board[i][j] = null;
			}
		}

		this.board = null;
		this.lastTurn = null;

		App.log("Board::destroy done");
}

	// Hash keys generated by Zobrist method
	// https://www.cs.wisc.edu/techreports/1970/TR88.pdf
	static private int zobrist[][][] = new int[ClassicRules.MAX_PLAYERS][ClassicRules.MAX_WIDTH][ClassicRules.MAX_HEIGHT];
	static private int zobristMove;
	
	private Piece[][] board;
	// here could be history
	private Turn lastTurn;
	private int turnsCount;
	private int hashCode;
}
