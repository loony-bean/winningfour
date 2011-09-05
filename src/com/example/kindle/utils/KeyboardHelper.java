/**
 * 
 */
package com.example.kindle.utils;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import com.amazon.kindle.kindlet.event.KindleSimulatedKeyEvent;

/**
 * Helper class for keyboard-related methods.
 */
public class KeyboardHelper
{
	/**
	 * Posts a simulated keystroke event into kindle's system event queue.
	 * 
	 * @param sender Component that sends the event.
	 * @param keyCode Code of the keystroke.
	 */
	public static void simulateKey(final Component sender, int keyCode)
	{
		if (sender != null)
		{
			Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(
					new KindleSimulatedKeyEvent(sender, 
							KeyEvent.KEY_PRESSED, 
							System.currentTimeMillis(), 
							0, 
							keyCode, 
							KeyEvent.CHAR_UNDEFINED)
			);
		}
	}
}
