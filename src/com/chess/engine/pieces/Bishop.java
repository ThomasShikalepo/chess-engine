package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Bishop extends Piece{

    public static final int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9, -7, 7, 9};

    Bishop(int piecePossion, Alliance pieceAlliance) {
        super(piecePossion, pieceAlliance);
    }
    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

       final List<Move> legalMoves = new ArrayList<>();

        for(final int candidateCoordinateOffset: CANDIDATE_MOVE_VECTOR_COORDINATES){
            int candidateDestinationCoordinate = this.piecePossion;
            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                if (isFirstColumnExclusion(candidateDestinationCoordinate,candidateCoordinateOffset) ||
                        isEightColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)){
                    break;
                }
                candidateDestinationCoordinate +=candidateCoordinateOffset;
                if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                    // ?Get the tile at the destination position.
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    // ?Check if the destination tile is not occupied by any piece.
                    if (!candidateDestinationTile.isTileOccupied()) {
                        // ?If not occupied, add a new move to the list of legal moves.
                        legalMoves.add(new Move.majorMove(board, this, candidateDestinationCoordinate));
                    } else {
                        // ?If the destination tile is occupied, get the piece on that tile.
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        // ?Get the alliance (color) of the piece on the destination tile.
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        // ?Check if the piece on the destination tile is of the opposite alliance.
                        if (this.pieceAlliance != pieceAlliance) {
                            // ?If it's an opponent's piece, add a new move to the list of legal moves.
                            legalMoves.add(new Move.AttackMove(board
                                    , this, candidateDestinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }

                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }
    private static  boolean isFirstColumnExclusion(final int currentPossion, final int candidateOfset){
        return BoardUtils.FIRST_COLUMN[currentPossion] && (candidateOfset == -9 || candidateOfset == 7);
    }

    private static  boolean isEightColumnExclusion(final int currentPossion, final int candidateOfset){
        return BoardUtils.EIGHTH_COLUMN[currentPossion] && (candidateOfset == -7 || candidateOfset == 9);
    }
}
