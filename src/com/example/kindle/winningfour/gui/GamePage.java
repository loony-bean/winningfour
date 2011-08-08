/**
 * 
 */
package com.example.kindle.winningfour.gui;

import java.awt.BorderLayout;

import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KLabel;

/**
 *
 */
public class GamePage extends PageState
{
	GamePage(final KindletContext context, final String name)
	{
		super(context, name);
		this.panel.setLayout(new BorderLayout());
		this.panel.add(new KLabel("Game"));
		this.gameBoard = new GameView();
		this.panel.add(this.gameBoard, BorderLayout.CENTER);
		this.focusOwner = this.gameBoard;
		this.panel.setBounds(this.root.getBounds());
	}

	public void enter()
	{
		super.enter();
		this.gameBoard.setup();
		this.root.repaint();
	}
	
	GameView gameBoard;
}
