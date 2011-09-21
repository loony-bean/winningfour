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
import com.example.kindle.winningfour.options.AppOptions;
import com.example.kindle.winningfour.options.IOptionsListener;

/**
 *
 */
public class GamePage extends PageState
{
	GamePage(final KindletContext context, final GamePanel parent, final String name)
	{
		super(context, parent, name);

		GameView gameView = App.gamer.getView();

		this.panel = new ImagePanel(AppOptions.FILE_NAME_BOARD);
		this.panel.setPreferredSize((Dimension) App.screenSize.clone());
		KPanel p = new KPanel();

		p.setBackground(new Color(0x000000FF, true));
		p.setLayout(new BorderLayout());
		gameView.setBackground(new Color(0x000000FF, true));
		p.add(gameView, BorderLayout.CENTER);
		p.setPreferredSize((Dimension) App.screenSize.clone());
		this.focusOwner = gameView;

		((ImagePanel) this.panel).setInner(p);
	}

	public void enter()
	{
		super.enter();
		((ImagePanel) this.panel).reset();
		
		App.opts.setOptionsListener(new IOptionsListener()
		{
			public void onOptionsChanged()
			{
				App.opts.apply();
				((ImagePanel) GamePage.this.panel).reset();
			}
		});
		
		App.gamer.start();
		App.gamer.startTimer();
	}

	public void leave()
	{
		super.leave();
		App.opts.setOptionsListener(null);
		this.parent.repaint();
		App.gamer.stopTimer();
	}

    public void destroy()
    {
		App.log("GamePage::destroy");

    	((ImagePanel) this.panel).destroy();
    	
    	super.destroy();

    	App.log("GamePage::destroy done");
    }
}
