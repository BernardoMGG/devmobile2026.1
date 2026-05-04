package com.example.alomundo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Pergunta2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pergunta2)

        var buttonS2 = findViewById<Button>(R.id.buttonS2)
        var buttonN2 = findViewById<Button>(R.id.buttonN2)
        val pontosRecebidos = intent.getIntExtra("Pontos", 0)
        buttonS2.setOnClickListener {
            val intent = Intent(this, Pergunta3::class.java)

            intent.putExtra("Pontos", pontosRecebidos + 1)
            startActivity(intent)
        }
        buttonN2.setOnClickListener {
            val intent = Intent(this, Pergunta3::class.java)

            intent.putExtra("Pontos", pontosRecebidos + 0)
            startActivity(intent)
        }
    }
}