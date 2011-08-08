/**
 * 
 */
package com.example.kindle.winningfour.gui;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KButton;
import com.amazon.kindle.kindlet.ui.KPanel;
import com.example.kindle.sm.SignalEvent;
import com.example.kindle.winningfour.App;

/**
 *
 */
public class MenuPage extends PageState
{
	MenuPage(final KindletContext context, final String name)
	{
		super(context, name);
        this.panel.add(this.createMainMenu());
	}

	public void enter()
	{
		super.enter();
		this.root.repaint();
	}

    public Container createMainMenu()
    {
        final KPanel panel = new KPanel(new GridLayout(0, 1));
        
        boolean first = true;
        String[] menuItems = {"menu_new_game", "menu_options", "menu_instructions", "menu_exit"};
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
            if (first)
            {
            	this.focusOwner = option;
            	first = false;
            }
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
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                action.run();
            }
        });
        return button;
    }
}
