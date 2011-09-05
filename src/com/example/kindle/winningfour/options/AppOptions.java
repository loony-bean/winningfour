package com.example.kindle.winningfour.options;

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
import com.example.kindle.winningfour.App;

/**
 * Application options manager.
 */
public class AppOptions
{
	public final static String FILE_NAME_OPTIONS	= "options.json";
	public final static String FILE_NAME_GAMELOG	= "gamelog.txt";
	
	public final static String OP_T_BOARD_SIZE		= "Board Size";
	public final static String OP_V_7X6 			= "7x6";
	public final static String OP_V_8X7 			= "8x7";
	public final static String OP_V_9X7 			= "9x7";
	public final static String OP_V_10X7 			= "10x7";

	public final static String OP_T_OPPONENT 		= "Opponent";
	public final static String OP_V_HUMAN 			= "human";
	public final static String OP_V_COMPUTER 		= "computer";

	public final static String OP_T_FIRST_TURN		= "First turn";
	public final static String OP_V_YOU 			= "you";
	public final static String OP_V_OPPONENT		= "opponent";
	//public final static String OP_V_RANDOM 			= "random";
	
	public final static String OP_T_TIMER			= "Timer";
	public final static String OP_V_OFF 			= "off";
	public final static String OP_V_10SEC 			= "10 sec";
	public final static String OP_V_15SEC 			= "15 sec";
	public final static String OP_V_30SEC 			= "30 sec";

	public final static String OP_T_SKIN			= "Skin";
	public final static String OP_V_CLASSIC 		= "classic";
	public final static String OP_V_SKETCHY			= "sketchy";
	public final static String OP_V_CHEESE 			= "cheese";
	public final static String OP_V_URBAN			= "urban";

    /** {@inheritDoc} */
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
	
	/**
	 * Represents option values in options model. Consist of String items
	 * and selection index.
	 */
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

	/**
	 * Default constructor.
	 */
	public AppOptions()
	{
		this.init();
	}
	
	/**
	 * Creates options model and option pane.
	 */
	private void init()
	{
		this.model = new LinkedHashMap();
		this.pendingOptions = new JSONObject();
		this.currentOptions = new JSONObject();

		this.model.put(OP_T_BOARD_SIZE, new OptionValues(new String[]{OP_V_7X6, OP_V_8X7, OP_V_9X7, OP_V_10X7}, 0));
		this.model.put(OP_T_OPPONENT, new OptionValues(new String[]{OP_V_HUMAN, OP_V_COMPUTER}, 0));
		this.model.put(OP_T_FIRST_TURN, new OptionValues(new String[]{OP_V_YOU, OP_V_OPPONENT}, 0));
		this.model.put(OP_T_TIMER, new OptionValues(new String[]{OP_V_OFF, OP_V_10SEC, OP_V_15SEC, OP_V_30SEC}, 0));
		this.model.put(OP_T_SKIN, new OptionValues(new String[]{OP_V_CLASSIC, OP_V_SKETCHY, OP_V_CHEESE, OP_V_URBAN}, 0));

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
	
	/**
	 * Returns option pane previously created.
	 * 
	 * @return KTextOptionPane object.
	 */
	public KTextOptionPane getTextOptionPane()
	{
		return this.textOptionPane;
	}

	/**
	 * Creates option pane by the model data.
	 * 
	 * @return KTextOptionPane object.
	 */
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

	/**
	 * Creates single item in option pane.
	 * 
	 * @param title Option title.
	 * @param values String values.
	 * @param sel Selection index.
	 * @param updater Updater object that will listen for option updates.
	 * 
	 * @return KTextOptionListMenu object.
	 */
	private KTextOptionListMenu createOptionListMenu(final String title, final String[] values, int sel, OptionUpdater updater)
	{
		KTextOptionListMenu item = new KTextOptionListMenu(title, values);
		item.setSelectedIndex(sel);
		item.addItemListener(updater);
		
		return item;
	}

	/**
	 * Obtains option object by its name.
	 * 
	 * @param key Unique string ID of the option.
	 * 
	 * @return Option object.
	 */
	public Object get(final String key)
	{
		Object result = null;
		
		if (this.currentOptions.containsKey(key))
		{
			result = this.currentOptions.get(key);
		}
		
		return result;
	}
	
	/**
	 * Loads options from file.
	 * 
	 * @return True if the options has been loaded successfully, false otherwise.
	 */
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

	/**
	 * Saves current options to file.
	 */
	public void save()
	{
		FileHelper.write(FILE_NAME_OPTIONS, this.currentOptions.toString(), false);
	}

	/**
	 * Applies pending options. Will update the options pane and
	 * save current options to the file.
	 */
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

	/**
	 * Rolls back all pending changes and set option pane values to their
	 * previous state.
	 */
	public void revert()
	{
		if (this.pendingOptions.size() > 0)
		{
			this.pendingOptions.clear();
			this.syncTextOptionPane();
		}
	}

	/**
	 * Indicates if the options has pending changes waiting for commit.
	 * 
	 * @return True if there are pending changes, false otherwise.
	 */
	public boolean isChanged()
	{
		boolean result = false;

		Iterator i = this.pendingOptions.keySet().iterator();
		while (i.hasNext())
		{
			String key = (String)i.next();
			if (this.currentOptions.containsKey(key))
			{
				String lhs = (String) this.currentOptions.get(key);
				String rhs = (String) this.pendingOptions.get(key);
				if (!lhs.equals(rhs))
				{
					result = true;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * Synchronizes options pane with stored option values.
	 */
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

	/**
	 * Object destruction.
	 */
	public void destroy()
	{
		this.currentOptions.clear();
		this.currentOptions = null;
		
		this.pendingOptions.clear();
		this.pendingOptions = null;
		
		this.model.clear();
		this.model = null;

		for (int i = 0; i < this.textOptionPane.size(); i++)
		{
			this.textOptionPane.remove(i);
		}
		
		this.textOptionPane = null;
	}
	
	/** Currently active options data. */
	private JSONObject currentOptions;

	/** Options that are not yet active. Can be committed or reverted. */
	private JSONObject pendingOptions;
	
	/** Options model that defines options titles, items and default values. */
	private LinkedHashMap model;
	
	/** Option pane constructed by the options model. */
	private KTextOptionPane textOptionPane;
}
