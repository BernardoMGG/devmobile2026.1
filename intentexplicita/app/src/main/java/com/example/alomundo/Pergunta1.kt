package com.example.alomundo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class Pergunta1 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pergunta1)

        var buttonS1 = findViewById<Button>(R.id.buttonS1)
        var buttonN1 = findViewById<Button>(R.id.buttonN1)
        buttonS1.setOnClickListener {
            val intent = Intent(this, Pergunta2::class.java)

            intent.putExtra("Pontos", 1)
            startActivity(intent)
        }
        buttonN1.setOnClickListener {
            val intent = Intent(this, Pergunta2::class.java)

            intent.putExtra("Pontos", 0)
            startActivity(intent)
        }
    }
}
