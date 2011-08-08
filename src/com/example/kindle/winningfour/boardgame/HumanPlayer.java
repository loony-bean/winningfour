package com.example.kindle.winningfour.boardgame;

import java.awt.Color;

import com.example.kindle.boardgame.IGameContext;
import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.boardgame.ITurn;

public class HumanPlayer implements IPlayer
{
	public HumanPlayer(Color color)
	{
		this.color = color;
	}

	public Color getColor()
	{
		return this.color;
	}

	public void interrupt()
	{
		// TODO Auto-generated method stub
	}

	public ITurn think(IGameContext gameContext)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private Color color;
}
