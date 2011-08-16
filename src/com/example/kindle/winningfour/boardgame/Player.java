package com.example.kindle.winningfour.boardgame;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.example.kindle.boardgame.IPlayer;

public class Player implements IPlayer
{
	public Player(final Color color, final String name)
	{
		this.color = color;
		this.name = name;
		
		this.keyAdapter = new KeyAdapter()
		{
			public void keyPressed(KeyEvent event)
			{
				Player.this.onKeyboard(event);
			}
		};
	}
	
	public void destroy()
	{
		if (this.keyAdapter != null)
		{
			this.keyAdapter = null;
		}
	}

	public Color getColor()
	{
		return this.color;
	}

	public String getName()
	{
		return this.name;
	}
	
	public KeyAdapter getKeyAdapter()
	{
		return this.keyAdapter;
	}

	protected void onKeyboard(KeyEvent event)
	{
	}

	public void interrupt()
	{
	}

	public void think()
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
			return rhs.getName().equals(lhs.getName());
		}

		return false;
	}

	private KeyAdapter keyAdapter;
	private Color color;
	private String name;
}
