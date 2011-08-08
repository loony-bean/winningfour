package com.example.kindle.boardgame;

import java.awt.Color;

public interface IPlayer
{
	public Color getColor();
	public ITurn think(IGameContext gameContext);
	public void interrupt();
}
