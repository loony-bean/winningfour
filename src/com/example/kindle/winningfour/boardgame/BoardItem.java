package com.example.kindle.winningfour.boardgame;

import com.example.kindle.boardgame.IBoard2DItem;
import com.example.kindle.boardgame.IPiece;
import com.example.kindle.boardgame.IPosition2D;

public class BoardItem implements IBoard2DItem
{
	public BoardItem(final Piece piece, final IPosition2D position, int type)
	{
		this.piece = piece;
		this.position = position;
		this.type = type;
	}

	public IPiece getPiece()
	{
		return this.piece;
	}

	public IPosition2D getPosition()
	{
		return this.position;
	}
	
	public int getType()
	{
		return this.type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	private final Piece piece;
	private final IPosition2D position;
	private int type;
}
