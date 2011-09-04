package com.example.kindle.winningfour.skins;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import com.example.kindle.winningfour.boardgame.BoardItem;

public interface ISkin
{
	public BoardLayout getLayout(final Dimension boardSize);
	public void paintBoard(final Graphics g, final Component parent);
	public void paintBoardItem(final Graphics g, final Component parent, final BoardItem item);
	public String getName();
}

