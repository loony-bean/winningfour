package com.example.kindle.winningfour.boardgame.states;

import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.winningfour.boardgame.GameController;

public class TurnState extends GameState
{
	public TurnState(GameController game, IPlayer player, final String name)
	{
		super(game, player, name);
		this.status = "" + this.getPlayer().getName() + "'s turn";
		this.keyAdapter = this.getPlayer().getKeyAdapter();
	}
}
