package com.example.alomundo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Pergunta3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pergunta3)

        var buttonS3 = findViewById<Button>(R.id.buttonS3)
        var buttonN3 = findViewById<Button>(R.id.buttonN3)
        val pontosRecebidos = intent.getIntExtra("Pontos", 0)
        buttonS3.setOnClickListener {
            val intent = Intent(this, Resposta::class.java)

            intent.putExtra("Pontos", pontosRecebidos + 1)
            startActivity(intent)
        }
        buttonN3.setOnClickListener {
            val intent = Intent(this, Resposta::class.java)

            intent.putExtra("Pontos", pontosRecebidos + 0)
            startActivity(intent)
        }
    }
}