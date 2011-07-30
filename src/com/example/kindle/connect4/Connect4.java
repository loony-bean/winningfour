package com.example.kindle.connect4;

import java.awt.BorderLayout;
import java.util.ResourceBundle;

import com.amazon.kindle.kindlet.AbstractKindlet;
import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KLabel;
import com.amazon.kindle.kindlet.ui.KTextComponent;
import com.amazon.kindle.kindlet.ui.KindletUIResources;

/**
 * A sample application which draws a greeting string on centered on the screen.
 */
public class Connect4 extends AbstractKindlet {
    /**
     * {@inheritDoc}
     */
    public void create(final KindletContext context) {
        // Grab the greeting from the appropriate resource bundle
        final String greeting = ResourceBundle.getBundle("com.example.kindle.connect4.Connect4Resources").getString("greeting");

        final KLabel label = new KLabel(greeting, KTextComponent.CENTER);
        label.setFont(context.getUIResources().getBodyFont(KindletUIResources.KFontFamilyName.SERIF));
        
        // Add the component to the root container
        context.getRootContainer().add(label, BorderLayout.CENTER);
    }
}
