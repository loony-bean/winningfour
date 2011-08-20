package com.example.kindle.winningfour.boardgame;

import java.awt.event.KeyAdapter;

import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.sm.SignalEvent;
import com.example.kindle.sm.State;
import com.example.kindle.winningfour.boardgame.GameController;

public class GameState extends State
{
	public GameState(GameController game, int player, final String name)
	{
		super(name);
		this.player = player;
		this.game = game;
	}

	public void enter()
	{
		if (this.keyAdapter != null)
		{
			this.game.getView().addKeyListener(this.keyAdapter);
		}

		this.game.setStatusText(this.status);
	}
	
	public void leave()
	{
		if (this.keyAdapter != null)
		{
			this.game.getView().removeKeyListener(this.keyAdapter);
		}
	}
	
	public IPlayer getPlayer()
	{
		return this.game.getPlayers()[this.player];
	}
	
	public void pulse(SignalEvent signal)
	{
		this.game.pulse(signal);
	}

	protected String status;
	protected GameState state;
	protected KeyAdapter keyAdapter;
	protected int player;
	private GameController game;
}
