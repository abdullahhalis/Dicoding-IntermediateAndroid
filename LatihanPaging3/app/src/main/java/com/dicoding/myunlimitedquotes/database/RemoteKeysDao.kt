package com.dicoding.myunlimitedquotes.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeys>)

    @Query("SELECT * from remote_keys WHERE id = :id")
    suspend fun getRemoteKeyId(id: String): RemoteKeys?

    @Query("DELETE from remote_keys")
    suspend fun deleteRemoteKeys()
}