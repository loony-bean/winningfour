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
	
	/**
	 * Returns type of the piece on board. Piece type is 
	 * a special value that is associated with each piece 
	 * and can can tell if it is selected, put recently, 
	 * is part of a winning set, etc.
	 * 
	 * @return Type of the board piece.
	 */
	public int getType();
}
