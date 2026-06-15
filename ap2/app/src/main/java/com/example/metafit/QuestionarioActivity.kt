package com.example.metafit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class QuestionarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionario)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, BasicInfoFragment())
                .commit()
        }
    }
}