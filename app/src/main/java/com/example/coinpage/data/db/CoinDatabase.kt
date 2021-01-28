package com.example.coinpage.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.coinpage.data.model.Coin
import com.example.coinpage.data.model.RemoteKeys

@Database(entities = [Coin::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class CoinDatabase : RoomDatabase() {


    abstract fun coinDao(): CoinDao
    abstract fun keysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: CoinDatabase? = null

        fun getInstance(context: Context): CoinDatabase = synchronized(this) {
            INSTANCE = INSTANCE ?: Room
                .databaseBuilder(context, CoinDatabase::class.java, "Coin.db")
                .build()
            return@synchronized INSTANCE!!
        }

    }

}