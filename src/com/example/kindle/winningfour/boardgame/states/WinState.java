package com.example.kindle.winningfour.boardgame.states;

import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.sm.State;

public class WinState extends State
{
	public WinState(IPlayer winner, final String name)
	{
		super(name);
		this.winner = winner;
	}
	
	public IPlayer getWinner()
	{
		return this.winner;
	}
	
	private IPlayer winner;
}
