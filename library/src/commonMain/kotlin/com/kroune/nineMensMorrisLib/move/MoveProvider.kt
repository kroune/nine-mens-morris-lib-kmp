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
val moveProvider: List<List<Int>> = listOf(
    listOf(1, 9),
    listOf(0, 2, 4),
    listOf(1, 14),
    listOf(10, 4),
    listOf(1, 3, 5, 7),
    listOf(4, 13),
    listOf(7, 11),
    listOf(6, 4, 8),
    listOf(7, 12),
    listOf(0, 10, 21),
    listOf(9, 3, 11, 18),
    listOf(6, 10, 15),
    listOf(8, 17, 13),
    listOf(5, 12, 14, 20),
    listOf(2, 13, 23),
    listOf(11, 16),
    listOf(15, 17, 19),
    listOf(12, 16),
    listOf(10, 19),
    listOf(16, 18, 20, 22),
    listOf(13, 19),
    listOf(9, 22),
    listOf(19, 21, 23),
    listOf(14, 22)
)

/**
 * in fact, there are other ways to get possible triples without mapping them.
 * I just think this is the easiest and the fastest one
 */
val removeChecker: List<List<List<Int>>> = listOf(
    listOf(listOf(1, 2), listOf(9, 21)),
    listOf(listOf(0, 2), listOf(4, 7)),
    listOf(listOf(0, 1), listOf(14, 23)),
    listOf(listOf(4, 5), listOf(10, 18)),
    listOf(listOf(1, 7), listOf(3, 5)),
    listOf(listOf(3, 4), listOf(13, 20)),
    listOf(listOf(7, 8), listOf(11, 15)),
    listOf(listOf(6, 8), listOf(4, 1)),
    listOf(listOf(6, 7), listOf(12, 17)),
    listOf(listOf(0, 21), listOf(10, 11)),
    listOf(listOf(3, 18), listOf(9, 11)),
    listOf(listOf(9, 10), listOf(6, 15)),
    listOf(listOf(8, 17), listOf(13, 14)),
    listOf(listOf(5, 20), listOf(12, 14)),
    listOf(listOf(12, 13), listOf(2, 23)),
    listOf(listOf(6, 11), listOf(16, 17)),
    listOf(listOf(15, 17), listOf(19, 22)),
    listOf(listOf(15, 16), listOf(8, 12)),
    listOf(listOf(3, 10), listOf(19, 20)),
    listOf(listOf(18, 20), listOf(16, 22)),
    listOf(listOf(18, 19), listOf(5, 13)),
    listOf(listOf(0, 9), listOf(22, 23)),
    listOf(listOf(16, 19), listOf(21, 23)),
    listOf(listOf(21, 22), listOf(2, 14))
)

/**
 * lists all possible triples
 */
val triplesMap: List<List<Int>> = listOf(
    listOf(0, 1, 2),
    listOf(3, 4, 5),
    listOf(6, 7, 8),
    listOf(9, 10, 11),
    listOf(12, 13, 14),
    listOf(15, 16, 17),
    listOf(18, 19, 20),
    listOf(21, 22, 23),
    listOf(0, 9, 21),
    listOf(3, 10, 18),
    listOf(6, 11, 15),
    listOf(1, 4, 7),
    listOf(16, 19, 22),
    listOf(5, 13, 20),
    listOf(2, 14, 23)
)
