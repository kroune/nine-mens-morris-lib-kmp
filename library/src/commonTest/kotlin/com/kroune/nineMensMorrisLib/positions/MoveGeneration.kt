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

open class MoveGeneration {
    val position1 = Position(
        // @formatter:off
        arrayOf(
            BLUE_,                  BLUE_,                  EMPTY,
                    GREEN,          EMPTY,          EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
            EMPTY,  GREEN,  EMPTY,          EMPTY,  EMPTY,  EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            EMPTY,                  EMPTY,                  GREEN
        ),
        // @formatter:on
        1u, 1u, pieceToMove = true, removalCount = 0u
    )

    val position1Result = mutableListOf(
        Movement(null, 2),
        Movement(null, 4),
        Movement(null, 5),
        Movement(null, 6),
        Movement(null, 7),
        Movement(null, 8),
        Movement(null, 9),
        Movement(null, 11),
        Movement(null, 12),
        Movement(null, 13),
        Movement(null, 14),
        Movement(null, 15),
        Movement(null, 16),
        Movement(null, 17),
        Movement(null, 18),
        Movement(null, 19),
        Movement(null, 20),
        Movement(null, 21),
        Movement(null, 22)
    )

    val position2 = Position(
        // @formatter:off
        arrayOf(
            BLUE_,                  EMPTY,                  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
                            GREEN,  EMPTY,  GREEN,
            EMPTY,  EMPTY,  BLUE_,          EMPTY,  EMPTY,  EMPTY,
                            BLUE_,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            EMPTY,                  EMPTY,                  EMPTY
        ),
        // @formatter:on
        6u, 6u, pieceToMove = true, removalCount = 0u
    )

    val position2Result = mutableListOf(
        Movement(null, 1),
        Movement(null, 2),
        Movement(null, 3),
        Movement(null, 4),
        Movement(null, 5),
        Movement(null, 7),
        Movement(null, 9),
        Movement(null, 10),
        Movement(null, 12),
        Movement(null, 13),
        Movement(null, 14),
        Movement(null, 16),
        Movement(null, 17),
        Movement(null, 18),
        Movement(null, 19),
        Movement(null, 20),
        Movement(null, 21),
        Movement(null, 22),
        Movement(null, 23),
    )

    val position3 = Position(
        // @formatter:off
        arrayOf(
            BLUE_,                  EMPTY,                  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
                            GREEN,  GREEN,  GREEN,
            EMPTY,  EMPTY,  BLUE_,          EMPTY,  EMPTY,  EMPTY,
                            BLUE_,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            EMPTY,                  EMPTY,                  EMPTY
        ),
        // @formatter:on
        5u, 6u, pieceToMove = true, removalCount = 1u
    )

    val position3Result = mutableListOf(
        Movement(0, null),
        Movement(11, null),
        Movement(15, null),
    )

    val position4 = Position(
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

    val position4Result = mutableListOf(
        Movement(null, 0),
        Movement(null, 1),
        Movement(null, 2),
        Movement(null, 3),
        Movement(null, 4),
        Movement(null, 5),
        Movement(null, 7),
        Movement(null, 9),
        Movement(null, 10),
        Movement(null, 12),
        Movement(null, 13),
        Movement(null, 14),
        Movement(null, 16),
        Movement(null, 17),
        Movement(null, 18),
        Movement(null, 19),
        Movement(null, 21),
        Movement(null, 22),
        Movement(null, 23),
    )

    val position5 = Position(
        // @formatter:off
        arrayOf(
            BLUE_,                  BLUE_,                  EMPTY,
                    GREEN,          EMPTY,          EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
            EMPTY,  GREEN,  EMPTY,          EMPTY,  EMPTY,  EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            EMPTY,                  BLUE_,                  GREEN
        ),
        // @formatter:on
        0u, 0u, pieceToMove = false, removalCount = 0u
    )

    val position5Result = mutableListOf(
        Movement(0, 2),
        Movement(0, 4),
        Movement(0, 5),
        Movement(0, 6),
        Movement(0, 7),
        Movement(0, 8),
        Movement(0, 9),
        Movement(0, 11),
        Movement(0, 12),
        Movement(0, 13),
        Movement(0, 14),
        Movement(0, 15),
        Movement(0, 16),
        Movement(0, 17),
        Movement(0, 18),
        Movement(0, 19),
        Movement(0, 20),
        Movement(0, 21),
        Movement(1, 2),
        Movement(1, 4),
        Movement(1, 5),
        Movement(1, 6),
        Movement(1, 7),
        Movement(1, 8),
        Movement(1, 9),
        Movement(1, 11),
        Movement(1, 12),
        Movement(1, 13),
        Movement(1, 14),
        Movement(1, 15),
        Movement(1, 16),
        Movement(1, 17),
        Movement(1, 18),
        Movement(1, 19),
        Movement(1, 20),
        Movement(1, 21),
        Movement(22, 2),
        Movement(22, 4),
        Movement(22, 5),
        Movement(22, 6),
        Movement(22, 7),
        Movement(22, 8),
        Movement(22, 9),
        Movement(22, 11),
        Movement(22, 12),
        Movement(22, 13),
        Movement(22, 14),
        Movement(22, 15),
        Movement(22, 16),
        Movement(22, 17),
        Movement(22, 18),
        Movement(22, 19),
        Movement(22, 20),
        Movement(22, 21)
    )

    val position6 = Position(
        // @formatter:off
        arrayOf(
            BLUE_,                  BLUE_,                  EMPTY,
                    GREEN,          EMPTY,          EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
            EMPTY,  GREEN,  EMPTY,          EMPTY,  EMPTY,  EMPTY,
                            EMPTY,  GREEN,  EMPTY,
                    EMPTY,          BLUE_,          EMPTY,
            EMPTY,                  BLUE_,                  GREEN
        ),
        // @formatter:on
        0u, 0u, pieceToMove = false, removalCount = 0u
    )

    val position6Result = mutableListOf(
        Movement(0, 9),
        Movement(1, 2),
        Movement(1, 4),
        Movement(19, 18),
        Movement(19, 20),
        Movement(22, 21),
    )
}
