package com.example.sirene.model

import androidx.room.*
import com.example.sirene.Link
import com.example.sirene.Save

@Dao
interface SaveDAO {

    @Query("select * from save where id_company = :id_company")
    abstract fun is_saved(id_company: Long) : Save?

    @Insert
    fun create(save: Save): Long

    @Delete
    fun delete(save: Save)

    @Update
    fun update(save: Save)

}