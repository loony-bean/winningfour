package com.example.kindle.boardgame;

/**
 * Board game interface.
 */
public interface IGame
{
	/**
	 * Starts the game.
	 */
	public void start();

	/**
	 * Stops the game.
	 */
	public void stop();

	/**
	 * Resets the game. Basically this method recreates gaming
	 * environment: board, players, rules, listeners, etc.
	 */
	public void reset();

	/**
	 * Returns game context. Game context encapsulates everything
	 * a player needs to know to make a turn.
	 * 
	 * @return Current game context.
	 */

	public IGameContext getContext();

	/**
	 * Invokes all the steps that are considered making a turn. Like
	 * validating the moves, moving the pieces, checking game conditions
	 * afterwards, etc.
	 * 
	 * @param turn Turn to make.
	 */
	public void makeTurn(ITurn turn);

	/**
	 * Game destruction.
	 */
	public void destroy();
}
