/**
 * 
 */
package com.example.kindle.winningfour.gui;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.event.KindleKeyCodes;
import com.example.kindle.sm.State;
import com.example.kindle.utils.DialogHelper;
import com.example.kindle.utils.KeyboardHelper;
import com.example.kindle.winningfour.App;

/**
 *
 */
public class OptionsPage extends State
{
	OptionsPage(final KindletContext context, final ImagePanel parent, final String name)
	{
		super(name);
		this.context = context;
		this.active = false;
		this.focusListener = new FocusListener()
		{
			public void focusLost(FocusEvent e)
			{
				OptionsPage.this.active = false;
			}
			
			public void focusGained(FocusEvent e)
			{
				if (!OptionsPage.this.active)
				{
					OptionsPage.this.active = true;
					App.pager.back();
				}
			}
		};
	}

	public void enter()
	{
		super.enter();
		
		this.focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		this.focused.addFocusListener(this.focusListener);
		
		this.context.setTextOptionPane(App.opts.getTextOptionPane());

		App.opts.revert();
		
		KeyboardHelper.simulateKey(this.focused, KindleKeyCodes.VK_TEXT);
	}

	public void leave()
	{
		super.leave();
		this.focused.removeFocusListener(this.focusListener);
		
		if (!App.gamer.isStopped())
		{
			DialogHelper.ConfirmDialog("Pressing OK will apply new options and start a new game.",
					new Runnable() {
						public void run() {
							App.gamer.stop();
							App.opts.commit();
						}},
					new Runnable() {
						public void run() {
							App.opts.revert();
						}});
		}
		else
		{
			App.opts.commit();
		}

		this.context.setTextOptionPane(null);
}

	KindletContext context;
	boolean active;
	Component focused;
	FocusListener focusListener;
}
