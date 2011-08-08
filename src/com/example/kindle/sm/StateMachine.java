/**
 * 
 */
package com.example.kindle.sm;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import com.amazon.kindle.kindlet.event.KindleKeyCodes;
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
		
		keyEventDispatcher = new KeyEventDispatcher() {
            public boolean dispatchKeyEvent(final KeyEvent key)
            {
                if (key.getKeyCode() == KindleKeyCodes.VK_BACK)
                {
                    key.consume();
                	App.log("VK_BACK");
                	App.pager.pushEvent(new KeyboardEvent(KindleKeyCodes.VK_BACK));
                    return true;
                }
                else if (key.getKeyCode() == KindleKeyCodes.VK_TEXT)
                {
                    key.consume();
                	App.log("VK_TEXT");
                	App.pager.pushEvent(new KeyboardEvent(KindleKeyCodes.VK_TEXT));
                    return true;
                }

                return false;
            }
		};
    	KeyboardFocusManager fm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        fm.addKeyEventDispatcher(this.keyEventDispatcher);
	}
	
	public void destroy()
	{
    	KeyboardFocusManager fm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        fm.removeKeyEventDispatcher(this.keyEventDispatcher);
	}
	
	public void start()
	{
		if (!this.running)
		{
			this.running = true;
			this.jump(initialState);
		}
	}

	public void stop()
	{
		if (this.running)
		{
			this.running = false;
			this.jump(finalState);
		}
	}

	public boolean isRunning()
	{
		return this.running;
	}

	public void restart()
	{
		this.start();
		this.stop();
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

		Iterator i = currentState.transitions().iterator();
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
		App.log("App::jump " + state.getClass().getSimpleName());
		if (currentState != null)
		{
			currentState.leave();
		}
		
		historyState = currentState; 
		currentState = state;
		currentState.enter();
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
		return currentState;
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

	boolean running;
	
	private KeyEventDispatcher keyEventDispatcher;
}
