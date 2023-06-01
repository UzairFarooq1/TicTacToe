package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupWindow
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.tictactoe.databinding.ActivityAiplayBinding
import com.example.tictactoe.models.Board
import com.example.tictactoe.models.BoardState
import com.example.tictactoe.models.Cell
import com.example.tictactoe.models.CellState

class AIPlay : AppCompatActivity() {
    lateinit var binding: ActivityAiplayBinding
    val vm: AIModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAiplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm.board.observe(this, updateBoard)
        bindClickEvents()
        var PName = intent.getStringExtra("P1NAME")
        binding.TVP1.text = PName
    }
    var PWin = 0
    var AIWin = 0

    val updateBoard = Observer<Board> { board ->
        binding.square0.setImageResource(board.topLeft.res)
        binding.square1.setImageResource(board.topCenter.res)
        binding.square2.setImageResource(board.topRight.res)
        binding.square3.setImageResource(board.centerLeft.res)
        binding.square4.setImageResource(board.centerCenter.res)
        binding.square5.setImageResource(board.centerRight.res)
        binding.square6.setImageResource(board.bottomLeft.res)
        binding.square7.setImageResource(board.bottomCenter.res)
        binding.square8.setImageResource(board.bottomRight.res)
        when (board.boardState) {
            BoardState.X_WON -> {
                setupBoard(true)
                Toast.makeText(this@AIPlay,"Congratutaions!!\n       You win", Toast.LENGTH_SHORT).show()
                PWin++
                binding.TVScoreP1.text = PWin.toString()
                binding.TVScoreAI.text = AIWin.toString()
            }
            BoardState.O_WON -> {
                setupBoard(true)
                Toast.makeText(this@AIPlay,"Akira Won", Toast.LENGTH_SHORT).show()
                AIWin++
                binding.TVScoreAI.text = AIWin.toString()
                binding.TVScoreP1.text = PWin.toString()
            }
            BoardState.DRAW -> {
                setupBoard(true)
                Toast.makeText(this@AIPlay,"Its a Draw", Toast.LENGTH_SHORT).show()
            }
            BoardState.INCOMPLETE -> {
                setupBoard()
            }
        }
    }


    private fun setupBoard(disable: Boolean = false) {
        binding.square0.isEnabled = !disable
        binding.square1.isEnabled = !disable
        binding.square2.isEnabled = !disable
        binding.square3.isEnabled = !disable
        binding.square4.isEnabled = !disable
        binding.square5.isEnabled = !disable
        binding.square6.isEnabled = !disable
        binding.square7.isEnabled = !disable
        binding.square8.isEnabled = !disable

        binding.square0.alpha = if (disable) 0.5f else 1f
        binding.square1.alpha = if (disable) 0.5f else 1f
        binding.square2.alpha = if (disable) 0.5f else 1f
        binding.square3.alpha = if (disable) 0.5f else 1f
        binding.square4.alpha = if (disable) 0.5f else 1f
        binding.square5.alpha = if (disable) 0.5f else 1f
        binding.square6.alpha = if (disable) 0.5f else 1f
        binding.square7.alpha = if (disable) 0.5f else 1f
        binding.square8.alpha = if (disable) 0.5f else 1f
    }

    private fun bindClickEvents() {
        binding.square0.setOnClickListener { vm.boardClicked(Cell.TOP_LEFT) }
        binding.square1.setOnClickListener { vm.boardClicked(Cell.TOP_CENTER) }
        binding.square2.setOnClickListener { vm.boardClicked(Cell.TOP_RIGHT) }
        binding.square3.setOnClickListener { vm.boardClicked(Cell.CENTER_LEFT) }
        binding.square4.setOnClickListener { vm.boardClicked(Cell.CENTER_CENTER) }
        binding.square5.setOnClickListener { vm.boardClicked(Cell.CENTER_RIGHT) }
        binding.square6.setOnClickListener { vm.boardClicked(Cell.BOTTOM_LEFT) }
        binding.square7.setOnClickListener { vm.boardClicked(Cell.BOTTOM_CENTER) }
        binding.square8.setOnClickListener { vm.boardClicked(Cell.BOTTOM_RIGHT) }
        binding.buttonReset.setOnClickListener { vm.resetBoard() }
    }



}