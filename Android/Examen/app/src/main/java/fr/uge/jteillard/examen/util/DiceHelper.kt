package fr.uge.jteillard.examen.util

object MatrixDiceHelper {
    private val data = listOf(
        0b0, // for 0, nothing is marked
        0b000010000, // for 1
        0b001000100, // for 2
        0b001010100, // for 3
        0b101000101, // for 4
        0b101010101, // for 5
        0b101101101 // for 6
    )

    fun getMark(diceValue: Int, row: Int, col: Int) =
        data[diceValue].and(1.shl((2 - row) * 3 + (2 - col))) > 0

    fun getMark(diceValue: Int, index: Int) =
        getMark(diceValue, index / 3, index % 3)
}