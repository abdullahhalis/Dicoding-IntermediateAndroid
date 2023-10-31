package com.dicoding.mystory.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "story")
data class Story(
    @PrimaryKey
    val id: String,
    val photoUrl: String?,
    val name: String?,
    val description: String?
)
