package jchess.gamelogic;

import jchess.game.*;
import jchess.game.movement.ChessAction;
import jchess.game.movement.MovementPattern;
import jchess.util.Json;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *  A Class for Three Player Chess Game Logic.
 */
public class ThreeWayChessGameLogic implements IGameLogic {

    private Gameboard _Board;
    private IGameHistory _History;

    private LinkedHashMap<HexagonalPlayerType, Boolean> _Players;
    private HexagonalPlayerType _activePlayer;
    private Iterator<Map.Entry<HexagonalPlayerType, Boolean>> _playerIterator;

    private Figure _activeFigure;
    private int _turnNumber;

    /**
     *
     * @return List of Players with bool depicting player's king's status.
     */
    @Override
    public Map<HexagonalPlayerType, Boolean> getPlayerInformation() {
        return _Players;
    }


    /**
     * initialize Board and set Figures (currently done with DefaultHexagonalGameboard)
     * initialize GameHistory
     * initialize Order of Players (insertion-order)
     * initialize Turnnumber (turn 1 will be stored with nextTurn())
     * nextTurn() sets _activePlayer, increases _turnNumber and will fill _History with first GameState
     */
    @Override
    public void initializeGame() {
        /**
         * TODO: need GameboardFactory with capability to build a board from a gamehistory (json)
         */
        _Board = new DefaultHexagonalGameboard();
        _History = new GameHistory();
        _Players = new LinkedHashMap<HexagonalPlayerType, Boolean>();
        _Players.put(HexagonalPlayerType.WHITE, Boolean.TRUE);
        _Players.put(HexagonalPlayerType.BLACK, Boolean.TRUE);
        _Players.put(HexagonalPlayerType.GRAY, Boolean.TRUE);
        _playerIterator = _Players.entrySet().iterator();
        _turnNumber = 0;
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
            if (!_playerIterator.hasNext())
                _playerIterator = _Players.entrySet().iterator();
            possibleNextPlayer = _playerIterator.next().getKey();
            if (_Players.get(possibleNextPlayer)) break;
        }
        while (_activePlayer != possibleNextPlayer);
        return possibleNextPlayer;
    }

    /**
     * @return true if no more turns are possible. Last activePlayer wins.
     */
    @Override
    public boolean isGameEnded() {
        return _activePlayer == getNextActivePlayer();
    }

    @Override
    public Gameboard getGameBoard() {
        return _Board;
    }

    /**
     * @return return current Gamestate with all Figures on Board, current turn number and current player.
     */
    @Override
    public GameState getCurrentGameState() {
        return GameState.Create(_Board.getAllFigures(), _turnNumber, _activePlayer);
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
        if (_activeFigure == null) {
            /**
             * if no Figure was selected, save selected tile/figure;
             * if selected tile/figure is not a figure, user clicked on empty tile, do nothing
             * we now know a figure was selected, safe selectedFigure
             * if player is not owner of selected figure, do nothing.
             * now the only possibility is that the Figure is his own, set activeFigure to selectedFigure
             */
            Optional<Figure> selected = _Board.getFigure(tile);
            if (!selected.isPresent()) return;
            Figure selectedFigure = selected.get();
            if (selectedFigure.getOwner() != _activePlayer) return;
            _activeFigure = selectedFigure;
        } else {
            /**
             * An activeFigure was selected now check for possible Actions
             */
            boolean isValidMove = getPossibleMoves().stream().anyMatch(chessaction -> chessaction.getEndPosition() == tile);
            if (isValidMove) {
                _Board.moveTo(_Board.getPositionOf(_activeFigure), tile);
                nextTurn();
            }
            /**
             * If not a valid move, deselect figure
             */
            else {
                _activeFigure = null;
            }
        }
    }

    /**
     * @param player the Player whose figurelist is filtered for the king
     * @return true if there is no king in players figurelist.
     */
    boolean isKingDead(HexagonalPlayerType player) {
        Stream<Figure> figures = _Board.getAllFigures().values().stream();

        boolean isKingAlive = figures.filter(figure -> figure.getOwner() == player)
                .anyMatch(figure -> figure.getType() == FigureType.KING);

        return !isKingAlive;

    }

    /**
     * Setting the next Player, reset activeFigure, increase turnNumber
     * and update the boolean entry of the _Players Map according to the status of the player's King.
     * Also create a new GameState and add it to the History.
     */
    private void nextTurn() {
        _activePlayer = getNextActivePlayer();
        _activeFigure = null;
        _turnNumber++;

        Stream<HexagonalPlayerType> players = _Players.keySet().stream();
        players.forEach(player -> _Players.put(player, !isKingDead(player)));

        GameState state = GameState.Create(_Board.getAllFigures(), _turnNumber, _activePlayer);
        _History.addGameState(state);

    }


    /**
     * @return Flattened List of possible Actions the selected Figure can do
     * List is flattened so possible Actions of a Figure with multiple movementPatterns is easier to handle.
     */
    @Override
    public ArrayList<ChessAction> getPossibleMoves() {
        if (_activeFigure == null) {
            return new ArrayList<>();
        }
        List<MovementPattern> patterns = _activeFigure.getPattern();
        Function<MovementPattern, Stream<ChessAction>> mapper = (pattern) -> pattern.getPossibleActions(_activeFigure, _Board).stream();
        Stream<ChessAction> moves = patterns.stream().flatMap(mapper);
        return moves.collect(Collectors.toCollection(ArrayList<ChessAction>::new));
    }

    /**
     *
     * @return current active Player
     */
    @Override
    public HexagonalPlayerType getCurrentPlayer() {
        return _activePlayer;
    }

    /**
     * @return selected Figure
     */
    @Override
    public Figure getActiveFigure() {
        return _activeFigure;
    }

    /**
     * @return serialized History for saving a Game
     */
    @Override
    public String saveGame() {
        return Json.getGenson().serialize(_History);
    }

    /**
     * @param json a String to deserialize to the GameHistory _History.
     */
    @Override
    public void loadGame(String json) {
        /**
         * TODO: Exception Handling
         */
        _History = Json.getGenson().deserializeInto(json, _History);
        /**
         * TODO: Set Board according to current GameState
         */
    }


}
