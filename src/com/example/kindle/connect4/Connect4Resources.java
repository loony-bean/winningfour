package com.example.kindle.connect4;

import java.util.ListResourceBundle;

/**
 * Resources for the {@link Connect4} application.
 * <p>
 * Resources include:
 * <dl>
 *  <dt>greeting</dt><dd>Displayable greeting.</dd>
 * </dl>
 */
public class Connect4Resources extends ListResourceBundle {
    /**
     * {@inheritDoc}
     */
    public Object[][] getContents() {
        return contents;
    }

    /**
     * The actual resources required for this bundle.
     */
    static final Object[][] contents = {
        { "greeting", "Connect4" }
    };
}
