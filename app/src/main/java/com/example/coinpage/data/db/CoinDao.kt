package com.example.coinpage.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coinpage.data.model.Coin

@Dao
interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(coins:List<Coin>)

    @Query("select * from coin")
    fun allCoins():PagingSource<Int,Coin>

    @Query("delete from coin")
    suspend fun clearCoins()
}