package com.example.kindle.winningfour.boardgame;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.example.kindle.boardgame.IGameContext;
import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.winningfour.App;

public class Player implements IPlayer
{
	public Player(final Color color)
	{
		this.color = color;
		
		this.keyAdapter = new KeyAdapter()
		{
			public void keyPressed(KeyEvent event)
			{
				Player.this.onKeyboard(event);
			}
		};
	}
	
	public Color getColor()
	{
		return this.color;
	}

	public KeyAdapter getKeyAdapter()
	{
		return this.keyAdapter;
	}

	protected void onKeyboard(final KeyEvent event)
	{
	}

	public void interrupt() throws InterruptedException
	{
	}

	public void think(final IGameContext context)
	{
	}

	public boolean equals(Object other)
	{
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;

		if (getClass() == IPlayer.class)
		{
			IPlayer lhs = (IPlayer) this;
			IPlayer rhs = (IPlayer) other;
			return rhs.getColor().equals(lhs.getColor());
		}

		return false;
	}

	public void destroy()
	{
		App.log("Player::destroy");

		this.keyAdapter = null;

		App.log("Player::destroy done");
	}

	private KeyAdapter keyAdapter;
	private final Color color;
}
