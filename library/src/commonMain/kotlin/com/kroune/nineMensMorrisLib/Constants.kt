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
package com.kroune.nineMensMorrisLib

/**
 * pieces cost
 */
const val PIECE_COST = 10_000

/**
 * cost of additional moves (unnecessary) in winning positions
 */
const val DEPTH_COST = 1

/**
 * how much danger enemy unfinished triples are
 */
const val ENEMY_UNFINISHED_TRIPLES_COST = 3

/**
 * how much unfinished triple costs
 */
const val UNFINISHED_TRIPLES_COST = 200

/**
 * how much possible triple costs
 */
const val POSSIBLE_TRIPLE_COST = 5

/**
 * needed pieces to be able to fly over the board
 */
const val PIECES_TO_FLY: UByte = 3U

/**
 * evaluation of a won game
 * NOTE: it can't be too big, or it may cause int overflow
 * @see Position.evaluate
 */
const val WON_GAME_COST = 1_000_000_000

/**
 * evaluation of a lost game
 * NOTE: it can't be too big, or it may cause int overflow
 * @see Position.evaluate
 */
const val LOST_GAME_COST = -1_000_000_000

/**
 * fast, readable way for creating green piece
 */
const val GREEN = true

/**
 * fast, readable way for creating blue piece
 */
const val BLUE_ = false

/**
 * fast, readable way for creating empty piece
 */
inline val EMPTY: Boolean? get() = null

/**
 * a default game start position
 */
@Suppress("unused")
val gameStartPosition: Position
    get() {
        return Position(
            // @formatter:off
            arrayOf(
                EMPTY,                  EMPTY,                  EMPTY,
                        EMPTY,          EMPTY,          EMPTY,
                                EMPTY,  EMPTY,  EMPTY,
                EMPTY,  EMPTY,  EMPTY,          EMPTY,  EMPTY,  EMPTY,
                                EMPTY,  EMPTY,  EMPTY,
                        EMPTY,          EMPTY,          EMPTY,
                EMPTY,                  EMPTY,                  EMPTY
            ),
            // @formatter:on
            9u, 9u, pieceToMove = true
        )
    }
