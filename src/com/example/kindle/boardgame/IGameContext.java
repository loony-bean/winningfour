package com.example.kindle.boardgame;

public interface IGameContext
{
	public IBoard2D getBoard();
	public IRules getRules();
	public IPlayer[] getPlayers();
	//public IRules getOptions();
}
