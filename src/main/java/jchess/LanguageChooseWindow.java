package jchess;

import javax.swing.*;
import java.awt.*;

/**
 * A window that will allow the user to change the language.
 */
public class LanguageChooseWindow extends JDialog {

    JLabel text;
    GridBagLayout gbl;
    GridBagConstraints gbc;

    LanguageChooseWindow(Frame parent) throws Exception {
        super(parent);

        this.setTitle(Settings.lang("choose_language_window_title"));
        Dimension winDim = new Dimension(550, 230);
        this.setMinimumSize(winDim);
        this.setMaximumSize(winDim);
        this.setSize(winDim);
        this.setResizable(false);
        this.setLayout(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.gbl = new GridBagLayout();
        this.gbc = new GridBagConstraints();

        this.setLayout(this.gbl);

        this.text = new JLabel("Later there will be a possibility to change the language here.");
        this.text.setLocation(50, 50);
        this.add(this.text);
    }

}
