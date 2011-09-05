package com.example.kindle.winningfour.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import com.amazon.kindle.kindlet.ui.KImage;
import com.amazon.kindle.kindlet.ui.KPanel;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.AppResources;
import com.example.kindle.winningfour.skins.ISkin;

public class ImagePanel extends KPanel
{
	public ImagePanel(final String name)
	{
		this.setLayout(null);
		this.backgroundPane = new KImage(null);
		this.add(this.backgroundPane);
		this.name = name;
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

		Image image = AppResources.getImage(name, this, size.width, size.height);

		this.backgroundPane.setBounds(0, 0, size.width, size.height);
		this.backgroundPane.setImage(image);
		
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
		this.remove(this.backgroundPane);
		this.add(this.inner);
		this.add(this.backgroundPane);
		
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

		this.backgroundPane.setImage(null);
		this.backgroundPane = null;
		
		if (this.backgroundImage != null)
		{
			this.backgroundImage.flush();
			this.backgroundImage = null;
		}
		
		this.layoutSize = null;
		this.inner = null;

		App.log("ImagePanel::destroy done");
	}

	private static final long serialVersionUID = 1L;
	private String name;
	private Image backgroundImage;
	private KImage backgroundPane;
	private Dimension layoutSize;
	private Container inner;
	private ISkin skin;
}
