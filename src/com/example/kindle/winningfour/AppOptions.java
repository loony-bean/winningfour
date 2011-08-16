package com.example.kindle.winningfour;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;
import java.util.LinkedHashMap;

import com.amazon.kindle.kindlet.ui.KTextOptionListMenu;
import com.amazon.kindle.kindlet.ui.KTextOptionPane;
import com.example.kindle.boardgame.IPlayer;
import com.example.kindle.winningfour.boardgame.ComputerPlayer;
import com.example.kindle.winningfour.boardgame.HumanPlayer;

public class AppOptions
{
	private class OptionUpdater implements ItemListener
	{
		public OptionUpdater(final String key)
		{
			this.key = key;
		}
		
		public void itemStateChanged(ItemEvent e)
		{
			if (e.getStateChange() == ItemEvent.SELECTED)
			{
				App.log(this.key);
				App.log("" + e.getItem());
			}
		}

		private String key;
	}

	private class PlayerFactory
	{
		public IPlayer create(final String key, final Color color, final String name)
		{
			IPlayer result = null;
			
			if (key.equals("human"))
			{
				result = new HumanPlayer(color, name);
			}
			else if (key.equals("computer"))
			{
				result = new ComputerPlayer(color, name);
			}
			
			return result;
		}
	}
	
	private class OptionValues
	{
		public OptionValues(String[] values, int selected)
		{
			this.values = values;
			this.selected = selected;
		}
			
		public String[] values;
		public int selected;
	}
	
	public void init()
	{
		this.model = new LinkedHashMap();
		this.options = new LinkedHashMap();

		this.model.put("Board Size", new OptionValues(new String[]{"6x7", "8x7", "9x7", "10x7"}, 0));
		this.model.put("Opponent", new OptionValues(new String[]{"computer", "human"}, 0));
		this.model.put("First turn", new OptionValues(new String[]{"you", "opponent", "random"}, 0));
		this.model.put("Timer", new OptionValues(new String[]{"off", "5 sec", "10 sec", "15 sec"}, 0));
		this.model.put("Skin", new OptionValues(new String[]{"classic", "magnetic", "baloons", "pirates"}, 0));

		if (!this.load())
		{
			this.options.put("Board Size", "7x6");
			this.options.put("Opponent", "human");
			this.options.put("First turn", "you");
			this.options.put("Timer", "off");
			this.options.put("Skin", "classic");
		}
	}

	public KTextOptionPane createTextOptionPane()
	{
		KTextOptionPane options = new KTextOptionPane();
		Iterator i = this.model.keySet().iterator();
		while (i.hasNext())
		{
			String key = (String)i.next();
			OptionValues items = (OptionValues)this.model.get(key);
			OptionUpdater updater = new OptionUpdater(key);

			options.addListMenu(createOptionListMenu(key, items.values, items.selected, updater));

		}

		return options;
	}

	private KTextOptionListMenu createOptionListMenu(final String title, final String[] values, int sel, OptionUpdater updater)
	{
		KTextOptionListMenu item = new KTextOptionListMenu(title, values);
		item.addItemListener(updater);
		
		return item;
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
