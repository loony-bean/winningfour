package com.example.kindle.winningfour.boardgame.states;

import java.awt.Color;

import com.example.kindle.winningfour.boardgame.HumanPlayer;

public class Player1Turn extends TurnState
{
	public Player1Turn()
	{
		super(new HumanPlayer(Color.yellow), null, "Player1Turn");
	}
}
