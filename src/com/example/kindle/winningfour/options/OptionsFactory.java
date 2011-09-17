package com.example.kindle.winningfour.options;

import java.awt.Color;
import java.awt.Dimension;

import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.boardgame.ComputerPlayer;
import com.example.kindle.winningfour.boardgame.HumanPlayer;
import com.example.kindle.winningfour.skins.ISkin;
import com.example.kindle.winningfour.skins.classic.ClassicSkin;
import com.example.kindle.winningfour.skins.sketchy.SketchySkin;
import com.example.kindle.winningfour.skins.urban.UrbanSkin;
import com.example.kindle.winningfour.skins.wood.WoodSkin;

/**
 * Creates different game objects using current options state.
 */
public class OptionsFactory
{
	/**
	 * Returns current board dimensions as defined in options.
	 * 
	 * @return Board size (rows and columns number).
	 */
	public Dimension createBoardSize()
	{
		Dimension result = null;
		String key = (String) App.opts.get(AppOptions.OP_T_BOARD_SIZE);
		
		if (key.equals(AppOptions.OP_V_7X6))
		{
			result = new Dimension(7, 6);
		}
		else if (key.equals(AppOptions.OP_V_8X7))
		{
			result = new Dimension(8, 7);
		}
		else if (key.equals(AppOptions.OP_V_9X7))
		{
			result = new Dimension(9, 7);
		}
		else if (key.equals(AppOptions.OP_V_10X7))
		{
			result = new Dimension(10, 7);
		}
		
		return result;
	}

	/**
	 * Returns players array in game order. Uses opponent type and who turns first options.
	 * 
	 * @return Array of players in which players are making turn ordered
	 * by their position in the array. Closer to the beginning make turn first. 
	 */
	public IPlayer[] createPlayers()
	{
		IPlayer[] players = new IPlayer[2];
		
		String firstTurn = (String) App.opts.get(AppOptions.OP_T_FIRST_TURN);
		String opponent = (String) App.opts.get(AppOptions.OP_T_OPPONENT);

		if (opponent.equals(AppOptions.OP_V_COMPUTER))
		{
			if (firstTurn.equals(AppOptions.OP_V_YOU))
			{
				players[0] = new HumanPlayer(Color.white);
				players[1] = new ComputerPlayer(Color.black);;
			}
			else
			{
				players[0] = new ComputerPlayer(Color.white);;
				players[1] = new HumanPlayer(Color.black);
			}
		}
		else if (opponent.equals(AppOptions.OP_V_HUMAN))
		{
			players[0] = new HumanPlayer(Color.white);;
			players[1] = new HumanPlayer(Color.black);
		}

		return players; 
	}

	/**
	 * Returns current skin as set in options.
	 * 
	 * @return Graphical skin definition object.
	 */
	public ISkin createSkin()
	{
		ISkin result = null;
		String key = (String) App.opts.get(AppOptions.OP_T_SKIN);
		Dimension bs = this.createBoardSize();
		
		if (key.equals(AppOptions.OP_V_CLASSIC))
		{
			result = (ISkin) new WoodSkin(bs);
		}
		else if (key.equals(AppOptions.OP_V_SKETCHY))
		{
			result = (ISkin) new SketchySkin(bs);
		}
		else if (key.equals(AppOptions.OP_V_CHEESE))
		{
			result = (ISkin) new ClassicSkin(bs);
		}
		else if (key.equals(AppOptions.OP_V_URBAN))
		{
			result = (ISkin) new UrbanSkin(bs);
		}

		return result;
	}
	
	/**
	 * Returns current game timer timeout as set in options.
	 * 
	 * @return Timeout value for the game clock.
	 */
	public int createGameClockTimeout()
	{
		int timeout = 0;
		
		String key = (String) App.opts.get(AppOptions.OP_T_TIMER);
		if (key.equals(AppOptions.OP_V_OFF))
		{
		}
		else if (key.equals(AppOptions.OP_V_10SEC))
		{
			timeout = 10;
		}
		else if (key.equals(AppOptions.OP_V_15SEC))
		{
			timeout = 15;
		}
		else if (key.equals(AppOptions.OP_V_30SEC))
		{
			timeout = 30;
		}
		
		return timeout;
	}
}
