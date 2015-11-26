package jchess.game.movement;

import jchess.game.Figure;
import jchess.game.Gameboard;
import jchess.game.Position2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by stephan on 23.11.2015.
 */
public class KnightMovement implements MovementPattern {
    /**
     * Provides a list of possible action the figure can perform (but needn't).
     *
     * @param figure     The figure it self.
     * @param chessboard The chessboard in its current state.
     * @return A non-null list (can be empty) containing possible actions. An empty list indicates, that this figure
     * cannot do anything at the moment (like the rook at the start).
     */

    @Override
    public List<ChessAction> getPossibleActions(Figure figure, Gameboard chessboard) {

        /*
        * Knight is able to move one straight and one diagonal.
        * The diagonal moves look like this: one straight, one to each side.
        * The diagonal-restraint is ignored by knights ability to jump over other figures.
        * https://github.com/Meldanor/ccd2015/wiki/Knight
        * https://github.com/Meldanor/ccd2015/wiki
        * */

        int[][] posDelta    = {
                {-3,-1},        //NE1   = NE,NE,NW
                {-2,1},         //NE2   = NE,NE,E
                {-1,2},         //E1    = E,E,NE
                {1,3},          //E2    = E,E,SE
                {2,3},          //SE1   = SE,SE,E
                {3,2},          //SE2   = SE,SE,SW
                {3,1},          //SW1   = SW,SW,SE
                {2,-1},         //SW2   = SW,SW,W
                {1,-2},         //W1    = W,W,SW
                {-1,-3},        //W2    = W,W,NW
                {-2,-3},        //NW1   = NW,NW,W
                {-3,-2}         //NW2   = NW,NW,NE
        };

        Position2D pos = chessboard.getPositionOf(figure);
        List<ChessAction> knightMovements = new ArrayList<>();

        int x = pos.getX();
        int y = pos.getY();

        //create List of possible Movements
        for (int[] delta:posDelta){
            Position2D possibleMove = Position2D.of(x+delta[0],y+delta[1]);

            //check if possibleMove would be in bounds of chessboard
            if(!chessboard.isInField(possibleMove)) continue;

            //check if target-field is occupied by a figure
            Optional<Figure> possibleOccupant = chessboard.getFigure(possibleMove);
            if(!possibleOccupant.isPresent()) {
                //Add possible move to knightsMovements
                knightMovements.add(moveTo(figure,possibleMove));
                continue;
            }

            Figure occupant = possibleOccupant.get();
            //Add capture move, because we know occupant is enemy
            if(occupant.getOwner() != figure.getOwner()) {
                knightMovements.add(captureEnemy(figure,occupant,possibleMove));
            }
        }
        return knightMovements;
    }
}
