package com.example.sirene

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Settings(
    @PrimaryKey(autoGenerate = true) val id_search: Long?,
    var code_postal : String,
    var departement_code: String?,
    val naf_code : String?,
    var use: Boolean

) : Serializable {



}