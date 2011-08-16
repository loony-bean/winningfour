package com.example.kindle.winningfour.boardgame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.amazon.kindle.kindlet.event.KindleKeyCodes;
import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.sm.SignalEvent;
import com.example.kindle.sm.State;
import com.example.kindle.sm.StateMachine;
import com.example.kindle.winningfour.App;

public class GameStateMachine extends StateMachine
{
	public static final String NEW = "new";
	public static final String TURN = "turn";
	public static final String WIN = "win";
	public static final String DRAW = "draw";

	public GameStateMachine(GameController game, GameView gameView, IPlayer p1, IPlayer p2)
	{
		super();
		
		NewGame newGame = new NewGame(game);
		DrawState drawState = new DrawState(game);
		Player1Turn player1Turn = new Player1Turn(game, p1);
		Player2Turn player2Turn = new Player2Turn(game, p2);
		Player1Win player1Win = new Player1Win(game, p1);
		Player2Win player2Win = new Player2Win(game, p2);

		this.addState(newGame);
		this.addState(drawState);
    	this.addState(player1Turn);
    	this.addState(player2Turn);
    	this.addState(player1Win);
    	this.addState(player2Win);
    	
    	newGame.onSignal(TURN, player1Turn);
    	player1Turn.onSignal(WIN, player1Win);
    	player1Turn.onSignal(DRAW, drawState);
    	player1Turn.onSignal(TURN, player2Turn);
    	//player1Turn.onTimer("game_timer", player2Win);
    	player2Turn.onSignal(WIN, player2Win);
    	player2Turn.onSignal(DRAW, drawState);
    	player2Turn.onSignal(TURN, player1Turn);
    	//player2Turn.onTimer("game_timer", player1Win);
    	player1Win.onSignal(NEW, newGame);
    	player2Win.onSignal(NEW, newGame);

    	this.setInitialState(newGame);
	}

	private class NewGame extends State
	{
		public NewGame(GameController game)
		{
			super("NewGame");
			this.game = game;
		}
		
		public void enter()
		{
			this.game.reset();
			this.game.pulse(new SignalEvent(GameStateMachine.TURN));
			this.game.repaint();
		}

		private GameController game;
	}

	private class TurnState extends GameState
	{
		public TurnState(GameController game, IPlayer player, final String name)
		{
			super(game, player, name);
			this.status = "" + this.getPlayer().getName() + "'s turn";
			this.keyAdapter = this.getPlayer().getKeyAdapter();
		}
		
		public void enter()
		{
			super.enter();
			this.getPlayer().think();
		}
	}

	private class Player1Turn extends TurnState
	{
		public Player1Turn(GameController game, IPlayer player)
		{
			super(game, player, "Player1Turn");
		}
	}

	private class Player2Turn extends TurnState
	{
		public Player2Turn(GameController game, IPlayer player)
		{
			super(game, player, "Player2Turn");
		}
	}

	private class WinState extends GameState
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
		
		public void enter()
		{
			super.enter();
			App.gamer.stop();
		}
	}

	private class Player1Win extends WinState
	{
		public Player1Win(GameController game, IPlayer player)
		{
			super(game, player, "Player1Win");
		}
	}

	private class Player2Win extends WinState
	{
		public Player2Win(GameController game, IPlayer player)
		{
			super(game, player, "Player2Win");
		}
	}

	private class DrawState extends GameState
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
}
