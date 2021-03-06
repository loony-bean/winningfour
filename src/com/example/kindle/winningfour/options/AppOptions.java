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
import com.example.kindle.sm.SignalEvent;
import com.example.kindle.utils.DialogHelper;
import com.example.kindle.utils.FileHelper;
import com.example.kindle.winningfour.App;
import com.example.kindle.winningfour.AppResources;

/**
 * Application options manager.
 */
public class AppOptions
{
	public final static String FILE_NAME_OPTIONS	= "options.json";
	public final static String FILE_NAME_GAMELOG	= "gamelog.txt";
	
	public final static String FILE_NAME_BOARD		= "board.png";

	public final static int STATUS_NO_CHANGES		= 0;
	public final static int STATUS_DISPLAY_CHANGES	= 1;
	public final static int STATUS_GAME_CHANGES		= 2;

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
		public OptionUpdater(final String key, JSONObject opts, final IOptionsListener listener)
		{
			this.key = key;
			this.opts = opts;
			this.listener = listener;
		}
		
		public void itemStateChanged(ItemEvent e)
		{
			if (e.getStateChange() == ItemEvent.SELECTED)
			{
				App.log(this.key + ":" + e.getItem());
				this.opts.put(this.key, e.getItem());
				this.listener.onOptionsChanged();
			}
		}

		private JSONObject opts;
		private String key;
		private IOptionsListener listener;
	}

	/**
	 * Represents option values in options model. Consist of String items
	 * and selection index.
	 */
	private class OptionValues
	{
		public OptionValues(final String[] values, int selected)
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
			OptionUpdater updater = new OptionUpdater(key, this.pendingOptions, new IOptionsListener()
			{
				public void onOptionsChanged()
				{
					AppOptions.this.onOptionsChanged();
				}
			});
			optionPane.addListMenu(createOptionListMenu(key, items.values, items.selected, updater));
		}

		return optionPane;
	}

	/**
	 * Method that accepts notifications from Option Pane menu
	 * items and collect them into one single notification feed.
	 */
	private void onOptionsChanged()
	{
		if (this.listener != null)
		{
			this.listener.onOptionsChanged();
		}
	}

	/**
	 * Sets listener that will be notified when some option on
	 * the Option Pane changes.
	 * 
	 * @param listener Listener object.
	 */
	public void setOptionsListener(final IOptionsListener listener)
	{
		this.listener = listener;
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
	private KTextOptionListMenu createOptionListMenu(final String title, final String[] values, int sel, final OptionUpdater updater)
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
			String[] text = FileHelper.read(App.getHomeFilePath(FILE_NAME_OPTIONS));
			if (text.length != 0)
			{
				this.currentOptions = (JSONObject) parser.parse(text[0]);
				result = true;
			}
		}
		catch (ParseException e)
		{
			// defaults would be okay
			e.printStackTrace();
		};
		
		return result;
	}

	/**
	 * Saves current options to file.
	 */
	public void save()
	{
		FileHelper.write(App.getHomeFilePath(FILE_NAME_OPTIONS), this.currentOptions.toString(), false);
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
	public int getStatus()
	{
		int result = STATUS_NO_CHANGES;

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
					if (key.equals(OP_T_SKIN))
					{
						result = STATUS_DISPLAY_CHANGES;
					}
					else
					{
						result = STATUS_GAME_CHANGES;
						break;
					}
				}
			}
		}

		return result;
	}

	public void apply()
	{
		int opstat = this.getStatus();
		if (opstat == STATUS_DISPLAY_CHANGES)
		{
			this.commit();
			App.gamer.getView().reset();
			App.gamer.repaint();
		}
		else if (opstat == STATUS_GAME_CHANGES)
		{
			if (!App.gamer.isStopped())
			{
				DialogHelper.confirm(App.bundle.getString(AppResources.KEY_CONFIRM_OPTIONS),
						new Runnable() {
							public void run() {
								App.gamer.stop();
								AppOptions.this.commit();
								App.pager.pushEvent(new SignalEvent(AppResources.SIG_NEW_GAME));
							}},
						new Runnable() {
							public void run() {
								AppOptions.this.revert();
							}});
			}
			else
			{
				this.commit();
			}
		}
	}

	/**
	 * Returns list of supported skin names.
	 * 
	 * @return List of available skins name strings.
	 */
	public String[] getSkinNamesList()
	{
		OptionValues skins = (OptionValues)this.model.get(OP_T_SKIN);
		return skins.values;
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

		this.listener = null;

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

	/** Listener to be notified when some option value changes on Option Pane. */
	private IOptionsListener listener;
}
