/**
 * 
 */
package com.example.kindle.sm;

/**
 *
 */
public class Transition
{
	public Transition(Event event, State to)
	{
		this.event = event;
		this.to = to;
	}

	public final State to;
	public final Event event;
}
