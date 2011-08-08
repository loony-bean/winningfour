package com.example.kindle.winningfour.boardgame.states;

import com.example.kindle.boardgame.IGameContext;
import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.boardgame.ITurn;
import com.example.kindle.sm.State;

public class TurnState extends State
{
	public TurnState(IPlayer player, IGameContext context, final String name)
	{
		super(name);
		this.player = player;
		this.context = context;
	}
	
	public void enter()
	{
		ITurn turn = this.player.think(context);
	}
	
	public IPlayer getPlayer()
	{
		return this.player;
	}

	private IGameContext context;
	private IPlayer player;
}
