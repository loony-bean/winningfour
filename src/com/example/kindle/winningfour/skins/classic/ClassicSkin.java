package com.example.kindle.winningfour.skins.classic;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import com.example.kindle.winningfour.boardgame.BoardItem;
import com.example.kindle.winningfour.skins.BaseSkin;
import com.example.kindle.winningfour.skins.BoardLayout;

public class ClassicSkin extends BaseSkin
{
	public ClassicSkin(final Dimension boardSize)
	{
		super(boardSize);
	}

	public BoardLayout getLayout(final Dimension size)
	{
		BoardLayout result = new BoardLayout();

		result.boardLeftTopX = (int) (this.boardLeftTopX * size.width);
		result.boardLeftTopY = (int) (this.boardLeftTopY * size.height);
		result.selectorY = (int) (this.selectorY * size.height);

		int cols = this.boardSize.width;
		result.pieceSizeX = (int) ((size.width - 2*result.boardLeftTopX - this.pieceGap*(cols-1)) / cols);
		result.pieceSizeY = result.pieceSizeX;

		result.pieceGapX = this.pieceGap;
		result.pieceGapY = this.pieceGap;

		return result;
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
		return "classic";
	}

	// percents calculated from 600 x 780 factor
	private double selectorY = 580.0/760.0;
	private double boardLeftTopX = 100.0/600.0;
	private double boardLeftTopY = 220.0/760.0;

	// pixels
	private int pieceGap = 4;
}
