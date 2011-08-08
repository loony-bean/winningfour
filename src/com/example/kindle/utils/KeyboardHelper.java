/**
 * 
 */
package com.example.kindle.utils;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import com.amazon.kindle.kindlet.event.KindleSimulatedKeyEvent;

/**
 *
 */
public class KeyboardHelper
{
	public static void simulateKey(Component component, int keyCode)
	{
		//Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		
		if (component != null)
		{
			Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(
					new KindleSimulatedKeyEvent(component, 
							KeyEvent.KEY_PRESSED, 
							System.currentTimeMillis(), 
							0, 
							keyCode, 
							KeyEvent.CHAR_UNDEFINED)
			);
		}
	}
}
