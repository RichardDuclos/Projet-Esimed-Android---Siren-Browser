package com.example.sirene

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Research(
    @PrimaryKey(autoGenerate = true) val id_search: Long?,
    val text : String,
    val zip_code: String?,
    val departement_code: String?,
    val archive : Boolean,
    val date: Long?,
    val nb_result : Int?

) : Serializable {



}