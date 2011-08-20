package com.example.kindle.utils;

import java.awt.Component;
import java.awt.KeyboardFocusManager;

import com.amazon.kindle.kindlet.ui.KOptionPane;

public class DialogHelper
{
	public static void ConfirmDialog(final String text, final Runnable onOk, final Runnable onCancel)
	{
		Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		KOptionPane.showConfirmDialog(focused, text, "Confirmation", 
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
}
