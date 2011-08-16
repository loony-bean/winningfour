package com.example.kindle.winningfour.boardgame;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.amazon.kindle.kindlet.event.KindleKeyCodes;
import com.example.kindle.boardgame.IPiece;
import com.example.kindle.boardgame.IPosition2D;
import com.example.kindle.boardgame.Position2D;
import com.example.kindle.winningfour.App;

public class HumanPlayer extends Player
{
	public HumanPlayer(final Color color, final String name)
	{
		super(color, name);
	}
	
	protected void onKeyboard(KeyEvent event)
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
						IPosition2D pos = new Position2D(App.gamer.getSelectedRow(), 0);
						App.gamer.makeTurn(new Turn(piece, pos, null));
						break;
					}
					case KindleKeyCodes.VK_FIVE_WAY_LEFT:
						App.gamer.selectPrev();
						break;
					case KindleKeyCodes.VK_FIVE_WAY_RIGHT:
						App.gamer.selectNext();
						break;
					default:
						key = 0;
						break;
	            }
				
				event.consume();
            }
        }

		App.log("HumanPlayer::onKeyboard done");
	}
}
