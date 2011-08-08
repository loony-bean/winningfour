package com.example.kindle.boardgame;

public class Position2D implements IPosition2D
{
	public Position2D (int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public int x()
	{
		return this.x;
	}

	public int y()
	{
		return this.y;
	}

	private int x;
	private int y;
}
