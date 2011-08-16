package com.example.kindle.winningfour.boardgame;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.example.kindle.winningfour.App;

public class ComputerPlayer extends Player
{
	public ComputerPlayer(final Color color, final String name)
	{
		super(color, name);
	}

	public void think()
	{
		App.log("ComputerPlayer::think");
	}

	protected void onKeyboard(KeyEvent event)
	{
		App.log("ComputerPlayer::onKeyboard" + event);
	}
}
