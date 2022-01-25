package com.example.sirene.model

import com.example.sirene.Company
import com.google.gson.annotations.SerializedName

class Results {
    @SerializedName("total_results")
    var total_results: Long? = null

    @SerializedName("total_pages")
    var total_pages: Long? = null

    @SerializedName("per_page")
    var per_page: Long? = null

    @SerializedName("page")
    var page: Long? = null

    @SerializedName("etablissement")
    var etablissement: List<Company>? = null
}