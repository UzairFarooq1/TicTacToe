package com.example.tictactoe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.models.Board
import com.example.tictactoe.models.BoardState
import com.example.tictactoe.models.Cell
import com.example.tictactoe.models.CellState

class AIModel : ViewModel() {
    val mainBoard = Board()
    val board = MutableLiveData(mainBoard)

    private fun updateBoard() {
        board.value = mainBoard
    }

    fun boardClicked(cell: Cell) {
        if (mainBoard.setCell(cell, CellState.x)) {
            updateBoard()
            if (mainBoard.boardState == BoardState.INCOMPLETE) {
                aiTurn()
            }
        }
    }

    private fun aiTurn() {
        val oWinningCell = mainBoard.findNextWinningMove(CellState.o)
        val xWinningCell = mainBoard.findNextWinningMove(CellState.x)
        when {
            oWinningCell != null -> mainBoard.setCell(oWinningCell, CellState.o)
            xWinningCell != null -> mainBoard.setCell(xWinningCell, CellState.o)
            mainBoard.setCell(Cell.CENTER_CENTER, CellState.o) -> Unit
            else -> do {
                val nextCell = Cell.values().random()
                val placeSuccess = mainBoard.setCell(nextCell, CellState.o)
            } while (!placeSuccess)
        }

        updateBoard()
    }

    fun resetBoard() {
        mainBoard.clearBoard()
        updateBoard()
    }
}