package com.project.libum.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity)

    @Query("SELECT * FROM book WHERE id = :id")
    suspend fun getBook(id: Int): BookEntity?

    @Query("UPDATE book SET readPercent = :percent WHERE id = :id")
    suspend fun updateReadPercent(id: Int, percent: Int): Int

    @Query("UPDATE book SET lastReadPage = :page WHERE id = :id")
    suspend fun updateLastReadPage(id: Int, page: Int): Int
}
