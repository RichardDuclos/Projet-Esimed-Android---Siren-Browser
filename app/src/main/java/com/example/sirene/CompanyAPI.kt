package com.example.sirene

import com.example.sirene.model.Results
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CompanyAPI {
    @GET("sirene/v1/full_text/{Company}")
    fun search(@Path("Company") company: String, @Query("per_page") per_page : Int, @Query("code_postal") zip_code: String, @Query("departement") departement: String/*, @Query("libelle_activite_principale") libelle_activite_principale : String*/) : Call<Results>
}