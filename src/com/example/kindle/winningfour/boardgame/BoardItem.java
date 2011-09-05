package com.example.kindle.winningfour.boardgame;

import com.example.kindle.boardgame.IBoard2DItem;
import com.example.kindle.boardgame.IPiece;
import com.example.kindle.boardgame.IPosition2D;

public class BoardItem implements IBoard2DItem
{
	public BoardItem(Piece piece, IPosition2D position)
	{
		this.piece = piece;
		this.position = position;
	}

	public IPiece getPiece()
	{
		return this.piece;
	}

	public IPosition2D getPosition()
	{
		return this.position;
	}
	
	private final Piece piece;
	private final IPosition2D position;
}
