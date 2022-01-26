package com.example.sirene

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Company(
    @PrimaryKey(autoGenerate = false) val id: Long?,
    var siren: String,
    var siret: String,
    var numero_voie: String?,
    var indice_repetition: String?,
    var type_voie: String?,
    var libelle_voie: String?,
    var code_postal: String?,
    var cedex: String?,
    var libelle_region: String?,
    var arrondissement: String?,
    var departement: String?,
    var libelle_commune: String?,
    var is_siege: String?,
    var libelle_activite_principale: String?,
    var libelle_nature_juridique_entreprise: String?,
    var nom_raison_sociale: String?,
    var longitude: String?,
    var latitude: String?
)  : Serializable {
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
        return "Company(id=$id, siren='$siren', siret='$siret', nom='$nom_raison_sociale'"
    }

}