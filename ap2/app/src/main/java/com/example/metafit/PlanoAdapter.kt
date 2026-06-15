package com.example.metafit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlanoAdapter(private val planos: List<FitnessPlan>) : RecyclerView.Adapter<PlanoAdapter.PlanoViewHolder>() {

    class PlanoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNome: TextView = view.findViewById(R.id.tvItemNome)
        val tvResumo: TextView = view.findViewById(R.id.tvItemResumo)
        val tvDieta: TextView = view.findViewById(R.id.tvItemDieta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plano, parent, false)
        return PlanoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlanoViewHolder, position: Int) {
        val plano = planos[position]

        holder.tvNome.text = plano.nome_completo

        val dif = plano.peso_desejado - plano.peso_atual
        val acao = if (dif < 0) "Perder" else "Ganhar"
        holder.tvResumo.text = "Meta: $acao ${Math.abs(dif)} kg em ${plano.resultado_dias} dias"

        holder.tvDieta.text = "Dieta: ${plano.rotina_alimentar}"
    }

    override fun getItemCount(): Int = planos.size
}