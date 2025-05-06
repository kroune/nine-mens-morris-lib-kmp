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

/**
 * used to store movement
 * @param startIndex index of place a piece moves from
 * @param endIndex index of place a piece moves to
 */
@Suppress("EqualsOrHashCode")
class Movement(val startIndex: Int?, val endIndex: Int?) {
    /**
     * @param pos position we have a more for
     * @return position after specified move
     */
    fun producePosition(pos: Position): Position {
        val copy = pos.copy()
        if (endIndex != null) {
            if (copy.positions[endIndex] == copy.pieceToMove) {
                error("illegal move $this $pos")
            }
            // this happens either when we move a piece or place it
            copy.positions[endIndex] = copy.pieceToMove
        } else {
            // this happens only when we remove smth
            if (copy.positions[startIndex!!]!!) {
                // if it is true, we remove green piece
                if (copy.greenPiecesAmount == 0.toUByte()) {
                    error("illegal green piece count $this $pos")
                }
                copy.greenPiecesAmount--
            } else {
                if (copy.bluePiecesAmount == 0.toUByte()) {
                    error("illegal green piece count $this $pos")
                }
                copy.bluePiecesAmount--
            }
        }
        if (startIndex == null) {
            // this happens when we place smth
            if (copy.pieceToMove) {
                copy.freeGreenPieces--
            } else {
                copy.freeBluePieces--
            }
        } else {
            if (copy.positions[startIndex] == null) {
                error("illegal move $this $pos")
            }
            copy.positions[startIndex] = null
        }
        if (copy.removalCount > 1u) {
            copy.removalCount--
        } else {
            copy.removalCount = copy.removalAmount(this)
            if (copy.removalCount == 0.toUByte()) {
                copy.pieceToMove = !copy.pieceToMove
            }
        }
        return copy
    }

    /**
     * adds method for checking if movements are equal
     * used for test
     */
    override fun equals(other: Any?): Boolean {
        if (other is Movement) {
            return this.startIndex == other.startIndex && this.endIndex == other.endIndex
        }
        return super.equals(other)
    }

    /**
     * provides a human-readable output of the class content
     */
    override fun toString(): String {
        return "Movement($startIndex, $endIndex)"
    }
}
