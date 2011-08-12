package com.example.kindle.boardgame;

public interface IGame
{
	public IGameContext getContext();
	public void reset();
	public void start();
	public void stop();
	public void makeTurn(ITurn turn);

}
