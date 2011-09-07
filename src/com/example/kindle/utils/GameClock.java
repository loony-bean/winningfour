package com.example.kindle.utils;

import com.amazon.kindle.kindlet.util.Timer;
import com.amazon.kindle.kindlet.util.TimerTask;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.options.OptionsFactory;


public class GameClock
{
	public final static int RESOLUTION = 1000;
	public final static int TICK       = 1000;

	public GameClock()
	{
		this.enabled = false;
	}

	public void start()
	{
		this.enabled = true;
		this.startTime = System.currentTimeMillis();
	}

	public void stop()
	{
		this.enabled = false;
	}

	public void reset(final Runnable task)
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
		this.timeout = (new OptionsFactory()).createGameClockTimeout();

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

	public long getDelta()
	{
		return System.currentTimeMillis() - this.startTime;
	}

	public int getIncrement()
	{
		return this.increment;
	}

	public int getTimeout()
	{
		return this.timeout;
	}

	public void destroy()
	{
		this.timer = null;
		this.task = null;
	}

	private Timer timer;
	private Runnable task;
	private boolean enabled;
	private long startTime;
	private int increment;
	private int timeout;
}
