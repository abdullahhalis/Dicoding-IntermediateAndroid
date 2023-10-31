package com.dicoding.mystory.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: List<Story>)

    @Query("SELECT * from story")
    fun getAllStory(): PagingSource<Int, Story>

    @Query("DELETE from story")
    suspend fun deleteAllStory()
}