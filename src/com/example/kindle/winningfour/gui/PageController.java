/**
 * 
 */
package com.example.kindle.winningfour.gui;

import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.event.KindleKeyCodes;
import com.example.kindle.sm.StateMachine;

/**
 *
 */
public class PageController extends StateMachine
{
	public PageController(KindletContext context) 
	{
		super();
		GamePage gamePage = new GamePage(context, "Game");
		MenuPage menuPage = new MenuPage(context, "Menu");
		OptionsPage optionsPage = new OptionsPage(context, "Options");
		InstructionsPage instructionsPage = new InstructionsPage(context, "Instructions");
		ConfirmExitState confirmExitState = new ConfirmExitState(context, "ConfirmExit");
    	this.addState(menuPage);
    	this.addState(gamePage);
    	this.addState(optionsPage);
    	this.addState(instructionsPage);
    	menuPage.onSignal("menu_new_game", gamePage);
    	menuPage.onSignal("menu_options", optionsPage);
    	menuPage.onSignal("menu_instructions", instructionsPage);
    	menuPage.onSignal("menu_exit", confirmExitState);
    	gamePage.onKey(KindleKeyCodes.VK_BACK, menuPage);
    	gamePage.onKey(KindleKeyCodes.VK_TEXT, optionsPage);
    	optionsPage.onKey(KindleKeyCodes.VK_BACK, menuPage);
    	//optionsPage.onKey(KindleKeyCodes.VK_TEXT, menuPage);
    	instructionsPage.onKey(KindleKeyCodes.VK_BACK, menuPage);
    	this.setInitialState(menuPage);
	}
}
