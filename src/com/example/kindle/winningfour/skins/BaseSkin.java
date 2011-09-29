package com.example.kindle.winningfour.skins;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import org.json.simple.JSONObject;

import com.example.kindle.boardgame.Board2DItemType;
import com.example.kindle.boardgame.IPiece;
import com.example.kindle.boardgame.IPosition2D;
import com.example.kindle.utils.FileHelper;
import com.example.kindle.winningfour.AppResources;
import com.example.kindle.winningfour.boardgame.BoardItem;

// TODO: save images in 16 grays
public class BaseSkin implements ISkin
{
	public static final double DESIGN_WIDTH  = 600.0;
	public static final double DESIGN_HEIGHT = 760.0;

	public BaseSkin(final String name, Dimension boardSize)
	{
		this.boardSize = boardSize;
		this.boardLayout = FileHelper.json(this.getClass().getResourceAsStream("layout.json"));
		this.name = name;
	}

	public BoardLayout getLayout(final Dimension size)
	{
		return null;
	}

	public String[] getPlayerNames()
	{
		String[] result = {(String) this.boardLayout.get("n1"), (String) this.boardLayout.get("n2")}; 
		return result;
	}

	protected int asInt(final JSONObject json, final String key)
	{
		return Integer.parseInt((String) this.boardLayout.get(key));
	}

	protected BoardLayout createLayout(final Dimension size)
	{
		BoardLayout result = new BoardLayout();

		double wfactor = size.width / BaseSkin.DESIGN_WIDTH;
		double hfactor = size.height / BaseSkin.DESIGN_HEIGHT;
		
		Rectangle rect = new Rectangle();
		rect.x = this.asInt(this.boardLayout, "x");
		rect.y = this.asInt(this.boardLayout, "y");
		rect.width = this.asInt(this.boardLayout, "width");
		rect.height = this.asInt(this.boardLayout, "height");
		int sely = this.asInt(this.boardLayout, "sely");
		int laby = this.asInt(this.boardLayout, "laby");
		int gapx = this.asInt(this.boardLayout, "gapx");

		result.boardRect.x = (int) (rect.x * wfactor);
		result.boardRect.y = (int) (rect.y * hfactor);
		result.boardRect.width = (int) (rect.width * wfactor);
		result.boardRect.height = (int) (rect.height * hfactor);

		result.selectorY = (int) (sely * hfactor);
		result.labelY = (int) (laby * hfactor);

		int cols = this.boardSize.width;
		int rows = this.boardSize.height;
		result.pieceSizeX = (int) ((result.boardRect.width - gapx*(cols - 1)) / cols);
		result.pieceSizeY = result.pieceSizeX;

		result.pieceGapX = gapx;
		result.pieceGapY = (result.boardRect.height - (result.pieceSizeY * rows))/(rows - 1);
		
		return result;
	}

	public void paintBoard(final Graphics g, final Component parent)
	{
	}
	
	public String getImageId(final Color color, int type)
	{
		String id = (color == Color.black) ? "black" : "white";
		if (type == Board2DItemType.LAST)
		{
			id += "_last";
		}
		
		return id + ".png";
	}

	public void paintBoardItem(final Graphics g, final Component parent, final BoardItem item)
	{
		BoardLayout l = this.getLayout(parent.getSize());
		IPiece piece = item.getPiece();

		if (piece != null)
		{
			String id = getImageId(piece.getPlayer().getColor(), item.getType());
			IPosition2D pos = item.getPosition();

			this.paintImageToGrid(g, parent, id, l, pos.x(), pos.y());
		}
	}

	protected void paintPiece(final Graphics g, final Component parent, final IPiece piece, final Rectangle rect, int type)
	{
		if (piece != null)
		{
			String id = getImageId(piece.getPlayer().getColor(), type);
			
			this.paintImage(g, parent, id, rect);
		}
	}

	protected void paintImage(final Graphics g, final Component parent, String id, final Rectangle rect)
	{
		Image image = AppResources.getImage(null, id, parent, rect.width, rect.height);
		g.drawImage(image, rect.x, rect.y, null);
	}

	protected void paintImageToGrid(final Graphics g, final Component parent, String id,
			final BoardLayout l, int row, int col)
	{
		int x = (int) (l.boardRect.x + row*(l.pieceSizeX+l.pieceGapX));
		int y = (int) (l.boardRect.y + col*(l.pieceSizeY+l.pieceGapY));
		
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

	public String getName()
	{
		return this.name;
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
	protected JSONObject boardLayout;
	private String name;
}
