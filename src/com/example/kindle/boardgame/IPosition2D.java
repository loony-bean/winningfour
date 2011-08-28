package com.example.kindle.boardgame;

/**
 * Position on two square dimensional board.
 */
public interface IPosition2D
{
	/**
	 * Returns row number starting from 0.
	 * 
	 * @return Row number.
	 */
	public int row();

	/**
	 * Returns column number starting from 0.
	 * 
	 * @return Column number.
	 */
	public int col();

	/**
	 * Makes a clone of the position.
	 * 
	 * @return Cloned position.
	 */
	public Object clone();
}
