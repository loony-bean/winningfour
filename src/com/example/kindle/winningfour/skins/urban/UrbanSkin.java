package com.example.kindle.winningfour.skins.urban;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.example.kindle.winningfour.boardgame.BoardItem;
import com.example.kindle.winningfour.skins.BaseSkin;
import com.example.kindle.winningfour.skins.BoardLayout;

public class UrbanSkin extends BaseSkin
{
	public UrbanSkin(final Dimension boardSize)
	{
		super(boardSize);
		this.boardRect = new Rectangle(28, 117, 546, 518);
	}

	public BoardLayout getLayout(final Dimension size)
	{
		return this.createLayout(size, this.boardRect, this.selectorY, this.pieceGapX);
	}

	public void paintBoard(final Graphics g, final Component parent)
	{
		super.paintBoard(g, parent);
	}

	public void paintBoardItem(final Graphics g, final Component parent, final BoardItem item)
	{
		super.paintBoardItem(g, parent, item);
	}

	public String getName()
	{
		return "urban";
	}

	private int selectorY = 660;
	private Rectangle boardRect;

	private int pieceGapX = 4;
}
