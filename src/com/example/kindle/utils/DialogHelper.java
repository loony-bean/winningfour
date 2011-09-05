package com.example.kindle.utils;

import java.awt.Component;
import java.awt.KeyboardFocusManager;

import com.amazon.kindle.kindlet.ui.KOptionPane;
import com.example.kindle.winningfour.App;

/**
 * Helper class for creating dialog and message boxes.
 */
public class DialogHelper
{
	/**
	 * Displays confirm dialog with ok and cancel options.
	 * 
	 * @param msg Message (question) that will be displayed to the user.
	 * @param onOk ok button action.
	 * @param onCancel cancel button action.
	 */
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

	/**
	 * Displays error message that represent an internal game error.
	 * Usually after this kind of error you need to stop the game and
	 * push the user to GUI main.
	 * 
	 * @param msg Error message to display.
	 * @param onOk Action to be invoked after the dialog closes.
	 */
	public static void error(final String msg, final Runnable onOk)
	{
		App.log("DialogHelper::ErrorMessageDialog: " + msg);

		Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		KOptionPane.showMessageDialog(focused, msg,
				new KOptionPane.MessageDialogListener()
				{
					public void onClose()
					{
						onOk.run();
					}
				});
	}
}
