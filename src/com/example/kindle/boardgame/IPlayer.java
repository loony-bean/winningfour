package com.example.kindle.boardgame;

import java.awt.Color;
import java.awt.event.KeyAdapter;

/**
 * Player of a board game.
 */
public interface IPlayer
{
	/**
	 * Returns color of the player. Usually in two-player board games
	 * it is common that the player that has the first turn plays
	 * 'whites' and the other player plays 'blacks' but different 
	 * games can have different traditions and thus color coding.
	 * 
	 * @return Color of the player's pieces.
	 */
	public Color getColor();

	/**
	 * This method should be called when it is time for player
	 * to think on game and make it's move. Player is provided
	 * with game context, that encapsulates all the game information
	 * that is accessible for the player at the moment.
	 * 
	 * For computer player this method usually starts a thread.
	 * 
	 * @param context Current game context.
	 */
	public void think(final IGameContext context);

	/**
	 * Call this method to interrupt currently 'thinking' player.
	 * 
	 * @throws InterruptedException Can throw exception.
	 */
	public void interrupt() throws InterruptedException;
	
	/**
	 * Returns keyboard adapter that will be used by GUI while
	 * the player is thinking about his next turn.
	 * 
	 * @return Keyboard adapter.
	 */
	public KeyAdapter getKeyAdapter();

	/** {@inheritDoc} */
	public boolean equals(Object other);

	/**
	 * Player destruction.
	 */
	public void destroy();
}
