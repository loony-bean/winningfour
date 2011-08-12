package com.example.kindle.winningfour.boardgame.states;

import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.winningfour.boardgame.GameController;

public class Player1Turn extends TurnState
{
	public Player1Turn(GameController game, IPlayer player)
	{
		super(game, player, "Player1Turn");
	}
}
