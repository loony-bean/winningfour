/**
 * 
 */
package com.example.kindle.winningfour.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KLabel;
import com.amazon.kindle.kindlet.ui.KPanel;
import com.amazon.kindle.kindlet.ui.KTextArea;
import com.example.kindle.utils.FileHelper;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.AppResources;

/**
 *
 */
public class InstructionsPage extends PageState
{
	InstructionsPage(final KindletContext context, final ImagePanel parent, final String name)
	{
		super(context, parent, name);
		this.panel = new KPanel();
		this.panel.setPreferredSize((Dimension) App.screenSize.clone());
		this.panel.setBackground(new Color(0x000000FF, true));
		this.panel.setLayout(new FlowLayout());

		KLabel title = new KLabel("Instructions");
		title.setFont(AppResources.getFont(AppResources.ID_FONT_PAGE_TITLE));
        this.panel.add(title, BorderLayout.CENTER);

        KTextArea textArea = new KTextArea();
        
        String[] text = FileHelper.read("instructions.txt");
        for (int i = 0; i < text.length; i++)
        {
        	textArea.append(text[i] + "\n");
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
