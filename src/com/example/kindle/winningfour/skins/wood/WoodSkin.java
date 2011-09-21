package com.example.kindle.winningfour.skins.wood;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.example.kindle.boardgame.IPiece;
import com.example.kindle.boardgame.IPosition2D;
import com.example.kindle.winningfour.boardgame.BoardItem;
import com.example.kindle.winningfour.skins.BaseSkin;
import com.example.kindle.winningfour.skins.BoardLayout;

public class WoodSkin extends BaseSkin
{
	public WoodSkin(final Dimension boardSize)
	{
		super("wood", boardSize);
	}

	public BoardLayout getLayout(final Dimension size)
	{
		BoardLayout l = this.createLayout(size);

		l.pieceGapY = -3;
		l.boardRect.y += l.boardRect.height - (l.pieceSizeY + l.pieceGapY) * boardSize.height;
		l.pieceGapX += 1;

		return l;
	}

	public void paintBoard(final Graphics g, final Component parent)
	{
		Dimension size = parent.getSize();
		BoardLayout l = this.getLayout(size);
		
		double hfactor = size.height / BaseSkin.DESIGN_HEIGHT;

		for (int i = 0; i < this.boardSize.width + 1; i++)
		{
			// TODO: refactor
			int recty = this.asInt(this.boardLayout, "y");
			int recth = this.asInt(this.boardLayout, "height");

			int sx = l.pieceSizeX;
			int gap = l.pieceGapX;
			int x = (int) (l.boardRect.x + i*(sx+gap)+3);
			int ry = (int) (recth * hfactor);
			int y = (int) (recty * hfactor);
			int rx = 2*sx/3;

			this.paintImage(g, parent, "stick.png", new Rectangle(x-gap-rx+rx/2, y, rx, ry));
		}
	}

	public void paintBoardItem(final Graphics g, final Component parent, final BoardItem item)
	{
		super.paintBoardItem(g, parent, item);
	}
}

