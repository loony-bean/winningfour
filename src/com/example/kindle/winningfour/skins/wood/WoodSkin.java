package com.example.kindle.winningfour.skins.wood;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import com.example.kindle.winningfour.AppResources;
import com.example.kindle.winningfour.boardgame.BoardItem;
import com.example.kindle.winningfour.skins.BoardLayout;
import com.example.kindle.winningfour.skins.ISkin;

public class WoodSkin implements ISkin
{
	public WoodSkin(Dimension boardSize)
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
		result.pieceSizeY = result.pieceSizeX;

		result.pieceGapX = this.pieceGapX;
		result.pieceGapY = this.pieceGapY;

		return result;
	}

	public void paintBoard(Graphics g, Component parent)
	{
		Dimension size = parent.getSize();
		BoardLayout l = this.getLayout(size);

		for (int i = 0; i < this.boardSize.width + 1; i++)
		{
				int sx = l.pieceSizeX;
				int gap = l.pieceGapX;
				int x = (int) (l.boardLeftTopX + i*(sx+gap));
				int ry = (this.boardSize.height) * l.pieceSizeY;
				int y = (int) (this.boardPlaneY * size.height) - ry;
				int rx = 2*sx/3;

				Image image = AppResources.getImage("stick.png", parent, rx, ry);
				g.drawImage(image, x-gap-rx+rx/2, y, null);
		}
	}

	public void paintBoardItem(final Graphics g, final Component parent, final BoardItem item)
	{
		Dimension size = parent.getSize();
		BoardLayout l = this.getLayout(size);
		String id = null;

		int overlap = l.pieceSizeY/8;
		int oy = (overlap - 1) * this.boardSize.height;

		int y = (int) (this.boardPlaneY * size.height) - (this.boardSize.height) * l.pieceSizeY;
		y += item.getPosition().col()*(l.pieceSizeY + l.pieceGapY - overlap) + oy;
		int x = l.boardLeftTopX + item.getPosition().row()*(l.pieceSizeX + l.pieceGapX);

		if (item.getPiece() != null)
		{
			Color color = item.getPiece().getPlayer().getColor();
			id = (color == Color.black) ? "black.png" : "white.png";

			Image image = AppResources.getImage(id, parent, l.pieceSizeX, l.pieceSizeY);

			g.drawImage(image, x, y, null);
		}
	}

	public String getName()
	{
		return "wood";
	}

	private double selectorY = 600.0/760.0;
	private double boardLeftTopX = 100.0/600.0;
	private double boardLeftTopY = 220.0/760.0;
	private double boardPlaneY = 500.0/760.0;

	private int pieceGapX = 4;
	private int pieceGapY = 2;

	private Dimension boardSize;
}