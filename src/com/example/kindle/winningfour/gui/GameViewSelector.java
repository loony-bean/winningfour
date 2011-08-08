package com.example.kindle.winningfour.gui;

import java.awt.Graphics;
import java.awt.Image;

import com.example.kindle.winningfour.App;

public class GameViewSelector extends GameImage
{
	public GameViewSelector(Image image, int rows)
	{
		super(image);
		this.rows = rows;
		this.currentRow = 0;
	}

	public void destroy()
	{
		super.destroy();
	}

	public void paint(Graphics g)
	{
		App.log("GameViewSelector::paint");
		super.paint(g);
	}

	public void moveRight()
	{
		if (this.currentRow < this.rows - 1)
		{
			this.currentRow += 1;
		}
	}

	public void moveLeft()
	{
		if (this.currentRow > 0)
		{
			this.currentRow -= 1;
		}
	}

	public int getCurrentRow()
	{
		return this.currentRow;
	}

	private static final long serialVersionUID = 1L;
	private int currentRow;
	private int rows;
}
