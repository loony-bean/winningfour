package com.example.kindle.winningfour.boardgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import com.example.kindle.boardgame.GameEvent;
import com.example.kindle.boardgame.IGame;
import com.example.kindle.boardgame.IGameContext;
import com.example.kindle.boardgame.IGameEventListener;
import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.boardgame.IRules;
import com.example.kindle.boardgame.ITurn;
import com.example.kindle.sm.SignalEvent;
import com.example.kindle.winningfour.boardgame.rules.classic.Rules;

public class GameController implements IGame
{
	public GameController(GameView gameView)
	{
		IPlayer p1 = new HumanPlayer(Color.yellow, "Plato");
		IPlayer p2 = new HumanPlayer(Color.blue, "Socrates");

		this.gameView = gameView;
		this.stateMachine = new GameStateMachine(this, this.gameView, p1, p2);
	}

	public IGameContext getContext()
	{
		return null;
	}

	public void reset()
	{
		this.board = new Board(new Dimension(7, 6));

		this.setSelectedRow(this.board.getWidth()/2);
		this.gameView.setItems(this.board.getItems());

		this.rules = new Rules();
		this.rules.setEventListener(new IGameEventListener()
		{
			public void onGameEvent(int event)
			{
				if (event == GameEvent.WIN)
				{
					GameController.this.pulse(new SignalEvent(GameStateMachine.WIN));
				}
				else if (event == GameEvent.DRAW)
				{
					GameController.this.pulse(new SignalEvent(GameStateMachine.DRAW));
				}
			}
		});
		
		this.repaint();
	}

	public void restart()
	{
		this.stop();
		this.start();
	}

	public void start()
	{
		this.stateMachine.start();
		this.setStopped(false);
	}

	public void stop()
	{
		this.setStopped(true);
		this.stateMachine.stop();
	}

	public void makeTurn(ITurn turn)
	{
		if(this.board.isTurnAvailable(turn))
		{
			this.board.putPiece(turn.getPiece(), turn.getPosition().x());
			this.rules.afterPlayerTurn(this.board);
			this.gameView.setItems(this.board.getItems());
			
			this.pulse(new SignalEvent(GameStateMachine.TURN));
		}
	}
	
	public void pulse(SignalEvent signal)
	{
		this.stateMachine.pushEvent(signal);
	}
	
	public Dimension getBoardSize()
	{
		return new Dimension(this.board.getWidth(), this.board.getHeight());
	}
	
	public void selectNext()
	{
		if (this.selectedRow < this.board.getWidth() - 1)
		{
			this.setSelectedRow(this.selectedRow + 1);
		}
	}

	public void selectPrev()
	{
		if (this.selectedRow > 0)
		{
			this.setSelectedRow(this.selectedRow - 1);
		}
	}

	public int getSelectedRow()
	{
		return this.selectedRow;
	}

	public void setSelectedRow(int row)
	{
		this.selectedRow = row;
		this.gameView.setSelectedRow(this.selectedRow);
	}

	public GameView getView()
	{
		return this.gameView;
	}

	public void setStatusText(String status)
	{
		this.gameView.setStatusText(status);
	}

	public void repaint()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				GameController.this.gameView.repaint();
			}
		});
	}

	public void setStopped(boolean flag)
	{
		this.stopped = flag;
	}
	
	public boolean isStopped()
	{
		return this.stopped;
	}

	private boolean stopped = true;
	
	private final GameView gameView;
	private final GameStateMachine stateMachine;
	private Board board;
	private int selectedRow;
	private IRules rules;
}
