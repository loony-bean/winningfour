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
		this.panel = new ImagePanel("background.gif");
		panel.setPreferredSize(root.getPreferredSize());
		root.add(panel, BorderLayout.CENTER);
		
		ResetGame resetGame = new ResetGame("ResetGame");
		GamePage gamePage = new GamePage(context, panel, "Game");
		MenuPage menuPage = new MenuPage(context, panel, "Menu");
		OptionsPage optionsPage = new OptionsPage(context, panel, "Options");
		InstructionsPage instructionsPage = new InstructionsPage(context, panel, "Instructions");
		ConfirmExitState confirmExitState = new ConfirmExitState("ConfirmExit");
    	this.addState(menuPage);
    	this.addState(gamePage);
    	this.addState(optionsPage);
    	this.addState(instructionsPage);
    	resetGame.onSignal(AppResources.KEY_MENU_NEW_GAME, gamePage);
    	menuPage.onSignal(AppResources.KEY_MENU_NEW_GAME, resetGame);
    	menuPage.onSignal(AppResources.KEY_MENU_RESUME_GAME, gamePage);
    	menuPage.onSignal(AppResources.KEY_MENU_OPTIONS, optionsPage);
    	menuPage.onSignal(AppResources.KEY_MENU_INSTRUCTIONS, instructionsPage);
    	menuPage.onSignal(AppResources.KEY_MENU_EXIT, confirmExitState);
    	gamePage.onKey(KindleKeyCodes.VK_BACK, menuPage);
    	instructionsPage.onKey(KindleKeyCodes.VK_BACK, menuPage);

    	this.setInitialState(menuPage);
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
							App.pager.pushEvent(new SignalEvent(AppResources.KEY_MENU_NEW_GAME));
						}},
					new Runnable() {
						public void run() {
            				App.pager.back();
						}});
			}
			else
			{
    			App.pager.pushEvent(new SignalEvent(AppResources.KEY_MENU_NEW_GAME));
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
	
	private ImagePanel panel;
}
