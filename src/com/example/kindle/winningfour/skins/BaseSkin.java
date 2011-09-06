package com.example.kindle.winningfour.skins;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import com.example.kindle.boardgame.IPiece;
import com.example.kindle.boardgame.IPosition2D;
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

		IPiece piece = item.getPiece();
		if (piece != null)
		{
			Color color = piece.getPlayer().getColor();
			String id = (color == Color.black) ? "black.png" : "white.png";
			IPosition2D pos = item.getPosition();

			this.paintImageToGrid(g, parent, id, l, pos.row(), pos.col());
		}
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

	protected void paintImageToGrid(final Graphics g, final Component parent, String id,
			final BoardLayout l, int row, int col)
	{
		int x = (int) (l.boardLeftTopX + row*(l.pieceSizeX+l.pieceGapX));
		int y = (int) (l.boardLeftTopY + col*(l.pieceSizeY+l.pieceGapY));
		
		this.paintImage(g, parent, id, new Rectangle(x, y, l.pieceSizeX, l.pieceSizeY));
	}

	public void paintHoles(final Graphics g, final Component parent)
	{
		BoardLayout l = this.getLayout(parent.getSize());
	
		for (int row = 0; row < this.boardSize.width; row++)
		{
			for (int col = 0; col < this.boardSize.height; col++)
			{
				this.paintImageToGrid(g, parent, "hole.png", l, row, col);
			}
		}
	}

	/** {@inheritDoc} */
	public boolean equals(Object other)
	{
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;

		BaseSkin otherSkin = (BaseSkin) other;
		return this.getName().equals(otherSkin.getName());
	}

	// number or rows and columns
	protected Dimension boardSize;
}
