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

open class Memory {
    val wonPosition = Position(
        // @formatter:off
        arrayOf(
            EMPTY,                  EMPTY,                  BLUE_,
                    EMPTY,          EMPTY,          EMPTY,
                            EMPTY,  EMPTY,  BLUE_,
            EMPTY,  EMPTY,  GREEN,          EMPTY,  EMPTY,  EMPTY,
                            GREEN,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            EMPTY,                  EMPTY,                  GREEN
        ),
        // @formatter:on
        0u, 1u,
        pieceToMove = true
    )
}