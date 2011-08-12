package com.example.kindle.winningfour.boardgame.states;

import java.awt.event.KeyAdapter;

import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.sm.SignalEvent;
import com.example.kindle.sm.State;
import com.example.kindle.winningfour.boardgame.GameController;

public class GameState extends State
{
	public GameState(GameController game, IPlayer player, final String name)
	{
		super(name);
		this.game = game;
		this.player = player;
	}

	public void enter()
	{
		if (this.keyAdapter != null)
		{
			this.game.getFocusOwner().addKeyListener(this.keyAdapter);
		}

		this.game.setStatusText(this.status);
	}
	
	public void leave()
	{
		if (this.keyAdapter != null)
		{
			this.game.getFocusOwner().removeKeyListener(this.keyAdapter);
		}
	}
	
	public void pulse(SignalEvent signal)
	{
		this.game.pulse(signal);
	}

	public IPlayer getPlayer()
	{
		return this.player;
	}

	protected String status;
	protected GameState state;
	protected KeyAdapter keyAdapter;
	private IPlayer player;
	private GameController game;
}
