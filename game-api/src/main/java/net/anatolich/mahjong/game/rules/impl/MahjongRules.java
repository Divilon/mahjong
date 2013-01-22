package net.anatolich.mahjong.game.rules.impl;

import java.util.EnumSet;
import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.Piece;
import net.anatolich.mahjong.game.Tile;
import net.anatolich.mahjong.game.rules.Rules;

/**
 * Default mahjong rules implementation.
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class MahjongRules implements Rules {

    private final EnumSet<Tile.Type> typeComplementTypes = EnumSet.of(Tile.Type.SEASONS, Tile.Type.FLOWERS);

    @Override
    public boolean isPieceOpen( Coordinates coordinates, Board board ) {
        Piece piece = board.getPieceAt(coordinates);
        if ( piece == null ) {
            return false;
        }

        for ( int xOffset = -1; xOffset <= 1; xOffset++ ) {
            for ( int yOffset = -1; yOffset <= 1; yOffset++ ) {
                if ( board.getPieceAt(coordinates.translate(xOffset, yOffset, 1)) != null ) {
                    return false;
                }
            }
        }

        boolean blockedFromLeft = false;
        for ( int i = -1; i <= 1; i++ ) {
            if ( board.getPieceAt(coordinates.translate(-2, i, 0)) != null ) {
                blockedFromLeft = true;
            }
        }

        boolean blockedFromRight = false;
        for ( int i = -1; i <= 1; i++ ) {
            if ( board.getPieceAt(coordinates.translate(2, i, 0)) != null ) {
                blockedFromRight = true;
            }
        }

        if (blockedFromLeft && blockedFromRight){
            return false;
        }

        return true;
    }

    @Override
    public boolean isMoveLegal( Coordinates start, Coordinates end, Board board ) {
        if (!isPieceOpen(start, board) || !isPieceOpen(end, board) ){
            return false;
        }

        Tile startTile = board.getPieceAt(start).getTile();
        Tile endTile = board.getPieceAt(end).getTile();

        if (typeComplementTypes.contains(startTile.getType()) ) {
            return startTile.getType().equals(endTile.getType());
        }

        return startTile.getType().equals(endTile.getType()) && startTile.getValue().equals(endTile.getValue());

    }
}