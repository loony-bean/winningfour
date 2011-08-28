package com.example.kindle.boardgame;

import java.util.ArrayList;

/**
 * Two dimensional square board game.
 */
public interface IBoard2D
{
	/**
	 * Makes a turn. Acts like putting a players piece
	 * on some position on the board.
	 * 
	 * @param turn Turn to make.
	 */
	public void turn(ITurn turn);

	/**
	 * Undoes last move.
	 */
	public void undo();

	/**
	 * Returns board width as number of rows.
	 * 
	 * @return Number of rows.
	 */
	public int getWidth();

	/**
	 * Returns board height as number of columns.
	 * 
	 * @return Number of columns.
	 */
	public int getHeight();


	/**
	 * Returns last made turn on the board.
	 * 
	 * @return Last turn.
	 */
	public ITurn getLastTurn();

	/**
	 * Returns total amount of turns made on the board by all the players.
	 * 
	 * @return Number of turns made.
	 */
	public int getTurnsCount();


	/**
	 * Checks if the position given is valid for the current size board.
	 * 
	 * @param position Position to check.
	 * 
	 * @return True is the position is situated on the board, false otherwise.
	 */
	public boolean isPositionOnBoard(IPosition2D position);

	/**
	 * Sets a piece into position given.
	 * 
	 * @param piece Piece to set.
	 * @param position Position for the piece.
	 */
	public void setPiece(IPiece piece, IPosition2D position);
	
	/**
	 * Returns a piece present on the position given.
	 * 
	 * @param position Position to check.
	 * 
	 * @return Piece found on the position. This can be null.
	 */
	public IPiece getPiece(IPosition2D position);
	
	/**
	 * Returns an unordered list of board items, each presenting
	 * a piece and its position, which can be found on current board.
	 * 
	 * @return Unordered list of board items.
	 */
	public ArrayList getItems();
	
	/**
	 * Makes a clone of the board.
	 * 
	 * @return Cloned board.
	 */
	public Object clone();
	
	/**
	 * Returns current board hash code presentation. It is used for 
	 * differing boards with different pieces arrangement.
	 * 
	 * @return Hash code uniquely representing current pieces
	 * arrangement on the board.
	 */
	public int hashCode();
	
	/**
	 * Board destruction.
	 */
	public void destroy();
}
