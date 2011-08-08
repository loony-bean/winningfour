package com.example.kindle.boardgame;

import java.util.ArrayList;

public interface ITurnValidator
{
	public IPlayer getPlayer();
	public ArrayList getAvailableTurns();
	public ArrayList getAvailableTurns(IPiece piece);
	public boolean isTurnAvailable(ITurn turn);
}
