package com.example.kindle.winningfour;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.winningfour.boardgame.HumanPlayer;

public class AppOptions
{
	private class PlayerFactory
	{
		public IPlayer create(final String key, final Color color, final String name)
		{
			IPlayer result = null;
			
			if (key.equals("human"))
			{
				//result = new HumanPlayer(color, name);
			}
			else if (key.equals("computer"))
			{
				//result = new ComputerPlayer(color, name);
			}
			
			return result;
		}
	}
	
	private class OptPair
	{
		public OptPair(String key, Object value)
		{
			this.setKey(key);
			this.setValue(value);
		}
			
		public void setKey(String key)
		{
			this.key = key;
		}

		public String getKey()
		{
			return this.key;
		}

		public void setValue(Object value)
		{
			this.value = value;
		}

		public Object getValue()
		{
			return this.value;
		}

		private String key;
		private Object value;
	}
	
	public void init()
	{
		ArrayList sizes = new ArrayList();
		sizes.add(new OptPair("7x6", new Dimension(7, 6)));
		sizes.add(new OptPair("8x7", new Dimension(8, 7)));
		sizes.add(new OptPair("9x7", new Dimension(9, 7)));
		sizes.add(new OptPair("10x7", new Dimension(10, 7)));
		this.model.put("Board Size", sizes);

		if (!this.load())
		{
			this.options.put("Board Size", "7x6");
			this.options.put("Opponent", "human");
			this.options.put("First turn", "you");
			this.options.put("Timer", "off");
			this.options.put("Skin", "classic");
		}
	}

	public void set(final String key, final Object value)
	{
		if (this.options.containsKey(key))
		{
			this.options.put(key, value);
		}
	}

	public Object get(final String key)
	{
		Object result = null;
		
		if (this.options.containsKey(key))
		{
			result = this.options.get(key);
		}
		
		return result;
	}
	
	public boolean load()
	{
		return false;
	}
	
	public void save()
	{
	}
	
	private LinkedHashMap options;
	private LinkedHashMap model;
}
