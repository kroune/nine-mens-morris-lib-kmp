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

import com.kroune.nineMensMorrisLib.positions.Equals
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("unused")
class EqualsTest : Equals() {
    /**
     * this tests our copy method actually works
     */
    @Test
    fun `equals test`() {
        val position1 = position.copy()
        assertEquals(position, position1)
    }

    /**
     * this tests if our copy method actually works
     */
    @Test
    fun `not equals test`() {
        assertEquals(position, position2)
    }
}
