package com.example.kindle.winningfour.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import com.example.kindle.winningfour.App;

public class GameImage extends Container
{
	public GameImage(final Image image)
	{
		this.setImage(image);
	}

	public void setImage(final Image image)
	{
		if (this.image != null)
		{
			this.image.flush();
			this.image = null;
		}
		
		this.image = image;
	}

	public void paint(Graphics g)
	{
		App.log("GameImage::paint in clipBounds " + g.getClipBounds());
		super.paint(g);
		
		if (this.image != null)
		{
			g.drawImage(this.image, 0, 0, null);
		}
		else
		{
			// TODO: setup drawing callback
			Dimension d = this.getSize();
			g.setColor(Color.black);
			g.setXORMode(Color.white);
			g.fillRect(4, 0, d.width - 1 - 8, 2);
			g.fillRect(2, 2, d.width - 1 - 4, 2);
			g.fillRect(4, 4, d.width - 1 - 8, 2);
		}
	}

	public void destroy()
	{
		App.log("GameImage::destroy");

		if (this.image != null)
		{
			this.image.flush();
			this.image = null;
		}

		App.log("GameImage::destroy done");
	}

	private static final long serialVersionUID = 1L;
	private Image image;
}
