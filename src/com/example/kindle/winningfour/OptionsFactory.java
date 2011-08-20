package com.example.kindle.winningfour;

import java.awt.Color;
import java.awt.Dimension;

import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.winningfour.boardgame.ComputerPlayer;
import com.example.kindle.winningfour.boardgame.HumanPlayer;

public class OptionsFactory
{
	public IPlayer createPlayer(final String key, final Color color, final String name)
	{
		IPlayer result = null;
		if (key.equals("human"))
		{
			result = new HumanPlayer(color, name);
		}
		else if (key.equals("computer"))
		{
			result = new ComputerPlayer(color, name);
		}
		return result;
	}
	
	public Dimension createBoardSize()
	{
		Dimension result = null;
		String key = (String) App.opts.get(AppOptions.OP_T_BOARD_SIZE);
		
		if (key.equals("7x6"))
		{
			result = new Dimension(7, 6);
		}
		else if (key.equals("8x7"))
		{
			result = new Dimension(8, 7);
		}
		else if (key.equals("9x7"))
		{
			result = new Dimension(9, 7);
		}
		else if (key.equals("10x7"))
		{
			result = new Dimension(10, 7);
		}
		
		return result;
	}

	public IPlayer[] createPlayers()
	{
		IPlayer[] players = new IPlayer[2];
		
		String firstTurn = (String) App.opts.get(AppOptions.OP_T_FIRST_TURN);
		String opponent = (String) App.opts.get(AppOptions.OP_T_OPPONENT);

		IPlayer you = new HumanPlayer(Color.yellow, "Plato");
		IPlayer opp = null;

		if (opponent.equals("computer"))
		{
			opp = new ComputerPlayer(Color.blue, "Socrates");
		}
		else if (opponent.equals("human"))
		{
			opp = new HumanPlayer(Color.blue, "Socrates");
		}

		if (firstTurn.equals("you"))
		{
			players[0] = you;
			players[1] = opp;
		}
		else if (firstTurn.equals("opponent"))
		{
			players[0] = opp;
			players[1] = you;
		}
		else if (firstTurn.equals("random"))
		{
			players[0] = you;
			players[1] = opp;
		}
		
		return players; 
	}
}
