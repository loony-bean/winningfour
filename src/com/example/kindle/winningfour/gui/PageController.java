/**
 * 
 */
package com.example.kindle.winningfour.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.KeyboardFocusManager;

import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.event.KindleKeyCodes;
import com.amazon.kindle.kindlet.ui.KOptionPane;
import com.example.kindle.sm.SignalEvent;
import com.example.kindle.sm.State;
import com.example.kindle.sm.StateMachine;
import com.example.kindle.utils.DialogHelper;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.AppResources;

/**
 *
 */
public class PageController extends StateMachine
{
	private class ResetGame extends State
	{
		public ResetGame(String name)
		{
			super(name);
		}
		
		public void enter()
		{
			if (App.gamer.isStopped() == false)
			{
				DialogHelper.ConfirmDialog(App.bundle.getString(AppResources.KEY_CONFIRM_NEW_GAME), new Runnable()
				{
					public void run()
					{
            			App.gamer.stop();
            			App.pager.pushEvent(new SignalEvent(AppResources.KEY_MENU_NEW_GAME));
					}
				});
			}
			else
			{
    			App.pager.pushEvent(new SignalEvent(AppResources.KEY_MENU_NEW_GAME));
			}
		}
	}
	
	public PageController(KindletContext context)
	{
		super();
		
		Container root = context.getRootContainer();
		ImagePanel panel = new ImagePanel("background.gif");
		panel.setPreferredSize(root.getPreferredSize());
		root.add(panel, BorderLayout.CENTER);
		
		ResetGame resetGame = new ResetGame("ResetGame");
		GamePage gamePage = new GamePage(context, panel, "Game");
		MenuPage menuPage = new MenuPage(context, panel, "Menu");
		OptionsPage optionsPage = new OptionsPage(context, panel, "Options");
		InstructionsPage instructionsPage = new InstructionsPage(context, panel, "Instructions");
		ConfirmExitState confirmExitState = new ConfirmExitState(context, panel, "ConfirmExit");
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
    	//gamePage.onKey(KindleKeyCodes.VK_TEXT, optionsPage);
    	//optionsPage.onKey(KindleKeyCodes.VK_BACK, menuPage);
    	//optionsPage.onKey(KindleKeyCodes.VK_TEXT, menuPage);
    	this.setInitialState(menuPage);
	}
}
