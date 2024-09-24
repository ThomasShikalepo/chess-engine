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

import static com.chess.engine.board.Move.*;

//?The Knight class represents the knight piece in chess.
public class Knight extends Piece {

    // ?These are the possible moves a knight can make from its current position.
    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    // ?Constructor for the Knight class. It sets the position and alliance (color) of the knight.
    Knight(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    // ?This method calculates the legal moves for the knight from its current position.
    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        // ?This list will store all the legal moves.
        final List<Move> legalMoves = new ArrayList<>();

        // ?Loop through all possible moves.
        for (final int currentCandidate : CANDIDATE_MOVE_COORDINATES) {
            // ?This will store the destination position the knight can move to.
            // ?Calculate the destination position.
            final int candidateDestinationCoordinate = this.piecePossion + currentCandidate;

            // ?Check if the destination position is a valid tile coordinate.
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {

                // ?Check if the move is invalid due to the knight being on the edge of the board.
                if(isFistColumnExclusion(this.piecePossion, currentCandidate) ||
                        isSecondColoumn(this.piecePossion, currentCandidate)   ||
                        isSeventhColoumn(this.piecePossion, currentCandidate)  ||
                        isEighthColoumnExclusion(this.piecePossion, currentCandidate)) {
                    // ?If any of the edge cases apply, skip this move and continue with the next candidate.
                    continue;
                }

                // ?Get the tile at the destination position.
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                // ?Check if the destination tile is not occupied by any piece.
                if (!candidateDestinationTile.isTileOccupied()) {
                    // ?If not occupied, add a new move to the list of legal moves.
                    legalMoves.add(new majorMove(board, this, candidateDestinationCoordinate));
                } else {
                    // ?If the destination tile is occupied, get the piece on that tile.
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    // ?Get the alliance (color) of the piece on the destination tile.
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                    // Check if the piece on the destination tile is of the opposite alliance.
                    if (this.pieceAlliance != pieceAlliance) {
                        // ?If it's an opponent's piece, add a new move to the list of legal moves.
                        legalMoves.add(new AttackMove(board
                        , this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        // ?Return an immutable list of all legal moves.
        return ImmutableList.copyOf(legalMoves);
    }

    // ?Check if the knight is in the first column and if the move is invalid due to being on the edge of the board.
    public static boolean isFistColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] &&
                (candidateOffset == -17 || candidateOffset == -10 || candidateOffset == 6 || candidateOffset == 15);
    }

    // ?Check if the knight is in the second column and if the move is invalid due to being on the edge of the board.
    private static boolean isSecondColoumn(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SECOND_COLUMN[currentPosition] &&
                (candidateOffset == -10 || candidateOffset == 6);
    }

    // ?Check if the knight is in the seventh column and if the move is invalid due to being on the edge of the board.
    private static boolean isSeventhColoumn(final int currentPosition, final int candidateOffset){
        return BoardUtils.SEVENTH_COLUMN[currentPosition] &&
                (candidateOffset == -6 || candidateOffset == 10 );
    }

    // ?Check if the knight is in the eighth column and if the move is invalid due to being on the edge of the board.
    private static boolean isEighthColoumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] &&
                (candidateOffset == -15 || candidateOffset == -6 || candidateOffset == 10 || candidateOffset == 17);
    }
}
