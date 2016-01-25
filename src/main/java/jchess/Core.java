package jchess;

import jchess.game.DefaultHexagonalGameboard;
import jchess.game.HexagonalGameboard;
import jchess.ui.HexagonalGameboardGUI;

/**
 * The entrance of the program.
 * @since 02.12.2015
 */
public class Core {
    public static void main(String[] args) {
        System.out.println("Hello World, this is JChess 2.0 by Dash!");

        HexagonalGameboardGUI gui = new HexagonalGameboardGUI();

        HexagonalGameboard gameboard = new DefaultHexagonalGameboard();
        gui.drawGameboardState(gameboard);

    }
}
