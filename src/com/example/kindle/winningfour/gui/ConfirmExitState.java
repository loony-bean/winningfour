/**
 * 
 */
package com.example.kindle.winningfour.gui;

import com.example.kindle.sm.State;
import com.example.kindle.utils.DialogHelper;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.AppResources;

/**
 *
 */
public class ConfirmExitState extends State
{
	public ConfirmExitState(final String name)
	{
		super(name);
	}

	public void enter()
	{
		DialogHelper.confirm(App.bundle.getString(AppResources.KEY_CONFIRM_EXIT),
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
}
