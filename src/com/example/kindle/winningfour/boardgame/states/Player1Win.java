package com.example.kindle.winningfour.boardgame.states;

import java.awt.Color;

import com.example.kindle.winningfour.boardgame.HumanPlayer;

public class Player1Win extends WinState
{
	public Player1Win()
	{
		super(new HumanPlayer(Color.yellow), "Player1Win");
	}
}
