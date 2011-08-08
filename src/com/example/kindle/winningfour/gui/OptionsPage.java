/**
 * 
 */
package com.example.kindle.winningfour.gui;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.event.KindleKeyCodes;
import com.amazon.kindle.kindlet.ui.KTextOptionListMenu;
import com.amazon.kindle.kindlet.ui.KTextOptionPane;
import com.example.kindle.utils.KeyboardHelper;
import com.example.kindle.winningfour.App;

/**
 *
 */
public class OptionsPage extends PageState
{
	OptionsPage(final KindletContext context, final String name)
	{
		super(context, name);
		this.focusOwner = this.panel;

		this.options = new KTextOptionPane();
		KTextOptionListMenu boardSize = new KTextOptionListMenu("Board Size", new String[]{"7x6", "8x7", "9x7", "10x7"});
		boardSize.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				int i = 0;
			}
		});
		
		this.options.addListMenu(boardSize);
		this.options.addListMenu(new KTextOptionListMenu("Player", new String[]{"computer", "human"}));
		this.options.addListMenu(new KTextOptionListMenu("Timer", new String[]{"off", "5 sec", "10 sec", "15 sec"}));
		
		this.focusListener = new FocusListener()
		{
			public void focusLost(FocusEvent e)
			{
				OptionsPage.this.active = false;
				App.log("-focus");
			}
			
			public void focusGained(FocusEvent e)
			{
				App.log("+focus");
				if (!OptionsPage.this.active)
				{
					OptionsPage.this.active = true;
					App.log("+back");
					App.pager.back();
				}
			}
		};
		
		this.keyEventDispatcher = new KeyEventDispatcher() {
            public boolean dispatchKeyEvent(final KeyEvent key)
            {

            	if (key.getKeyCode() == KindleKeyCodes.VK_TEXT)
                {
                    key.consume();
                    return true;
                }
                else if (key.getKeyCode() == KindleKeyCodes.VK_FIVE_WAY_SELECT)
                {
                    //key.consume();
                    return false;
                }

                return false;
            }
		};
	}

	public void enter()
	{
		super.enter();
        this.context.setTextOptionPane(this.options);
		App.log("+simulate");
		this.focusOwner.addFocusListener(this.focusListener);
		KeyboardHelper.simulateKey(OptionsPage.this.focusOwner, KindleKeyCodes.VK_TEXT);

		KeyboardFocusManager fm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        fm.addKeyEventDispatcher(this.keyEventDispatcher);
	}

	public void leave()
	{
		super.leave();
        this.context.setTextOptionPane(null);
        this.focusOwner.removeFocusListener(this.focusListener);

        KeyboardFocusManager fm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        fm.removeKeyEventDispatcher(this.keyEventDispatcher);
	}

	KTextOptionPane options;
	FocusListener focusListener;
	KeyEventDispatcher keyEventDispatcher;
}
