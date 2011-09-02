package com.example.kindle.winningfour.skins.classic;

import java.awt.Dimension;

import com.example.kindle.winningfour.skins.BoardLayout;
import com.example.kindle.winningfour.skins.ISkin;

public class ClassicSkin implements ISkin
{
	public ClassicSkin(Dimension boardSize)
	{
		this.boardSize = boardSize;
	}

	public BoardLayout getLayout(Dimension size)
	{
		BoardLayout result = new BoardLayout();
		
		result.boardLeftTopX = (int) (this.boardLeftTopX * size.width);
		result.boardLeftTopY = (int) (this.boardLeftTopY * size.height);
		result.selectorY = (int) (this.selectorY * size.height);

		int rows = this.boardSize.width;
		result.pieceSizeX = (int) ((size.width - 2*result.boardLeftTopX - this.pieceGapX*(rows-1)) / rows);
		
		result.pieceGapX = this.pieceGapX;
		
		return result;
	}

	// percents calculated from 600 x 780 factor
	private double selectorY = 580.0/760.0;
	private double boardLeftTopX = 100.0/600.0;
	private double boardLeftTopY = 220.0/760.0;
	private int pieceGapX = 4;
	
	private Dimension boardSize;
}
