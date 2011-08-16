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
import com.example.kindle.winningfour.boardgame.GameController;
import com.example.kindle.winningfour.boardgame.GameView;

/**
 *
 */
public class GamePage extends PageState
{
	GamePage(final KindletContext context, final ImagePanel parent, final String name)
	{
		super(context, parent, name);
		this.panel = new ImagePanel("background2.png");
		this.panel.setPreferredSize(new Dimension(App.screenSize.width, App.screenSize.height));
		KPanel p = new KPanel();
		
		GameView gameView = App.gamer.getView();
		
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
		App.gamer.start();
	}

	public void leave()
	{
		super.leave();
		this.parent.repaint();
	}
}
