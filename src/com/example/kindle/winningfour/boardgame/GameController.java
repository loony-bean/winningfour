package com.example.kindle.winningfour.boardgame;

import com.example.kindle.sm.StateMachine;
import com.example.kindle.winningfour.boardgame.states.WinState;
import com.example.kindle.winningfour.boardgame.states.Player1Turn;
import com.example.kindle.winningfour.boardgame.states.Player1Win;
import com.example.kindle.winningfour.boardgame.states.Player2Turn;
import com.example.kindle.winningfour.boardgame.states.Player2Win;

public class GameController extends StateMachine
{
	public static final String WIN = "win";
	public static final String END_TURN = "end_turn";

	public GameController()
	{
		super();
		Player1Turn player1Turn = new Player1Turn();
		Player2Turn player2Turn = new Player2Turn();
		Player1Win player1Win = new Player1Win();
		Player2Win player2Win = new Player2Win();
    	//this.addState(setupGame);
    	this.addState(player1Turn);
    	this.addState(player2Turn);
    	this.addState(player1Win);
    	this.addState(player2Win);
    	player1Turn.onSignal("win", player1Win);
    	player1Turn.onSignal("end_turn", player2Turn);
    	//player1Turn.onTimer("game_timer", player2Win);
    	player2Turn.onSignal("win", player2Win);
    	player2Turn.onSignal("end_turn", player1Turn);
    	//player2Turn.onTimer("game_timer", player1Win);

    	//this.setInitialState(setupGame);
    	this.setInitialState(player1Turn);
	}
}
