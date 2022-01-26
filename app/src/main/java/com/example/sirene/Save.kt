package com.example.sirene

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Company::class,
            parentColumns = ["id"],
            childColumns = ["id_company"],
            onDelete = ForeignKey.CASCADE
        ),

    ]
)
data class Save(
    @PrimaryKey(autoGenerate = true) val id_: Long?,
    val id_company : Long,
)  {



}