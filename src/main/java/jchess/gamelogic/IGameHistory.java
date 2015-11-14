package jchess.gamelogic;

/**
 * Created by stephan on 13.11.2015.
 */
public interface IGameHistory {
    void getTurnHistory();
    void stepHistoryBackward();
    void stepHistoryForward();
}
