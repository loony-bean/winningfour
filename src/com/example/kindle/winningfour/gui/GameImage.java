package com.example.kindle.winningfour.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import com.example.kindle.winningfour.App;

public class GameImage extends Container
{
	public GameImage(Image image)
	{
		this.setImage(image);
	}

	public void setImage(Image image)
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
		
		Graphics2D g2d = (Graphics2D)g;
		
		if (this.image != null)
		{
			g2d.drawImage(this.image, 0, 0, null);
		}
		else
		{
			Dimension d = this.getSize();
			g2d.setColor(Color.black);
			g2d.fillRect(0, 0, d.width - 1, d.height - 1);
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
