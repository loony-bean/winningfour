package com.example.kindle.utils;

import java.awt.Component;
import java.awt.KeyboardFocusManager;

import com.amazon.kindle.kindlet.ui.KOptionPane;
import com.example.kindle.winningfour.App;

public class DialogHelper
{
	public static void confirm(final String msg, final Runnable onOk, final Runnable onCancel)
	{
		Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		KOptionPane.showConfirmDialog(focused, msg, "Confirmation", 
                new KOptionPane.ConfirmDialogListener()
				{
                    public void onClose(final int option)
                    {
                    	if (option == KOptionPane.OK_OPTION)
                    	{
                    		onOk.run();
                    	}
                    	else
                    	{
                    		onCancel.run();
                    	}
                    }
                });
	}

	public static void error(final String msg)
	{
		App.log("DialogHelper::ErrorMessageDialog: " + msg);

		Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		KOptionPane.showMessageDialog(focused, msg,
				new KOptionPane.MessageDialogListener()
				{
					public void onClose()
					{
						App.gamer.stop();
						App.pager.home();
					}
				});
	}
}
