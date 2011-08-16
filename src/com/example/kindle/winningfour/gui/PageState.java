/**
 * 
 */
package com.example.kindle.winningfour.gui;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusListener;

import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KPanel;

import com.example.kindle.sm.State;

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
			this.parent.setInner(this.panel);
		}
        
		this.focusOwner.requestFocus();
        this.active = true;
	}

	public void leave()
	{
		super.leave();
		this.focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        this.active = false;
	}

	boolean active;
	KPanel panel;
	ImagePanel parent;
	KindletContext context;
	Component focusOwner;
	FocusListener focusListener;
}
