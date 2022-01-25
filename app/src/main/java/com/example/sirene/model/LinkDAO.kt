package com.example.sirene.model

import androidx.room.*
import com.example.sirene.Link

@Dao
interface LinkDAO {

    @Query("select * from Link")
    abstract fun getAll() : List<Link>




    @Insert
    fun create(search: Link): Long

    @Delete
    fun delete(search: Link)

    @Update
    fun update(search: Link)

}