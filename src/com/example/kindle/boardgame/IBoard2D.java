package com.example.kindle.boardgame;

import java.util.ArrayList;

public interface IBoard2D
{
	public void setPiece(IPiece piece, IPosition2D position);
	public IPiece getPiece(IPosition2D position);
	public ArrayList getItems();
	public ITurn getLastTurn();
	public int getTurnsCount();
	public boolean isPositionOnBoard(IPosition2D p);
	public int getWidth();
	public int getHeight();
	public void destroy();
}
