/**
 * 
 */
package com.example.kindle.winningfour.gui;

import java.awt.Color;
import java.awt.GridLayout;

import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KButton;
import com.amazon.kindle.kindlet.ui.KLabel;
import com.amazon.kindle.kindlet.ui.KPanel;

/**
 *
 */
public class InstructionsPage extends PageState
{
	InstructionsPage(KindletContext context, String name)
	{
		super(context, name);
		this.panel = new ImagePanel("background.gif");
		this.panel.setLayout(new GridLayout(0, 1));
		KPanel inner = new KPanel();
        Color transparent = new Color(0x000000FF, true);
		inner.setBackground(transparent);
		inner.add(new KLabel("Instructions"));
		this.focusOwner = inner;
		inner.add(new KButton("Button"));
		((ImagePanel) this.panel).setInner(inner);
	}
	
	public void enter()
	{
		super.enter();
		this.root.repaint();
	}
}
