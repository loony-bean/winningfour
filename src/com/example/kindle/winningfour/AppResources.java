package com.example.kindle.winningfour;

import java.util.ListResourceBundle;

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
}
