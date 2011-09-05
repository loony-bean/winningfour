package com.example.kindle.boardgame;

/**
 * A piece that has it's position on two dimensional square board.
 */
public interface IBoard2DItem
{
	/**
	 * Returns piece that is set.
	 * 
	 * @return Piece on game board.
	 */
	public IPiece getPiece();

	/**
	 * Returns position of the piece on board.
	 * 
	 * @return Piece's position on game board.
	 */
	public IPosition2D getPosition();
}
