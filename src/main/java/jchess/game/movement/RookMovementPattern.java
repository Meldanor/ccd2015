package jchess.game.movement;

import jchess.game.Figure;
import jchess.game.Gameboard;
import jchess.game.Position2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Provides a list of possible {@link ChessAction} for a rook figure
 *
 * @since 18.11.2015.
 */
public class RookMovementPattern implements MovementPattern {


    //Direction-Vectors [x, y] for possible rook movements (stepwise)
    private final static int[] WEST = {0,-1};
    private final static int[] NORTHWEST = {-1,-1};
    private final static int[] NORTHEAST = {-1,0};
    private final static int[] EAST = {0,1};
    private final static int[] SOUTHEAST = {1,1};
    private final static int[] SOUTHWEST = {1,0};

    private int range;

    /**
     * Default constructor. The MovementPattern computes all possible moves on the given gameboard.
     */
    public RookMovementPattern()
    {
        this.range = Integer.MAX_VALUE;
    }

    /**
     * Constructs a MovementPattern with a limited number of steps to be taken.
     *
     * @param range Maximum number of fields the corresponding figure is allowed to move.
     */
    public RookMovementPattern(int range)
    {
        this.range = range;
    }

    /**
     * Checks for possible moves in
     *
     * @param figure        The rook to find the possible moves for.
     * @param chessboard    The chessboard on which to check for possible moves.
     * @return A List of all possible moves for the rook.
     */
    @Override
    public List<ChessAction> getPossibleActions(Figure figure, Gameboard chessboard) {

        List<ChessAction> actions = new ArrayList<>();

        //check to the left
        actions.addAll(checkDirection(figure,chessboard,WEST));

        //check to the top-left
        actions.addAll(checkDirection(figure,chessboard,NORTHWEST));

        //check to the top-right
        actions.addAll(checkDirection(figure,chessboard,NORTHEAST));

        //check to the right
        actions.addAll(checkDirection(figure,chessboard,EAST));

        //check to the bottom-right
        actions.addAll(checkDirection(figure,chessboard,SOUTHEAST));

        //check to the bottom-left
        actions.addAll(checkDirection(figure,chessboard,SOUTHWEST));


        return actions;
    }

    /**
     *
     * @param figure        The rook to check for moves in a direction for.
     * @param chessboard    The chessboard on which to check for possible moves.
     * @param direction     The direction in which to check for moves.
     * @return A List of all determined possible moves in the given direction for the given rook figure.
     */
    private List<ChessAction> checkDirection(Figure figure, Gameboard chessboard, int[] direction) {
        Position2D figurePos = chessboard.getPositionOf(figure);
        Position2D nextPos = Position2D.of(figurePos.getX()+direction[0], figurePos.getY()+direction[1]);
        List<ChessAction> dirActions = new ArrayList<>();

        Optional target;

        int steps = 0;

        while (chessboard.isInField(nextPos) && steps < range) {
            target = chessboard.getFigure(nextPos);
            if (!target.isPresent()) {
                dirActions.add(moveTo(figure, nextPos));
            } else {
                Figure targetFigure = (Figure) target.get();
                if (targetFigure.isOppositesFigure(figure)) {
                    dirActions.add(captureEnemy(figure, targetFigure, nextPos));
                }
                break;
            }
            steps++;
            nextPos = Position2D.of(nextPos.getX() + direction[0], nextPos.getY() + direction[1]);

        }
        return dirActions;
    }
}
