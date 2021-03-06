package jchess.gamelogic;

import jchess.event.EventBroadcaster;
import jchess.event.EventType;
import jchess.event.impl.FigureSelectedEvent;
import jchess.event.impl.GameboardUpdatedEvent;
import jchess.event.impl.PositionClickedEvent;
import jchess.game.*;
import jchess.game.movement.ChessAction;
import jchess.game.movement.MovementPattern;
import jchess.util.Json;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A Class for Three Player Chess Game Logic.
 */
public class ThreeWayChessGameLogic implements IGameLogic {

    private Gameboard gameBoard;
    private IGameHistory gameHistory;

    private LinkedHashMap<HexagonalPlayerType, Boolean> players;
    private HexagonalPlayerType activePlayer;
    private Iterator<Map.Entry<HexagonalPlayerType, Boolean>> playerIterator;

    private Figure activeFigure;
    private int turnNumber;

    /**
     * @return List of Players with bool depicting player's king's status.
     */
    @Override
    public Map<HexagonalPlayerType, Boolean> getPlayerInformation() {
        return players;
    }


    /**
     * initialize Board and set Figures (currently done with DefaultHexagonalGameboard)
     * initialize GameHistory
     * initialize Order of Players (insertion-order)
     * initialize Turnnumber (turn 1 will be stored with nextTurn())
     * nextTurn() sets activePlayer, increases turnNumber and will fill gameHistory with first GameState
     */
    @Override
    public void initializeGame() {
        /**
         * TODO: GameboardFactory with capability to build a board from a gamehistory (json)
         */
        EventBroadcaster.register(EventType.POSITION_CLICKED, (PositionClickedEvent event) -> onBoardTileSelected(event.getPosition()));

        gameBoard = new DefaultHexagonalGameboard();
        gameHistory = new GameHistory();
        players = new LinkedHashMap<>();
        players.put(HexagonalPlayerType.WHITE, Boolean.TRUE);
        players.put(HexagonalPlayerType.BLACK, Boolean.TRUE);
        players.put(HexagonalPlayerType.GRAY, Boolean.TRUE);
        playerIterator = players.entrySet().iterator();
        turnNumber = 0;
        new GameboardUpdatedEvent(gameBoard).trigger();
        nextTurn();
    }

    /**
     * The order for keys and values while iterating on a LinkedHashMap is guaranteed according to javadoc.
     * http://docs.oracle.com/javase/6/docs/api/java/util/LinkedHashMap.html
     *
     * @return the Player who is able to play next turn.
     */
    private HexagonalPlayerType getNextActivePlayer() {
        HexagonalPlayerType possibleNextPlayer;
        do {
            if (!playerIterator.hasNext())
                playerIterator = players.entrySet().iterator();
            possibleNextPlayer = playerIterator.next().getKey();
            if (players.get(possibleNextPlayer)) break;
        }
        while (activePlayer != possibleNextPlayer);
        return possibleNextPlayer;
    }

    /**
     * @return true if no more turns are possible. Last activePlayer wins.
     */
    @Override
    public boolean isGameEnded() {
        return activePlayer == getNextActivePlayer();
    }

    @Override
    public Gameboard getGameBoard() {
        return gameBoard;
    }

    /**
     * @return return current Gamestate with all Figures on Board, current turn number and current player.
     */
    @Override
    public GameState getCurrentGameState() {
        return GameState.Create(gameBoard.getAllFigures(), turnNumber, activePlayer);
    }

    /**
     * Eventhandler for Tileselection
     *
     * @param tile The Tile ID. Can be null, if no tile is selected.
     */
    @Override
    public void onBoardTileSelected(Position2D tile) {
        /**
         * If no tile was selected, nothing happens
         */
        if (tile == null) return;
        /**
         * Check if a Figure was already selected
         */
        if (activeFigure == null) {
            /**
             * if no Figure was selected, save selected tile/figure;
             * if selected tile/figure is not a figure, user clicked on empty tile, do nothing
             * we now know a figure was selected, save selectedFigure
             * if player is not owner of selected figure, do nothing.
             * now the only possibility is that the Figure is his own, set activeFigure to selectedFigure
             */
            Optional<Figure> selected = gameBoard.getFigure(tile);
            if (!selected.isPresent()) return;

            Figure selectedFigure = selected.get();
            if (selectedFigure.getOwner() != activePlayer) return;

            activeFigure = selectedFigure;
            new FigureSelectedEvent(tile, getPossibleMoves()).trigger();
        } else {

            /**
             * An activeFigure was selected now check for possible Actions
             */
            boolean isValidMove = getPossibleMoves().stream().anyMatch(chessaction -> chessaction.getEndPosition() == tile);
            if (isValidMove) {
                gameBoard.moveTo(gameBoard.getPositionOf(activeFigure), tile);
                new GameboardUpdatedEvent(gameBoard).trigger();
                nextTurn();
            }
            /**
             * If not a valid move, deselect figure
             */
            else {
                activeFigure = null;
                new FigureSelectedEvent(null, null).trigger();
            }
        }
    }

    /**
     * @param player the Player whose figurelist is filtered for the king
     * @return true if a king is found in players figurelist.
     */
    boolean hasPlayerKingFigure(HexagonalPlayerType player) {
        Stream<Figure> figures = gameBoard.getAllFigures().values().stream();

        return figures.filter(figure -> figure.getOwner() == player)
                .anyMatch(figure -> figure.getType() == FigureType.KING);

    }

    /**
     * Setting the next Player, reset activeFigure, increase turnNumber
     * and update the boolean entry of the players Map according to the status of the player's King.
     * Also create a new GameState and add it to the gameHistory.
     */
    private void nextTurn() {
        activePlayer = getNextActivePlayer();
        activeFigure = null;
        turnNumber++;

        Stream<HexagonalPlayerType> players = this.players.keySet().stream();
        players.forEach(player -> this.players.put(player, hasPlayerKingFigure(player)));

        GameState state = GameState.Create(gameBoard.getAllFigures(), turnNumber, activePlayer);
        gameHistory.addGameState(state);

    }


    /**
     * @return Flattened List of possible Actions the selected Figure can do
     * List is flattened so possible Actions of a Figure with multiple movementPatterns is easier to handle.
     */
    @Override
    public List<ChessAction> getPossibleMoves() {
        if (activeFigure == null) {
            return new ArrayList<>();
        }
        List<MovementPattern> patterns = activeFigure.getPattern();
        Function<MovementPattern, Stream<ChessAction>> mapper = (pattern) -> pattern.getPossibleActions(activeFigure, gameBoard).stream();
        Stream<ChessAction> moves = patterns.stream().flatMap(mapper);
        return moves.collect(Collectors.toList());
    }

    /**
     * @return current active Player
     */
    @Override
    public HexagonalPlayerType getCurrentPlayer() {
        return activePlayer;
    }

    /**
     * @return selected Figure
     */
    @Override
    public Figure getActiveFigure() {
        return activeFigure;
    }

    /**
     * @return serialized gameHistory for saving a Game
     */
    @Override
    public String saveGame() {
        return Json.getGenson().serialize(gameHistory);
    }

    /**
     * @param json a String to deserialize to the gameHistory.
     */
    @Override
    public void loadGame(String json) {
        /**
         * TODO: Exception Handling
         */
        gameHistory = Json.getGenson().deserializeInto(json, gameHistory);
        /**
         * TODO: Set Board according to current GameState
         */
    }


}
