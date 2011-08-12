package com.example.kindle.boardgame;

import java.util.Date;

public interface ITurn
{
	public IPosition2D getPosition();
	public IPiece getPiece();
	public Date getDate();
}
