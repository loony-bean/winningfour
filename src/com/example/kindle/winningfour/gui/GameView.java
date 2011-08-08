package com.example.kindle.winningfour.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import com.amazon.kindle.kindlet.event.KindleKeyCodes;
import com.amazon.kindle.kindlet.ui.KImage;
import com.amazon.kindle.kindlet.ui.KLabel;
import com.amazon.kindle.kindlet.ui.KindletUIResources;
import com.amazon.kindle.kindlet.ui.KindletUIResources.KFontStyle;
import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.boardgame.Position2D;
import com.example.kindle.sm.SignalEvent;
import com.example.kindle.utils.ImageHelper;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.boardgame.Board;
import com.example.kindle.winningfour.boardgame.BoardItem;
import com.example.kindle.winningfour.boardgame.Game;
import com.example.kindle.winningfour.boardgame.GameController;
import com.example.kindle.winningfour.boardgame.HumanPlayer;
import com.example.kindle.winningfour.boardgame.Piece;
import com.example.kindle.winningfour.boardgame.rules.classic.Rules;
import com.example.kindle.winningfour.boardgame.rules.classic.TurnValidator;
import com.example.kindle.winningfour.boardgame.states.TurnState;

public class GameView extends Container
{
	private static final long serialVersionUID = -5347103364915065773L;
	Image backgroundImage;
	Image selectorImage;
	KImageSnoop backgroundPane;
	
	//GameImage checkerPane;
	GameViewSelector gameSelector;
	KLabel someLabel;
	Dimension layoutSize;
	KeyAdapter keyListener;

	int hx = 100;
	int hy = 100;
	int s = 50;
	int gap = 10;

	Board board;
	Game game;
	
	HumanPlayer human1 = new HumanPlayer(Color.yellow);
	HumanPlayer human2 = new HumanPlayer(Color.blue);

	
	GameView()
	{
		App.log("GameBoard::GameBoard");
		this.setLayout(null);

		this.board = new Board(new Dimension(7, 6));
		this.game = new Game(new Rules());

		Toolkit tk = Toolkit.getDefaultToolkit();
		//this.backgroundImage = tk.createImage(getClass().getResource("background.gif"));
		this.selectorImage = tk.createImage(getClass().getResource("selector.png"));

		// for now create empty images (we'll load them when we know which size we need)
		//this.backgroundPane = new KImageSnoop(null, "background");
		//this.checkerPane = new GameImage(null);
		this.gameSelector = new GameViewSelector(null, this.board.getWidth());
		this.someLabel = new KLabel("Use the 5-way to make your next turn", KLabel.CENTER);
		
		Font ff = KindletUIResources.getInstance().getFont(KindletUIResources.KFontFamilyName.MONOSPACE, 21, KFontStyle.PLAIN, false);
		this.someLabel.setFont(ff);

		// Add all the stuff in z-order: top items first, to bottom items last 
		//this.add(this.checkerPane);
		this.add(this.gameSelector);
		this.add(this.someLabel);
		//this.add(this.backgroundPane);

		//this.checkerPane.setFocusable(false);
		this.gameSelector.setFocusable(false);
		//this.backgroundPane.setFocusable(false);
		
		this.setFocusable(true);

		this.keyListener = new KeyAdapter() 
		{
			public void keyPressed(KeyEvent event) 
			{ 
				GameView.this.onKeyboard(event); 
			}
		};
	
        this.addKeyListener(this.keyListener);
	    this.setFocusTraversalKeysEnabled(false);  
		
		App.log("GameBoard::GameBoard done");
	}

	void destroy()
	{
		App.log("GameBoard::destroy");

		if (this.keyListener!=null)
		{
			this.removeKeyListener(this.keyListener);
			this.keyListener = null;
		}

		this.removeAll();

		//this.backgroundPane.setImage(null);
		//this.backgroundPane = null;
		
		//this.checkerPane.destroy();
		//this.checkerPane = null;

		this.gameSelector.destroy();
		this.gameSelector = null;

		this.someLabel = null;

		this.backgroundImage.flush();
		this.backgroundImage = null;
		
		this.selectorImage.flush();
		this.selectorImage = null;

		App.log("GameBoard::destroy done");
	}

	public void doLayout()
	{
		App.log("GameBoard::doLayout for " + this.getSize());
		final Dimension thissize = this.getSize();
		
		if (this.layoutSize != null && this.layoutSize.equals(thissize))
		{
			App.log("GameBoard::doLayout early exit (same size)");
			return;
		}

		this.layoutSize = thissize;
		Image image = null;
		
		//this.backgroundPane.setBounds(0, 0, thissize.width, thissize.height);
		//image= this.backgroundImage.getScaledInstance(thissize.width, thissize.height, Image.SCALE_FAST);
		//ImageHelper.waitForImage(image, this);
		//this.backgroundPane.setImage(image);

		int checkersize = 50;
		this.gameSelector.setBounds(thissize.width/2 - checkersize/2,thissize.height/2 - checkersize/2, checkersize, checkersize);
		image = this.selectorImage.getScaledInstance(checkersize, checkersize, Image.SCALE_SMOOTH);
		ImageHelper.waitForImage(image, this);
		this.gameSelector.setImage(image);
		
		this.gameSelector.setLocation(hx, hy - s);

		Dimension sz = this.someLabel.getPreferredSize();
		this.someLabel.setBounds(0, thissize.height - sz.height, thissize.width, sz.height);
		
		App.log("GameBoard::doLayout done");
	}

	public void paint(Graphics g)
	{
		App.log("GameBoard::paint in clipBounds " + g.getClipBounds());
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.BLACK);
		
		Dimension d = this.getSize();
		for (int i = 0; i < d.width - 10; i += 15)
		{
			g2d.fillOval(i+2, 10, 10, 10);
		}
		Rectangle rl = this.someLabel.getBounds();
		g2d.setColor(Color.WHITE);
		for (int i = 0; i < d.width - 10; i += 15)
		{
			g2d.fillRect(i + 2, rl.y - 15, 10, 10);
		}

		ArrayList items = board.getItems();
		Iterator iter = items.iterator();
		while(iter.hasNext())
		{
			BoardItem item = (BoardItem)iter.next();
			if (item.getPiece() != null)
			{
				Position2D pos = (Position2D)item.getPosition();
				Color color = item.getPiece().getPlayer().getColor();
				g2d.setColor(color);
				g2d.fillOval(hx + pos.x()*(s+gap), hy + pos.y()*(s+gap), s, s);
			}
		}
	}

	public void moveSelector(final int key)
	{
		App.log("GameBoard::moveElement " + key);
		
		switch (key)
		{
			case KindleKeyCodes.VK_FIVE_WAY_LEFT: 
				this.gameSelector.moveLeft();
				break;
				
			case KindleKeyCodes.VK_FIVE_WAY_RIGHT: 
				this.gameSelector.moveRight(); 
				break;
		}

		int selectorRow = this.gameSelector.getCurrentRow();
		this.gameSelector.setLocation(hx + selectorRow*(s+gap), this.gameSelector.getY());

		App.log("GameBoard::moveElement done");
	}
	
	private void onKeyboard(KeyEvent event)
	{
		int key = event.getKeyCode();
		App.log("GameBoard::onKeyboard" + event + " *************");

		if (event.isActionKey())
		{
            if (key == KindleKeyCodes.VK_BACK)
            {
            	key = 0; 
				event.consume();
            }
            else
            {
				switch (key)
				{
					case KindleKeyCodes.VK_FIVE_WAY_SELECT:
					{
						int row = this.gameSelector.getCurrentRow();
						TurnState turn = (TurnState)game.getController().getCurrentState();
						IPlayer p = turn.getPlayer();
						this.board.putPiece(new Piece(p), row);
						game.getController().pushEvent(new SignalEvent(GameController.END_TURN));
						this.repaint();
						break;
					}
					case KindleKeyCodes.VK_FIVE_WAY_LEFT:
					case KindleKeyCodes.VK_FIVE_WAY_RIGHT:
						this.moveSelector(key);
						break;
					default: key = 0; break; // ignore
	            }
				event.consume();
            }
        }
		
		switch (key)
		{
			case 'L':
			case 'R':
				this.moveSelector((char)key);
				break;
				
			case 0:
				break;
		}
		
		App.log("GameBoard::onKeyboard done");
	}

	private class KImageSnoop extends KImage
	{
		private static final long serialVersionUID = 1L;
		String nameToLog;
		
		public KImageSnoop(Image image, String nameToLog)
		{
			super(image);
			this.nameToLog = nameToLog;
		}

		public void paint(Graphics g)
		{
			App.log("KImageSnoop::paint for " + this.nameToLog + " in clipBounds " + g.getClipBounds());
			super.paint(g);
		}
	}

	public void setup()
	{
		game.setup();
	}
}
