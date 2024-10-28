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
package com.kroune.nineMensMorrisLib.positions

import com.kroune.nineMensMorrisLib.BLUE_
import com.kroune.nineMensMorrisLib.EMPTY
import com.kroune.nineMensMorrisLib.GREEN
import com.kroune.nineMensMorrisLib.Position
import com.kroune.nineMensMorrisLib.move.Movement

open class Strategy {
    val position1 = Position(
        // @formatter:off
        arrayOf(
            BLUE_,                  EMPTY,                  EMPTY,
                    GREEN,          EMPTY,          EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
            EMPTY,  GREEN,  EMPTY,          EMPTY,  EMPTY,  EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          BLUE_,
            EMPTY,                  EMPTY,                  EMPTY
        ),
        // @formatter:on
        0u, 0u, pieceToMove = true, removalCount = 0u
    )

    val correctResult1 = null

    val position2 = Position(
        // @formatter:off
        arrayOf(
            BLUE_,                  EMPTY,                  EMPTY,
                    GREEN,          EMPTY,          EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
            EMPTY,  GREEN,  EMPTY,          EMPTY,  EMPTY,  EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          BLUE_,
            EMPTY,                  EMPTY,                  EMPTY
        ),
        // @formatter:on
        1u, 1u, pieceToMove = true, removalCount = 0u
    )

    val correctResult2 = Movement(null, 18)

    val position3 = Position(
        // @formatter:off
        arrayOf(
            EMPTY,                  EMPTY,                  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
                            GREEN,  EMPTY,  GREEN,
            EMPTY,  EMPTY,  BLUE_,          EMPTY,  EMPTY,  EMPTY,
                            BLUE_,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          BLUE_,
            EMPTY,                  EMPTY,                  EMPTY
        ),
        // @formatter:on
        5u, 5u, pieceToMove = false, removalCount = 0u
    )

    val correctResult3 = Movement(null, 7)

    val position4 = Position(
        // @formatter:off
        arrayOf(
            BLUE_,                  EMPTY,                  BLUE_,
                    EMPTY,          BLUE_,          EMPTY,
                            EMPTY,  BLUE_,  EMPTY,
            EMPTY,  EMPTY,  EMPTY,          GREEN,  GREEN,  GREEN,
                            EMPTY,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            BLUE_,                  EMPTY,                  EMPTY
        ),
        // @formatter:on
        0u, 0u, pieceToMove = false, removalCount = 0u
    )

    val correctResult4 = Movement(0, 1)

    val position5 = Position(
        // @formatter:off
        arrayOf(
            BLUE_,                  EMPTY,                  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
            EMPTY,  EMPTY,  EMPTY,          EMPTY,  EMPTY,  EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            GREEN,                  EMPTY,                  GREEN
        ),
        // @formatter:on
        6u, 7u, pieceToMove = false, removalCount = 0u
    )

    val correctResult5 = Movement(null, 22)

    /*
    0-----------------1-----------------2
    |                 |                 |
    |     3-----------4-----------5     |
    |     |           |           |     |
    |     |     6-----7-----8     |     |
    |     |     |           |     |     |
    9-----10----11          12----13----14
    |     |     |           |     |     |
    |     |     15----16----17    |     |
    |     |           |           |     |
    |     18----------19----------20    |
    |                 |                 |
    21----------------22----------------23
     */
}
