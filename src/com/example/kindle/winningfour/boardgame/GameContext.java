package com.example.kindle.winningfour.boardgame;

import com.example.kindle.boardgame.IBoard2D;
import com.example.kindle.boardgame.IGameContext;
import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.boardgame.IRules;

public class GameContext implements IGameContext
{
	public GameContext(final IBoard2D board, final IRules rules, final IPlayer[] players)
	{
		this.board = board;
		this.rules = rules;
		this.players = players;
	}

	public IBoard2D getBoard()
	{
		return this.board;
	}

	public IRules getRules()
	{
		return this.rules;
	}

	public IPlayer[] getPlayers()
	{
		return this.players;
	}

	private IBoard2D board;
	private IRules rules;
	private IPlayer[] players;
}
