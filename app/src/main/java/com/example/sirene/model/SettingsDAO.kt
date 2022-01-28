package com.example.sirene.model

import androidx.room.*
import com.example.sirene.Company
import com.example.sirene.Link
import com.example.sirene.Save
import com.example.sirene.Settings

@Dao
interface SettingsDAO {


    @Query("select * from settings")
    abstract fun getAll() : List<Settings>

    @Insert
    fun create(save: Settings): Long

    @Delete
    fun delete(save: Settings)

    @Update
    fun update(save: Settings)

}