/**
 * 
 */
package com.example.kindle.winningfour.gui;

import java.awt.Component;
import java.awt.KeyboardFocusManager;

import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KPanel;

import com.example.kindle.sm.State;
import com.example.kindle.winningfour.App;

/**
 *
 */
public class PageState extends State
{
	PageState(final KindletContext context, final ImagePanel parent, final String name)
	{
		super(name);
		this.context = context;
		this.parent = parent;
		this.focusOwner = this.parent;
		this.active = false;
	}

	public void enter()
	{
		super.enter();
		if (this.panel != null)
		{
			this.focusOwner.requestFocus();
			this.parent.setInner(this.panel);
			this.panel.repaint();
		}
        
		this.focusOwner.requestFocus();
		this.focusOwner.repaint();
        this.active = true;
	}

	public void leave()
	{
		super.leave();
		//this.focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        this.active = false;
	}
	
	public void destroy()
	{
		App.log("PageState::destroy");

		super.destroy();
		
		this.panel = null;
		this.context = null;
		this.focusOwner = null;
		this.parent = null;

		App.log("PageState::destroy done");
	}

	protected boolean active;
	protected KPanel panel;
	protected ImagePanel parent;
	protected KindletContext context;
	protected Component focusOwner;
}
