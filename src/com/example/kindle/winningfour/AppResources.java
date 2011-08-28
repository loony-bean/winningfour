package com.example.kindle.winningfour;

import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Iterator;
import java.util.ListResourceBundle;
import java.util.WeakHashMap;

import com.amazon.kindle.kindlet.ui.KindletUIResources;
import com.amazon.kindle.kindlet.ui.KindletUIResources.KFontStyle;
import com.example.kindle.utils.ImageHelper;

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
	public static final String KEY_MENU_EXIT 			= "menu_exit";

	public static final String KEY_CONFIRM_NEW_GAME 	= "confirm_new_game";
	public static final String KEY_CONFIRM_EXIT 		= "confirm_exit";
	
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
        { KEY_MENU_EXIT, "Exit" },
        { KEY_CONFIRM_NEW_GAME, "You have a game in progress. Do you want to start a new game?" },
        { KEY_CONFIRM_EXIT, "Exit game?" },
        { KEY_ERROR_NO_TURNS, "No available turns." }
    };
    
    public static Image getImage(String key, Container parent)
    {
    	Image image = (Image)imagesMap.get(key);
    	if (image == null)
    	{
    		Toolkit tk = Toolkit.getDefaultToolkit();
    		image = tk.createImage(parent.getClass().getResource(key));

    		image = image.getScaledInstance(parent.getWidth(), parent.getHeight(), Image.SCALE_SMOOTH);
    		ImageHelper.waitForImage(image, parent);
    		imagesMap.put(key, image);
    	}
    	
    	return image;
    }
    
    public static Font getFont(final int id)
    {
    	if (id == ID_FONT_GAME_STATUS)
    	{
    		return KindletUIResources.getInstance().getFont(KindletUIResources.KFontFamilyName.MONOSPACE, 21, KFontStyle.PLAIN, false);
    	}
    	else if (id == ID_FONT_MENU)
    	{
    		return KindletUIResources.getInstance().getFont(KindletUIResources.KFontFamilyName.SANS_SERIF, 31, KFontStyle.PLAIN, false);
    	}
    	else if (id == ID_FONT_NORMAL)
    	{
    		return KindletUIResources.getInstance().getFont(KindletUIResources.KFontFamilyName.SANS_SERIF, 25, KFontStyle.PLAIN, false);
    	}
    	else if (id == ID_FONT_PAGE_TITLE)
    	{
    		return KindletUIResources.getInstance().getFont(KindletUIResources.KFontFamilyName.SANS_SERIF, 36, KFontStyle.PLAIN, false);
    	}
		
    	return KindletUIResources.getInstance().getFont(KindletUIResources.KFontFamilyName.MONOSPACE, 21, KFontStyle.PLAIN, false);
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
    }
    
    static private final WeakHashMap imagesMap = new WeakHashMap();
}
