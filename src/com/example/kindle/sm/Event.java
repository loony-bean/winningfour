/**
 * 
 */
package com.example.kindle.sm;

import com.example.kindle.sm.SignalEvent;

/**
 *
 */
public class Event
{
	public boolean equals(Object other)
	{
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;

		if (getClass() == SignalEvent.class)
		{
			SignalEvent lhs = (SignalEvent) this;
			SignalEvent rhs = (SignalEvent) other;
			return rhs.signal.equals(lhs.signal);
		}

		if (getClass() == KeyboardEvent.class)
		{
			KeyboardEvent lhs = (KeyboardEvent) this;
			KeyboardEvent rhs = (KeyboardEvent) other;
			return rhs.keycode == lhs.keycode;
		}

		return false;
	}
}
