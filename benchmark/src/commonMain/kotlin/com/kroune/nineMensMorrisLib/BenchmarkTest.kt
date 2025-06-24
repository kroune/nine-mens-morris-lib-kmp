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

import kotlinx.benchmark.*

@Suppress("unused")
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(BenchmarkTimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
class BenchmarkTest : Benchmark() {
    // this test takes more time and not very useful most of the time
//    @kotlinx.benchmark.Benchmark
    fun benchmark(bh: Blackhole) {
        val move = benchmark1.findBestMove(6u)
        bh.consume(move)
        Cache.wipeCache()
    }

    @kotlinx.benchmark.Benchmark
    fun benchmark2(bh: Blackhole) {
        val move = benchmark2.findBestMove(6u)
        bh.consume(move)
        Cache.wipeCache()
    }
}
