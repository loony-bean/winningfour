package com.example.kindle.winningfour.boardgame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;

import com.amazon.kindle.kindlet.ui.KLabel;
import com.example.kindle.boardgame.Position2D;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.AppResources;
import com.example.kindle.winningfour.boardgame.BoardItem;
import com.example.kindle.winningfour.gui.GameImage;
import com.example.kindle.winningfour.skins.BoardLayout;

public class GameView extends Container
{
	public GameView()
	{
		App.log("GameView::GameView");
		this.setLayout(null);

		this.gameSelector = new GameImage(null);
		this.statusLabel = new KLabel("", KLabel.CENTER);
		this.statusLabel.setFont(AppResources.getFont(AppResources.ID_FONT_GAME_STATUS));

		// Add items in z-order: top items first, to bottom items last
		this.add(this.gameSelector);
		this.add(this.statusLabel);

		this.gameSelector.setFocusable(false);

		this.setFocusable(true);
	    this.setFocusTraversalKeysEnabled(false);
		
		App.log("GameView::GameView done");
	}

	public void doLayout()
	{
		App.log("GameView::doLayout for " + this.getSize());
		final Dimension size = this.getSize();
		
		if (this.layoutSize != null && this.layoutSize.equals(size))
		{
			App.log("GameView::doLayout early exit (same size)");
			return;
		}

		this.layoutSize = size;
		this.skin = AppResources.getLayout(size);

		this.gameSelector.setBounds(size.width/2 - this.skin.pieceSizeX/2, this.skin.selectorY,
				this.skin.pieceSizeX, this.skin.pieceSizeX);
		this.gameSelector.setImage(AppResources.getImage("selector.png", this,
				this.skin.pieceSizeX, this.skin.pieceSizeX));
		this.gameSelector.setLocation(this.gameSelector.getLocation().x, this.skin.selectorY);

		Dimension sz = this.statusLabel.getPreferredSize();
		this.statusLabel.setBounds(0, sz.height, size.width, sz.height);
		
		App.log("GameView::doLayout done");
	}
	
	public void reset()
	{
		this.layoutSize = null;
	}

	public void paint(Graphics g)
	{
		App.log("GameView::paint in clipBounds " + g.getClipBounds());

		super.paint(g);
		this.paintItems(this.items, g);
	}
	
	public void paintItems(ArrayList items, Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;

		Iterator iter = items.iterator();
		while(iter.hasNext())
		{
			BoardItem item = (BoardItem)iter.next();

			Position2D pos = (Position2D)item.getPosition();

			String id = "hole.png";

			int hx = this.skin.boardLeftTopX;
			int hy = this.skin.boardLeftTopY;
			int s = this.skin.pieceSizeX;
			int gap = this.skin.pieceGapX;
			
			int x = hx + pos.row()*(s+gap);
			int y = hy + pos.col()*(s+gap);

			if (item.getPiece() != null)
			{
				Color color = item.getPiece().getPlayer().getColor();
				id = (color == Color.blue) ? "spider.png" : "mouse.png";
			}
			
			Image image = AppResources.getImage(id, this,
					this.skin.pieceSizeX, this.skin.pieceSizeX);
			
			g2d.drawImage(image, x, y, null);
		}
	}
	
	public void setItems(ArrayList items)
	{
		this.items = items;
		this.repaint();
	}

	public void setSelectedRow(int row)
	{
		if (row == -1)
		{
			this.gameSelector.setVisible(false);
		}
		else
		{
			if (this.skin != null)
			{
				this.gameSelector.setLocation(this.skin.boardLeftTopX +
						row*(this.skin.pieceSizeX + this.skin.pieceGapX),
						this.skin.selectorY);
			}
			
			this.gameSelector.setVisible(true);
		}

		this.gameSelector.repaint();
	}

	public void setStatusText(String text)
	{
		this.statusLabel.setText(text);
	}

	public void destroy()
	{
		App.log("GameView::destroy");

		this.removeAll();

		this.gameSelector.destroy();
		this.gameSelector = null;

		this.statusLabel = null;

		this.items.clear();
		this.items = null;
		
		this.layoutSize = null;

		App.log("GameView::destroy done");
	}

	private static final long serialVersionUID = 1;
	private GameImage gameSelector;
	private KLabel statusLabel;
	private Dimension layoutSize;
	private ArrayList items;

	private BoardLayout skin;
}
