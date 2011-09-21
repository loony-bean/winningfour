package com.example.kindle.winningfour.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import com.amazon.kindle.kindlet.ui.KImage;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.AppResources;

public class ImagePanel extends GamePanel
{
	public ImagePanel(final String name)
	{
		super();
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

		super.doLayout();

		Image image = AppResources.getImage(null, name, this, this.layoutSize.width, this.layoutSize.height);

		this.backgroundPane.setBounds(0, 0, this.layoutSize.width, this.layoutSize.height);
		this.backgroundPane.setImage(image);
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
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

	void destroy()
	{
		App.log("ImagePanel::destroy");

		super.destroy();
		
		this.backgroundPane.setImage(null);
		this.backgroundPane = null;
		
		if (this.backgroundImage != null)
		{
			this.backgroundImage.flush();
			this.backgroundImage = null;
		}
		
		App.log("ImagePanel::destroy done");
	}

	private static final long serialVersionUID = 1L;
	private String name;
	private Image backgroundImage;
	private KImage backgroundPane;
}
