package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


var codeCreater = false
var code = "null"
var codeMatch = false
var checkTemp = true
var keyValue : String = "null"
class onlineCode : AppCompatActivity() {
    lateinit var TVhead : TextView
    lateinit var codeEdit : EditText
    lateinit var btnCreate : Button
    lateinit var btnJoin : Button
    lateinit var Pbar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_code)

        TVhead = findViewById(R.id.TVonline)
        codeEdit = findViewById(R.id.codeInput)
        btnCreate = findViewById(R.id.btnCreate)
        btnJoin = findViewById(R.id.btnJoin)
        Pbar = findViewById(R.id.Pbar)

        btnCreate.setOnClickListener {
            code = "null"
            codeMatch = false
            checkTemp = true
            keyValue = "null"
            code = codeEdit.text.toString()
            btnCreate.visibility = View.GONE
            btnJoin.visibility = View.GONE
            codeEdit.visibility = View.GONE
            TVhead.visibility = View.GONE
            Pbar.visibility = View.VISIBLE
            if (code != "null" && code!=""){
                codeCreater = true
                FirebaseDatabase.getInstance().reference.child("codes").addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var check = isValueAvailable(snapshot,code)
                        Handler().postDelayed({
                            if(check==true){
                                btnCreate.visibility = View.VISIBLE
                                btnJoin.visibility = View.VISIBLE
                                codeEdit.visibility = View.VISIBLE
                                TVhead.visibility = View.VISIBLE
                                Pbar.visibility = View.GONE

                            } else{
                                FirebaseDatabase.getInstance().reference.child("codes").push().setValue(code)
                                isValueAvailable(snapshot, code)
                                checkTemp = false
                                Handler().postDelayed({
                                    accepted()
                                    Toast.makeText(this@onlineCode,"Please wait for Opponent",Toast.LENGTH_SHORT).show()
                                },300)
                            }
                        },2000)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }else{
                btnCreate.visibility = View.VISIBLE
                btnJoin.visibility = View.VISIBLE
                codeEdit.visibility = View.VISIBLE
                TVhead.visibility = View.VISIBLE
                Pbar.visibility = View.GONE
                Toast.makeText(this,"Please Insert a valid code",Toast.LENGTH_SHORT).show()

            }


        }

        btnJoin.setOnClickListener {
            code = "null"
            codeMatch = false
            checkTemp = true
            keyValue = "null"
            code = codeEdit.text.toString()
            if(code!="null"&& code!=""){
                btnCreate.visibility = View.GONE
                btnJoin.visibility = View.GONE
                codeEdit.visibility = View.GONE
                TVhead.visibility = View.GONE
                Pbar.visibility = View.VISIBLE
                codeCreater = false
                FirebaseDatabase.getInstance().reference.child("codes").addValueEventListener(object:ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var data : Boolean = isValueAvailable(snapshot, code)
                        Handler().postDelayed({
                            if(data==true){
                                codeMatch = true
                                accepted()
                                btnCreate.visibility = View.VISIBLE
                                btnJoin.visibility = View.VISIBLE
                                codeEdit.visibility = View.VISIBLE
                                TVhead.visibility = View.VISIBLE
                                Pbar.visibility = View.GONE

                        }else{
                                btnCreate.visibility = View.VISIBLE
                                btnJoin.visibility = View.VISIBLE
                                codeEdit.visibility = View.VISIBLE
                                TVhead.visibility = View.VISIBLE
                                Pbar.visibility = View.GONE
                                Toast.makeText(this@onlineCode,"Invalid Code",Toast.LENGTH_SHORT).show()
                            }
                        },2000)

                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

            }else{
                Toast.makeText(this,"Please Enter Valid Code",Toast.LENGTH_SHORT).show()
            }

        }


    }

    fun accepted(){
        var PlayerName = intent.getStringExtra("PNAME")
        var intentPassName = Intent(this@onlineCode,OnlineMP::class.java)
        intentPassName.putExtra("PNAME",PlayerName)
        startActivity(intentPassName)

        btnCreate.visibility = View.VISIBLE
        btnJoin.visibility = View.VISIBLE
        codeEdit.visibility = View.VISIBLE
        TVhead.visibility = View.VISIBLE
        Pbar.visibility = View.GONE

    }
    fun isValueAvailable(snapshot: DataSnapshot,code : String): Boolean{
        var data = snapshot.children
        data.forEach{
            var value = it.getValue().toString()
            if (value == code){
                keyValue = it.key.toString()
                return true

            }
        }
        return false
    }
}