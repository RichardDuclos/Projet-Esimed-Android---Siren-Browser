package com.example.sirene

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class code_naf(
    @PrimaryKey(autoGenerate = false) val codenafape: String,
    var description : String?,
    var section : Int?,
    var description_section : String?


)  : Serializable {
    override fun toString(): String {
        return this.description as String
    }
}