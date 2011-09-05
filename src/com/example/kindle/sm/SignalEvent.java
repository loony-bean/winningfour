package com.example.kindle.sm;

/**
 * General purpose application event defined as string value.
 */
public class SignalEvent extends Event
{
	public SignalEvent(String signal)
	{
		this.signal = signal;
	}

	/** {@inheritDoc} */
	public String toString()
	{
		return "SignalEvent:" + this.signal;
	}

	/** Event string identifier. */
	public final String signal;
}
