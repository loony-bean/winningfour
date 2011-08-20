/**
 * 
 */
package com.example.kindle.winningfour.gui;

import com.amazon.kindle.kindlet.KindletContext;
import com.example.kindle.sm.State;
import com.example.kindle.utils.DialogHelper;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.AppResources;

/**
 *
 */
public class ConfirmExitState extends State
{
	public ConfirmExitState(final KindletContext context, final ImagePanel parent, final String name)
	{
		super(name);
		this.context = context;
		this.parent = parent;
	}

	public void enter()
	{
		DialogHelper.ConfirmDialog(App.bundle.getString(AppResources.KEY_CONFIRM_EXIT),
			new Runnable() {
				public void run() {
					App.pager.stop();
				}},
			new Runnable() {
				public void run() {
					App.pager.back();
				}});
	}

	public void leave()
	{
	}

	KindletContext context;
	ImagePanel parent;
}
