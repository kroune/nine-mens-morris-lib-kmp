## This project provides a library for a game called [**"Nine mens morris"**](https://en.wikipedia.org/wiki/Nine_men%27s_morris)

#### All information and source code are provided AS-IS, without express or implied warranties.

New game position is created using [position class](src/main/kotlin/com/kroune/nineMensMorrisLib/Position.kt)

```kotlin
    val piecePlacements = arrayOf(
        GREEN,                  EMPTY,                  EMPTY,
                BLUE_,          EMPTY,          EMPTY,
                        EMPTY,  BLUE_,  EMPTY,
        EMPTY,  EMPTY,  EMPTY,          EMPTY,  EMPTY,  EMPTY,
                        EMPTY,  EMPTY,  EMPTY,
                EMPTY,          EMPTY,          EMPTY,
        EMPTY,                  EMPTY,                  EMPTY
    )

    Position(
        positions = piecePlacments,
        greenPiecesAmount = 4u,
        bluePiecesAmount = 2u,
        pieceToMove = false,
        removalCount = 1u
    )
```

**warning** it is recommended not to touch greenPiecesAmount/bluePiecesAmount, they are used for calculations speed up
breaking there functionality may lead to hardly debuggable behaviour

to get bot recommendation you can use 
```kotlin
    Position.solve(depth)
```
first element of the pair is final position evaluation (note: it is always calculated for green pieces)
and second one is winning move sequence (note: the first move is the last one in the sequence)



**Project progress**
1. [x] write unit tests
2. [x] use code style analyser
3. [x] create a better hash function
4. [x] reuse piece count data
5. [x] create transposition hash map
6. [ ] reuse hash calculations
7. [ ] implement NNUE position evaluation
8. [ ] create a table base of all possible moves (since there isn't as many positions as in chess)
9. [ ] implement alpha-beta pruning (and move ordering)
10. [ ] reuse possible moves generation