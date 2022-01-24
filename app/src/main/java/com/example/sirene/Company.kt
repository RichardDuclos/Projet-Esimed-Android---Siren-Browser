package com.example.sirene

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Company(
    @PrimaryKey(autoGenerate = false) val id: Long?,
    var siren: String,
    var siret: String,
    var l1_declaree: String?,
    var l2_declaree: String?,
    var l3_declaree: String?,
    var l4_declaree: String?,
    var l5_declaree: String?,
    var l7_declaree: String?,

)  {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Company

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Company(id=$id, siren='$siren', siret='$siret', l1_declaree=$l1_declaree, l2_declaree=$l2_declaree, l3_declaree=$l3_declaree, l4_declaree=$l4_declaree, l5_declaree=$l5_declaree, l7_declaree=$l7_declaree)"
    }

}