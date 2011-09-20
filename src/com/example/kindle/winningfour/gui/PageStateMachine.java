/**
 * 
 */
package com.example.kindle.winningfour.gui;

import java.awt.BorderLayout;
import java.awt.Container;

import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.event.KindleKeyCodes;
import com.example.kindle.sm.SignalEvent;
import com.example.kindle.sm.State;
import com.example.kindle.sm.StateMachine;
import com.example.kindle.utils.DialogHelper;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.AppResources;

/**
 *
 */
public class PageStateMachine extends StateMachine
{
	public PageStateMachine(final KindletContext context)
	{
		super();

		Container root = context.getRootContainer();
		this.panel = new GamePanel();
		panel.setPreferredSize(root.getPreferredSize());
		root.add(panel, BorderLayout.CENTER);

		InitGame initGame = new InitGame("InitGame");
		ResetGame resetGame = new ResetGame("ResetGame");
		GamePage gamePage = new GamePage(context, panel, "Game");
		OptionsPage optionsPage = new OptionsPage(context, panel, "Options");
		InstructionsPage instructionsPage = new InstructionsPage(context, panel, "Instructions");
    	this.addState(initGame);
    	this.addState(resetGame);
    	this.addState(gamePage);
    	this.addState(optionsPage);
    	this.addState(instructionsPage);
    	initGame.onSignal(AppResources.SIG_NEW_GAME, gamePage);
    	resetGame.onSignal(AppResources.SIG_NEW_GAME, gamePage);
    	gamePage.onKey('I', instructionsPage);
    	gamePage.onKey('N', resetGame);
    	gamePage.onKey(KindleKeyCodes.VK_TEXT, optionsPage);
    	gamePage.onSignal(AppResources.SIG_NEW_GAME, resetGame);
    	instructionsPage.onKey(KindleKeyCodes.VK_BACK, gamePage);
    	instructionsPage.onKey('I', gamePage);

    	this.setInitialState(initGame);
	}

	private class InitGame extends State
	{
		public InitGame(final String name)
		{
			super(name);
		}
		
		public void enter()
		{
			//AppResources.preload(App.gamer.getView());
			App.pager.pushEvent(new SignalEvent(AppResources.SIG_NEW_GAME));
		}
	}

	private class ResetGame extends State
	{
		public ResetGame(final String name)
		{
			super(name);
		}
		
		public void enter()
		{
			if (App.gamer.isStopped() == false)
			{
				DialogHelper.confirm(App.bundle.getString(AppResources.KEY_CONFIRM_NEW_GAME),
					new Runnable() {
						public void run() {
							App.gamer.stop();
							App.pager.pushEvent(new SignalEvent(AppResources.SIG_NEW_GAME));
						}},
					new Runnable() {
						public void run() {
            				App.pager.back();
						}});
			}
			else
			{
    			App.pager.pushEvent(new SignalEvent(AppResources.SIG_NEW_GAME));
			}
		}
	}

	public void destroy()
	{
		App.log("PageStateMachine::destroy");

		super.destroy();
		
		this.panel.destroy();
		this.panel = null;

		App.log("PageStateMachine::destroy done");
	}

	private GamePanel panel;
}
