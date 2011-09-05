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
		return null;
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
			
			this.paintImage(g, parent, id, rect);
		}
	}

	protected void paintImage(final Graphics g, final Component parent, String id, final Rectangle rect)
	{
		Image image = AppResources.getImage(id, parent, rect.width, rect.height);
		g.drawImage(image, rect.x, rect.y, null);
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
				
				this.paintImage(g, parent, "hole.png", new Rectangle(x, y, s, s));
			}
		}
	}

	// number or rows and columns
	protected Dimension boardSize;
}
