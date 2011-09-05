package com.example.kindle.boardgame;

/**
 * Game state listener for board game. Two states are
 * considered: started and stopped.
 */
public interface IGameStateListener
{
	/**
	 * Method that will be invoked after a game is started.
	 */
	public void onStart();

	/**
	 * Method that will be invoked after a game is stopped.
	 */
	public void onStop();
}
