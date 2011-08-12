package com.example.kindle.winningfour.boardgame.states;

import com.example.kindle.sm.SignalEvent;
import com.example.kindle.sm.State;
import com.example.kindle.winningfour.boardgame.GameController;
import com.example.kindle.winningfour.boardgame.GameStateMachine;

public class NewGame extends State
{
	public NewGame(GameController game)
	{
		super("NewGame");
		this.game = game;
	}
	
	public void enter()
	{
		this.game.reset();
		this.game.pulse(new SignalEvent(GameStateMachine.TURN));
		this.game.repaint();
	}

	private GameController game;
}
