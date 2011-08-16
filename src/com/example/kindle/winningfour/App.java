package com.example.kindle.winningfour;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import com.amazon.kindle.kindlet.AbstractKindlet;
import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.event.KindleKeyCodes;
import com.amazon.kindle.kindlet.ui.KTextOptionListMenu;
import com.amazon.kindle.kindlet.ui.KTextOptionPane;
import com.amazon.kindle.kindlet.ui.KindleOrientation;
import com.example.kindle.sm.KeyboardEvent;
import com.example.kindle.utils.KeyboardHelper;
import com.example.kindle.winningfour.boardgame.GameController;
import com.example.kindle.winningfour.boardgame.GameView;
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

    	KeyboardFocusManager fm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        fm.removeKeyEventDispatcher(this.keyEventDispatcher);

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
		
		// Menus
		this.options = this.createOptions();
		this.context.setTextOptionPane(this.options);

		// Setting up globals
		App.screenSize = this.root.getSize();

		// Options
		this.context.getOrientationController().lockOrientation(KindleOrientation.PORTRAIT);
		
		// Global Events
		this.keyEventDispatcher = new KeyEventDispatcher()
		{
            public boolean dispatchKeyEvent(final KeyEvent key)
            {
            	boolean consumed = false;
            	int code = key.getKeyCode();
                
            	if (code == KindleKeyCodes.VK_BACK)
                {
                	consumed = true;
                	App.pager.pushEvent(new KeyboardEvent(code));
                }
                else if (code == KindleKeyCodes.VK_TEXT)
                {
                	consumed = true;
            		Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
                	KeyboardHelper.simulateKey(focused, code);
                }

                if (consumed)
                {
                	App.log("Action key pressed: " + code);
                    key.consume();
                    return true;
                }

                return false;
            }
		};
    	
		KeyboardFocusManager fm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        fm.addKeyEventDispatcher(this.keyEventDispatcher);

		// App start code
		this.gameView = new GameView();
		App.gamer = new GameController(this.gameView);
		App.pager = new PageController(this.context);
		App.pager.start();
		
		this.initialStartDone = true;
	
		App.log("App::initalStart done");
	}

	private KTextOptionPane createOptions()
	{
		KTextOptionPane options = new KTextOptionPane();
		options.addListMenu(createOptionItem("Board Size", new String[]{"7x6", "8x7", "9x7", "10x7"}));
		options.addListMenu(createOptionItem("Opponent", new String[]{"computer", "human"}));
		options.addListMenu(createOptionItem("First turn", new String[]{"you", "opponent", "random"}));
		options.addListMenu(createOptionItem("Timer", new String[]{"off", "5 sec", "10 sec", "15 sec"}));
		options.addListMenu(createOptionItem("Skin", new String[]{"classic", "magnetic", "baloons", "pirates"}));
		return options;
	}

	private KTextOptionListMenu createOptionItem(final String header, final String[] values)
	{
		KTextOptionListMenu item = new KTextOptionListMenu(header, values);
		item.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				if (e.getStateChange() == ItemEvent.SELECTED)
				{
					App.log(e.paramString());
				}
			}
		});
		
		return item;
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
    public static Dimension screenSize;
    public static GameController gamer;

    private GameView gameView;

    private KindletContext context;
    private Container root;
    private boolean initialStartDone;
    
    private static long startTime = System.currentTimeMillis();
	private static Logger logger = Logger.getLogger("App");
	
	private KeyEventDispatcher keyEventDispatcher;
	private KTextOptionPane options;
	
	public static void log(String msg)
	{
		double timestamp = (System.currentTimeMillis() - App.startTime)/1000.0;
		logger.info("[" + timestamp + " - " + Thread.currentThread().getName() + "]   " + msg);
	}
}