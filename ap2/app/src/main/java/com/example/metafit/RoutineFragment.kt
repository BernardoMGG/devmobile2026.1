package com.example.metafit

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment

class RoutineFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_routine, container, false)

        val spDiet = view.findViewById<Spinner>(R.id.spDiet)
        val spPreference = view.findViewById<Spinner>(R.id.spPreference)
        val rgExercise = view.findViewById<RadioGroup>(R.id.rgExercise)
        val btnCalcular = view.findViewById<Button>(R.id.btnCalcular)

        val dietOptions = arrayOf("Equilibrada (3 refeições)", "Fracionada (5+ refeições)", "Jejum Intermitente")
        val dietAdapter = ArrayAdapter(requireContext(), R.layout.spinner_text, dietOptions)
        dietAdapter.setDropDownViewResource(R.layout.spinner_dropdown_text)
        spDiet.adapter = dietAdapter

        val prefOptions = arrayOf("Levantamento de Peso / Força", "Exercícios Aeróbicos / Cárdio", "Esportes / Lutas")
        val prefAdapter = ArrayAdapter(requireContext(), R.layout.spinner_text, prefOptions)
        prefAdapter.setDropDownViewResource(R.layout.spinner_dropdown_text)
        spPreference.adapter = prefAdapter

        btnCalcular.setOnClickListener {
            val nomeStr = arguments?.getString("NOME") ?: "Usuário"
            val alturaStr = arguments?.getString("altura") ?: "0"
            val pesoAtualStr = arguments?.getString("pesoAtual") ?: "0"
            val pesoDesejadoStr = arguments?.getString("pesoDesejado") ?: "0"

            val rotinaAlimentar = spDiet.selectedItem.toString()
            val preferenciaTreino = spPreference.selectedItem.toString()

            val selectedRbId = rgExercise.checkedRadioButtonId
            if (selectedRbId == -1) {
                Toast.makeText(context, "Por favor, selecione a frequência de exercícios.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedRb = view.findViewById<RadioButton>(selectedRbId)
            val diasExercicio = when (selectedRb.id) {
                R.id.rbSedentario -> 1
                R.id.rbModerado -> 3
                R.id.rbIntenso -> 5
                else -> 0
            }

            val intent = Intent(requireActivity(), ResultadoActivity::class.java).apply {
                putExtra("NOME_COMPLETO", nomeStr)
                putExtra("PESO_ATUAL", pesoAtualStr.toDoubleOrNull() ?: 0.0)
                putExtra("PESO_DESEJADO", pesoDesejadoStr.toDoubleOrNull() ?: 0.0)
                putExtra("ROTINA_ALIMENTAR", rotinaAlimentar)
                putExtra("PREFERENCIA_TREINO", preferenciaTreino)
                putExtra("DIAS_EXERCICIO", diasExercicio)
            }
            startActivity(intent)
        }

        return view
    }
}