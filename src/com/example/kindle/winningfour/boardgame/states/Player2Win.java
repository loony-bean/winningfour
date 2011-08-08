package com.example.kindle.winningfour.boardgame.states;

import java.awt.Color;

import com.example.kindle.winningfour.boardgame.HumanPlayer;

public class Player2Win extends WinState
{
	public Player2Win()
	{
		super(new HumanPlayer(Color.blue), "Player2Win");
	}
}
