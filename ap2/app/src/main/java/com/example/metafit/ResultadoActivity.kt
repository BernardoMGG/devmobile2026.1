package com.example.metafit // Mantenha o seu pacote original!

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultadoActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)

        val tvTituloPlano = findViewById<TextView>(R.id.tvTituloPlano)
        val tvMeta = findViewById<TextView>(R.id.tvMeta)
        val tvDias = findViewById<TextView>(R.id.tvDias)
        val tvCalorias = findViewById<TextView>(R.id.tvCalorias)
        val tvRecomendacao = findViewById<TextView>(R.id.tvRecomendacao)
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)
        val btnCompartilhar = findViewById<Button>(R.id.btnCompartilhar)

        val nomeCompleto = intent.getStringExtra("NOME_COMPLETO") ?: "Usuário"
        val altura = intent.getDoubleExtra("ALTURA", 0.0)
        val pesoAtual = intent.getDoubleExtra("PESO_ATUAL", 0.0)
        val pesoDesejado = intent.getDoubleExtra("PESO_DESEJADO", 0.0)
        val rotinaAlimentar = intent.getStringExtra("ROTINA_ALIMENTAR") ?: ""
        val preferenciaTreino = intent.getStringExtra("PREFERENCIA_TREINO") ?: ""
        val diasExercicio = intent.getIntExtra("DIAS_EXERCICIO", 0)

        tvTituloPlano.text = "Plano de $nomeCompleto"
        val diferencaPeso = pesoDesejado - pesoAtual
        val acao = if (diferencaPeso < 0) "Perder" else "Ganhar"
        val quilosAbsolutos = Math.abs(diferencaPeso)

        val caloriasTotais = quilosAbsolutos * 7700
        val diasNecessarios = if (quilosAbsolutos == 0.0) 0 else (caloriasTotais / 500).toInt()
        val caloriasMeta = if (diferencaPeso < 0) -500.0 else 500.0

        val recomendacaoFinal = when {
            acao == "Ganhar" && preferenciaTreino.contains("Peso") ->
                "Hipertrofia Clássica: Foco em exercícios compostos pesados (Supino, Agachamento, Levantamento Terra) com repetições de 8 a 12 e descanso longo entre as séries."

            acao == "Perder" && preferenciaTreino.contains("Aeróbicos") ->
                "Queima Calórica Eficiente: Treinos dinâmicos de alta intensidade (HIIT) intercalados com corrida estabilizada, ciclismo ou natação de 40 minutos."

            acao == "Perder" && preferenciaTreino.contains("Peso") ->
                "Recomposição Corporal: Musculação focada em manter a massa magra acelerando o metabolismo + 20 minutos de cardio moderado ao término de cada treino."

            preferenciaTreino.contains("Esportes") ->
                "Condicionamento Atlético: Circuitos funcionais de agilidade, pliometria, sacos de pancada ou prática direta de modalidades como Jiu-Jitsu/Boxe."

            else -> "Treino de Adaptação Geral: Calistenia básica (flexões, barras fixed, passadas) associada a caminhadas aceleradas diárias para melhora da resistência."
        }

        tvMeta.text = "Meta: $acao ${String.format("%.1f", quilosAbsolutos)} kg"
        tvDias.text = "Tempo estimado: $diasNecessarios dias"
        tvCalorias.text = if (diferencaPeso < 0) "Meta: Déficit de 500 kcal/dia" else "Meta: Superávit de 500 kcal/dia"
        tvRecomendacao.text = recomendacaoFinal

        btnCompartilhar.setOnClickListener {
            val mensagemShare = "Montei meu plano no MetaFit!\nObjetivo: $acao $quilosAbsolutos kg em $diasNecessarios dias.\nRecomendação: $recomendacaoFinal"
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, mensagemShare)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(sendIntent, "Enviar plano para..."))
        }

        btnSalvar.setOnClickListener {
            val plano = FitnessPlan(
                nome_completo = nomeCompleto,
                altura = altura,
                peso_atual = pesoAtual,
                peso_desejado = pesoDesejado,
                rotina_alimentar = rotinaAlimentar,
                perfil_treino = recomendacaoFinal,
                dias_exercicio = diasExercicio,
                resultado_dias = diasNecessarios,
                calorias_meta = caloriasMeta
            )

            RetrofitClient.instance.savePlan(plano).enqueue(object : Callback<FitnessPlan> {
                override fun onResponse(call: Call<FitnessPlan>, response: Response<FitnessPlan>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@ResultadoActivity, "Plano salvo no Banco de Dados!", Toast.LENGTH_LONG).show()

                        val intentVoltar = Intent(this@ResultadoActivity, MainActivity::class.java)
                        intentVoltar.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intentVoltar)
                    } else {
                        Toast.makeText(this@ResultadoActivity, "Erro no servidor: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<FitnessPlan>, t: Throwable) {
                    Toast.makeText(this@ResultadoActivity, "Erro crítico de rede. Verifique o terminal Python.", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}