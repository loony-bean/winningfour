package com.example.kindle.winningfour.boardgame.states;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.amazon.kindle.kindlet.event.KindleKeyCodes;
import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.sm.SignalEvent;
import com.example.kindle.winningfour.boardgame.GameController;
import com.example.kindle.winningfour.boardgame.GameStateMachine;

public class WinState extends GameState
{
	public WinState(GameController game, IPlayer player, final String name)
	{
		super(game, player, name);
		this.status = "" + this.getPlayer().getName() + " wins. Press Select or N";
		this.keyAdapter = new KeyAdapter()
		{
			public void keyPressed(KeyEvent event)
			{
				int key = event.getKeyCode();
				if (key == KindleKeyCodes.VK_FIVE_WAY_SELECT || key == 'N')
				{
					WinState.this.pulse(new SignalEvent(GameStateMachine.NEW));
					event.consume();
				}
			}
		};
	}
}
