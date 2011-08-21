package com.example.kindle.boardgame;

import java.awt.Color;
import java.awt.event.KeyAdapter;

public interface IPlayer
{
	public Color getColor();
	public String getName();
	public void interrupt();
	public void think();
	public KeyAdapter getKeyAdapter();
	public boolean equals(Object other);
	public void destroy();
}
