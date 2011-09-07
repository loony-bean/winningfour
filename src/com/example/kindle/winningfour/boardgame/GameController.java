package com.example.kindle.winningfour.boardgame;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Iterator;

import com.example.kindle.boardgame.GameEvent;
import com.example.kindle.boardgame.IGame;
import com.example.kindle.boardgame.IGameContext;
import com.example.kindle.boardgame.IGameEventListener;
import com.example.kindle.boardgame.IGameStateListener;
import com.example.kindle.boardgame.IPiece;
import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.boardgame.IPosition2D;
import com.example.kindle.boardgame.IRules;
import com.example.kindle.boardgame.ITurn;
import com.example.kindle.boardgame.Position2D;
import com.example.kindle.sm.SignalEvent;
import com.example.kindle.utils.GameClock;
import com.example.kindle.utils.Recorder;
import com.example.kindle.utils.StringHelper;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.boardgame.rules.ClassicRules;
import com.example.kindle.winningfour.options.AppOptions;
import com.example.kindle.winningfour.options.OptionsFactory;

public class GameController implements IGame
{
	public GameController(final GameView gameView)
	{
		App.log("GameController::create");

		this.gameView = gameView;
		this.stateMachine = new GameStateMachine(this, this.gameView);
		this.recorder = new Recorder(AppOptions.FILE_NAME_GAMELOG);
		this.listeners = new ArrayList();
		this.clock = new GameClock();
		
		App.log("GameController::create done");
	}

	public IGameContext getContext()
	{
		return new GameContext(board, rules, players);
	}

	public void reset()
	{
		App.log("GameController::reset");

		OptionsFactory opfact = new OptionsFactory();

		this.board = new Board(opfact.createBoardSize());
		this.players = opfact.createPlayers();

		this.rules = new ClassicRules();
		this.rules.setEventListener(new IGameEventListener()
		{
			public void onGameEvent(int event)
			{
				if (event == GameEvent.WIN)
				{
					GameController.this.setSelectedRow(-1);
					GameController.this.pulse(new SignalEvent(GameStateMachine.WIN));
				}
				else if (event == GameEvent.DRAW)
				{
					GameController.this.setSelectedRow(-1);
					GameController.this.pulse(new SignalEvent(GameStateMachine.DRAW));
				}
			}
		});

		int bw = this.board.getWidth();
		int center = (bw % 2 == 1) ? bw/2 : bw/2 - 1;
		this.setSelectedRow(center);

		this.gameView.setItems(this.board.getItems());
		this.gameView.reset();

		Runnable clockTask = new Runnable()
		{
			public void run()
			{
				int percents = GameController.this.gameView.getProgressPercents();
				int increment = GameController.this.clock.getIncrement();
				GameController.this.gameView.setProgressTicks(percents + increment);

				long timeout = GameController.this.clock.getTimeout();
				if (GameController.this.clock.getDelta() > timeout * GameClock.RESOLUTION)
				{
					App.gamer.pulse(new SignalEvent(GameStateMachine.TIMEOUT));
				}
			}
		};

		this.clock.reset(clockTask, (new OptionsFactory()).createGameClockTimeout());

		this.repaint();

		App.log("GameController::reset done");
	}

	public void restart()
	{
		App.log("GameController::restart");

		this.stop();
		this.start();

		App.log("GameController::restart done");
	}

	public void start()
	{
		App.log("GameController::start");

		if (!this.stateMachine.isRunning())
		{
			this.stateMachine.start();
			this.setStopped(false);
			this.recorder.start();
		}

		App.log("GameController::start done");
	}

	public void stop()
	{
		App.log("GameController::stop");

		this.setStopped(true);
		this.stateMachine.stop();

		this.recorder.stop();
		
		System.gc();

		App.log("GameController::stop done");
	}
	
	public void setRestoring(boolean flag)
	{
		this.restoring = flag;
	}

	public boolean isRestoring()
	{
		return this.restoring;
	}

	public boolean isSuspended()
	{
		return (this.recorder.hasData());
	}

	public void restore()
	{
		App.log("GameController::restore");

		ArrayList recording = new ArrayList();
		String[] gamelog = this.recorder.load();
		for (int i = 0; i < gamelog.length; i++)
		{
			String[] s = StringHelper.split(gamelog[i], "-");
			recording.add(new Position2D(Integer.parseInt(s[0]), Integer.parseInt(s[1])));
		}
		
		if (recording != null)
		{
			this.setRestoring(true);
			
			this.start();
			this.recorder.setEnabled(false);
			
			IPiece piece1 = new Piece(this.players[0]);
			IPiece piece2 = new Piece(this.players[1]);

			Iterator i = recording.iterator();
			int turnnum = 0;
			while (i.hasNext())
			{
				ITurn turn = null;
				IPosition2D pos = (IPosition2D) i.next();
				if (turnnum % 2 == 0)
				{
					turn = new Turn(piece1, pos);
				}
				else
				{
					turn = new Turn(piece2, pos);
				}

				turnnum += 1;
				this.makeTurn(turn);
			}

			this.setRestoring(false);

			this.recorder.setEnabled(true);
		}

		App.log("GameController::restore done");
	}

	public void makeTurn(final ITurn turn)
	{
		App.log("GameController::makeTurn");

		if(this.rules.isTurnAvailable(this.board, turn))
		{
			this.board.putPiece(turn.getPiece(), turn.getPosition().row());
			this.rules.afterPlayerTurn(this.board);
			this.gameView.setItems(this.board.getItems());

			this.recorder.record(this.board.getLastTurn().toString());
			
			this.pulse(new SignalEvent(GameStateMachine.TURN));
		}

		App.log("GameController::makeTurn done");
	}
	
	public void pulse(final SignalEvent signal)
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

	public void setStatusText(final String status)
	{
		this.gameView.setStatusText(status);
	}

	public void repaint()
	{
		App.log("GameController::repaint");

		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				GameController.this.gameView.repaint();
			}
		});

		App.log("GameController::repaint will post runnable");
	}

	public void setStopped(boolean flag)
	{
		this.stopped = flag;

		Iterator i = this.listeners.iterator();
		while (i.hasNext())
		{
			IGameStateListener listener = (IGameStateListener) i.next();
			if (flag == true)
			{
				listener.onStop();
			}
			else
			{
				listener.onStart();
			}
		}
	}

	public boolean isStopped()
	{
		return this.stopped;
	}

	public void addStateListener(final IGameStateListener listener)
	{
		App.log("GameController::addStateListener");

		this.listeners.add(listener);

		App.log("GameController::addStateListener done");
	}

	public void startTimer()
	{
		this.gameView.setProgressTicks(0);
		this.clock.start();
	}

	public void stopTimer()
	{
		this.clock.stop();
		this.gameView.setProgressTicks(GameClock.RESOLUTION);
	}

	public void destroy()
	{
		App.log("GameController::destroy");

		this.gameView = null;

		this.stateMachine.destroy();
		this.stateMachine = null;

		this.board.destroy();
		this.board = null;

		this.rules.destroy();
		this.rules = null;

		for (int i = 0; i < this.players.length; i++)
		{
			this.players[i].destroy();
			this.players[i] = null;
		}
		this.players = null;

		this.recorder.destroy();
		this.recorder = null;

		this.listeners.clear();
		this.listeners = null;

		this.clock.destroy();
		this.clock = null;
		
		App.log("GameController::destroy done");
	}

	private boolean restoring = false;
	private boolean stopped = true;
	
	private GameView gameView;
	private GameStateMachine stateMachine;
	private Board board;
	private int selectedRow;
	private IRules rules;
	private IPlayer[] players;
	private Recorder recorder;
	private ArrayList listeners;
	private GameClock clock;
}
