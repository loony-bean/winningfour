package com.example.kindle.winningfour.boardgame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;

import com.amazon.kindle.kindlet.ui.KLabel;
import com.example.kindle.boardgame.Position2D;
import com.example.kindle.utils.ImageHelper;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.AppResources;
import com.example.kindle.winningfour.boardgame.BoardItem;
import com.example.kindle.winningfour.gui.GameImage;

public class GameView extends Container
{
	public GameView()
	{
		App.log("GameView::GameView");
		this.setLayout(null);

		Toolkit tk = Toolkit.getDefaultToolkit();
		this.selectorImage = tk.createImage(getClass().getResource("selector.png"));

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
		Image image = null;
		
		this.gameSelector.setBounds(size.width/2 - s/2,size.height/2 - s/2, s, s);
		image = this.selectorImage.getScaledInstance(s, s, Image.SCALE_SMOOTH);
		ImageHelper.waitForImage(image, this);
		this.gameSelector.setImage(image);
		
		this.gameSelector.setLocation(this.gameSelector.getLocation().x, hy - s);

		Dimension sz = this.statusLabel.getPreferredSize();
		this.statusLabel.setBounds(0, sz.height, size.width, sz.height);
		
		App.log("GameView::doLayout done");
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
			if (item.getPiece() != null)
			{
				Position2D pos = (Position2D)item.getPosition();
				Color color = item.getPiece().getPlayer().getColor();
				g2d.setColor(color);
				g2d.fillOval(hx + pos.row()*(s+gap), hy + pos.col()*(s+gap), s, s);
			}
		}

		g2d.setColor(Color.black);
		g2d.drawRect(hx - gap, hy - gap, 7*(s+gap) + gap, 6*(s+gap) + gap);
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
			this.gameSelector.setLocation(hx + row*(s+gap), hy - s);
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

		this.selectorImage.flush();
		this.selectorImage = null;
		
		this.items.clear();
		this.items = null;
		
		this.layoutSize = null;

		App.log("GameView::destroy done");
	}

	private static final long serialVersionUID = 1;
	private Image selectorImage;
	private GameImage gameSelector;
	private KLabel statusLabel;
	private Dimension layoutSize;
	private ArrayList items;

	private int hx = 100;
	private int hy = 100;
	private int s = 50;
	private int gap = 10;
}
