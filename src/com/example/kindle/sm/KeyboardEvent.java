/**
 * 
 */
package com.example.kindle.sm;

/**
 *
 */
public class KeyboardEvent extends Event
{
	public KeyboardEvent(int keycode)
	{
		this.keycode = keycode;
	}

	public final int keycode;
}
