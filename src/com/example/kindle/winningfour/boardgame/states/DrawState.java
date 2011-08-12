package com.example.kindle.winningfour.boardgame.states;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.amazon.kindle.kindlet.event.KindleKeyCodes;
import com.example.kindle.sm.SignalEvent;
import com.example.kindle.winningfour.boardgame.GameController;
import com.example.kindle.winningfour.boardgame.GameStateMachine;

public class DrawState extends GameState
{
	public DrawState(GameController game)
	{
		super(game, null, "DrawState");
		this.status = "Drawn game. Press Select or N";
		this.keyAdapter = new KeyAdapter()
		{
			public void keyPressed(KeyEvent event)
			{
				int key = event.getKeyCode();
				if (key == KindleKeyCodes.VK_FIVE_WAY_SELECT || key == 'N')
				{
					DrawState.this.pulse(new SignalEvent(GameStateMachine.NEW));
					event.consume();
				}
			}
		};
	}
}
