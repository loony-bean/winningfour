package com.example.kindle.winningfour.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;

import com.amazon.kindle.kindlet.ui.KPanel;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.AppResources;
import com.example.kindle.winningfour.skins.ISkin;

public class GamePanel extends KPanel
{
	public GamePanel()
	{
		this.setLayout(null);
	}
	
	public void doLayout()
	{
		final Dimension size = this.getSize();

		if (this.layoutSize != null && this.layoutSize.equals(size))
		{
			return;
		}

		this.layoutSize = size;
		this.skin = AppResources.getSkin();

		if (this.inner != null)
		{
			Dimension sz = this.inner.getPreferredSize();
			Dimension pz = new Dimension((this.layoutSize.width - sz.width)/2, (this.layoutSize.height - sz.height)/2); 
			this.inner.setBounds(pz.width, pz.height, sz.width, sz.height);
		}
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
	}
	
	public Container getInner()
	{
		return this.inner; 
	}
	
	public void setInner(final Container inner)
	{
		if (this.inner != null)
		{
			this.remove(this.inner);
		}
		
		this.inner = inner;
		this.add(this.inner);
		
		if (this.layoutSize != null)
		{
			Dimension sz = this.inner.getPreferredSize();
			Dimension pz = new Dimension((this.layoutSize.width - sz.width)/2, (this.layoutSize.height - sz.height)/2); 
			this.inner.setBounds(pz.width, pz.height, sz.width, sz.height);
		}
	}

	public void enter()
	{
		if (this.skin != null && !this.skin.equals(AppResources.getSkin()))
		{
			this.layoutSize = null;
		}
	}

	void destroy()
	{
		App.log("ImagePanel::destroy");

		this.removeAll();
		
		this.layoutSize = null;
		this.inner = null;

		App.log("ImagePanel::destroy done");
	}

	private static final long serialVersionUID = 1L;
	private Dimension layoutSize;
	private Container inner;
	private ISkin skin;
}
