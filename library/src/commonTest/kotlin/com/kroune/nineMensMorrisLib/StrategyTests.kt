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

import com.kroune.nineMensMorrisLib.positions.Strategy
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("unused")
class StrategyTests : Strategy() {
    @Test
    fun `winning test1`() {
        val ourResult = position1.findBestMove(2u)
        assertEquals(ourResult, correctResult1)
    }

    @Test
    fun `winning test2`() {
        val ourResult = position2.findBestMove(2u)
        assertEquals(ourResult, correctResult2)
    }

    @Test
    fun `winning test3`() {
        val ourResult = position3.findBestMove(2u)
        assertEquals(ourResult, correctResult3)
    }

    @Test
    fun `winning test4`() {
        val ourResult = position4.findBestMove(2u)
        assertEquals(ourResult, correctResult4)
    }

    @Test
    fun `winning test5`() {
        val ourResult = position5.findBestMove(4u)
        assertEquals(ourResult, correctResult5)
    }
}
