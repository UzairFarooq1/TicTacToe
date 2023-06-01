package com.example.tictactoe.models


import androidx.annotation.DrawableRes
import com.example.tictactoe.R

sealed class CellState(@DrawableRes val res: Int) {
    object Blank : CellState(R.drawable.ic_blank)
    object x : CellState(R.drawable.ic_x)
    object o : CellState(R.drawable.ic_o)
}