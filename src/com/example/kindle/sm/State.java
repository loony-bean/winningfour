/**
 * 
 */
package com.example.kindle.sm;

import java.util.ArrayList;

import com.example.kindle.winningfour.App;

/**
 *
 */
public class State
{
	public State(String name)
	{
		this.transitions = new ArrayList();
		this.name = name;
	}
	
	public void enter()
	{
		App.log("" + this.name + "::state enter");
	}
	
	public void leave()
	{
		App.log("" + this.name + "::state leave");
	}
	
	public void addTransition(Event event, State to)
	{
		this.transitions.add(new Transition(event, to));
	}
	
	public void onSignal(String signal, State to)
	{
		this.addTransition(new SignalEvent(signal), to);
	}

	public void onKey(int key, State to)
	{
		this.addTransition(new KeyboardEvent(key), to);
	}

	public ArrayList transitions()
	{
		return this.transitions;
	}

	public boolean equals(Object other)
	{
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;

		State otherState = (State) other;
		return this.name.equals(otherState.name);
	}

	private final ArrayList transitions;
	private final String name;
}
