package com.example.metafit // <-- Lembre de conferir se é o seu pacote!

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoricoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historico)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewPlanos)
        val btnVoltar = findViewById<Button>(R.id.btnVoltar)

        recyclerView.layoutManager = LinearLayoutManager(this)

        btnVoltar.setOnClickListener {
            finish()
        }

        RetrofitClient.instance.getPlans().enqueue(object : Callback<List<FitnessPlan>> {
            override fun onResponse(call: Call<List<FitnessPlan>>, response: Response<List<FitnessPlan>>) {
                if (response.isSuccessful && response.body() != null) {

                    val listaPlanos = response.body()!!.toMutableList()

                    val adapter = PlanoAdapter(listaPlanos) { plano, position ->
                        deletarPlano(plano, position, listaPlanos, recyclerView)
                    }

                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this@HistoricoActivity, "Erro ao carregar histórico.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<FitnessPlan>>, t: Throwable) {
                Toast.makeText(this@HistoricoActivity, "Falha na conexão com o banco.", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun deletarPlano(plano: FitnessPlan, position: Int, lista: MutableList<FitnessPlan>, recycler: RecyclerView) {

        RetrofitClient.instance.deletePlan(plano.nome_completo).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    lista.removeAt(position)
                    recycler.adapter?.notifyItemRemoved(position)
                    recycler.adapter?.notifyItemRangeChanged(position, lista.size)

                    Toast.makeText(this@HistoricoActivity, "Plano deletado com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@HistoricoActivity, "Erro ao deletar do banco.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@HistoricoActivity, "Falha na rede ao tentar deletar.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}