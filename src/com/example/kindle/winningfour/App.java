package com.example.kindle.winningfour;

import java.awt.Container;
import java.awt.EventQueue;
import java.util.ResourceBundle;

import com.amazon.kindle.kindlet.AbstractKindlet;
import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KindleOrientation;
import com.example.kindle.winningfour.gui.PageController;

import org.apache.log4j.Logger;

/**
 */
public class App extends AbstractKindlet {
    /** {@inheritDoc} */
    public void create(final KindletContext context)
    {
    	App.log("App::create");
		
		this.context = context;
		this.root = this.context.getRootContainer();

		App.log("App::create done");
    }

    public void destroy()
    {
		App.log("App::destroy");

		// TODO: save game state/options to file

		this.root.setLayout(null);  // no need to waste time on implicit root-layout calls down from here 
		this.root.removeAll();

		// TODO: destroy() everything
		// TODO: null out pointers

		System.gc();
		
		App.log("App::destroy done.\n\nOver and out!");
	}

    public void start()
    {
		App.log("App::start");

		synchronized (this)
		{
	    	App.log("App::start synchronized");
			App.setStopped(false);
	    	super.start();

			if (!this.initialStartDone)
			{  
				App.log("App::start initial");
				if (!App.isStopped())
				{
					Runnable runnable = 
						new Runnable() 
						{
						    public void run() 
						    {
						    	App.this.initalStart();
						    }
						};
					App.log("App::start will post runnable");
					EventQueue.invokeLater(runnable);
				}
			}
			else
			{
				App.log("App::start resume after pause");
				// usually nothing much to do here, unless you did something 
				// in stop() that needs to be rebuilt.
			}
			
			/* TODO: build timers and possibly background threads (if any) ... */
		}
		
		App.log("App::start done");
    }

    protected void initalStart()
    {
		App.log("App::initalStart");

		if (App.isStopped())
		{
			App.log("App::initalStart early exit (stopped)");
			return; 
		}
		
		// Options
		this.context.getOrientationController().lockOrientation(KindleOrientation.PORTRAIT);

		// App start code
		App.pager = new PageController(this.context);
		App.pager.start();
		
		this.initialStartDone = true;
	
		App.log("App::initalStart done");
	}

	public void stop()
    {
		App.log("App::stop");
		App.setStopped(true);
		
		synchronized (this)
		{
	    	App.log("App::stop syncrhonized");
			/* TODO: ... tear down timers if you have any... */
		}
		
		App.log("App::stop done");
	}
    
	public static void setStopped(boolean flag)
	{
		App.stopped = flag;
	}
	
	public static boolean isStopped()
	{
		return App.stopped;
	}

    private static boolean stopped = false;

    public static ResourceBundle bundle = ResourceBundle.getBundle("com.example.kindle.winningfour.AppResources");
    public static PageController pager;

    private KindletContext context;
    private Container root;
    private boolean initialStartDone;
    
    private static long startTime = System.currentTimeMillis();
	private static Logger logger = Logger.getLogger("App");
	
	public static void log(String msg)
	{
		double timestamp = (System.currentTimeMillis() - App.startTime)/1000.0;
		logger.info("[" + timestamp + " - " + Thread.currentThread().getName() + "]   " + msg);
	}
}