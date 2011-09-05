package com.example.kindle.winningfour.boardgame;

import com.example.kindle.boardgame.IPiece;
import com.example.kindle.boardgame.IPlayer;

public class Piece implements IPiece
{
	public Piece(final IPlayer player)
	{
		this.player = player;
	}
	
	public IPlayer getPlayer()
	{
		return player;
	}
	
	private final IPlayer player;
}
