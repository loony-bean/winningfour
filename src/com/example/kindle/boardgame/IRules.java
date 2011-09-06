package com.example.kindle.boardgame;

import java.util.ArrayList;

/**
 * Board game rules.
 */
public interface IRules
{
	/**
	 * Action that will make initial pieces setup. 
	 */
	public void onSetup();

	/**
	 * Action that is played before each turn made on board.
	 * In case any game events take place appropriate listener
	 * will be notified.
	 * 
	 * @param board Current board.
	 */
	public void beforePlayerTurn(final IBoard2D board);

	/**
	 * Action that is played after each turn made on board.
	 * In case any game events take place appropriate listener
	 * will be notified.
	 * 
	 * @param board Current board.
	 */
	public void afterPlayerTurn(final IBoard2D board);

	/**
	 * Checks if current board arrangement is an end of the game.
	 * 
	 * @param board Current board.
	 * 
	 * @return True if the game on the board has ended, false otherwise.
	 */
	public boolean isEndGame(final IBoard2D board);
	
	/**
	 * Returns an numerical evaluation of the arrangement on board given.
	 * This is necessary mainly for AI computer player for making
	 * decisions about its next turns.
	 * 
	 * @param board Current board.
	 * @param depth Current lookup depth. Defines for how many plies ahead
	 * current arrangement is being considered.
	 * 
	 * @return Numerical value representing current board pieces arrangement
	 * from the current player point of view. Bigger numbers mean better
	 * game condition.
	 */
	public int evaluate(final IBoard2D board, int depth);

	/**
	 * Returns ordered list of available turns for given player.
	 * 
	 * @param board Current board.
	 * @param player Current player.
	 * 
	 * @return List of available turns for player. Turns should be
	 * ordered from best to worst using internal heuristics.
	 */
	public ArrayList getAvailableTurns(final IBoard2D board, final IPlayer player);
	
	/**
	 * Checks if the turn is available.
	 * 
	 * @param board Current board.
	 * @param turn Turn to check.
	 * 
	 * @return True if the turn is available, false otherwise.
	 */
	public boolean isTurnAvailable(final IBoard2D board, final ITurn turn);

	/**
	 * Sets game event listener. This listener will be notified
	 * if any game events (like win or loose) occur as a result
	 * of players actions.
	 * 
	 * @param gameEventListener Game event listener to set.
	 */
	public void setEventListener(final IGameEventListener gameEventListener);

	public int getMaxPlayers();
	public int getMaxWidth();
	public int getMaxHeight();
	
	/**
	 * Rules destruction.
	 */
	public void destroy();
}
