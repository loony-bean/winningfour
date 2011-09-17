/**
 * 
 */
package com.example.kindle.winningfour.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Iterator;

import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KLabel;
import com.amazon.kindle.kindlet.ui.KPanel;
import com.amazon.kindle.kindlet.ui.KTextArea;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.AppResources;

/**
 *
 */
public class InstructionsPage extends PageState
{
	InstructionsPage(final KindletContext context, final GamePanel parent, final String name)
	{
		super(context, parent, name);
		this.panel = new KPanel();	
		this.panel.setPreferredSize(new Dimension(App.screenSize.width, App.screenSize.height - 20));
		this.panel.setBackground(new Color(0x000000FF, true));
		this.panel.setLayout(new FlowLayout());

		KLabel title = new KLabel("Instructions");
		title.setFont(AppResources.getFont(AppResources.ID_FONT_PAGE_TITLE));
        this.panel.add(title, BorderLayout.CENTER);

        KTextArea textArea = new KTextArea();

        ArrayList text = new ArrayList();
        text.add("You and your opponent take turns putting pieces into the " +
        		"playing area using the 5-way selector. Press Left or Right " +
        		"to move selector and Down, Up or Select to put a new piece. " +
        		"To win you need to connect four pieces of your color vertically, " +
        		"horizontally or diagonally.");
        text.add("");
        text.add("You can use game options to change the board size, choose " +
        		"computer or human to be your opponent or select who will make " +
        		"the first turn. Also you can adjust game timer and select " +
        		"graphical skin you like.");
        text.add("");
        text.add("After the game is done press N or Select to start a new game.");
        text.add("");
        text.add("Press Back or I to return to the game.");
        Iterator iter = text.iterator();
        while (iter.hasNext())
        {
        	textArea.append("" + iter.next() + "\n");
        }
        
        textArea.setBackground(new Color(0x000000FF, true));
        textArea.setFont(AppResources.getFont(AppResources.ID_FONT_NORMAL));
        textArea.setEditable(false);
        textArea.setFocusable(false);
        
        this.panel.add(textArea);
	}
	
	public void enter()
	{
		super.enter();
	}

	public void leave()
	{
		super.leave();
		this.parent.repaint();
	}
	
    public void destroy()
    {
		App.log("InstructionsPage::destroy");

    	super.destroy();

    	App.log("InstructionsPage::destroy done");
    }
}
