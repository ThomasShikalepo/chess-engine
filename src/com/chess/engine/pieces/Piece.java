package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {

    protected final int piecePossion;
    protected final Alliance pieceAlliance;

    Piece(final int piecePossion, final Alliance pieceAlliance){
        this.pieceAlliance = pieceAlliance;
        this.piecePossion = piecePossion;
    }
    public Alliance getPieceAlliance(){
        return this.pieceAlliance;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);

}
