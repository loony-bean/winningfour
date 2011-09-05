package com.example.kindle.sm;

import java.util.ArrayList;

import com.example.kindle.winningfour.App;

/**
 * Defines a state of state machine. At every time state machine is running
 * in some state, until transition will take place. Available transitions are
 * stored within a State and can be registered using State methods.
 */
public class State
{
	/**
	 * State constructor.
	 * 
	 * @param name State name used for logging.
	 */
	public State(String name)
	{
		this.transitions = new ArrayList();
		this.name = name;
	}
	
	/**
	 * Called each time state machine is entering the state.
	 */
	public void enter()
	{
		App.log("" + this.name + "::state enter");
	}
	
	/**
	 * Called each time state machine is leaving the state.
	 */
	public void leave()
	{
		App.log("" + this.name + "::state leave");
	}
	
	/**
	 * Registers transition within a state.
	 * 
	 * @param event What event is expected.
	 * @param to What state will be entered on that event.
	 */
	public void addTransition(Event event, State to)
	{
		App.log("" + this + "::addTransition from " + event + " to " + to);
		this.transitions.add(new Transition(event, to));
	}
	
	/**
	 * Helper method for registering signal event transitions.
	 * 
	 * @param signal What signal is expected.
	 * @param to What state will be entered on that event.
	 */
	public void onSignal(String signal, State to)
	{
		this.addTransition(new SignalEvent(signal), to);
	}

	/**
	 * Helper method for registering keyboard event transitions.
	 * 
	 * @param signal What keyboard event is expected.
	 * @param to What state will be entered on that event.
	 */
	public void onKey(int key, State to)
	{
		this.addTransition(new KeyboardEvent(key), to);
	}

	/**
	 * Returns a list of transactions registered within this state.
	 * 
	 * @return List of available transactions.
	 */
	public ArrayList transitions()
	{
		return this.transitions;
	}

	/** {@inheritDoc} */
	public boolean equals(Object other)
	{
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;

		State otherState = (State) other;
		return this.name.equals(otherState.name);
	}

	/** {@inheritDoc} */
	public String toString()
	{
		return this.name;
	}

	/**
	 * State destruction.
	 */
	public void destroy()
	{
		App.log("" + this + " state::destroy");
		
		this.transitions.clear();
		this.transitions = null;

		App.log("" + this + " state::destroy done");
	}

	/** List of available transitions from this state */
	private ArrayList transitions;
	
	/** State name used for logging */
	private final String name;
}
