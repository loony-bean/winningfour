package com.example.kindle.boardgame;

/**
 * Basic implementation of position on two square dimensional board.
 */
public class Position2D implements IPosition2D
{
	/**
	 * Position constructor given row and column numbers (starting from zero).
	 * 
	 * @param x Column number.
	 * @param y Row number.
	 */
	public Position2D(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/** {@inheritDoc} */
	public int x()
	{
		return this.x;
	}

	/** {@inheritDoc} */
	public int y()
	{
		return this.y;
	}

	/** {@inheritDoc} */
	public Object clone()
	{
		return new Position2D(this.x, this.y);
	}

	/** {@inheritDoc} */
	public boolean equals(Object other)
	{
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;

		Position2D otherPosition = (Position2D) other;
		return (this.x == otherPosition.x && this.y == otherPosition.y);
	}

	/** Column number on board. */
	private int x;

	/** Row number on board. */
	private int y;
}
