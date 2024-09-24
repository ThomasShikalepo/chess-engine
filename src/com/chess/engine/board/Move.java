package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.cache.RemovalListener;

public abstract class Move {
    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;

    private Move(final Board board, final Piece movedPiece, final int destinationCoordinate){
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }
    public static final class majorMove extends Move{
       public majorMove(final Board board,
                  final Piece movedPiece,
                  final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }
    public static final class AttackMove extends Move{

        final Piece actackedPieace;

       public AttackMove(final Board board,
                   final Piece movedPiece,
                   final int destinationCoordinate,
                   final Piece actackedPieace) {
            super(board, movedPiece, destinationCoordinate);
            this.actackedPieace = actackedPieace;
        }
    }
}
