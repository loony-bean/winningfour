package com.example.kindle.winningfour.boardgame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.example.kindle.boardgame.IPosition2D;
import com.example.kindle.boardgame.ITurn;
import com.example.kindle.boardgame.Position2D;
import com.example.kindle.utils.FileHelper;
import com.example.kindle.utils.StringHelper;
import com.example.kindle.winningfour.App;

public class Recorder
{
	public Recorder(final String filename)
	{
		this.file = new File(System.getProperty("kindlet.home"), filename);
		this.enabled = true;
	}
	
	public void restart()
	{
		this.stop();
		this.start();
	}

	public void start()
	{
		App.log("Recorder::destroy");

		try
		{
			this.writer = new FileWriter(this.file, true);
		}
		catch (IOException e)
		{
			App.log("Recorder::start exception for file " + this.file.getName()); 
		}

		App.log("Recorder::destroy done");
}

	public void stop()
	{
		if (this.file != null && this.file.exists())
		{
			this.file.delete();
		}
	}
	
	public ArrayList load()
	{
		ArrayList result = new ArrayList();
		if (this.hasData())
		{
			String[] gamelog = FileHelper.read(this.file.getName());
			for (int i = 0; i < gamelog.length; i++)
			{
				String[] s = StringHelper.split(gamelog[i], "-");
				result.add(new Position2D(Integer.parseInt(s[0]), Integer.parseInt(s[1])));
			}
		}

		return result;
	}
	
	public boolean hasData()
	{
		return (this.file != null && this.file.exists() && this.file.length() > 0);
	}

	public void record(ITurn turn)
	{
		if (this.writer != null && this.enabled)
		{
			try
			{
				IPosition2D pos = turn.getPosition();
				String entry = "" + pos.x() + "-" + pos.y() + "\n";
				this.writer.write(entry);
				this.writer.flush();
			}
			catch (IOException e)
			{
				App.log("Recorder::record exception");
			}
		}
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public boolean isEnabled()
	{
		return this.enabled;
	}

	public void destroy()
	{
		if (writer != null)
		{ 
			try
			{
				writer.close();
			}
			catch (IOException e)
			{
				App.log("Recorder::destroy exception");
			} 
		} 
	}

	private boolean enabled;
	private File file;
	private FileWriter writer;
}
