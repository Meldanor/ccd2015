package jchess.gamelogic;

import java.util.Map;

/**
 * Created by stephan on 11.11.2015.
 * Contains data for one chess turn.
 * todo: serializable
 */
public class GameState {

    public Map<String, String> fieldIDbyFigureID;
    public int turnNumber;
    public String activePlayerID;

}
