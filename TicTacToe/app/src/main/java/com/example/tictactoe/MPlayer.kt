package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MPlayer : AppCompatActivity() {
    lateinit var onlinebtn : Button
    lateinit var offlinebtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mplayer)

        onlinebtn=findViewById(R.id.btnonline)
        offlinebtn = findViewById(R.id.btnoffline)

        onlinebtn.setOnClickListener {
            singleUser = false
            startActivity(Intent(this,onlineCode::class.java))
        }
        offlinebtn.setOnClickListener {
            singleUser = false
            startActivity(Intent(this,NameMplayer::class.java))
        }


    }
}