package com.example.alomundo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class Resposta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resposta)

        val pontuacaoFinal = intent.getIntExtra("Pontos", 0)
        val resposta = findViewById<TextView>(R.id.Resposta)

        if (pontuacaoFinal == 3) {
            resposta.text = "Você é o Superman!"
        } else if (pontuacaoFinal == 2){
            resposta.text = "Você é o Homem-Aranha!"
        } else if (pontuacaoFinal == 1){
            resposta.text = "Você é o Wolverine!"
        } else if (pontuacaoFinal == 0){
            resposta.text = "Você é um vilão!"
        }

        findViewById<Button>(R.id.button_github).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("https://github.com/BernardoMGG/devmobile2026.1"))
            startActivity(intent)
        }
    }
}
