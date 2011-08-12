package com.example.kindle.winningfour.boardgame;

import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.sm.StateMachine;
import com.example.kindle.winningfour.boardgame.states.NewGame;
import com.example.kindle.winningfour.boardgame.states.Player1Turn;
import com.example.kindle.winningfour.boardgame.states.Player1Win;
import com.example.kindle.winningfour.boardgame.states.Player2Turn;
import com.example.kindle.winningfour.boardgame.states.Player2Win;

public class GameStateMachine extends StateMachine
{
	public static final String NEW = "new";
	public static final String TURN = "turn";
	public static final String WIN = "win";

	public GameStateMachine(GameController game, GameView gameView, IPlayer p1, IPlayer p2)
	{
		super();
		
		NewGame newGame = new NewGame(game);
		Player1Turn player1Turn = new Player1Turn(game, p1);
		Player2Turn player2Turn = new Player2Turn(game, p2);
		Player1Win player1Win = new Player1Win(game, p1);
		Player2Win player2Win = new Player2Win(game, p2);

		this.addState(newGame);
    	this.addState(player1Turn);
    	this.addState(player2Turn);
    	this.addState(player1Win);
    	this.addState(player2Win);
    	
    	newGame.onSignal(TURN, player1Turn);
    	player1Turn.onSignal(WIN, player1Win);
    	player1Turn.onSignal(TURN, player2Turn);
    	//player1Turn.onTimer("game_timer", player2Win);
    	player2Turn.onSignal(WIN, player2Win);
    	player2Turn.onSignal(TURN, player1Turn);
    	//player2Turn.onTimer("game_timer", player1Win);
    	player1Win.onSignal(NEW, newGame);
    	player2Win.onSignal(NEW, newGame);

    	this.setInitialState(newGame);
	}
}
