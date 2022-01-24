package com.example.sirene

import com.example.sirene.model.Results
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CompanyAPI {
    @GET("/sirene/v1/full_text/{Company}")
    fun search(@Path("Company") company: String) : Call<Results>
}