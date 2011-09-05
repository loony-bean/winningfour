package com.example.kindle.winningfour.skins;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import com.example.kindle.boardgame.IPiece;
import com.example.kindle.winningfour.AppResources;
import com.example.kindle.winningfour.boardgame.BoardItem;

public class BaseSkin implements ISkin
{
	public BaseSkin(Dimension boardSize)
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
		result.pieceSizeX = (int) ((size.width - 2*result.boardLeftTopX - this.pieceGapX*(cols-1)) / cols);
		result.pieceSizeY = result.pieceSizeX;

		result.pieceGapX = this.pieceGapX;
		result.pieceGapY = this.pieceGapY;

		return result;
	}

	public void paintBoard(final Graphics g, final Component parent)
	{
		this.paintHoles(g, parent);
	}

	public void paintBoardItem(final Graphics g, final Component parent, final BoardItem item)
	{
		BoardLayout l = this.getLayout(parent.getSize());
	
		int x = l.boardLeftTopX + item.getPosition().row()*(l.pieceSizeX+l.pieceGapX);
		int y = l.boardLeftTopY + item.getPosition().col()*(l.pieceSizeY+l.pieceGapX);
	
		this.paintPiece(g, parent, item.getPiece(), new Rectangle(x, y, l.pieceSizeX, l.pieceSizeY));
	}

	public String getName()
	{
		return "base";
	}

	protected void paintPiece(final Graphics g, final Component parent, final IPiece piece, final Rectangle rect)
	{
		if (piece != null)
		{
			Color color = piece.getPlayer().getColor();
			String id = (color == Color.black) ? "black.png" : "white.png";
			Image image = AppResources.getImage(id, parent, rect.width, rect.height);
			g.drawImage(image, rect.x, rect.y, null);
		}
	}

	public void paintHoles(final Graphics g, final Component parent)
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

	// percents calculated from 600 x 780 factor
	private double selectorY = 580.0/760.0;
	private double boardLeftTopX = 100.0/600.0;
	private double boardLeftTopY = 220.0/760.0;
	
	// pixels
	private int pieceGapX = 4;
	private int pieceGapY = 4;
	
	// number or rows and columns
	private Dimension boardSize;
}
