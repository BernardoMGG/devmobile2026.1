package com.example.metafit

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.create
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path

data class FitnessPlan(
    val id: Int? = null,
    val nome_completo: String,
    val altura: Double,
    val peso_atual: Double,
    val peso_desejado: Double,
    val rotina_alimentar: String,
    val perfil_treino: String,
    val dias_exercicio: Int,
    val resultado_dias: Int,
    val calorias_meta: Double
)

interface ApiService {
    @POST("planos")
    fun savePlan(@Body plan: FitnessPlan): Call<FitnessPlan>

    @GET("planos")
    fun getPlans(): Call<List<FitnessPlan>>

    @PUT("planos/{nome}")
    fun updatePlan(@Path("nome") nome: String, @Body plan: FitnessPlan): Call<FitnessPlan>

    @DELETE("planos/{nome}")
    fun deletePlan(@Path("nome") nome: String): Call<Void>
}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8000/"

    val instance: ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create<ApiService>()
}