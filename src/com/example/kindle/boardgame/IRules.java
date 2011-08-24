package com.example.kindle.boardgame;

import java.util.ArrayList;

public interface IRules
{
	public void onSetup();
	public void afterPlayerTurn(IBoard2D board);
	public void beforePlayerTurn(IBoard2D board);

	public boolean isEndGame(IBoard2D board);
	public int evaluate(IBoard2D board);

	public ArrayList getAvailableTurns(IBoard2D board, IPlayer player);
	public boolean isTurnAvailable(IBoard2D board, ITurn turn);

	public void setEventListener(IGameEventListener gameEventListener);
	public void destroy();
}
