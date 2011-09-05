package com.example.kindle.sm;

import java.util.ArrayList;
import java.util.Iterator;

import com.example.kindle.winningfour.App;

/**
 * Basic state machine.
 */
public class StateMachine
{
	/**
	 * Default constructor.
	 */
	public StateMachine()
	{
		this.states = new ArrayList();
		this.running = false;
	}

	/**
	 * Starts state machine. SM begins execution from initial state,
	 * that is set via setInitialState call.
	 */
	public void start()
	{
		if (!this.running)
		{
			this.running = true;
			this.jump(this.initialState);
		}
	}

	/**
	 * Stops state machine. Before stop SM will try to enter final state,
	 * that is set via setFinalState call.
	 */
	public void stop()
	{
		if (this.running)
		{
			this.running = false;
			this.jump(this.finalState);
		}
	}

	/**
	 * Indicates if state machine is running.
	 * 
	 * @return True if running, false otherwise.
	 */
	public boolean isRunning()
	{
		return this.running;
	}

	/**
	 * Restarts state machine.
	 */
	public void restart()
	{
		this.stop();
		this.start();
	}

	/**
	 * Performs transition into previous state.
	 */
	public void back()
	{
		App.log("App::back");

		this.jump(historyState);
		
		App.log("App::back done");
	}

	/**
	 * Jumps into initial state.
	 */
	public void home()
	{
		App.log("App::home");

		this.jump(initialState);
		
		App.log("App::home done");
	}

	/**
	 * Passes event into state machine. SM will search for appropriate
	 * transition among registered and in case perform a transition into
	 * the new state.
	 * 
	 * @param event Input event.
	 */
	public void pushEvent(Event event)
	{
		App.log("App::pushEvent " + event.getClass().getSimpleName());

		Iterator i = this.currentState.transitions().iterator();
		while(i.hasNext())
		{
			Transition t = (Transition) i.next(); 
			if (t.event.equals(event))
			{
				this.jump(t.to);
			}
		}
		
		App.log("App::pushEvent done");
	}

	/**
	 * Performs a transition from current state to the new one. Current
	 * transition will be finalized by leave() call, and new one will
	 * be initialized by calling it's enter() method.
	 * 
	 * @param state State to jump to.
	 */
	private void jump(State state)
	{
		if (state == null)
		{
			App.log("App::jump null");
			return;
		}
		
		App.log("App::jump " + state.getClass().getSimpleName());
		
		if (this.currentState != null)
		{
			this.currentState.leave();
		}
		
		this.historyState = this.currentState; 
		this.currentState = state;
		this.currentState.enter();
		
		App.log("App::jump done");
	}
	
	/**
	 * Sets a special state that will be entered first after state machine start.
	 * 
	 * @param initialState State to be initial.
	 */
	public void setInitialState(State initialState)
	{
		this.initialState = initialState;
	}

	/**
	 * Sets a special state that will be entered last after state machine stop.
	 * 
	 * @param initialState State to be final.
	 */
	public void setFinalState(State finalState)
	{
		this.finalState = finalState;
	}

	/**
	 * Adds a state into state machine.
	 *  
	 * @param state State to add.
	 */
	public void addState(State state)
	{
		this.states.add(state);
	}
	
	/**
	 * State machine destruction.
	 */
	public void destroy()
	{
		App.log("StateMachine::destroy");

		Iterator i = this.states.iterator();
		while (i.hasNext())
		{
			State state = (State) i.next();
			state.destroy();
		}
		
		this.states.clear();
		this.states = null;
		
		this.historyState = null;
		this.currentState = null;
		this.initialState = null;
		this.finalState = null;

		App.log("StateMachine::destroy done");
}

	/** List of states within the state machine. */
	private ArrayList states;

	/** Previous state used for back() calls */
	private State historyState;

	/** State machine current state. */
	private State currentState;

	/** Special state that is entered first. */
	private State initialState;
	
	/** Special state that is entered last. */
	private State finalState;

	/** Indicates if state machine is running. */
	private boolean running;
}
