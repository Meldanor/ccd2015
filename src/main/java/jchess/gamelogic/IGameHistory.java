package jchess.gamelogic;

import java.util.List;

/**
 * Created by stephan on 13.11.2015.
 */
public interface IGameHistory {
    void addGameState(GameState state);
    GameState getCurrentState();
    List<GameState> getTurnHistory();
    void stepHistoryBackward();
    void stepHistoryForward();
    String save();
    void load(String json);
}
