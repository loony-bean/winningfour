package com.example.kindle.utils;

import java.awt.Component;
import java.awt.KeyboardFocusManager;

import com.amazon.kindle.kindlet.ui.KOptionPane;
import com.example.kindle.winningfour.App;

public class DialogHelper
{
	public static void ConfirmDialog(final String text, final Runnable okrunner)
	{
		Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		KOptionPane.showConfirmDialog(focused, text, "Confirmation", 
                new KOptionPane.ConfirmDialogListener()
				{
                    public void onClose(final int option)
                    {
                    	if (option == KOptionPane.OK_OPTION)
                    	{
                    		okrunner.run();
                    	}
                    	else
                    	{
                    		App.pager.back();
                    	}
                    }
                });
	}
}
