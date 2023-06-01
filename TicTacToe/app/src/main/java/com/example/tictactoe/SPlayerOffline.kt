package com.example.tictactoe

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.viewbinding.ViewBinding
import com.example.tictactoe.databinding.ActivitySplayerOfflineBinding
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

class SPlayerOffline : AppCompatActivity() {

    private lateinit var binding: ActivitySplayerOfflineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplayerOfflineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnReset : Button = binding.btnReset
        btnReset.setOnClickListener {
            ClearAll()
        }
        var P1Name = intent.getStringExtra("P1NAME")
        binding.TVP1.text = P1Name + "(X)"
        var P2Name = intent.getStringExtra("P2NAME")
        binding.TVP2.text = P2Name + "(O)"

    }


    fun buClick(view: View) {

        val buSelected:Button = view as Button
        var cellId=0
        when(buSelected.id)
        {
            R.id.btn0 -> cellId=1
            R.id.btn1 -> cellId=2
            R.id.btn2-> cellId=3

            R.id.btn3 -> cellId=4
            R.id.btn4-> cellId=5
            R.id.btn5 -> cellId=6

            R.id.btn6 -> cellId=7
            R.id.btn7 -> cellId=8
            R.id.btn8 -> cellId=9

        }
        //Toast.makeText(this,"ID: $cellId",Toast.LENGTH_SHORT).show()
        PlayGame(cellId,buSelected)
        checkWinner()
    }

    var emptyCell = ArrayList<Int>()
    var player1=ArrayList<Int>()
    var player2=ArrayList<Int>()
    var activeP=1

    private fun PlayGame(cellId: Int, buSelected: Button) {

        if (activeP == 1) {
            buSelected.text = "X"
            buSelected.setTextColor(Color.BLACK)
            //buSelected.setBackgroundColor(Color.GRAY)
            player1.add(cellId)
            emptyCell.add(cellId)
            buSelected.isEnabled = false
            activeP = 2



        } else
        {

            buSelected.text = "O"
            buSelected.setTextColor(Color.BLACK)
            //buSelected.setBackgroundColor(Color.LTGRAY)
            player2.add(cellId)
            emptyCell.add(cellId)
            activeP = 1
            buSelected.isEnabled = false



        }
    }
        private fun checkWinner(){
            var winner = -1

            // row 1
            if (player1.contains(1) && player1.contains(2) && player1.contains(3)) {
                winner = 1
            }
            if (player2.contains(1) && player2.contains(2) && player2.contains(3)) {
                winner = 2
            }
            // row 2
             if (player1.contains(4) && player1.contains(5) && player1.contains(6)) {
                winner = 1
            }
            if (player2.contains(4) && player2.contains(5) && player2.contains(6)) {
                winner = 2
            }
            // row 3
             if (player1.contains(7) && player1.contains(8) && player1.contains(9)) {
                winner = 1
            }
            if (player2.contains(7) && player2.contains(8) && player2.contains(9)) {
                winner = 2
            }


            // col 1

             if (player1.contains(1) && player1.contains(4) && player1.contains(7)) {
                winner = 1
            }
            if (player2.contains(1) && player2.contains(4) && player2.contains(7)) {
                winner = 2
            }
            // col 2
             if (player1.contains(2) && player1.contains(5) && player1.contains(8)) {
                winner = 1
            }
            if (player2.contains(2) && player2.contains(5) && player2.contains(8)) {
                winner = 2
            }
            // col 3
             if (player1.contains(3) && player1.contains(6) && player1.contains(9)) {
                winner = 1
            }
            if (player2.contains(3) && player2.contains(6) && player2.contains(9)) {
                winner = 2
            }
            if (player1.contains(1) && player1.contains(5) && player1.contains(9)) {
                winner = 1
            }
            if (player2.contains(1) && player2.contains(5) && player2.contains(9)) {
                winner = 2
            }
            if (player1.contains(3) && player1.contains(5) && player1.contains(7)) {
                winner = 1
            }
            if (player2.contains(3) && player2.contains(5) && player2.contains(7)) {
                winner = 2
            }
            if (emptyCell.contains(1) && emptyCell.contains(2) && emptyCell.contains(3) && emptyCell.contains(4) && emptyCell.contains(5) &&
                emptyCell.contains(6) && emptyCell.contains(7) && emptyCell.contains(8) && emptyCell.contains(9)){
                winner = 0
            }



            if (winner == 1 ){
                countP1win++
                btndisable()
                disableReset()
                val build = AlertDialog.Builder(this)
                build.setTitle("Game Over")
                build.setMessage( binding.TVP1.text.toString() +" has Won! \n Do you want to play again?")
                build.setPositiveButton("OK"){dialog,which ->
                    ClearAll()
                }
                build.setNegativeButton("Exit"){dialog,which->
                    exitProcess(1)
                }
                build.show()
            }
            else if ( winner == 2){
                countP2win++
                btndisable()
                disableReset()
                val build = AlertDialog.Builder(this)
                build.setTitle("Game Over")
                build.setMessage( binding.TVP2.text.toString() + " has Won! \n Do you want to play again?")
                build.setPositiveButton("OK"){dialog,which ->
                    ClearAll()
                }
                build.setNegativeButton("Exit"){dialog,which->
                    exitProcess(1)
                }
                build.show()

            }
            else if (winner == 0)
            {
                countDraw++
                btndisable()
                disableReset()
                val build = AlertDialog.Builder(this)
                build.setTitle("Game Over")
                build.setMessage("It's a Draw! \n Do you want to play again?")
                build.setPositiveButton("OK"){dialog,which ->
                    ClearAll()
                }
                build.setNegativeButton("Exit"){dialog,which->
                    exitProcess(1)
                }
                build.show()
            }
            else
            {

            }

            }




    var countP1win=0
    var countP2win=0
    var countDraw=0



    fun ClearAll() {
        player1.clear()
        player2.clear()
        emptyCell.clear()
        activeP = 1;
        for (i in 1..9){
            var btnSelect : Button?
            btnSelect = when(i){
                1->binding.btn0
                2->binding.btn1
                3->binding.btn2
                4->binding.btn3
                5->binding.btn4
                6->binding.btn5
                7->binding.btn6
                8->binding.btn7
                9->binding.btn8
                else ->{binding.btn0}
            }
            btnSelect.isEnabled = true
            btnSelect.text = ""
            binding.P1Score.text = " $countP1win"
            binding.P2Score.text = " $countP2win"
            btnSelect.setBackgroundColor(Color.parseColor("#FF4444"))

        }
    }

    private fun btndisable() {
        for (i in 1..9) {
            val btnSelect = when(i){

                1->binding.btn0
                2->binding.btn1
                3->binding.btn2
                4->binding.btn3
                5->binding.btn4
                6->binding.btn5
                7->binding.btn6
                8->binding.btn7
                9->binding.btn8
                else ->{binding.btn0
                }
            }
            if(btnSelect.isEnabled == true)
                btnSelect.isEnabled == false

            btnSelect.setBackgroundColor(Color.parseColor("#FF4444"))
        }


    }

    private fun disableReset() {
        binding.btnReset.isEnabled = false
        Handler().postDelayed(Runnable { binding.btnReset.isEnabled = true },2000)

    }


}



