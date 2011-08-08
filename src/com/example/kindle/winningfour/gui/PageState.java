/**
 * 
 */
package com.example.kindle.winningfour.gui;

import java.awt.BorderLayout;
import java.awt.Container;
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
	PageState(final KindletContext context, final String name)
	{
		super(name);
		this.context = context;
		this.root = context.getRootContainer();
		this.panel = new KPanel();
		this.active = false;
	}

	public void enter()
	{
		super.enter();
        this.root.add(this.panel, BorderLayout.CENTER);
        this.focusOwner.requestFocus();
        this.active = true;
	}

	public void leave()
	{
		super.leave();
		this.focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		this.root.remove(panel);
        this.active = false;
	}

	boolean active;
	KPanel panel;
	Container root;
	KindletContext context;
	Component focusOwner;
	FocusListener focusListener;
}
