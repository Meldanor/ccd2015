package jchess.gamelogic;

import jchess.util.Json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephan on 13.11.2015.
 * used for saving and loading Gamehistory and looking at past turns
 */
public class GameHistory implements IGameHistory {

    private ArrayList<GameState> GameStates;
    private int currentTurnIndex;

    GameHistory() {
        GameStates = new ArrayList<>();
        currentTurnIndex = -1;
    }

    @Override
    public void addGameState(GameState state) {

        if (currentTurnIndex == GameStates.size() - 1) {
            GameStates.add(state);
            currentTurnIndex++;
        }
         /*
        * TODO: Exception Handling
        * */
    }

    @Override
    public GameState getCurrentState() {
        if (currentTurnIndex < 0 || currentTurnIndex >= GameStates.size()) {
        /*
        * TODO: Exception Handling
        * */
            return null;
        }
        return GameStates.get(currentTurnIndex);
    }

    /**
     * Returns History of all turns made.
     */
    @Override
    public List<GameState> getTurnHistory() {
        return GameStates;
    }

    /**
     * Step one turn back in History
     */
    @Override
    public void stepHistoryBackward() {
        if (currentTurnIndex > 0) {
            currentTurnIndex--;
        }
    }

    /**
     * Step one turn forward in History
     */
    @Override
    public void stepHistoryForward() {
        if (currentTurnIndex < GameStates.size() - 1) {
            currentTurnIndex++;
        }
    }

    @Override
    public String save() {
        return Json.getGenson().serialize(this);
    }

    @Override
    public void load(String json) {
        /*
        * TODO: Exception Handling
        * */
        GameHistory loaded = new GameHistory();
        loaded = Json.getGenson().deserialize(json, GameHistory.class);
        this.GameStates = loaded.GameStates;

    }

}
