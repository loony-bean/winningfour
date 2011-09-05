package com.example.kindle.sm;

/**
 * Encapsulates a pair of event that comes into state machine,
 * and appropriate state that will be entered after this event
 * is dispatched.
 */
public class Transition
{
	public Transition(Event event, State to)
	{
		this.event = event;
		this.to = to;
	}

	/** Event that took place. */
	public final Event event;

	/** State that will be entered. */
	public final State to;
}
