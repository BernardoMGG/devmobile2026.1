package com.example.metafit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class BasicInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_basic_info, container, false)

        val etNome = view.findViewById<EditText>(R.id.etNomeCompleto)
        val etAltura = view.findViewById<EditText>(R.id.etAltura)
        val etPesoAtual = view.findViewById<EditText>(R.id.etPesoAtual)
        val etPesoDesejado = view.findViewById<EditText>(R.id.etPesoDesejado)
        val btnProximo = view.findViewById<Button>(R.id.btnProximo)

        btnProximo.setOnClickListener {
            val bundle = Bundle().apply {
                putString("NOME", etNome.text.toString())
                putString("altura", etAltura.text.toString())
                putString("pesoAtual", etPesoAtual.text.toString())
                putString("pesoDesejado", etPesoDesejado.text.toString())
            }

            val routineFragment = RoutineFragment()
            routineFragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, routineFragment)
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}