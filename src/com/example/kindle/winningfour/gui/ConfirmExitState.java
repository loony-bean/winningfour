/**
 * 
 */
package com.example.kindle.winningfour.gui;

import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KOptionPane;
import com.example.kindle.sm.State;
import com.example.kindle.winningfour.App;

/**
 *
 */
public class ConfirmExitState extends State
{
	public ConfirmExitState(final KindletContext context, final String name)
	{
		super(name);
		this.context = context;
	}

	public void enter()
	{
		KOptionPane.showConfirmDialog(this.context.getRootContainer(), "Exit game?", "Confirmation", 
                new KOptionPane.ConfirmDialogListener() {
                    /** {@inheritDoc} */
                    public void onClose(final int option)
                    {
                    	if (option == KOptionPane.OK_OPTION)
                    	{
                    		App.pager.stop();
                    	}
                    	else
                    	{
                    		App.pager.back();
                    	}
                    		
                    }
                });
	}

	public void leave()
	{
	}

	KindletContext context;
}
