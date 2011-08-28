package com.example.kindle.boardgame;

/**
 * Basic implementation of position on two square dimensional board.
 */
public class Position2D implements IPosition2D
{
	/**
	 * Position constructor given row and column numbers (starting from 0).
	 * 
	 * @param row Row number.
	 * @param col Column number.
	 */
	public Position2D(int row, int col)
	{
		this.row = row;
		this.col = col;
	}

	/** {@inheritDoc} */
	public int row()
	{
		return this.row;
	}

	/** {@inheritDoc} */
	public int col()
	{
		return this.col;
	}

	/** {@inheritDoc} */
	public Object clone()
	{
		return new Position2D(this.row, this.col);
	}

	/** Row number on board (from 0). */
	private int row;

	/** Column number on board (from 0). */
	private int col;
}
