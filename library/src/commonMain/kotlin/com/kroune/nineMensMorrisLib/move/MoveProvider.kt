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

/**
 * in fact, there are other ways to get possible move without mapping them.
 * I just think this is the easiest and the fastest one
 */
internal val moveProvider: Array<IntArray> = arrayOf(
    intArrayOf(1, 9),
    intArrayOf(0, 2, 4),
    intArrayOf(1, 14),
    intArrayOf(10, 4),
    intArrayOf(1, 3, 5, 7),
    intArrayOf(4, 13),
    intArrayOf(7, 11),
    intArrayOf(6, 4, 8),
    intArrayOf(7, 12),
    intArrayOf(0, 10, 21),
    intArrayOf(9, 3, 11, 18),
    intArrayOf(6, 10, 15),
    intArrayOf(8, 17, 13),
    intArrayOf(5, 12, 14, 20),
    intArrayOf(2, 13, 23),
    intArrayOf(11, 16),
    intArrayOf(15, 17, 19),
    intArrayOf(12, 16),
    intArrayOf(10, 19),
    intArrayOf(16, 18, 20, 22),
    intArrayOf(13, 19),
    intArrayOf(9, 22),
    intArrayOf(19, 21, 23),
    intArrayOf(14, 22)
)

/**
 * in fact, there are other ways to get possible triples without mapping them.
 * I just think this is the easiest and the fastest one
 */
internal val removeChecker: Array<Array<IntArray>> = arrayOf(
    arrayOf(intArrayOf(1, 2), intArrayOf(9, 21)),
    arrayOf(intArrayOf(0, 2), intArrayOf(4, 7)),
    arrayOf(intArrayOf(0, 1), intArrayOf(14, 23)),
    arrayOf(intArrayOf(4, 5), intArrayOf(10, 18)),
    arrayOf(intArrayOf(1, 7), intArrayOf(3, 5)),
    arrayOf(intArrayOf(3, 4), intArrayOf(13, 20)),
    arrayOf(intArrayOf(7, 8), intArrayOf(11, 15)),
    arrayOf(intArrayOf(6, 8), intArrayOf(4, 1)),
    arrayOf(intArrayOf(6, 7), intArrayOf(12, 17)),
    arrayOf(intArrayOf(0, 21), intArrayOf(10, 11)),
    arrayOf(intArrayOf(3, 18), intArrayOf(9, 11)),
    arrayOf(intArrayOf(9, 10), intArrayOf(6, 15)),
    arrayOf(intArrayOf(8, 17), intArrayOf(13, 14)),
    arrayOf(intArrayOf(5, 20), intArrayOf(12, 14)),
    arrayOf(intArrayOf(12, 13), intArrayOf(2, 23)),
    arrayOf(intArrayOf(6, 11), intArrayOf(16, 17)),
    arrayOf(intArrayOf(15, 17), intArrayOf(19, 22)),
    arrayOf(intArrayOf(15, 16), intArrayOf(8, 12)),
    arrayOf(intArrayOf(3, 10), intArrayOf(19, 20)),
    arrayOf(intArrayOf(18, 20), intArrayOf(16, 22)),
    arrayOf(intArrayOf(18, 19), intArrayOf(5, 13)),
    arrayOf(intArrayOf(0, 9), intArrayOf(22, 23)),
    arrayOf(intArrayOf(16, 19), intArrayOf(21, 23)),
    arrayOf(intArrayOf(21, 22), intArrayOf(2, 14))
)

/**
 * lists all possible triples
 */
internal val triplesMap: Array<IntArray> = arrayOf(
    intArrayOf(0, 1, 2),
    intArrayOf(3, 4, 5),
    intArrayOf(6, 7, 8),
    intArrayOf(9, 10, 11),
    intArrayOf(12, 13, 14),
    intArrayOf(15, 16, 17),
    intArrayOf(18, 19, 20),
    intArrayOf(21, 22, 23),
    intArrayOf(0, 9, 21),
    intArrayOf(3, 10, 18),
    intArrayOf(6, 11, 15),
    intArrayOf(1, 4, 7),
    intArrayOf(16, 19, 22),
    intArrayOf(5, 13, 20),
    intArrayOf(2, 14, 23)
)
