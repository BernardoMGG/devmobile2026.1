package com.example.intentsimplicitas

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val numero = findViewById<EditText>(R.id.et_number)

        // 📞 Abrir discador (sem permissão)
        findViewById<Button>(R.id.btn_call).setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:${numero.text}"))
            startActivity(intent)
        }

        // 🌐 Abrir navegador
        findViewById<Button>(R.id.btn_browser).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("https://google.com"))
            startActivity(intent)
        }

        // 📷 Abrir câmera
        findViewById<Button>(R.id.btn_camera).setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(intent)
        }

        // 📧 Enviar e-mail
        findViewById<Button>(R.id.btn_email).setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:exemplo@email.com")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Assunto do email")
            intent.putExtra(Intent.EXTRA_TEXT, "Mensagem do email")
            startActivity(intent)
        }

        // 📍 Abrir localização no mapa
        findViewById<Button>(R.id.btn_map).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("geo:-22.9068,-43.1729")) // Ex: Rio de Janeiro
            startActivity(intent)
        }
    }
}