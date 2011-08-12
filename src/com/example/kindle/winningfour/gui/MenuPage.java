/**
 * 
 */
package com.example.kindle.winningfour.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KButton;
import com.amazon.kindle.kindlet.ui.KPanel;
import com.amazon.kindle.kindlet.ui.KindletUIResources;
import com.amazon.kindle.kindlet.ui.KindletUIResources.KFontStyle;
import com.example.kindle.sm.SignalEvent;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.AppResources;

/**
 *
 */
public class MenuPage extends PageState
{
	MenuPage(final KindletContext context, final String name)
	{
		super(context, name);
		this.panel = new ImagePanel("background.gif");
		this.panel.setLayout(new GridLayout(0, 1));
		KPanel inner = new KPanel();
		inner.setPreferredSize(new Dimension(300, 300));
        Color transparent = new Color(0x000000FF, true);
		inner.setBackground(transparent);
		inner.setLayout(new GridLayout(0, 1));
        inner.add(createMainMenu());
		((ImagePanel) this.panel).setInner(inner);
	}

	public void enter()
	{
		super.enter();
		this.root.repaint();
	}

    public Container createMainMenu()
    {
    	final GridLayout layout = new GridLayout(0, 1, 8, 8);
        final KPanel panel = new KPanel(layout);
        Color transparent = new Color(0x000000FF, true);
		panel.setBackground(transparent);
        
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
}
