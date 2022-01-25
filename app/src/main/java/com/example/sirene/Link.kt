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
        ForeignKey(
            entity =Research::class,
            parentColumns = ["id_search"],
            childColumns = ["id_search"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Link(
    @PrimaryKey(autoGenerate = true) val id_link: Long?,
    val id_company : Long,
    val id_search: Long
)  {



}