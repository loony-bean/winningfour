package com.example.kindle.boardgame;

public interface IPosition2D
{
	public int x();
	public int y();
	public void adjust(int incx, int incy);
	public Object clone();
}
