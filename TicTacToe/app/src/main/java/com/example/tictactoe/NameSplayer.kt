package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class NameSplayer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name_splayer)

        var P1: EditText = findViewById(R.id.editTextP1)
        //var P2: EditText = findViewById(R.id.editTextP2)
        var btnPlay: Button = findViewById(R.id.buttonPlay)

        btnPlay.setOnClickListener {

            var Player1 = P1.text.toString()
            //var Player2 = P2.text.toString()
            if (Player1.isEmpty()) {
                Toast.makeText(this, "Please Enter Player Name", Toast.LENGTH_SHORT).show()
            } else {

                var intentPlay = Intent(this@NameSplayer, AIPlay::class.java)
                intentPlay.putExtra("P1NAME", P1.text.toString())
                startActivity(intentPlay)
            }
        }
    }
}