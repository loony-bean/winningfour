package com.example.kindle.winningfour.boardgame.rules.classic;

import com.example.kindle.boardgame.GameEvent;
import com.example.kindle.boardgame.IGameEventListener;
import com.example.kindle.boardgame.IRules;
import com.example.kindle.boardgame.ITurnValidator;

public class Rules implements IRules
{
	public Rules()
	{
		this.turnValidator = new TurnValidator();
	}
	
	public void afterPlayerTurn()
	{
		// if win condition
		// gameEventListener.onGameEvent(GameEvent.WIN);
		// or gameEventListener.onGameEvent(GameEvent.LOOSE);
		// or gameEventListener.onGameEvent(GameEvent.DRAW);
	}

	public void beforePlayerTurn()
	{
	}

	public ITurnValidator getTurnValidator()
	{
		return turnValidator;
	}

	public void onSetup()
	{
	}

	public void setEventListener(IGameEventListener gameEventListener)
	{
	}
	
	public int getGameStatus()
	{
		return GameEvent.CONTINUE;
	}
	
	private ITurnValidator turnValidator;
	private IGameEventListener gameEventListener;
}
