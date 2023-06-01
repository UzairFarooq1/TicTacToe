package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts

var singleUser = false

class MainActivity : AppCompatActivity() {
    lateinit var SPlayerBtn:Button
    lateinit var MPlayerBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SPlayerBtn = findViewById(R.id.btnSplayer)
        MPlayerBtn = findViewById(R.id.btnMplayer)

        SPlayerBtn.setOnClickListener {
            singleUser = true
            startActivity(Intent(this,NameSplayer::class.java))
        }
        MPlayerBtn.setOnClickListener {
            singleUser = false
            startActivity(Intent(this,MPlayer::class.java))
        }
    }
}