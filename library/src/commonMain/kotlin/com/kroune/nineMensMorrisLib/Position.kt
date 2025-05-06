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

import com.kroune.nineMensMorrisLib.move.Movement
import com.kroune.nineMensMorrisLib.move.moveProvider
import com.kroune.nineMensMorrisLib.move.removeChecker
import com.kroune.nineMensMorrisLib.move.triplesMap
import kotlin.math.max
import kotlin.math.min

/**
 * used for storing position data
 * @param positions all pieces
 * @param freeGreenPieces green pieces we can still place <= 26
 * @param freeBluePieces green pieces we can still place <= 26
 * @param greenPiecesAmount used for fast evaluation & game state checker (stores green pieces)
 * @param bluePiecesAmount used for fast evaluation & game state checker (stores blue pieces)
 * @param pieceToMove piece going to move next
 * @param removalCount number of pieces to remove <= 2
 * @see longHashCode
 */
@Suppress("EqualsOrHashCode", "LongParameterList")
class Position(
    var positions: Array<Boolean?>,
    var freeGreenPieces: UByte = 0u,
    var freeBluePieces: UByte = 0u,
    var greenPiecesAmount: UByte = ((positions.count { it == true }.toUByte() + freeGreenPieces).toUByte()),
    var bluePiecesAmount: UByte = (positions.count { it == false }.toUByte() + freeBluePieces).toUByte(),
    var pieceToMove: Boolean,
    var removalCount: UByte = 0u
) {
    /**
     * evaluates position
     * depth decreases at the higher depth
     * @return advantage of the green player
     */
    fun evaluate(depth: UByte = 0u): Int {
        // TODO: replace with NNUE
        var greenEvaluation = 0
        var blueEvaluation = 0
        run {
            if (greenPiecesAmount < PIECES_TO_FLY) {
                val depthCost = depth.toInt() * DEPTH_COST
                greenEvaluation = LOST_GAME_COST - depthCost
                blueEvaluation = WON_GAME_COST + depthCost
                return@run
            }
            if (bluePiecesAmount < PIECES_TO_FLY) {
                val depthCost = depth.toInt() * DEPTH_COST
                greenEvaluation = WON_GAME_COST + depthCost
                blueEvaluation = LOST_GAME_COST - depthCost
                return@run
            }

            greenEvaluation += (greenPiecesAmount.toInt() + if (pieceToMove) removalCount.toInt() else 0) * PIECE_COST
            blueEvaluation += (bluePiecesAmount.toInt() + if (!pieceToMove) removalCount.toInt() else 0) * PIECE_COST

            val (unfinishedTriples, findBlockedTriples) = triplesEvaluation()

            greenEvaluation += unfinishedTriples.first * if (pieceToMove)
                UNFINISHED_TRIPLES_COST else ENEMY_UNFINISHED_TRIPLES_COST
            blueEvaluation += unfinishedTriples.second * if (!pieceToMove)
                UNFINISHED_TRIPLES_COST else ENEMY_UNFINISHED_TRIPLES_COST


            greenEvaluation += findBlockedTriples.first * POSSIBLE_TRIPLE_COST
            blueEvaluation += findBlockedTriples.second * POSSIBLE_TRIPLE_COST
        }
        return greenEvaluation - blueEvaluation
    }

    /**
     * @return pair of unfinished triples (2 pieces of the same color and 1 empty)
     * and blocked triples (2 pieces of the same color and 1 of another)
     */
    fun triplesEvaluation(): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        var greenUnfinishedTriples = 0
        var blueUnfinishedTriples = 0
        var greenBlockedTriples = 0
        var blueBlockedTriples = 0
        for (triples in triplesMap) {
            var greenPieces = 0
            var bluePieces = 0
            triples.forEach {
                when (positions[it]) {
                    true -> {
                        greenPieces++
                    }

                    false -> {
                        bluePieces++
                    }

                    null -> {}
                }
            }
            if (greenPieces == 2 && bluePieces == 0) {
                greenUnfinishedTriples++
            }
            if (greenPieces == 0 && bluePieces == 2) {
                blueUnfinishedTriples++
            }
            if (greenPieces == 2 && bluePieces == 1) {
                greenBlockedTriples++
            }
            if (greenPieces == 1 && bluePieces == 2) {
                blueBlockedTriples++
            }
        }
        return Pair(
            greenUnfinishedTriples to blueUnfinishedTriples,
            greenBlockedTriples to blueBlockedTriples
        )
    }

    /**
     * WARNING: we also lose if we can't make a move
     * this function doesn't check this
     * @return true if the game has ended
     */
    private fun gameEnded(): Boolean {
        return greenPiecesAmount < PIECES_TO_FLY || bluePiecesAmount < PIECES_TO_FLY
    }

    /**
     * actual minimax search
     * we want to separate them, because it allows us to forget about storing move sequence,
     * which greatly improves performance, more over, minimax gets less precise at the last moves (because it doesn't
     * evaluate possible positions we can get from them enough), so there isn't any actual need to see al the sequence
     */
    private fun analyze(
        depth: UByte
    ): Int {
        if (depth == 0.toUByte() || gameEnded()) {
            return evaluate(depth)
        }
        // abort if this position was already analyzed
        Cache.getCache(this, depth)?.let {
            return it
        }
        // for all possible positions, we try to solve them
        val depthCost = depth.toInt() * DEPTH_COST
        /**
         * this assumes evaluation is > Int.MIN_VALUE and < Int.MAX_VALUE,
         * it is better than using null as default value,
         * because we don't want to check if it is null on every iteration
         */
        val defaultValue = if (pieceToMove) Int.MIN_VALUE else Int.MAX_VALUE
        var bestEvaluation: Int = defaultValue
        generateMoves().forEach {
            val pos = it.producePosition(this)
            /**
             * if we can perform an additional move we don't need to decrease depth
             * in order not to fuck up evaluation sorting
             */
            val shouldNotDecreaseDepth = (pos.removalCount > 0u && !pos.gameEnded())
            val result = if (shouldNotDecreaseDepth) {
                pos.analyze(depth)
            } else {
                pos.analyze((depth - 1u).toUByte())
            }
            bestEvaluation = if (pieceToMove) {
                max(result, bestEvaluation)
            } else {
                min(result, bestEvaluation)
            }
        }
        // it means that we can't make any move, so we lost
        if (bestEvaluation == defaultValue) {
            // we calculate bes
            bestEvaluation = if (pieceToMove) {
                (LOST_GAME_COST - depthCost) - (WON_GAME_COST + depthCost)
            } else {
                - (LOST_GAME_COST - depthCost) + (WON_GAME_COST + depthCost)
            }
        }
        Cache.addCache(this, depth, bestEvaluation)
        return bestEvaluation
    }

    /**
     * @param depth current depth
     * @return best move or null if none are possible
     */
    fun findBestMove(
        depth: UByte
    ): Movement? {
        // if (pieceToMove)  then we get a maximum evaluation else -> minimum
        var bestEvaluation: Int = if (pieceToMove) Int.MIN_VALUE else Int.MAX_VALUE
        var bestMove: Movement? = null
        generateMoves().forEach {
            val pos = it.producePosition(this)
            val shouldNotDecreaseDepth = (pos.removalCount > 0u && !pos.gameEnded())
            val evaluation = if (shouldNotDecreaseDepth) {
                pos.analyze(depth)
            } else {
                pos.analyze((depth - 1u).toUByte())
            }
            if (pieceToMove) {
                if (evaluation > bestEvaluation) {
                    bestMove = it
                    bestEvaluation = evaluation
                }
            } else {
                if (evaluation < bestEvaluation) {
                    bestMove = it
                    bestEvaluation = evaluation
                }
            }
        }
        return bestMove
    }

    /**
     * @return a copy of the current position
     */
    fun copy(): Position {
        return Position(
            positions.copyOf(),
            freeGreenPieces,
            freeBluePieces,
            greenPiecesAmount,
            bluePiecesAmount,
            pieceToMove,
            removalCount
        )
    }

    /**
     * @param move the last move we have performed
     * @return the amount of removes we need to perform
     */
    fun removalAmount(move: Movement): UByte {
        if (move.endIndex == null) return 0u

        return removeChecker[move.endIndex].count { list ->
            list.all { positions[it] == pieceToMove }
        }.toUByte()
    }

    /**
     * @return possible movements
     */
    fun generateMoves(): List<Movement> {
        val generatedList = when (gameState()) {
            GameState.Placement -> {
                generatePlacementMovements()
            }

            GameState.End -> {
                listOf()
            }

            GameState.Flying -> {
                generateFlyingMovements()
            }

            GameState.Normal -> {
                generateNormalMovements()
            }

            GameState.Removing -> {
                generateRemovalMoves()
            }
        }
        return generatedList
    }

    private fun generateRemovalMoves(): List<Movement> {
        val possibleMove: MutableList<Movement> = mutableListOf()
        positions.forEachIndexed { index, piece ->
            if (piece == !pieceToMove) {
                possibleMove.add(Movement(index, null))
            }
        }
        return possibleMove
    }

    /**
     * @return all possible normal movements
     */
    private fun generateNormalMovements(): List<Movement> {
        val possibleMove: MutableList<Movement> = mutableListOf()
        positions.forEachIndexed { startIndex, piece ->
            if (piece == pieceToMove) {
                moveProvider[startIndex].forEach { endIndex ->
                    if (positions[endIndex] == null) {
                        possibleMove.add(Movement(startIndex, endIndex))
                    }
                }
            }
        }
        return possibleMove
    }

    /**
     * @return all possible flying movements
     */
    private fun generateFlyingMovements(): List<Movement> {
        val possibleMove: MutableList<Movement> = mutableListOf()
        positions.forEachIndexed { startIndex, piece ->
            if (piece == pieceToMove) {
                positions.forEachIndexed { endIndex, endPiece ->
                    if (endPiece == null) {
                        possibleMove.add(Movement(startIndex, endIndex))
                    }
                }
            }
        }
        return possibleMove
    }

    /**
     * @return possible piece placements
     */
    private fun generatePlacementMovements(): List<Movement> {
        val possibleMove: MutableList<Movement> = mutableListOf()
        positions.forEachIndexed { endIndex, piece ->
            if (piece == null) {
                possibleMove.add(Movement(null, endIndex))
            }
        }
        return possibleMove
    }

    /**
     * @return state of the game for the currently playing moves
     */
    fun gameState(): GameState {
        return when {
            (gameEnded()) -> {
                GameState.End
            }

            (removalCount > 0u) -> {
                GameState.Removing
            }

            ((if (pieceToMove) freeGreenPieces else freeBluePieces) > 0U) -> {
                GameState.Placement
            }

            ((pieceToMove && greenPiecesAmount == PIECES_TO_FLY) ||
                    (!pieceToMove && bluePiecesAmount == PIECES_TO_FLY)) -> {
                GameState.Flying
            }

            else -> GameState.Normal
        }
    }

    /**
     * used for easier writing of auto tests
     */
    @Suppress("unused")
    fun displayAsCode(): String {
        var str = (
                """
        Position(
            mutableListOf(
                _____                   _____                   _____
                        _____           _____           _____
                                _____   _____   _____
                _____   _____   _____           _____   _____   _____
                                _____   _____   _____
                        _____           _____           _____
                _____                   _____                   _____
            ),
            freeGreenPieces = ${freeGreenPieces}u,
            freeBluePieces = ${freeBluePieces}u,
            pieceToMove = ${pieceToMove},
            removalCount = $removalCount
        )
        """.trimIndent()
                )
        repeat(24) {
            val newString = when (positions[it]) {
                null -> "EMPTY"
                false -> "BLUE_"
                true -> "GREEN"
            }
            str = str.replaceFirst("_____", newString)
        }
        return str
    }

    /**
     * this function is needed for unit tests,
     * especially needed is comparison with other positions
     */
    override fun equals(other: Any?): Boolean {
        if (other !is Position) {
            return super.equals(other)
        }
        for (i in positions.indices) {
            if (positions[i] != other.positions[i]) {
                return false
            }
        }
        return freeGreenPieces == other.freeGreenPieces && freeBluePieces == other.freeBluePieces
                && pieceToMove == other.pieceToMove
    }

    /**
     * prints position in human-readable form
     */
    override fun toString(): String {
        var str = ("""
            _____                   _____                   _____
                    _____           _____           _____
                            _____   _____   _____
            _____   _____   _____           _____   _____   _____
                            _____   _____   _____
                    _____           _____           _____
            _____                   _____                   _____
        """.trimIndent())
        repeat(24) {
            val newString = when (positions[it]) {
                null -> "empty"
                false -> "blue "
                true -> "green"
            }
            str = str.replaceFirst("_____", newString)
        }
        return str
    }

    /**
     * used for caching, replaces hashcode
     * this "hash" function has no collisions
     * each result is <= 14 symbols long
     * basically we use ternary, since most of the values are have <= 3 different possible value
     *
     * even though there is a test for hash collisions it is highly unrecommended to touch this
     * test can't fully check hash collisions
     *
     * so don't touch this unless you fully understand this code
     */
    fun longHashCode(): Long {
        var result = 0L
        // 3^30 = 205891132094649
        result += removalCount.toInt() * 205891132094649

        // 3^29 = 68630377364883
        result += (freeGreenPieces.toInt() / 9 * 68630377364883)
        // 3^28 = 22876792454961
        result += (freeGreenPieces.toInt() % 9 / 3 * 22876792454961)
        // 3^27 = 7625597484987
        result += (freeGreenPieces.toInt() % 3 * 7625597484987)

        // 3^26 = 2541865828329
        result += (freeBluePieces.toInt() / 9 * 2541865828329)
        // 3^25 = 847288609443
        result += (freeBluePieces.toInt() % 9 / 3 * 847288609443)
        // 3^24 = 282429536481
        result += (freeBluePieces.toInt() % 3 * 282429536481)

        // 3^23 = 94143178827
        var pow329 = 1
        positions.forEach {
            result += when (it) {
                null -> 0
                true -> 1
                false -> 2
            } * pow329
            pow329 *= 3
        }
        if (pieceToMove) {
            result *= -1
        }
        return result
    }
}
