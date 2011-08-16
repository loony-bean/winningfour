/**
 * 
 */
package com.example.kindle.sm;

import java.util.ArrayList;
import java.util.Iterator;

import com.example.kindle.winningfour.App;

/**
 *
 */
public class StateMachine
{
	public StateMachine()
	{
		this.states = new ArrayList();
		this.running = false;
	}
	
	public void start()
	{
		if (!this.running)
		{
			this.running = true;
			this.jump(this.initialState);
		}
	}

	public void stop()
	{
		if (this.running)
		{
			this.running = false;
			this.jump(this.finalState);
		}
	}

	public boolean isRunning()
	{
		return this.running;
	}

	public void restart()
	{
		this.stop();
		this.start();
	}

	public void back()
	{
		App.log("App::back");

		this.jump(historyState);
		
		App.log("App::back done");
	}

	public void home()
	{
		App.log("App::home");

		this.jump(initialState);
		
		App.log("App::home done");
	}

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
	
	public void setInitialState(State initialState)
	{
		this.initialState = initialState;
	}

	public void setFinalState(State finalState)
	{
		this.finalState = finalState;
	}

	public State getCurrentState()
	{
		return this.currentState;
	}

	public void addState(State state)
	{
		this.states.add(state);
	}
	
	private ArrayList states;

	private State historyState;
	private State currentState;
	private State initialState;
	private State finalState;

	private boolean running;
}
