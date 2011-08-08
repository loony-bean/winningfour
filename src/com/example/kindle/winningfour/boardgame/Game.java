package com.example.kindle.winningfour.boardgame;

import com.example.kindle.boardgame.IGame;
import com.example.kindle.boardgame.IRules;

public class Game implements IGame
{
	public Game(IRules rules)
	{
		this.controller = new GameController();
		this.rules = rules;
	}

	public IRules getRules()
	{
		return this.rules;
	}

	public void setup()
	{
		this.rules.onSetup();
		this.controller.start();
	}
	
	public GameController getController()
	{
		return this.controller;
	}

	private GameController controller;
	private IRules rules;
}
