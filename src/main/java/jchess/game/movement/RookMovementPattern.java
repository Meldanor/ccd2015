package jchess.game.movement;

import jchess.game.Figure;
import jchess.game.Gameboard;
import jchess.game.Position;
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


    /**
     * @param figure     The figure itself.
     * @param chessboard The chessboard in its current state.
     * @return A List of all possible moves for this rook-figure.
     */
    @Override
    public List<ChessAction> getPossibleActions(Figure figure, Gameboard chessboard) {

        Position2D pos = chessboard.getPositionOf(figure);

        List<ChessAction> actions = new ArrayList<>();

        int y = pos.getY();
        int x = pos.getX();

        Optional target;

        //check to the left
        while (chessboard.isInField(Position2D.of(x, --y))) {
            target = chessboard.getFigure(Position2D.of(x, y));
            if (!target.isPresent()) {
                actions.add(new ChessAction(ChessAction.ChessActionType.MOVE, figure, Position2D.of(x, y)));
            } else {
                if (((Figure) target.get()).isOppositesFigure(figure))
                    actions.add(new ChessAction(ChessAction.ChessActionType.CAPTURE, figure, Position2D.of(x, y)));
                break;
            }
        }

        //reset
        y = pos.getY();
        x = pos.getX();


        //check to the top-left
        while (chessboard.isInField(Position2D.of(--x, --y))) {
            target = chessboard.getFigure(Position2D.of(x, y));
            if (!target.isPresent()) {
                actions.add(new ChessAction(ChessAction.ChessActionType.MOVE, figure, Position2D.of(x, y)));
            } else {
                if (((Figure) target.get()).isOppositesFigure(figure))
                    actions.add(new ChessAction(ChessAction.ChessActionType.CAPTURE, figure, Position2D.of(x, y)));
                break;
            }
        }

        //reset
        y = pos.getY();
        x = pos.getX();


        //check to the top-right
        while (chessboard.isInField(Position2D.of(--x, y))) {
            target = chessboard.getFigure(Position2D.of(x, y));
            if (!target.isPresent()) {
                actions.add(new ChessAction(ChessAction.ChessActionType.MOVE, figure, Position2D.of(x, y)));
            } else {
                if (((Figure) target.get()).isOppositesFigure(figure))
                    actions.add(new ChessAction(ChessAction.ChessActionType.CAPTURE, figure, Position2D.of(x, y)));
                break;
            }
        }

        //reset
        y = pos.getY();
        x = pos.getX();


        //check to the right
        while (chessboard.isInField(Position2D.of(x, ++y))) {
            target = chessboard.getFigure(Position2D.of(x, y));
            if (!target.isPresent()) {
                actions.add(new ChessAction(ChessAction.ChessActionType.MOVE, figure, Position2D.of(x, y)));
            } else {
                if (((Figure) target.get()).isOppositesFigure(figure))
                    actions.add(new ChessAction(ChessAction.ChessActionType.CAPTURE, figure, Position2D.of(x, y)));
                break;
            }
        }

        //reset
        y = pos.getY();
        x = pos.getX();

        //check to the bottom-right
        while (chessboard.isInField(Position2D.of(++x, ++y))) {
            target = chessboard.getFigure(Position2D.of(x, y));
            if (!target.isPresent()) {
                actions.add(new ChessAction(ChessAction.ChessActionType.MOVE, figure, Position2D.of(x, y)));
            } else {
                if (((Figure) target.get()).isOppositesFigure(figure))
                    actions.add(new ChessAction(ChessAction.ChessActionType.CAPTURE, figure, Position2D.of(x, y)));
                break;
            }
        }


        //reset
        y = pos.getY();
        x = pos.getX();

        //check to the bottom-left
        while (chessboard.isInField(Position2D.of(++x, y))) {
            target = chessboard.getFigure(Position2D.of(x, y));
            if (!target.isPresent()) {
                actions.add(new ChessAction(ChessAction.ChessActionType.MOVE, figure, Position2D.of(x, y)));
            } else {
                if (((Figure) target.get()).isOppositesFigure(figure))
                    actions.add(new ChessAction(ChessAction.ChessActionType.CAPTURE, figure, Position2D.of(x, y)));
                break;
            }
        }


        return actions;
    }
}
