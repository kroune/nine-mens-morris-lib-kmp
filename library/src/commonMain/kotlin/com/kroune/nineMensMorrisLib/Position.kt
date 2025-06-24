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
import kotlinx.serialization.Serializable
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
 * @see uniqueHashCode
 */
@Suppress("EqualsOrHashCode", "LongParameterList")
@Serializable
class Position(
    var positions: Array<Boolean?>,
    var freeGreenPieces: UByte = 0u,
    var freeBluePieces: UByte = 0u,
    internal var greenPiecesAmount: UByte = ((positions.count { it == true }.toUByte() + freeGreenPieces).toUByte()),
    internal var bluePiecesAmount: UByte = (positions.count { it == false }.toUByte() + freeBluePieces).toUByte(),
    var pieceToMove: Boolean,
    var removalCount: UByte = 0u
) {
    constructor(
        positions: Array<Boolean?>,
        freeGreenPieces: Int = 0,
        freeBluePieces: Int = 0,
        pieceToMove: Boolean,
        removalCount: Int = 0
    ) : this(
        positions = positions,
        freeGreenPieces = freeGreenPieces.toUByte(),
        freeBluePieces = freeBluePieces.toUByte(),
        pieceToMove = pieceToMove,
        removalCount = removalCount.toUByte()
    )

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
    private fun triplesEvaluation(): Pair<Pair<Int, Int>, Pair<Int, Int>> {
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
            when {
                (greenPieces == 2 && bluePieces == 0) -> {
                    greenUnfinishedTriples++
                }

                (greenPieces == 0 && bluePieces == 2) -> {
                    blueUnfinishedTriples++
                }

                (greenPieces == 2 && bluePieces == 1) -> {
                    greenBlockedTriples++
                }

                (greenPieces == 1 && bluePieces == 2) -> {
                    blueBlockedTriples++
                }
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
    private fun gameEndedDueToSmallAmountOfPieces(): Boolean {
        return greenPiecesAmount < PIECES_TO_FLY || bluePiecesAmount < PIECES_TO_FLY
    }

    /**
     * actual minimax search with alpha-beta pruning
     * we want to separate them, because it allows us to forget about storing move sequence,
     * which greatly improves performance, more over, minimax gets less precise at the last moves (because it doesn't
     * evaluate possible positions we can get from them enough), so there isn't any actual need to see al the sequence
     * @param alpha best score that the maximizing player is assured
     * @param beta best score that the minimizing player is assured
     */
    fun analyze(
        depth: UByte,
        alpha: Int = Int.MIN_VALUE,
        beta: Int = Int.MAX_VALUE
    ): Int {
        if (depth == 0.toUByte() || gameEndedDueToSmallAmountOfPieces()) {
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
        var currentAlpha = alpha
        var currentBeta = beta

        generateMoves().forEach {
            val pos = it.producePosition(this)

            /**
             * if we can perform an additional move we don't need to decrease depth
             * in order not to fuck up evaluation sorting
             */
            val shouldNotDecreaseDepth = (pos.removalCount > 0u && !pos.gameEndedDueToSmallAmountOfPieces())
            val result = if (shouldNotDecreaseDepth) {
                pos.analyze(depth, currentAlpha, currentBeta)
            } else {
                pos.analyze((depth - 1u).toUByte(), currentAlpha, currentBeta)
            }

            if (pieceToMove) {
                bestEvaluation = max(result, bestEvaluation)
                currentAlpha = max(currentAlpha, bestEvaluation)
                if (currentBeta <= currentAlpha) {
                    return bestEvaluation // Beta cutoff
                }
            } else {
                bestEvaluation = min(result, bestEvaluation)
                currentBeta = min(currentBeta, bestEvaluation)
                if (currentBeta <= currentAlpha) {
                    return bestEvaluation // Alpha cutoff
                }
            }
        }
        // it means that we can't make any move, so we lost
        if (bestEvaluation == defaultValue) {
            // we calculate bes
            bestEvaluation = if (pieceToMove) {
                (LOST_GAME_COST - depthCost) - (WON_GAME_COST + depthCost)
            } else {
                -(LOST_GAME_COST - depthCost) + (WON_GAME_COST + depthCost)
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
        var alpha = Int.MIN_VALUE
        var beta = Int.MAX_VALUE

        generateMoves().forEach {
            val pos = it.producePosition(this)
            val shouldNotDecreaseDepth = (pos.removalCount > 0u && !pos.gameEndedDueToSmallAmountOfPieces())
            val evaluation = if (shouldNotDecreaseDepth) {
                pos.analyze(depth, alpha, beta)
            } else {
                pos.analyze((depth - 1u).toUByte(), alpha, beta)
            }

            if (pieceToMove) {
                if (evaluation > bestEvaluation) {
                    bestMove = it
                    bestEvaluation = evaluation
                }
                alpha = max(alpha, bestEvaluation)
            } else {
                if (evaluation < bestEvaluation) {
                    bestMove = it
                    bestEvaluation = evaluation
                }
                beta = min(beta, bestEvaluation)
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
    internal fun removalAmount(move: Movement): UByte {
        if (move.endIndex == null) return 0u

        return removeChecker[move.endIndex].count { list ->
            list.all { positions[it] == pieceToMove }
        }.toUByte()
    }

    /**
     * @return possible movements
     */
    fun generateMoves(): List<Movement> {
        return when (gameStateWithoutPossibleMovesCheck()) {
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
    }

    private fun generateRemovalMoves(): List<Movement> {
        return buildList {
            positions.forEachIndexed { index, piece ->
                if (piece == !pieceToMove) {
                    add(Movement(index, null))
                }
            }
        }
    }

    /**
     * @return all possible normal movements
     */
    private fun generateNormalMovements(): List<Movement> {
        return buildList {
            positions.forEachIndexed { startIndex, piece ->
                if (piece == pieceToMove) {
                    moveProvider[startIndex].forEach { endIndex ->
                        if (positions[endIndex] == null) {
                            add(Movement(startIndex, endIndex))
                        }
                    }
                }
            }
        }
    }

    /**
     * @return all possible flying movements
     */
    private fun generateFlyingMovements(): List<Movement> {
        return buildList {
            positions.forEachIndexed { startIndex, piece ->
                if (piece == pieceToMove) {
                    positions.forEachIndexed { endIndex, endPiece ->
                        if (endPiece == null) {
                            add(Movement(startIndex, endIndex))
                        }
                    }
                }
            }
        }
    }

    /**
     * @return possible piece placements
     */
    private fun generatePlacementMovements(): List<Movement> {
        return buildList {
            positions.forEachIndexed { endIndex, piece ->
                if (piece == null) {
                    add(Movement(null, endIndex))
                }
            }
        }
    }

    fun gameState(): GameState {
        return if (generateMoves().isEmpty())
            GameState.End
        else
            gameStateWithoutPossibleMovesCheck()
    }

    /**
     * @return state of the game for the currently playing moves doesn't check if user can make a move
     * reserved for internal use (there is a check in the minimax for possible moves availability)
     */
    private fun gameStateWithoutPossibleMovesCheck(): GameState {
        return when {
            (gameEndedDueToSmallAmountOfPieces()) -> {
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
     * this function is needed for unit tests,
     * especially needed is comparison with other positions
     */
    override fun equals(other: Any?): Boolean {
        if (other !is Position) {
            return false
        }
        if (freeGreenPieces != other.freeGreenPieces)
            return false
        if (freeBluePieces != other.freeBluePieces)
            return false
        if (pieceToMove != other.pieceToMove)
            return false
        for (i in 0..23) {
            if (positions[i] != other.positions[i]) {
                return false
            }
        }
        return true
    }

    /**
     * prints position in human-readable form
     */
    override fun toString(): String {
        var str = (
                """
        Position(
            arrayOf(
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
    fun uniqueHashCode(): Long {
        var result = 0L
        // handles removalCount (support for 0..2 removals)
        result += removalCount.toInt() * 205891132094649 // 3^30 = 205891132094649

        // handle freeGreenPieces (split into 3 parts to handle values 0..26)
        result += (freeGreenPieces.toInt() / 9 * 68630377364883) // 3^29 = 68630377364883
        result += (freeGreenPieces.toInt() % 9 / 3 * 22876792454961) // 3^28 = 22876792454961
        result += (freeGreenPieces.toInt() % 3 * 7625597484987) // 3^27 = 7625597484987

        // Handle freeBluePieces (split into 3 parts to handle values 0..26)
        result += (freeBluePieces.toInt() / 9 * 2541865828329) // 3^26 = 2541865828329
        result += (freeBluePieces.toInt() % 9 / 3 * 847288609443) // 3^25 = 847288609443
        result += (freeBluePieces.toInt() % 3 * 282429536481) // 3^24 = 282429536481

        // this variable will go from 3^0 = 1 up to 3^23 = 94143178827
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
