/**
 * 
 */
package com.example.kindle.winningfour.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.WeakHashMap;

import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KButton;
import com.amazon.kindle.kindlet.ui.KPanel;
import com.example.kindle.sm.SignalEvent;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.AppResources;
import com.example.kindle.winningfour.boardgame.IGameStateListener;

/**
 *
 */
public class MenuPage extends PageState
{
	MenuPage(final KindletContext context, final ImagePanel parent, final String name)
	{
		super(context, parent, name);
		this.panel = new KPanel();
		this.panel.setPreferredSize(new Dimension(300, 300));
		this.panel.setBackground(new Color(0x000000FF, true));
		this.panel.setLayout(new GridLayout(0, 1));
		this.panel.add(createMainMenu());
		
		this.gameStateListener = new IGameStateListener()
		{
			public void onStart()
			{
				KButton resumeOption = (KButton) MenuPage.this.options.get(AppResources.KEY_MENU_RESUME_GAME);
				resumeOption.setEnabled(true);
				resumeOption.requestFocus();
			}

			public void onStop()
			{
				KButton newOption = (KButton) MenuPage.this.options.get(AppResources.KEY_MENU_NEW_GAME);
				KButton resumeOption = (KButton) MenuPage.this.options.get(AppResources.KEY_MENU_RESUME_GAME);
				
				if (resumeOption.isFocusOwner())
				{
					newOption.requestFocus();
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
	
	public void adjustMenu()
	{
		KButton newOption = (KButton) this.options.get(AppResources.KEY_MENU_NEW_GAME);
		KButton resumeOption = (KButton) this.options.get(AppResources.KEY_MENU_RESUME_GAME);

		if (App.gamer.isStopped())
        {
			if (resumeOption.isFocusOwner())
			{
				newOption.requestFocus();
			}

			resumeOption.setEnabled(false);
        }
		else
		{
			if (resumeOption.isEnabled() == false)
			{
				resumeOption.setEnabled(true);
				resumeOption.requestFocus();
			}
		}
	}
    
	public Container createMainMenu()
    {
    	final GridLayout layout = new GridLayout(0, 1, 8, 8);
        final KPanel panel = new KPanel(layout);
		panel.setBackground(new Color(0x000000FF, true));
		this.options = new WeakHashMap();
        
        String[] menuItems = {AppResources.KEY_MENU_NEW_GAME,
        					  AppResources.KEY_MENU_RESUME_GAME,
        					  AppResources.KEY_MENU_OPTIONS,
        					  AppResources.KEY_MENU_INSTRUCTIONS,
        					  AppResources.KEY_MENU_EXIT};
        for(int i = 0; i < menuItems.length; i++)
        {
        	final String key = menuItems[i];
            Container option = addOption(App.bundle.getString(key), new Runnable()
            {
                public void run()
                {
                	App.pager.pushEvent(new SignalEvent(key));
                }
            });

            this.focusOwner = panel;
            this.options.put(key, option);
            panel.add(option);
        }

        return panel;
    }

    /**
     * Adds a labeled button which takes the supplied action when clicked to the panel.
     *
     * @param name the name of the button
     * @param action the action to take when the button is clicked
     */
    protected Container addOption(final String name, final Runnable action)
    {
        KButton button = new KButton(name);
		button.setFont(AppResources.getFont(AppResources.ID_FONT_MENU));

        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                action.run();
            }
        });

        return button;
    }
    
    IGameStateListener gameStateListener;
    WeakHashMap options;
}
