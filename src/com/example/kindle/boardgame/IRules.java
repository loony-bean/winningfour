package com.example.kindle.boardgame;

public interface IRules
{
	public void onSetup();
	public void afterPlayerTurn(IBoard2D board);
	public void beforePlayerTurn();
	public ITurnValidator getTurnValidator();
	public void setEventListener(IGameEventListener gameEventListener);
	public void destroy();
}
