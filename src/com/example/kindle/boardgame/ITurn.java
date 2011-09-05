package com.example.kindle.boardgame;

/**
 * Board game turn.
 * Currently only turns that add (not move or eat) pieces are considered. 
 */
public interface ITurn
{
	/**
	 * Returns piece that is set.
	 * 
	 * @return Piece that's been placed.
	 */
	public IPiece getPiece();

	/**
	 * Returns position of the placed piece.
	 * 
	 * @return Position of the placed piece on board.
	 */
	public IPosition2D getPosition();
	
	/** {@inheritDoc} */
	public String toString();
}
