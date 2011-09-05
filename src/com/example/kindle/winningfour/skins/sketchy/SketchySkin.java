package com.example.kindle.winningfour.skins.sketchy;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import com.example.kindle.winningfour.boardgame.BoardItem;
import com.example.kindle.winningfour.skins.BaseSkin;
import com.example.kindle.winningfour.skins.BoardLayout;

public class SketchySkin extends BaseSkin
{
	public SketchySkin(Dimension boardSize)
	{
		super(boardSize);
	}

	public BoardLayout getLayout(Dimension size)
	{
		BoardLayout result = new BoardLayout();

		result.boardLeftTopX = (int) (this.boardLeftTopX * size.width);
		result.boardLeftTopY = (int) (this.boardLeftTopY * size.height);
		result.selectorY = (int) (this.selectorY * size.height);

		int cols = this.boardSize.width;
		result.pieceSizeX = (int) ((size.width - 2*result.boardLeftTopX - this.pieceGapX*(cols-1)) / cols);
		result.pieceSizeY = result.pieceSizeX;

		result.pieceGapX = this.pieceGapX;
		result.pieceGapY = this.pieceGapY;

		return result;
	}

	public void paintBoard(Graphics g, Component parent)
	{
		super.paintBoard(g, parent);
	}

	public void paintBoardItem(Graphics g, Component parent, BoardItem item)
	{
		super.paintBoardItem(g, parent, item);
	}

	public String getName()
	{
		return "sketchy";
	}

	private double selectorY = 580.0/760.0;
	private double boardLeftTopX = 90.0/600.0;
	private double boardLeftTopY = 130.0/760.0;

	private int pieceGapX = 8;
	private int pieceGapY = 4;
}
