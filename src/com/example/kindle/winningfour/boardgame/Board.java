package com.example.kindle.winningfour.boardgame;

import java.awt.Dimension;
import java.util.ArrayList;

import com.example.kindle.boardgame.IBoard2D;
import com.example.kindle.boardgame.IBoard2DItem;
import com.example.kindle.boardgame.IPiece;
import com.example.kindle.boardgame.IPosition2D;
import com.example.kindle.boardgame.Position2D;

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

	public void setPiece(IPiece piece, IPosition2D position)
	{
		// TODO: TurnValidator, exception?
		this.board[position.x()][position.y()] = (Piece)piece;
	}
	
	public void putPiece(IPiece piece, int row)
	{
		for (int col = this.board[row].length - 1; col >= 0; col--)
		{
			if (this.board[row][col] == null)
			{
				this.setPiece(piece, new Position2D(row, col));
				return;
			}
		}
		// TODO: TurnValidator, exception?
	}

	public ArrayList search(IPiece piece)
	{
		// TODO
		return null;
	}

	public void resize(Dimension size)
	{
		// TODO
	}

	public int getWidth()
	{
		return this.board.length;
	}

	public int getHeight()
	{
		return this.board[0].length;
	}

	private Piece[][] board;
}
