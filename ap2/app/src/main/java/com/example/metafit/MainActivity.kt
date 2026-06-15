package com.example.metafit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnComecar = findViewById<Button>(R.id.btnComecar)
        val btnHistorico = findViewById<Button>(R.id.btnHistorico) // Resgatando o novo botão

        btnComecar.setOnClickListener {
            val intent = Intent(this, QuestionarioActivity::class.java)
            startActivity(intent)
        }

        btnHistorico.setOnClickListener {
            val intent = Intent(this, HistoricoActivity::class.java)
            startActivity(intent)
        }
    }
}