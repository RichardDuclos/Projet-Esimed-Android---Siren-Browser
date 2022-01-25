package com.example.sirene.model

import androidx.room.*
import com.example.sirene.Research

@Dao
interface ResearchDAO {

    @Query("select * from research ORDER BY date desc")
    abstract fun getAll() : List<Research>

    @Query("select * from research where id_search = :id_search")
    abstract fun getOne(id_search: Long) : Research?

    @Query("update research set archive = 1 where archive = 0 AND date + 86400 < CAST(strftime('%s', 'now') as LONG)")
    abstract fun updateArchive()

    @Query("select * from research where text = :text AND archive = 0 LIMIT 1")
    abstract fun getNonArchived(text: String) : List<Research>

    @Insert
    fun create(research: Research): Long

    @Delete
    fun delete(research: Research)

    @Update
    fun update(research: Research)

}