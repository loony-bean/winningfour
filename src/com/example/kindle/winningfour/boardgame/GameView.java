package com.example.kindle.winningfour.boardgame;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import com.amazon.kindle.kindlet.ui.KLabel;
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
		this.layout = AppResources.getSkin().getLayout(size);

		this.gameSelector.setBounds(size.width/2 - this.layout.pieceSizeX/2, this.layout.selectorY,
				this.layout.pieceSizeX, this.layout.pieceSizeX);
		this.gameSelector.setImage(AppResources.getImage("selector.png", this,
				this.layout.pieceSizeX, this.layout.pieceSizeX));
		
		this.gameSelector.setLocation(this.layout.boardLeftTopX +
				this.selectedRow*(this.layout.pieceSizeX + this.layout.pieceGapX),
				this.layout.selectorY);

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

		this.gameSelector.setLocation(this.layout.boardLeftTopX +
				this.selectedRow*(this.layout.pieceSizeX + this.layout.pieceGapX),
				this.layout.selectorY);

		super.paint(g);
		AppResources.getSkin().paintBoard(g, this);
		this.paintItems(this.items, g);
	}
	
	public void paintItems(ArrayList items, Graphics g)
	{
		Iterator iter = items.iterator();
		while(iter.hasNext())
		{
			AppResources.getSkin().paintBoardItem(g, this, (BoardItem)iter.next());
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
			this.selectedRow = row;
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
	private int selectedRow;

	private BoardLayout layout;
}
