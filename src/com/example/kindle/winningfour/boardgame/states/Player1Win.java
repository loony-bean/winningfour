package com.example.kindle.winningfour.boardgame.states;

import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.winningfour.boardgame.GameController;

public class Player1Win extends WinState
{
	public Player1Win(GameController game, IPlayer player)
	{
		super(game, player, "Player1Win");
	}
}
