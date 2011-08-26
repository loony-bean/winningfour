package com.example.kindle.winningfour.boardgame;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;

import com.example.kindle.boardgame.IBoard2D;
import com.example.kindle.boardgame.IBoard2DItem;
import com.example.kindle.boardgame.IPiece;
import com.example.kindle.boardgame.IPosition2D;
import com.example.kindle.boardgame.ITurn;
import com.example.kindle.boardgame.Position2D;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.boardgame.rules.classic.Rules;

public class Board implements IBoard2D
{
	public Board(Dimension size)
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

	public Board(IBoard2D b)
	{
		this.board = new Piece[b.getWidth()][b.getHeight()];

		for (int i = 0; i < this.board.length; i++)
		{
			for (int j = 0; j < this.board[i].length; j++)
			{
				IPosition2D pos = new Position2D(i, j);
				this.setPiece(b.getPiece(pos), pos);
			}
		}

		this.turnsCount = b.getTurnsCount();
		ITurn last = b.getLastTurn();
		this.lastTurn = new Turn(new Piece(last.getPiece().getPlayer()), last.getPosition());
		this.hashCode = b.hashCode();
	}

	public static void initHash()
	{
		Random rand = new Random();
		for (int p = 0; p < Rules.PLAYERS_MAX; p++)
		{
			for (int x = 0; x < Rules.WIDTH_MAX; x++)
			{
				for (int y = 0; y < Rules.HEIGHT_MAX; y++)
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
		for (int i = 0; i < this.board.length; i++)
		{
			for (int j = 0; j < this.board[i].length; j++)
			{
				IBoard2DItem item = new BoardItem(this.board[i][j], new Position2D(i, j));
				result.add(item);
			}
		}

		return result;
	}

	public IPiece getPiece(IPosition2D position)
	{
		return this.board[position.x()][position.y()];
	}

	public void setPiece(IPiece piece, IPosition2D pos)
	{
		this.board[pos.x()][pos.y()] = (Piece)piece;

		if (piece != null)
		{
			Turn turn = new Turn(piece, new Position2D(pos.x(), pos.y()));
			this.lastTurn = turn;
			
			this.hashCode ^= turn.hashCode();
			this.hashCode ^= Board.zobristMove;
		}
		
		this.turnsCount += 1;
	}

	public void putPiece(IPiece piece, int row)
	{
		for (int col = this.board[row].length - 1; col >= 0; col--)
		{
			if (this.board[row][col] == null)
			{
				this.setPiece(piece, new Position2D(row, col));
				break;
			}
		}
		// TODO: TurnValidator, exception?
	}

	public void undo()
	{
		if (this.lastTurn != null)
		{
			this.hashCode ^= lastTurn.hashCode();
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

	public boolean isPositionOnBoard(IPosition2D p)
	{
		return (p.x() >= 0 && p.x() < this.getWidth() &&
				p.y() >= 0 && p.y() < this.getHeight());
	}

	public ITurn getLastTurn()
	{
		return this.lastTurn;
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
	static private int zobrist[][][] = new int[Rules.PLAYERS_MAX][Rules.WIDTH_MAX][Rules.HEIGHT_MAX];
	static private int zobristMove;
	
	private Piece[][] board;
	// here could be history
	private Turn lastTurn;
	private int turnsCount;
	private int hashCode;
}
