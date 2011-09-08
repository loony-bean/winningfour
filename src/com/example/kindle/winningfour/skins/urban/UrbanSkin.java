package com.example.kindle.winningfour.skins.urban;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import com.example.kindle.winningfour.boardgame.BoardItem;
import com.example.kindle.winningfour.skins.BaseSkin;
import com.example.kindle.winningfour.skins.BoardLayout;

public class UrbanSkin extends BaseSkin
{
	public UrbanSkin(final Dimension boardSize)
	{
		super("urban", boardSize);
	}

	public BoardLayout getLayout(final Dimension size)
	{
		return this.createLayout(size);
	}

	public void paintBoard(final Graphics g, final Component parent)
	{
		super.paintBoard(g, parent);
	}

	public void paintBoardItem(final Graphics g, final Component parent, final BoardItem item)
	{
		super.paintBoardItem(g, parent, item);
	}
}
