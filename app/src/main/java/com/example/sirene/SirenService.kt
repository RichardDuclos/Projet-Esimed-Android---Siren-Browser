package com.example.sirene

import com.example.sirene.model.Results
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class SirenService {
    private val apiUrl = "https://entreprise.data.gouv.fr/api/"
    fun query(query: String, per_page: Int, zip_code: String, departement: String,/* libelle_activite_principale : String,*/ callback : Callback<Results>) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api: CompanyAPI = retrofit.create(CompanyAPI::class.java)

        val call: Call<Results> = api.search(query, per_page, zip_code, departement/*, libelle_activite_principale*/)
        call.enqueue(callback)
    }

}