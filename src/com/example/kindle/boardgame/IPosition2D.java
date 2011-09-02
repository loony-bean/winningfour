package com.example.kindle.boardgame;

/**
 * Position on two square dimensional board.
 */
public interface IPosition2D
{
	/**
	 * Returns row number starting from zero.
	 * 
	 * @return Row number.
	 */
	public int row();

	/**
	 * Returns column number starting from zero.
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
