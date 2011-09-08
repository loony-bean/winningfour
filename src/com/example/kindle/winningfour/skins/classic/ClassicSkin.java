package com.example.kindle.winningfour.skins.classic;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.example.kindle.winningfour.boardgame.BoardItem;
import com.example.kindle.winningfour.skins.BaseSkin;
import com.example.kindle.winningfour.skins.BoardLayout;

public class ClassicSkin extends BaseSkin
{
	public ClassicSkin(final Dimension boardSize)
	{
		super(boardSize);
		this.boardRect = new Rectangle(100, 226, 400, 345);
	}

	public BoardLayout getLayout(final Dimension size)
	{
		return this.createLayout(size, this.boardRect, this.selectorY, this.pieceGapX);
	}

	public void paintBoard(final Graphics g, final Component parent)
	{
		this.paintHoles(g, parent);
	}

	public void paintBoardItem(final Graphics g, final Component parent, final BoardItem item)
	{
		super.paintBoardItem(g, parent, item);
	}

	public String getName()
	{
		return "classic";
	}

	// fraction calculated from 600 x 780 factor
	private int selectorY = 580;
	private Rectangle boardRect;

	// pixels
	private int pieceGapX = 4;
}
