/**
 * 
 */
package com.example.kindle.winningfour.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KButton;
import com.amazon.kindle.kindlet.ui.KPanel;
import com.example.kindle.boardgame.IGameStateListener;
import com.example.kindle.sm.SignalEvent;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.AppResources;

/**
 *
 */
public class MenuPage extends PageState
{
	MenuPage(final KindletContext context, final ImagePanel parent, final String name)
	{
		super(context, parent, name);
		this.panel = new KPanel();
		this.panel.setPreferredSize(new Dimension(App.screenSize.width/2, App.screenSize.width/2));
		this.panel.setBackground(new Color(0x000000FF, true));
		this.panel.setLayout(new GridLayout(0, 1));
		this.panel.add(createMainMenu());

		this.initMenu();

		this.gameStateListener = new IGameStateListener()
		{
			public void onStart()
			{
				EventQueue.invokeLater(new Runnable()
				{
					public void run()
					{
						KButton resumeOption = (KButton) MenuPage.this.getMenuItem(AppResources.KEY_MENU_RESUME_GAME);
						resumeOption.setEnabled(true);

						MenuPage.this.focusOwner = resumeOption;
						MenuPage.this.focusOwner.requestFocus();
						MenuPage.this.panel.repaint();
					}
				});
			}

			public void onStop()
			{
				KButton newOption = (KButton) MenuPage.this.getMenuItem(AppResources.KEY_MENU_NEW_GAME);
				KButton resumeOption = (KButton) MenuPage.this.getMenuItem(AppResources.KEY_MENU_RESUME_GAME);

				if (MenuPage.this.focusOwner == resumeOption)
				{
					MenuPage.this.focusOwner = newOption;
				}

				resumeOption.setEnabled(false);
			}
		};

		App.gamer.addStateListener(this.gameStateListener);
	}

	public void enter()
	{
		super.enter();
	}
	
	public void initMenu()
	{
		if (!App.gamer.isSuspended())
        {
			KButton newOption = (KButton) this.getMenuItem(AppResources.KEY_MENU_NEW_GAME);
			KButton resumeOption = (KButton) this.getMenuItem(AppResources.KEY_MENU_RESUME_GAME);

			newOption.requestFocus();
			resumeOption.setEnabled(false);
        }
	}
    
	public Container createMainMenu()
    {
    	final GridLayout layout = new GridLayout(0, 1, 8, 8);
        final KPanel panel = new KPanel(layout);
		panel.setBackground(new Color(0x000000FF, true));
		this.menuItems = new HashMap();

        String[] menuItems = {AppResources.KEY_MENU_NEW_GAME,
        					  AppResources.KEY_MENU_RESUME_GAME,
        					  AppResources.KEY_MENU_OPTIONS,
        					  AppResources.KEY_MENU_INSTRUCTIONS};
        for(int i = 0; i < menuItems.length; i++)
        {
        	final String key = menuItems[i];
            Container menuItem = this.addMenuItem(App.bundle.getString(key), new Runnable()
            {
                public void run()
                {
                	App.pager.pushEvent(new SignalEvent(key));
                }
            });

            this.menuItems.put(key, menuItem);
            panel.add(menuItem);
        }

        return panel;
    }

    /**
     * Adds a labeled button which takes the supplied action when clicked to the panel.
     *
     * @param name the name of the button
     * @param action the action to take when the button is clicked
     */
    protected Container addMenuItem(final String name, final Runnable action)
    {
    	KButton item = new KButton(name);
		item.setFont(AppResources.getFont(AppResources.ID_FONT_MENU));

        item.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                action.run();
            }
        });

        return item;
    }

    protected Container getMenuItem(final String name)
    {
    	return (Container) this.menuItems.get(name);
    }

    public void destroy()
    {
		App.log("MenuPage::destroy");

    	super.destroy();
    	
    	this.gameStateListener = null;
    	this.menuItems.clear();
    	this.menuItems = null;

    	App.log("MenuPage::destroy done");
    }
    
    private IGameStateListener gameStateListener;
    private HashMap menuItems;
}
