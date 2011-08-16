package com.example.kindle.winningfour.boardgame;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.amazon.kindle.kindlet.event.KindleKeyCodes;
import com.example.kindle.boardgame.IPiece;
import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.boardgame.IPosition2D;
import com.example.kindle.boardgame.Position2D;
import com.example.kindle.winningfour.App;

public class HumanPlayer implements IPlayer
{
	public HumanPlayer(final GameController game, final Color color, final String name)
	{
		this.color = color;
		this.name = name;
		this.game = game;
		
		this.keyAdapter = new KeyAdapter()
		{
			public void keyPressed(KeyEvent event)
			{ 
				HumanPlayer.this.onKeyboard(event);
			}
		};
	}
	
	public void destroy()
	{
		if (this.keyAdapter != null)
		{
			this.keyAdapter = null;
		}
	}

	public Color getColor()
	{
		return this.color;
	}

	public String getName()
	{
		return this.name;
	}
	
	public KeyAdapter getKeyAdapter()
	{
		return this.keyAdapter;
	}

	public void interrupt()
	{
	}

	private void onKeyboard(KeyEvent event)
	{
		int key = event.getKeyCode();
		App.log("HumanPlayer::onKeyboard" + event);

		if (event.isActionKey())
		{
            if (key == KindleKeyCodes.VK_BACK)
            {
				event.consume();
            }
            else
            {
				switch (key)
				{
					case KindleKeyCodes.VK_FIVE_WAY_SELECT:
					case KindleKeyCodes.VK_FIVE_WAY_DOWN:
					case KindleKeyCodes.VK_FIVE_WAY_UP:
					{
						IPiece piece = new Piece(this);
						IPosition2D pos = new Position2D(this.game.getSelectedRow(), 0);
						this.game.makeTurn(new Turn(piece, pos, null));
						break;
					}
					case KindleKeyCodes.VK_FIVE_WAY_LEFT:
						this.game.selectPrev();
						break;
					case KindleKeyCodes.VK_FIVE_WAY_RIGHT:
						this.game.selectNext();
						break;
					default: key = 0; break; // ignore
	            }
				event.consume();
            }
        }
		//this.game.repaint();

		App.log("HumanPlayer::onKeyboard done");
	}

	public boolean equals(Object other)
	{
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;

		if (getClass() == IPlayer.class)
		{
			IPlayer lhs = (IPlayer) this;
			IPlayer rhs = (IPlayer) other;
			return rhs.getName().equals(lhs.getName());
		}

		return false;
	}

	private KeyAdapter keyAdapter;
	private GameController game;
	private Color color;
	private String name;
}
