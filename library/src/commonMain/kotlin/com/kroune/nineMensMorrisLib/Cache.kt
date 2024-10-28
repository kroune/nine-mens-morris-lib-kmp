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
 * Cache, used for caching positions analyzing
 */
object Cache {
    /**
     * <Position hash code, Pair<Depth, Solve result>>
     */
    private val localCache: HashMap<Long, Pair<UByte, Int>> = HashMap()

    /**
     * adds new cache if it didn't exist, or it had lower depth
     */
    fun addCache(pos: Position, depth: UByte, evaluation: Int) {
        val hash = pos.longHashCode()
        val cacheData = localCache[hash]
        if (cacheData == null || cacheData.first < depth) {
            localCache[hash] = Pair(depth, evaluation)
        }
    }

    /**
     * @returns cached result of position solving or null if no proper cache exists
     */
    fun getCache(pos: Position, neededDepth: UByte): Int? {
        val hash = pos.longHashCode()
        val cache = localCache[hash]
        if (cache == null || cache.first < neededDepth) {
            return null
        }
        return cache.second
    }

    /**
     * WARNING: it should only be used for tests
     * cache doesn't need to be cleared
     */
    fun wipeCache() {
        localCache.clear()
    }
}
