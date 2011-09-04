package com.example.kindle.winningfour.skins.classic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import com.example.kindle.winningfour.AppResources;
import com.example.kindle.winningfour.boardgame.BoardItem;
import com.example.kindle.winningfour.skins.BoardLayout;
import com.example.kindle.winningfour.skins.ISkin;

public class ClassicSkin implements ISkin
{
	public ClassicSkin(Dimension boardSize)
	{
		this.boardSize = boardSize;
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
		BoardLayout layout = this.getLayout(parent.getSize());

		for (int i = 0; i < this.boardSize.width; i++)
		{
			for (int j = 0; j < this.boardSize.height; j++)
			{
				int s = layout.pieceSizeX;
				int gap = layout.pieceGapX;
				int x = (int) (layout.boardLeftTopX + i*(s+gap));
				int y = (int) (layout.boardLeftTopY + j*(s+gap));
				
				Image image = AppResources.getImage("hole.png", parent, s, s);
				g.drawImage(image, x, y, null);
			}
		}
	}

	public void paintBoardItem(final Graphics g, final Component parent, final BoardItem item)
	{
		BoardLayout l = this.getLayout(parent.getSize());
		String id = null;

		int x = l.boardLeftTopX + item.getPosition().row()*(l.pieceSizeX+l.pieceGapX);
		int y = l.boardLeftTopY + item.getPosition().col()*(l.pieceSizeY+l.pieceGapX);

		if (item.getPiece() != null)
		{
			Color color = item.getPiece().getPlayer().getColor();
			id = (color == Color.black) ? "black.png" : "white.png";

			Image image = AppResources.getImage(id, parent, l.pieceSizeX, l.pieceSizeX);
			
			g.drawImage(image, x, y, null);
		}
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
	
	// number or rows and columns
	private Dimension boardSize;
}
