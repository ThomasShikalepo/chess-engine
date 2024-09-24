package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

// The Tile class represents a square on the chessboard.
// It can be either occupied by a piece or empty.
public abstract class Tile {

    // This number tells us the position of the tile on the board.
    protected final int tileCoordinate;

    // A map to store all possible empty tiles on the board.
    // This map is immutable, meaning it cannot be changed once created.
private static final Map<Integer, EmptyTile> EMPTY_TILE_CACHE = createAllPossibleEmptyTile();

    // This method creates all the empty tiles (64 of them) and puts them in a map.
    private static Map<Integer, EmptyTile> createAllPossibleEmptyTile() {
        // Create a new map to hold the empty tiles.
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        // Loop through all 64 positions on the chessboard.
        for (int i = 0; i < BoardUtils.NUM_Tiles; i++) {
            // Put an empty tile in each position.
            emptyTileMap.put(i, new EmptyTile(i));
        }
        // Return an immutable copy of the map.
        return ImmutableMap.copyOf(emptyTileMap);
    }

    // This method creates a tile. If there is a piece, it creates an occupied tile.
    // If there is no piece, it gets an empty tile from the map.
    public static Tile createTile(final int tileCoordinate, final Piece piece) {
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILE_CACHE.get(tileCoordinate);
    }

    // This is the constructor for the Tile class. It sets the tile's position.
    private Tile(final int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    // This method will tell us if the tile is occupied. Subclasses will define how this works.
    public abstract boolean isTileOccupied();

    // This method will return the piece on the tile. Subclasses will define how this works.
    public abstract Piece getPiece();

    // This class represents an empty tile on the board.
    public static final class EmptyTile extends Tile {

        // This is the constructor for the EmptyTile class. It sets the tile's position.
        EmptyTile(final int coordinate) {
            super(coordinate);
        }

        // This method returns false because the tile is empty.
        @Override
        public boolean isTileOccupied() {
            return false;
        }

        // This method returns null because there is no piece on an empty tile.
        @Override
        public Piece getPiece() {
            return null;
        }
    }

    // This class represents a tile that has a piece on it.
    public static final class OccupiedTile extends Tile {

        // This variable holds the piece on the tile.
        private final Piece pieceOnTile;

        // This is the constructor for the OccupiedTile class. It sets the tile's position and the piece on it.
        private OccupiedTile(int tileCoordinate,final Piece pieceOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        // This method returns true because the tile has a piece on it.
        @Override
        public boolean isTileOccupied() {
            return true;
        }

        // This method returns the piece on the tile.
        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }
    }
}
      