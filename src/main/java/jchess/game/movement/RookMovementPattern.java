package jchess.game.movement;

import jchess.game.Figure;
import jchess.game.Gameboard;
import jchess.game.Position2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Stephan Dörfler on 18.11.2015.
 */
public class RookMovementPattern implements MovementPattern {


    /**
     *
     * @param figure     The figure itself.
     * @param chessboard The chessboard in its current state.
     * @return A List of all possible moves for this rook-figure.
     */
    @Override
    public List<ChessAction> getPossibleActions(Figure figure, Gameboard chessboard) {

        Position2D pos = chessboard.getPositionOf(figure);

        List<ChessAction> actions = new ArrayList<>();

        //check to the left (X,Y-1)
        for (int y = pos.getY(); y > 0 ; y--) {
            if(chessboard.getFigure(Position2D.of(pos.getX(), y)) == Optional.empty()){
                
            }
        }

        //check to the top-left (X-1,Y-1)

        //check to the top-right (X-1,Y)

        //check to the right (X,Y+1)

        //check to the bottom-right (X+1,Y+1)

        //check to the bottom-left (X+1,Y)


        return actions;
    }
}
