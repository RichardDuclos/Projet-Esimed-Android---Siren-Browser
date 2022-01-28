package com.example.sirene.model

import androidx.room.*
import com.example.sirene.code_naf

@Dao
interface NAFDAO {


    @Query("select * from code_naf where description LIKE  '%' || :text || '%' OR codenafape LIKE  '%' || :text || '%' ORDER BY codenafape ")
    abstract fun getAllFilter(text: String) : List<code_naf>

    @Query("select * from code_naf where description LIKE  '%' || :text || '%' OR codenafape LIKE  '%' || :text || '%' ORDER BY codenafape LIMIT 1 OFFSET :position  ")
    abstract fun getWithFilterAndPosition(text: String, position : Int) : code_naf

    @Query("select * from code_naf where description = :description")
    abstract fun getOne(description : String) : List<code_naf>


    @Insert
    fun create(nafcode: code_naf): Long

    @Delete
    fun delete(nafcode: code_naf)

    @Update
    fun update(nafcode: code_naf)

}