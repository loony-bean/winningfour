package com.example.kindle.winningfour.boardgame;

import java.util.Date;

import com.example.kindle.boardgame.IPiece;
import com.example.kindle.boardgame.IPosition2D;
import com.example.kindle.boardgame.ITurn;

public class Turn implements ITurn
{
	public Turn(IPiece piece, IPosition2D position, Date date)
	{
		this.piece = piece;
		this.position = position;
		this.date = date;
	}

	public Date getDate()
	{
		return this.date;
	}

	public IPiece getPiece()
	{
		return this.piece;
	}

	public IPosition2D getPosition()
	{
		return this.position;
	}

	private Date date;
	private IPosition2D position;
	private IPiece piece;
}
