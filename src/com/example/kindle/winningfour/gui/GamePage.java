/**
 * 
 */
package com.example.kindle.winningfour.gui;

import java.awt.BorderLayout;

import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KPanel;
import com.example.kindle.winningfour.boardgame.GameController;
import com.example.kindle.winningfour.boardgame.GameView;

/**
 *
 */
public class GamePage extends PageState
{
	GamePage(final KindletContext context, final String name)
	{
		super(context, name);
		this.panel = new KPanel();
		this.panel.setLayout(new BorderLayout());
		this.gameView = new GameView();
		this.panel.add(this.gameView, BorderLayout.CENTER);
		this.focusOwner = this.gameView;
		this.panel.setBounds(this.root.getBounds());

		this.gameController = new GameController(gameView);
	}

	public void enter()
	{
		super.enter();
		this.gameController.start();
		this.panel.repaint();
	}

	GameController gameController;
	GameView gameView;
}
