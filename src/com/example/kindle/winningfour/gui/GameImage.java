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
			Dimension d = this.getSize();
			g.setColor(Color.black);
			g.fillRect(0, 0, d.width - 1, d.height - 1);
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
