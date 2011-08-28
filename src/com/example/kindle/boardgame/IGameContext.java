package com.example.kindle.boardgame;

/**
 * Board game context. In context everything necessary
 * for players to make a move is stored.
 */
public interface IGameContext
{
	/**
	 * Returns board with current pieces arrangement.
	 * 
	 * @return Current board.
	 */
	public IBoard2D getBoard();

	/**
	 * Returns actual playing rules.
	 * 
	 * @return Current rules.
	 */
	public IRules getRules();

	/**
	 * Returns array of competing players.
	 * 
	 * @return Current players.
	 */
	public IPlayer[] getPlayers();
}
