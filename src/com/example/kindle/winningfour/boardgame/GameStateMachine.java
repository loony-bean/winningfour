package com.example.kindle.winningfour.boardgame;

import com.example.kindle.sm.SignalEvent;
import com.example.kindle.sm.State;
import com.example.kindle.sm.StateMachine;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.AppResources;

public class GameStateMachine extends StateMachine
{
	public static final String NEW = "new";
	public static final String TURN = "turn";
	public static final String WIN = "win";
	public static final String DRAW = "draw";
	public static final String TIMEOUT = "timeout";

	public GameStateMachine(final GameController game, final GameView gameView)
	{
		super();
		
		NewGame newGame = new NewGame(game);
		DrawState drawState = new DrawState(game);
		Player1Turn player1Turn = new Player1Turn(game);
		Player2Turn player2Turn = new Player2Turn(game);
		Player1Win player1Win = new Player1Win(game);
		Player2Win player2Win = new Player2Win(game);

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
    	player1Turn.onSignal(TIMEOUT, player2Win);
    	player2Turn.onSignal(WIN, player2Win);
    	player2Turn.onSignal(DRAW, drawState);
    	player2Turn.onSignal(TURN, player1Turn);
    	player2Turn.onSignal(TIMEOUT, player1Win);
    	drawState.onSignal(NEW, newGame);
    	player1Win.onSignal(NEW, newGame);
    	player2Win.onSignal(NEW, newGame);

    	this.setInitialState(newGame);
	}

	private class NewGame extends State
	{
		public NewGame(final GameController game)
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
		
		public void destroy()
		{
			App.log("NewGame::destroy");

			super.destroy();
			this.game = null;

			App.log("NewGame::destroy done");
		}

		private GameController game;
	}

	private class TurnState extends GameState
	{
		public TurnState(final GameController game, int player, final String name)
		{
			super(game, player, name);
		}
		
		public void enter()
		{
			this.status = "" + this.getPlayer().getName() + App.bundle.getString(AppResources.KEY_GAME_TURN);
			this.keyAdapter = this.getPlayer().getKeyAdapter();

			super.enter();

			if (!App.gamer.isRestoring())
			{
				this.getPlayer().think(App.gamer.getContext());
				App.gamer.startTimer();
			}
		}

		public void leave()
		{
			super.leave();
			App.gamer.stopTimer();
		}
	}

	private class Player1Turn extends TurnState
	{
		public Player1Turn(final GameController game)
		{
			super(game, 0, "Player1Turn");
		}
	}

	private class Player2Turn extends TurnState
	{
		public Player2Turn(final GameController game)
		{
			super(game, 1, "Player2Turn");
		}
	}

	private class WinState extends GameState
	{
		public WinState(final GameController game, int player, final String name)
		{
			super(game, player, name);
			this.keyAdapter = this.endGameKeyAdapter;
		}
		
		public void enter()
		{
			this.status = "" + this.getPlayer().getName() + App.bundle.getString(AppResources.KEY_GAME_WIN);
			super.enter();
			App.gamer.stop();
		}
	}

	private class Player1Win extends WinState
	{
		public Player1Win(final GameController game)
		{
			super(game, 0, "Player1Win");
		}
	}

	private class Player2Win extends WinState
	{
		public Player2Win(final GameController game)
		{
			super(game, 1, "Player2Win");
		}
	}

	private class DrawState extends GameState
	{
		public DrawState(final GameController game)
		{
			super(game, 0, "DrawState");
			this.status = App.bundle.getString(AppResources.KEY_GAME_DRAW);
			this.keyAdapter = this.endGameKeyAdapter;
		}
		
		public void enter()
		{
			super.enter();
			App.gamer.stop();
		}
	}
}
