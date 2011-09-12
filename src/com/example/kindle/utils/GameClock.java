package com.example.kindle.utils;

import com.amazon.kindle.kindlet.util.Timer;
import com.amazon.kindle.kindlet.util.TimerTask;
import com.example.kindle.winningfour.App;

/**
 * Game clock for timeout and periodical game events.
 */
public class GameClock
{
	/**
	 * Defines amount of logical ticks to pass before game
	 * clock will ran out of time. Usually this should be
	 * used as a hint for some fine grain progress bar.
	 */
	public final static int RESOLUTION = 1000;

	/**
	 * Amount of milliseconds between game clock update task
	 * calls. Note that after the clock is started the first
	 * call takes place after a TICK number of milliseconds
	 * delay. 
	 */
	public final static int TICK = 1000;

	/**
	 * Default constructor.
	 */
	public GameClock()
	{
		this.enabled = false;
	}

	/**
	 * Enables timed actions. It is important to know that timer thread
	 * is not stopped if the clock has been stopped, but no actions
	 * are executed.
	 */
	public void start()
	{
		this.enabled = true;
		this.startTime = System.currentTimeMillis();
	}

	/**
	 * Disables timed actions.
	 */
	public void stop()
	{
		this.enabled = false;
	}

	/**
	 * Resets the clock. Previous timer will be canceled and destroyed
	 * and the new timer will be constructed according to timeout and
	 * TICK value. Timer is not started automatically after reset.
	 * 
	 * @param task Game task to be executed periodically.
	 * @param timeout Task timeout, usually used as a turn limit.
	 */
	public void reset(final Runnable task, int timeout)
	{
		this.task = task;

		TimerTask updateTask = new TimerTask()
		{
			public void run()
			{
				if (GameClock.this.enabled)
				{
					GameClock.this.task.run();
				}

				try
				{
					Thread.sleep(10, 0);
				}
				catch (InterruptedException e)
				{
					// we don't care
				}
			}
		};

		this.timer = App.getTimer();
		this.timeout = timeout;

		if (this.timeout != 0)
		{
			int tick = GameClock.TICK;
			if (tick % this.timeout == 0)
			{
				this.increment = tick / this.timeout;
			}
			else
			{
				this.increment = (tick / this.timeout) + 1;
			}
			
			this.timer.schedule(updateTask, TICK, TICK);
		}
	}

	/**
	 * Returns time delta between timer start time and current time.
	 * 
	 * @return Amount of milliseconds from the clock start.
	 */
	public long getDelta()
	{
		return System.currentTimeMillis() - this.startTime;
	}

	/**
	 * Returns clock progress according to TICK and RESOLUTION parameters.
	 * Generally increment is an amount of ticks that should take place
	 * every TICK amount of milliseconds in order to reach the RESOLUTION
	 * value on clock timeout. 
	 * 
	 * @return Clock increment value in ticks.
	 */
	public int getIncrement()
	{
		return this.increment;
	}

	/**
	 * Returns clock timeout value. Usually in board games the amount 
	 * of time to make a turn is limited and the player that was not
	 * able to make a turn in time looses the game.
	 * 
	 * @return Clock timeout value in seconds. 
	 */
	public int getTimeout()
	{
		return this.timeout;
	}

	/**
	 * Clock destruction.
	 */
	public void destroy()
	{
		this.timer = null;
		this.task = null;
	}

	/** Application timer. */
	private Timer timer;

	/** Game periodical task. */
	private Runnable task;
	
	/** Indicates if the timer is started. */
	private boolean enabled;

	/** Stores timer start time. */
	private long startTime;

	/** Stores timer increment in ticks. */
	private int increment;

	/** Stores timer timeout value in seconds. */
	private int timeout;
}
