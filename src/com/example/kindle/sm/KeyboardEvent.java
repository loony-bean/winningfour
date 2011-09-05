package com.example.kindle.sm;

/**
 * Event that is created from key stroke. 
 */
public class KeyboardEvent extends Event
{
	public KeyboardEvent(int keycode)
	{
		this.keycode = keycode;
	}

	/** {@inheritDoc} */
	public String toString()
	{
		return "KeyboardEvent:" + this.keycode;
	}

	/** Event key code. */
	public final int keycode;
}
