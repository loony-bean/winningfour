package com.example.kindle.winningfour;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazon.kindle.kindlet.ui.KTextOptionListMenu;
import com.amazon.kindle.kindlet.ui.KTextOptionMenuItem;
import com.amazon.kindle.kindlet.ui.KTextOptionPane;
import com.example.kindle.utils.FileHelper;

public class AppOptions
{
	public final static String FILE_NAME_OPTIONS	= "options.json";
	public final static String FILE_NAME_GAMELOG	= "gamelog.txt";
	
	public final static String OP_T_BOARD_SIZE		= "Board Size";
	public final static String OP_T_OPPONENT 		= "Opponent";
	public final static String OP_T_FIRST_TURN		= "First turn";
	public final static String OP_T_TIMER			= "Timer";
	public final static String OP_T_SKIN			= "Skin";

	private class OptionUpdater implements ItemListener
	{
		public OptionUpdater(final String key, JSONObject opts)
		{
			this.key = key;
			this.opts = opts;
		}
		
		public void itemStateChanged(ItemEvent e)
		{
			if (e.getStateChange() == ItemEvent.SELECTED)
			{
				App.log(this.key + ":" + e.getItem());
				this.opts.put(this.key, e.getItem());
			}
		}

		private JSONObject opts;
		private String key;
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

	public AppOptions()
	{
		this.init();
	}
	
	public void init()
	{
		this.model = new LinkedHashMap();
		this.pendingOptions = new JSONObject();
		this.currentOptions = new JSONObject();

		this.model.put(OP_T_BOARD_SIZE, new OptionValues(new String[]{"7x6", "8x7", "9x7", "10x7"}, 0));
		this.model.put(OP_T_OPPONENT, new OptionValues(new String[]{"human", "computer"}, 0));
		this.model.put(OP_T_FIRST_TURN, new OptionValues(new String[]{"you", "opponent", "random"}, 0));
		this.model.put(OP_T_TIMER, new OptionValues(new String[]{"off", "5 sec", "10 sec", "15 sec"}, 0));
		this.model.put(OP_T_SKIN, new OptionValues(new String[]{"classic", "magnetic", "baloons", "pirates"}, 0));

		if (!this.load())
		{
			Iterator i = this.model.keySet().iterator();
			while (i.hasNext())
			{
				String key = (String)i.next();
				OptionValues items = (OptionValues)this.model.get(key);
				this.currentOptions.put(key, items.values[items.selected]);
			}
			
			this.save();
		}
		
		this.textOptionPane = createTextOptionPane();
		this.syncTextOptionPane();
	}
	
	public KTextOptionPane getTextOptionPane()
	{
		return this.textOptionPane;
	}

	private KTextOptionPane createTextOptionPane()
	{
		KTextOptionPane optionPane = new KTextOptionPane();
		Iterator i = this.model.keySet().iterator();
		while (i.hasNext())
		{
			String key = (String)i.next();
			OptionValues items = (OptionValues)this.model.get(key);
			OptionUpdater updater = new OptionUpdater(key, this.pendingOptions);
			optionPane.addListMenu(createOptionListMenu(key, items.values, items.selected, updater));
		}

		return optionPane;
	}

	private KTextOptionListMenu createOptionListMenu(final String title, final String[] values, int sel, OptionUpdater updater)
	{
		KTextOptionListMenu item = new KTextOptionListMenu(title, values);
		item.setSelectedIndex(sel);
		item.addItemListener(updater);
		
		return item;
	}

	public Object get(final String key)
	{
		Object result = null;
		
		if (this.currentOptions.containsKey(key))
		{
			result = this.currentOptions.get(key);
		}
		
		return result;
	}
	
	public boolean load()
	{
		boolean result = false;
		JSONParser parser = new JSONParser();
		this.currentOptions.clear();

		try
		{
			String[] text = FileHelper.read(FILE_NAME_OPTIONS);
			if (text.length != 0)
			{
				this.currentOptions = (JSONObject) parser.parse(text[0]);
				result = true;
			}
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		};
		
		return result;
	}
	
	public void save()
	{
		FileHelper.write(FILE_NAME_OPTIONS, this.currentOptions.toString(), false);
	}

	public void restart()
	{
		this.pendingOptions.clear();
	}

	public void commit()
	{
		Iterator i = this.pendingOptions.keySet().iterator();
		while (i.hasNext())
		{
			String key = (String)i.next();
			if (this.currentOptions.containsKey(key))
			{
				this.currentOptions.put(key, this.pendingOptions.get(key));
			}
		}
		
		this.syncTextOptionPane();
		this.save();
	}

	public void revert()
	{
		this.pendingOptions.clear();
		this.syncTextOptionPane();
	}
	
	private void syncTextOptionPane()
	{
		Iterator i = this.textOptionPane.get().iterator();
		while (i.hasNext())
		{
			KTextOptionListMenu menu = (KTextOptionListMenu)i.next();
			int selected = 0;
			String key = menu.getTitle();
			Iterator j = menu.get().iterator();
			while (j.hasNext())
			{
				KTextOptionMenuItem item = (KTextOptionMenuItem)j.next();
				if(item.getValue().equals(this.currentOptions.get(key)))
				{
					menu.setSelectedIndex(selected);
					break;
				}
				else
				{
					selected += 1;
				}
			}
		}
	}

	private JSONObject currentOptions;
	private JSONObject pendingOptions;
	private LinkedHashMap model;
	private KTextOptionPane textOptionPane;
}
