package com.example.kindle.boardgame;

/**
 * Board game piece.
 */
public interface IPiece
{
	/**
	 * Returns player this piece belongs to.
	 * 
	 * @return Owner player of this piece.
	 */
	public IPlayer getPlayer();
}
