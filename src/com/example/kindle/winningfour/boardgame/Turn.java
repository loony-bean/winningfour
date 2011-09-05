package com.example.kindle.winningfour.boardgame;

import com.example.kindle.boardgame.IPiece;
import com.example.kindle.boardgame.IPosition2D;
import com.example.kindle.boardgame.ITurn;

public class Turn implements ITurn
{
	public Turn(final IPiece piece, final IPosition2D position)
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

	private IPosition2D position;
	private IPiece piece;
}
