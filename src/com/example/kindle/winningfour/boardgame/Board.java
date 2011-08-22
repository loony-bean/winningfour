package com.example.kindle.winningfour.boardgame;

import java.awt.Dimension;
import java.util.ArrayList;

import com.example.kindle.boardgame.IBoard2D;
import com.example.kindle.boardgame.IBoard2DItem;
import com.example.kindle.boardgame.IPiece;
import com.example.kindle.boardgame.IPosition2D;
import com.example.kindle.boardgame.ITurn;
import com.example.kindle.boardgame.Position2D;
import com.example.kindle.winningfour.App;

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
	}

	public Board(Board b)
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
		
		this.lastTurn = (Turn) b.getLastTurn();
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
			this.lastTurn = new Turn(piece, pos);
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
		App.log("Board::clone");

		return new Board(this);
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

	private Piece[][] board;
	// here could be history
	private Turn lastTurn;
	private int turnsCount;
}
