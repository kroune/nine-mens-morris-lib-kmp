/*
 * This file is part of nine-mens-morris-lib (https://github.com/kroune/nine-mens-morris-lib)
 * Copyright (C) 2024-2024  kroune
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact: kr0ne@tuta.io
 */
package com.kroune.nineMensMorrisLib.move

import com.kroune.nineMensMorrisLib.Position
import kotlinx.serialization.Serializable

/**
 * used to store movement
 * @param startIndex index of place a piece moves from
 * @param endIndex index of place a piece moves to
 */
@Serializable
data class Movement(val startIndex: Int?, val endIndex: Int?) {
    /**
     * @param oldPosition position we have a more for
     * @return position after specified move
     */
    fun producePosition(oldPosition: Position): Position {
        val newPosition = oldPosition.copy()
        processStartIndexUpdate(newPosition, oldPosition)
        processEndIndexUpdate(newPosition, oldPosition)
        processRemovalCountUpdate(newPosition)
        return newPosition
    }

    private fun processEndIndexUpdate(newPosition: Position, oldPosition: Position) {
        if (endIndex != null) {
            check(oldPosition.positions[endIndex] != oldPosition.pieceToMove) {
                "illegal move $this $oldPosition"
            }
            // this happens either when we move a piece or place it
            newPosition.positions[endIndex] = newPosition.pieceToMove
        } else {
            // this happens only when we remove smth
            if (oldPosition.positions[startIndex!!]!!) {
                // if it is true, we remove green piece
                check(oldPosition.greenPiecesAmount != 0.toUByte()) {
                    "illegal green piece count $this $oldPosition"
                }
                newPosition.greenPiecesAmount--
            } else {
                check(oldPosition.bluePiecesAmount != 0.toUByte()) {
                    "illegal green piece count $this $oldPosition"
                }
                newPosition.bluePiecesAmount--
            }
        }
    }

    private fun processStartIndexUpdate(newPosition: Position, oldPosition: Position) {
        if (startIndex == null) {
            // this happens when we place smth
            if (oldPosition.pieceToMove) {
                newPosition.freeGreenPieces--
            } else {
                newPosition.freeBluePieces--
            }
        } else {
            check(oldPosition.positions[startIndex] != null) {
                "illegal move $this $oldPosition"
            }
            newPosition.positions[startIndex] = null
        }
    }

    private fun processRemovalCountUpdate(newPosition: Position) {
        if (newPosition.removalCount > 1u) {
            newPosition.removalCount--
        } else {
            newPosition.removalCount = newPosition.removalAmount(this)
            if (newPosition.removalCount == 0.toUByte()) {
                newPosition.pieceToMove = !newPosition.pieceToMove
            }
        }
    }
}
