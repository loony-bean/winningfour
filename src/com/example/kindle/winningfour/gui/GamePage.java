/**
 * 
 */
package com.example.kindle.winningfour.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KPanel;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.boardgame.GameView;

/**
 *
 */
public class GamePage extends PageState
{
	GamePage(final KindletContext context, final ImagePanel parent, final String name)
	{
		super(context, parent, name);
		
		GameView gameView = App.gamer.getView();

		this.panel = new ImagePanel("board.png");
		this.panel.setPreferredSize(new Dimension(App.screenSize.width, App.screenSize.height));
		KPanel p = new KPanel();
		
		p.setBackground(new Color(0x000000FF, true));
		p.setLayout(new BorderLayout());
		gameView.setBackground(new Color(0x000000FF, true));
		p.add(gameView, BorderLayout.CENTER);
		p.setPreferredSize(new Dimension(App.screenSize.width, App.screenSize.height));
		gameView.setPreferredSize(new Dimension(App.screenSize.width, App.screenSize.height));
		this.focusOwner = gameView;

		((ImagePanel) this.panel).setInner(p);
	}

	public void enter()
	{
		super.enter();
		((ImagePanel) this.panel).reset();
		App.gamer.start();
	}

	public void leave()
	{
		super.leave();
		this.parent.repaint();
	}
	
    public void destroy()
    {
		App.log("GamePage::destroy");

    	((ImagePanel) this.panel).destroy();
    	
    	super.destroy();

    	App.log("GamePage::destroy done");
    }
}
