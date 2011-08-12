package com.example.kindle.winningfour.boardgame;

import com.example.kindle.boardgame.IBoard2D;
import com.example.kindle.boardgame.IGameContext;
import com.example.kindle.boardgame.IRules;

public class GameContext implements IGameContext
{
	public GameContext(IBoard2D board)
	{
		this.board = board;
	}

	public IRules getRules()
	{
		return null;
	}

	private IBoard2D board;
}
