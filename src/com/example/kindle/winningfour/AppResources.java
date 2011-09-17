package com.example.kindle.winningfour;

import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListResourceBundle;

import com.amazon.kindle.kindlet.ui.KindletUIResources;
import com.amazon.kindle.kindlet.ui.KindletUIResources.KFontFamilyName;
import com.amazon.kindle.kindlet.ui.KindletUIResources.KFontStyle;
import com.example.kindle.utils.ImageHelper;
import com.example.kindle.winningfour.options.OptionsFactory;
import com.example.kindle.winningfour.skins.ISkin;

/**
 * Resources for the {@link App} application.
 * <p>
 * Resources include:
 * <dl>
 *  <dt>menu_new_game</dt><dd>Main menu new game title</dd>
 * </dl>
 */
public class AppResources extends ListResourceBundle
{
	public static final int ID_FONT_NORMAL 				= 1;
	public static final int ID_FONT_MENU 				= 2;
	public static final int ID_FONT_GAME_STATUS 		= 3;
	public static final int ID_FONT_PAGE_TITLE			= 4;
	
	public static final String KEY_MENU_NEW_GAME 		= "menu_new_game";
	public static final String KEY_MENU_RESUME_GAME		= "menu_resume_game";
	public static final String KEY_MENU_OPTIONS 		= "menu_options";
	public static final String KEY_MENU_INSTRUCTIONS 	= "menu_instructions";

	public static final String KEY_GAME_LEGEND 			= "game_legend";
	public static final String KEY_GAME_TURN 			= "game_turn";
	public static final String KEY_GAME_WIN 			= "game_win";
	public static final String KEY_GAME_DRAW 			= "game_draw";

	public static final String KEY_CONFIRM_NEW_GAME 	= "confirm_new_game";
	public static final String KEY_CONFIRM_EXIT 		= "confirm_exit";
	public static final String KEY_CONFIRM_OPTIONS 		= "confirm_options";
	
	public static final String KEY_ERROR_NO_TURNS 		= "error_no_turns";

	/**
     * {@inheritDoc}
     */
    public Object[][] getContents()
    {
        return contents;
    }

    /**
     * The actual resources required for this bundle.
     */
    static final Object[][] contents =
    {
        { KEY_MENU_NEW_GAME, "New Game" },
        { KEY_MENU_RESUME_GAME, "Resume Game" },
        { KEY_MENU_OPTIONS, "Options" },
        { KEY_MENU_INSTRUCTIONS, "Instructions" },
        { KEY_GAME_LEGEND, "Press N to restart game, I for instructions" },
        { KEY_GAME_TURN, " turn" },
        { KEY_GAME_WIN, " wins!" },
        { KEY_GAME_DRAW, "Drawn game" },
        { KEY_CONFIRM_NEW_GAME, "You have a game in progress. Do you want to start a new game?" },
        { KEY_CONFIRM_EXIT, "Exit game?" },
        { KEY_CONFIRM_OPTIONS, "Pressing OK will apply new options and start a new game."},
        { KEY_ERROR_NO_TURNS, "No available turns." }
    };

    // TODO: images preloading thread

    public static Image getImage(final String key, final Component parent, int width, int height)
    {
		ISkin skin = (new OptionsFactory()).createSkin();
		String fullkey = skin.getName() + "-" + key + "-" + width + "-" + height;

    	Image image = (Image)imagesMap.get(fullkey);
    	if (image == null)
    	{
    		Toolkit tk = Toolkit.getDefaultToolkit();
    		image = tk.createImage(skin.getClass().getResource(key));

    		image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    		ImageHelper.waitForImage(image, parent);
    		imagesMap.put(fullkey, image);
    	}

    	return image;
    }

    public static ISkin getSkin()
    {
    	return (new OptionsFactory()).createSkin();
    }

    public static Font getFont(int id)
    {
    	KindletUIResources res = KindletUIResources.getInstance();
    	Font font = res.getFont(KFontFamilyName.MONOSPACE, 21, KFontStyle.PLAIN, false);

    	if (id == ID_FONT_GAME_STATUS)
    	{
    		font = res.getFont(KFontFamilyName.SERIF, 21, KFontStyle.PLAIN, false);
    	}
    	else if (id == ID_FONT_MENU)
    	{
    		font = res.getFont(KFontFamilyName.SANS_SERIF, 31, KFontStyle.PLAIN, false);
    	}
    	else if (id == ID_FONT_NORMAL)
    	{
    		font = res.getFont(KFontFamilyName.SANS_SERIF, 25, KFontStyle.PLAIN, false);
    	}
    	else if (id == ID_FONT_PAGE_TITLE)
    	{
    		font = res.getFont(KFontFamilyName.SANS_SERIF, 36, KFontStyle.PLAIN, false);
    	}

    	return font;
    }

    static public void destroy()
    {
    	Iterator i = imagesMap.keySet().iterator();
    	while (i.hasNext())
    	{
    		String key = (String)i.next();
    		Image image = (Image) imagesMap.get(key);
    		if (image != null)
    		{
    			image.flush();
    			image = null;
    		}
    	}

    	imagesMap.clear();
    	imagesMap = null;
    }
    
    static private HashMap imagesMap = new HashMap();
}
