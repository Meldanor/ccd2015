package jchess.gamelogic;

import jchess.*;

import java.util.List;

/**
 * Created by stephan on 11.11.2015.
 * Old Game Logic
 */

public class GameLogic {
    private Settings settings;
    private Chessboard chessboard;
    private Player activePlayer;
    private Moves moves;
    public boolean blockedChessboard;
    List<GameState> movesHistory;

    public Moves getMoves() {
        return moves;
    }

    public GameLogic(Settings settings, Chessboard chessboard, Player activePlayer, boolean blockedChessboard, Moves moves) {
        this.settings = settings;
        this.chessboard = chessboard;
        this.activePlayer = activePlayer;
        this.blockedChessboard = blockedChessboard;
        this.moves = moves;
    }


    /**
     * Method to Start new game
     */
    public void newGame() {

        chessboard.setPieces("", settings.playerWhite, settings.playerBlack);

        //System.out.println("new game, game type: "+settings.gameType.name());

        activePlayer = settings.playerWhite;
        if (activePlayer.playerType != Player.playerTypes.localUser) {
            this.blockedChessboard = true;
        }
        //dirty hacks starts over here :)
        //to fix rendering artefacts on first run
     /*   Game activeGame = JChessApp.jcv.getActiveTabGame();
        if (activeGame != null && JChessApp.jcv.getNumberOfOpenedTabs() == 0) {
            activeGame.chessboard.resizeChessboard(activeGame.chessboard.get_height(false));
            activeGame.chessboard.repaint();
            activeGame.repaint();
        }
        chessboard.repaint();
        this.repaint(); */
        //dirty hacks ends over here :)
    }

    public Settings getSettings() {
        return settings;
    }

    public Chessboard getChessboard() {
        return chessboard;
    }
}
