package com.example.kindle.winningfour;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
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
	public static final int ID_FONT_MENU = 1;
	public static final int ID_FONT_GAME_STATUS = 2;

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
        { "menu_new_game", "New Game" },
        { "menu_options", "Options" },
        { "menu_instructions", "Instructions" },
        { "menu_exit", "Exit" }
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
		
    	return KindletUIResources.getInstance().getFont(KindletUIResources.KFontFamilyName.MONOSPACE, 21, KFontStyle.PLAIN, false);
    }
    
    static private final WeakHashMap imagesMap = new WeakHashMap();
}
