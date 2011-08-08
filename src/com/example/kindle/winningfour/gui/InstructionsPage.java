/**
 * 
 */
package com.example.kindle.winningfour.gui;

import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KButton;
import com.amazon.kindle.kindlet.ui.KLabel;

/**
 *
 */
public class InstructionsPage extends PageState
{
	InstructionsPage(KindletContext context, String name)
	{
		super(context, name);
		this.panel.add(new KLabel("Instructions"));
		this.focusOwner = new KButton("Button");
		this.panel.add(this.focusOwner);
	}
	
	public void enter()
	{
		super.enter();
		this.root.repaint();
	}
}
