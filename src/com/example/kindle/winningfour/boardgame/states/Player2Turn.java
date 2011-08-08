package com.example.kindle.winningfour.boardgame.states;

import java.awt.Color;

import com.example.kindle.winningfour.boardgame.HumanPlayer;

public class Player2Turn extends TurnState
{
	public Player2Turn()
	{
		super(new HumanPlayer(Color.blue), null, "Player2Turn");
	}
}
