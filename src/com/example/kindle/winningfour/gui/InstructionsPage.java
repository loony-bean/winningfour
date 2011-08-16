/**
 * 
 */
package com.example.kindle.winningfour.gui;

import java.awt.Color;
import java.awt.Dimension;
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
	InstructionsPage(KindletContext context, final ImagePanel parent, String name)
	{
		super(context, parent, name);
		this.panel = new KPanel();
		this.panel.setPreferredSize(new Dimension(400, 400));
		this.panel.setBackground(new Color(0x000000FF, true));
		this.panel.setLayout(new GridLayout(0, 1));
        this.panel.add(new KLabel("Instructions"));
        this.panel.add(new KButton("Button"));
	}
	
	public void enter()
	{
		super.enter();
	}

	public void leave()
	{
		super.leave();
	}
}
