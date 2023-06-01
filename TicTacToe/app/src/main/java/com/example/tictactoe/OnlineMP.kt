package com.example.tictactoe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlin.system.exitProcess

var isMymove = codeCreater
class OnlineMP : AppCompatActivity() {
    lateinit var TVP1 : TextView
    lateinit var TVP2 : TextView
    lateinit var btn0 : Button
    lateinit var btn1 : Button
    lateinit var btn2 : Button
    lateinit var btn3 : Button
    lateinit var btn4 : Button
    lateinit var btn5 : Button
    lateinit var btn6 : Button
    lateinit var btn7 : Button
    lateinit var btn8 : Button
    lateinit var turn : TextView
    lateinit var btnReset : Button
    var P1Count = 0
    var P2Count = 0
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var Empty = ArrayList<Int>()
    var activeUser = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_mp)
        TVP1 = findViewById(R.id.TVP1)
        TVP2 = findViewById(R.id.TVP2)
        btn0 = findViewById(R.id.btn0)
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)
        btn6 = findViewById(R.id.btn6)
        btn7 = findViewById(R.id.btn7)
        btn8 = findViewById(R.id.btn8)
        btnReset = findViewById(R.id.btnReset)
        turn = findViewById(R.id.TVTurn)
        btnReset.setOnClickListener {
            reset()
        }



        FirebaseDatabase.getInstance().reference.child("data").child(code).addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                var data = snapshot.value
                if (isMymove==true){
                    isMymove = false
                    moveOnline(data.toString(), isMymove)
                }else{
                    isMymove = true
                    moveOnline(data.toString(), isMymove)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                reset()
                Toast.makeText(this@OnlineMP,"Game Reset",Toast.LENGTH_SHORT).show()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun moveOnline(data: String, move: Boolean) {

        if(move){
            var btnSelect : Button?
            btnSelect = when(data.toInt()){
                0->btn0
                1->btn1
                2->btn2
                3->btn3
                4->btn4
                5->btn5
                6->btn6
                7->btn7
                8->btn8
                else ->{btn0}
            }
            btnSelect.text = "0"
            turn.text = "Turn : Your Turn"
            btnSelect.setTextColor(Color.parseColor("#D22BB804"))
            player2.add(data.toInt())
            Empty.add(data.toInt())
            btnSelect.isEnabled=false
            checkWinner()

        }


    }

    private fun checkWinner() :Int {
        if (player1.contains(0) && player1.contains(1) && player1.contains(2) ||
            player1.contains(0) && player1.contains(3) && player1.contains(6)||
            player1.contains(2) && player1.contains(5) && player1.contains(8)||
            player1.contains(6) && player1.contains(7) && player1.contains(8)||
            player1.contains(3) && player1.contains(4) && player1.contains(5)||
            player1.contains(1) && player1.contains(4) && player1.contains(7)||
            player1.contains(0) && player1.contains(4) && player1.contains(8)||
            player1.contains(2) && player1.contains(4) && player1.contains(6)
                ){
            P1Count +=1
            btndisable()
            disableReset()
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("You have Won! \n Do you want to play again")
            //var P1score : TextView = findViewById(R.id.TVP1Score)

            build.setPositiveButton("OK"){dialog,which ->
                reset()
            }
            build.setNegativeButton("Exit"){dialog,which->
                removeCode()
                exitProcess(1)

            }
            Handler().postDelayed(Runnable { build.show()}, 2000)
            return 1
        }else if(
            player2.contains(0) && player2.contains(1) && player2.contains(2) ||
            player2.contains(0) && player2.contains(3) && player2.contains(6)||
            player2.contains(2) && player2.contains(5) && player2.contains(8)||
            player2.contains(6) && player2.contains(7) && player2.contains(8)||
            player2.contains(3) && player2.contains(4) && player2.contains(5)||
            player2.contains(1) && player2.contains(4) && player2.contains(7)||
            player2.contains(0) && player2.contains(4) && player2.contains(8)||
            player2.contains(2) && player2.contains(4) && player2.contains(6)
        ){
            P2Count +=1
            btndisable()
            disableReset()
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Opponent has Won! \n Do you want to play again?")
            build.setPositiveButton("OK"){dialog,which ->
                reset()
            }
            build.setNegativeButton("Exit"){dialog,which->
                removeCode()
                exitProcess(1)

            }
            Handler().postDelayed(Runnable { build.show()}, 2000)
            return 1
        }else if(Empty.contains(0) && Empty.contains(1) && Empty.contains(2) && Empty.contains(3)
            && Empty.contains(4) && Empty.contains(5) && Empty.contains(6) && Empty.contains(7) &&Empty.contains(8)){
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Draw")
            build.setMessage("Game draw"+"\n\n"+"Do you want to play again")
            build.setPositiveButton("OK"){dialog,which->
                reset()
            }
            build.setNegativeButton("Exit"){dialog,which->
                exitProcess(1)
                removeCode()
            }
            build.show()
            return 1

        }
        return 0
    }

    fun playnow(btnSelect : Button, currCell : Int){
        btnSelect.text = "X"
        Empty.remove(currCell)
        turn.text = "Turn : Opponent's turn"
        btnSelect.setTextColor(Color.BLACK)
        player1.add(currCell)
        Empty.add(currCell)
        btnSelect.isEnabled = false
        checkWinner()

    }

    fun reset() {
        player1.clear()
        player2.clear()
        Empty.clear()
        activeUser = 1
        for (i in 1..9){
            var btnSelect : Button?
            btnSelect = when(i){
                0->btn0
                1->btn1
                2->btn2
                3->btn3
                4->btn4
                5->btn5
                6->btn6
                7->btn7
                8->btn8
                else ->{btn0}
            }
            btnSelect.isEnabled = true
            btnSelect.text = ""
            TVP1.text = "Player 1 (X) : $P1Count"
            TVP2.text = "Player 2 (O) : $P2Count"
            btnSelect.setBackgroundColor(Color.parseColor("#FF4444"))
            isMymove = codeCreater
            if(codeCreater){
                FirebaseDatabase.getInstance().reference.child("data").child(code).removeValue()
            }
        }
    }

    fun btndisable(){
        for (i in 1..9){
            val btnselected = when(i){
                0->btn0
                1->btn1
                2->btn2
                3->btn3
                4->btn4
                5->btn5
                6->btn6
                7->btn7
                8->btn8
                else ->{btn0}
            }
            if (btnselected.isEnabled == true)
                btnselected.isEnabled = false
            btnselected.setBackgroundColor(Color.parseColor("#FF4444"))
        }
    }

    fun removeCode(){
        if(codeCreater){
            FirebaseDatabase.getInstance().reference.child("codes").child(keyValue).removeValue()
        }
    }

    fun disableReset(){
        btnReset.isEnabled = false
        Handler().postDelayed(Runnable { btnReset.isEnabled = true },2000)
    }

    fun updateDB(cellId : Int){
        FirebaseDatabase.getInstance().reference.child("data").child(code).push().setValue(cellId)
    }

    override fun onBackPressed() {
        removeCode()
        if (codeCreater){
            FirebaseDatabase.getInstance().reference.child("data").child(code).removeValue()
        }
        exitProcess(0)
    }


    fun buttonClick(view: View) {

        if (isMymove){
            val but = view as Button
            var cellOnline = 0
            when (but.id){
                R.id.btn0 -> cellOnline =0
                R.id.btn1 -> cellOnline =1
                R.id.btn2 -> cellOnline =2
                R.id.btn3 -> cellOnline =3
                R.id.btn4 -> cellOnline =4
                R.id.btn5 -> cellOnline =5
                R.id.btn6 -> cellOnline =6
                R.id.btn7 -> cellOnline =7
                R.id.btn8 -> cellOnline =8
                else->{cellOnline= 0}
            }
            playerTurn = false;
            Handler().postDelayed(Runnable { playerTurn = true},600)
            playnow(but,cellOnline)
            updateDB(cellOnline)


        }
    }
}