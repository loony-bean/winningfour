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

	public void adjust(int incx, int incy)
	{
		this.x += incx;
		this.y += incy;
	}

	private int x;
	private int y;
}
