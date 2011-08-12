package com.example.kindle.boardgame;

import java.util.ArrayList;

public interface IBoard2D
{
	public void setPiece(IPiece piece, IPosition2D position);
	public IPiece getPiece(IPosition2D position);
	public ArrayList getItems();
	public ITurn getLastTurn();
	public ArrayList search(IPiece piece);
	public int getWidth();
	public int getHeight();
	public int getTurnsCount();
}
