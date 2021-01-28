package com.example.coinpage.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coinpage.data.model.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    suspend fun insertKeys(keys:List<RemoteKeys>)

    @Query("select * from RemoteKeys where coinId like :id")
    suspend fun keysById(id:String):RemoteKeys

    @Query("delete from RemoteKeys")
    suspend fun clearKeys()
}