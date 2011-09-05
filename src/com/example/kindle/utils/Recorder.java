package com.example.kindle.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.example.kindle.utils.FileHelper;
import com.example.kindle.winningfour.App;

public class Recorder
{
	public Recorder(final String filename)
	{
		App.log("Recorder::create - " + filename);

		this.filename = filename;
		this.file = new File(System.getProperty("kindlet.home"), filename);
		this.enabled = true;
		
		App.log("Recorder::create done");
	}
	
	public void restart()
	{
		this.stop();
		this.start();
	}

	public void start()
	{
		App.log("Recorder::start " + this.filename);

		try
		{
			this.writer = new FileWriter(this.file, true);
		}
		catch (IOException e)
		{
			App.log("Recorder::start exception for file " + this.file.getName()); 
		}

		App.log("Recorder::start done");
}

	public void stop()
	{
		App.log("Recorder::stop - " + this.filename);

		if (this.file != null && this.file.exists())
		{
			this.file.delete();
		}
		
		App.log("Recorder::stop done");
	}
	
	public String[] load()
	{
		App.log("Recorder::load - " + this.filename);
		
		if (this.hasData())
		{
			String[] gamelog = FileHelper.read(this.file.getName());
			return gamelog;
		}

		return null;
	}
	
	public boolean hasData()
	{
		return (this.file != null && this.file.exists() && this.file.length() > 0);
	}

	public void record(final String entry)
	{
		App.log("Recorder::record - " + this.filename + " = " + entry);

		if (this.writer != null && this.enabled)
		{
			try
			{
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
		App.log("Recorder::destroy - " + this.filename);

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

		App.log("Recorder::destroy done");
	}

	private boolean enabled;
	private String filename;
	private File file;
	private FileWriter writer;
}
