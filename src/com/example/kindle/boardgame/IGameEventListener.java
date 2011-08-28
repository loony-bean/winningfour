package com.example.kindle.boardgame;

/**
 * Game events listener for board game. Game event basically occur
 * when someone wins or looses a game, or the game is drawn.
 */
public interface IGameEventListener
{
	/**
	 * Method that will be invoked after a game event occur.
	 * 
	 * @param event Game event id.
	 */
	public void onGameEvent(int event);
}
