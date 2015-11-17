/*
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Authors:
 * Mateusz Sławomir Lach ( matlak, msl )
 */
package jchess;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

/**
 * Class responsible for drawing the fold with local game settings
 *
 * TODO Refactoring and use of the MVC-pattern.
 */
public class DrawLocalSettings extends JPanel implements ActionListener, TextListener {

    JDialog parent; //needet to close newGame window
    JComboBox player1ColorBox;//to choose color of player
    JComboBox player2ColorBox;
    JComboBox player3ColorBox;
    ButtonGroup opponentChoiceGroup;//group for radio buttons
    JRadioButton opponentChoiceHumanHuman;
    JRadioButton opponentChoiceHumanComp;
    JRadioButton opponentChoiceCompComp;
    JFrame localPanel;
    JLabel compLevLab;
    JSlider computerLevel;//slider to choose jChess Engine level
    JTextField firstName;//editable field for nickname
    JTextField secondName;//editable field for nickname
    JTextField thirdName;//editable field for nickname
    JLabel firstNameLab;
    JLabel secondNameLab;
    JLabel thirdNameLab;
    GridBagLayout gbl;
    GridBagConstraints gbc;
    Container cont;
    JSeparator sep;
    JButton okButton;
    JCheckBox timeGame;
    JComboBox time4Game;
    String colors[] =
            {
                    Settings.lang("white"), Settings.lang("black"), Settings.lang("grey")
            };
    String times[] =
            {
                    "1", "3", "5", "8", "10", "15", "20", "25", "30", "60", "120"
            };


    /**
     * Method witch is checking correction of edit tables
     *
     * @param e Object where is saving this what contents edit tables
     */
    public void textValueChanged(TextEvent e) {
        Object target = e.getSource();
        if (target == this.firstName || target == this.secondName) {
            JTextField temp = new JTextField();
            if (target == this.firstName) {
                temp = this.firstName;
            } else if (target == this.secondName) {
                temp = this.secondName;
            }

            int len = temp.getText().length();
            if (len > 8) {
                try {
                    temp.setText(temp.getText(0, 7));
                } catch (BadLocationException exc) {
                    System.out.println("Something wrong in editables: \n" + exc);
                }
            }
        }
    }

    /**
     * Method responsible for changing the options which can make a player
     * when he want to start new local game
     *
     * @param e where is saving data of performed action
     */
    public void actionPerformed(ActionEvent e) {
        Object target = e.getSource();
        if (target == this.opponentChoiceHumanHuman) {
            this.secondName.setEnabled(true);
            this.thirdName.setEnabled(true);
            this.player2ColorBox.setEnabled(true);
            this.player3ColorBox.setEnabled(true);
            this.computerLevel.setEnabled(false);
        } else if (target == this.opponentChoiceHumanComp) {
            this.secondName.setEnabled(true);
            this.thirdName.setEnabled(false);
            this.player2ColorBox.setEnabled(true);
            this.player3ColorBox.setEnabled(false);
            this.computerLevel.setEnabled(true);
        } else if (target == this.opponentChoiceCompComp) {
            this.secondName.setEnabled(false);
            this.thirdName.setEnabled(false);
            this.player2ColorBox.setEnabled(false);
            this.player3ColorBox.setEnabled(false);
            this.computerLevel.setEnabled(true);
        } else if (target == this.player1ColorBox || target == this.player2ColorBox || target == this.player3ColorBox) {
            this.updatePlayerColors((JComboBox) target);
        } else if (target == this.okButton) {
            final int maxNameLength = 9;
            if (this.firstName.getText().length() > maxNameLength) {
                this.firstName.setText(this.trimString(firstName, maxNameLength));
            }
            if (this.secondName.getText().length() > maxNameLength) {
                this.secondName.setText(this.trimString(secondName, maxNameLength));
            }
            if (this.thirdName.getText().length() > maxNameLength) {
                this.thirdName.setText(this.trimString(thirdName, maxNameLength));
            }
            //TODO Prevent equal names.
            if (this.opponentChoiceHumanHuman.isSelected() && (this.firstName.getText().isEmpty()
                    || this.secondName.getText().isEmpty() || this.thirdName.getText().isEmpty())) {
                JOptionPane.showMessageDialog(this, Settings.lang("fill_3_names"));
                return;
            }
            if (this.opponentChoiceHumanComp.isSelected() && (this.firstName.getText().isEmpty()
                    || this.secondName.getText().isEmpty())) {
                JOptionPane.showMessageDialog(this, Settings.lang("fill_2_names"));
                return;
            }
            if (this.opponentChoiceCompComp.isSelected() && this.firstName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, Settings.lang("fill_1_name"));
                return;
            }

            //TODO Updates for third player. Also enable a match against the computer (currently not included).
            Game newGUI = JChessApp.jcv.addNewTab(this.firstName.getText() + " vs " + this.secondName.getText());
            Settings sett = newGUI.settings;//sett local settings variable
            Player pl1 = sett.playerWhite;//set local player variable
            Player pl2 = sett.playerBlack;//set local player variable
            sett.gameMode = Settings.gameModes.newGame;
            //if(this.firstName.getText().length() >9 ) this.firstName.setText(this.firstName.getText(0,8));

            // if-commands probably unnecessary for us, because we'll most likely use fixed first positions
            if (this.player1ColorBox.getActionCommand().equals("biały")) //if first player is white
            {
                pl1.setName(this.firstName.getText());//set name of player
                pl2.setName(this.secondName.getText());//set name of player
            } else //else change names
            {
                pl2.setName(this.firstName.getText());//set name of player
                pl1.setName(this.secondName.getText());//set name of player
            }
            pl1.setType(Player.playerTypes.localUser);//set type of player
            pl2.setType(Player.playerTypes.localUser);//set type of player
            sett.gameType = Settings.gameTypes.local;
            if (this.opponentChoiceHumanComp.isSelected() || this.opponentChoiceCompComp.isSelected()) //if computer oponent is checked
            {
                pl2.setType(Player.playerTypes.computer);
            }
            if (this.timeGame.isSelected()) //if timeGame is checked
            {
                String value = this.times[this.time4Game.getSelectedIndex()];//set time for game
                Integer val = new Integer(value);
                sett.timeLimitSet = true;
                sett.timeForGame = val * 60;//set time for game and mult it to seconds
                newGUI.gameClock.setTimes(sett.timeForGame, sett.timeForGame);
                newGUI.gameClock.start();
            }
            System.out.println(this.time4Game.getActionCommand());
            //this.time4Game.getComponent(this.time4Game.getSelectedIndex());
            System.out.println("****************\nStarting new game: " + pl1.name + " vs. " + pl2.name
                    + "\ntime 4 game: " + sett.timeForGame + "\ntime limit set: " + sett.timeLimitSet
                    + "\n****************");//4test
            newGUI.newGame();//start new Game
            this.parent.setVisible(false);//hide parent
            newGUI.chessboard.repaint();
            newGUI.chessboard.draw();

        }
    }

    /**
     * Initialize the UI-elements and position them.
     * @param parent
     */
    DrawLocalSettings(JDialog parent) {
        super();
        //this.setA//choose oponent
        this.parent = parent;
        this.player1ColorBox = new JComboBox(colors);
        this.player1ColorBox.setSelectedIndex(0);
        this.player2ColorBox = new JComboBox(colors);
        this.player2ColorBox.setSelectedIndex(1);
        this.player3ColorBox = new JComboBox(colors);
        this.player3ColorBox.setSelectedIndex(2);
        this.gbl = new GridBagLayout();
        this.gbc = new GridBagConstraints();
        this.sep = new JSeparator();
        this.okButton = new JButton(Settings.lang("ok"));
        this.compLevLab = new JLabel(Settings.lang("computer_level"));

        this.firstName = new JTextField("", 10);
        this.firstName.setSize(new Dimension(200, 50));
        this.secondName = new JTextField("", 10);
        this.secondName.setSize(new Dimension(200, 50));
        this.thirdName = new JTextField("", 10);
        this.thirdName.setSize(new Dimension(200, 50));
        this.firstNameLab = new JLabel(Settings.lang("first_player_name") + ": ");
        this.secondNameLab = new JLabel(Settings.lang("second_player_name") + ": ");
        this.thirdNameLab = new JLabel(Settings.lang("third_player_name") + ": ");
        this.opponentChoiceGroup = new ButtonGroup();
        this.computerLevel = new JSlider();
        this.timeGame = new JCheckBox(Settings.lang("time_game_min"));
        this.time4Game = new JComboBox(times);

        this.opponentChoiceHumanHuman = new JRadioButton(Settings.lang("vs_human_vs_human"), true);
        this.opponentChoiceHumanComp = new JRadioButton(Settings.lang("vs_human_vs_computer"), false);
        this.opponentChoiceCompComp = new JRadioButton(Settings.lang("vs_computer_vs_computer"), false);

        this.setLayout(gbl);
        this.okButton.addActionListener(this);
        this.opponentChoiceHumanHuman.addActionListener(this);
        this.opponentChoiceHumanComp.addActionListener(this);
        this.opponentChoiceCompComp.addActionListener(this);
        this.player1ColorBox.addActionListener(this);
        this.player2ColorBox.addActionListener(this);
        this.player3ColorBox.addActionListener(this);

        this.secondName.addActionListener(this);

        this.opponentChoiceGroup.add(opponentChoiceHumanHuman);
        this.opponentChoiceGroup.add(opponentChoiceHumanComp);
        this.opponentChoiceGroup.add(opponentChoiceCompComp);
        this.computerLevel.setEnabled(false);
        this.computerLevel.setMaximum(3);
        this.computerLevel.setMinimum(1);

        this.gbc.anchor = GridBagConstraints.CENTER;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.gbc.gridwidth = 3;
        this.addGridElement(opponentChoiceHumanHuman, 0, 0);
        this.addGridElement(opponentChoiceHumanComp, 3, 0);
        this.gbc.gridwidth = 6;
        this.addGridElement(opponentChoiceCompComp, 0, 1);

        this.gbc.insets = new Insets(16, 0, 0, 0);
        this.gbc.gridwidth = 2;
        this.addGridElement(firstNameLab, 0, 2);
        this.addGridElement(secondNameLab, 2, 2);
        this.addGridElement(thirdNameLab, 4, 2);
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.gbc.gridwidth = 2;
        this.addGridElement(firstName, 0, 3);
        this.addGridElement(secondName, 2, 3);
        this.addGridElement(thirdName, 4, 3);

        this.addGridElement(player1ColorBox, 0, 4);
        this.addGridElement(player2ColorBox, 2, 4);
        this.addGridElement(player3ColorBox, 4, 4);

        this.gbc.insets = new Insets(16, 0, 0, 0);
        this.addGridElement(compLevLab, 0, 5);
        this.gbc.gridwidth = 4;
        this.addGridElement(computerLevel, 2, 5);

        this.gbc.gridwidth = 2;
        this.addGridElement(timeGame, 1, 7);
        this.addGridElement(time4Game, 3, 7);

        this.gbc.insets = new Insets(10, 0, 0, 0);
        this.addGridElement(okButton, 2, 8);

        // disable, because there is no AI implementation yet
//        this.opponentChoiceHumanComp.setEnabled(false);
//        this.opponentChoiceCompComp.setEnabled(false);

    }

    /**
     * Method responsible for triming white symbols from strings
     *
     * @param txt    Where is capt value to equal
     * @param length How long is the string
     * @return result trimmed String
     */
    public String trimString(JTextField txt, int length) {
        String result = new String();
        try {
            result = txt.getText(0, length);
        } catch (BadLocationException exc) {
            System.out.println("Something wrong in editables: \n" + exc);
        }
        return result;
    }


    /**
     * Add an UI-element into the GridBagLayout by specifying its position.
     *
     * @param component the component that will be added into the layout.
     * @param gridx the gridx property of a GridBagConstraint for setting the position in the layout
     * @param gridy the gridy property of a GridBagConstraint for setting the position in the layout
     */
    private void addGridElement(Component component, int gridx, int gridy) {
        this.gbc.gridx = gridx;
        this.gbc.gridy = gridy;
        this.gbl.setConstraints(component, this.gbc);
        this.add(component);
    }


    /**
     * Updates the color in the ComboBoxes of the players (if necessary). Prevents from having
     * a single color selected by multiple players.
     *
     * @param changedColorComboBox the color box, that was adjusted by the user.
     */
    private void updatePlayerColors(JComboBox changedColorComboBox) {
        JComboBox otherColorComboBox1 = null;
        JComboBox otherColorComboBox2 = null;

        if (changedColorComboBox == this.player1ColorBox) {
            otherColorComboBox1 = this.player2ColorBox;
            otherColorComboBox2 = this.player3ColorBox;
        } else if (changedColorComboBox == this.player2ColorBox) {
            otherColorComboBox1 = this.player1ColorBox;
            otherColorComboBox2 = this.player3ColorBox;
        } else if (changedColorComboBox == this.player3ColorBox) {
            otherColorComboBox1 = this.player1ColorBox;
            otherColorComboBox2 = this.player2ColorBox;
        } else {
            //TODO Add exception
        }

        int selectedColorIndex1 = changedColorComboBox.getSelectedIndex();
        int selectedColorIndex2 = otherColorComboBox1.getSelectedIndex();
        int selectedColorIndex3 = otherColorComboBox2.getSelectedIndex();
        int currentlyMissingIndex = -1;

        if (selectedColorIndex1 != 0 && selectedColorIndex2 != 0 && selectedColorIndex3 != 0) {
            currentlyMissingIndex = 0;
        } else if (selectedColorIndex1 != 1 && selectedColorIndex2 != 1 && selectedColorIndex3 != 1) {
            currentlyMissingIndex = 1;
        } else if (selectedColorIndex1 != 2 && selectedColorIndex2 != 2 && selectedColorIndex3 != 2) {
            currentlyMissingIndex = 2;
        }

//        System.out.println("Missing Index: " + currentlyMissingIndex);

        if (currentlyMissingIndex != -1) {
            if (changedColorComboBox.getSelectedIndex() == otherColorComboBox1.getSelectedIndex()) {
                otherColorComboBox1.setSelectedIndex(currentlyMissingIndex);
            } else if (changedColorComboBox.getSelectedIndex() == otherColorComboBox2.getSelectedIndex()) {
                otherColorComboBox2.setSelectedIndex(currentlyMissingIndex);
            }
        }
    }

}