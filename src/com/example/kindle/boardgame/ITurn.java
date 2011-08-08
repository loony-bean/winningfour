package com.example.kindle.boardgame;

import java.util.Date;

public interface ITurn
{
	public IPlayer getPlayer();
	public IPosition2D getPosition();
	public IPiece getPiece();
	public Date getDate();
}
