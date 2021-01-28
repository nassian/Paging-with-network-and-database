package com.example.coinpage.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.coinpage.data.db.CoinDatabase
import com.example.coinpage.data.model.Coin
import com.example.coinpage.data.network.CoinService
import kotlinx.coroutines.flow.Flow

class CoinRepository @ExperimentalPagingApi constructor(
    private val coinDatabase: CoinDatabase,
    private val coinMediator: CoinMediator
) {

    @ExperimentalPagingApi
    fun getCoinsStream():Flow<PagingData<Coin>>{

        val pagingDataFactory={coinDatabase.coinDao().allCoins()}


        return Pager(PagingConfig(pageSize = 10,enablePlaceholders = false),
        pagingSourceFactory = pagingDataFactory,remoteMediator = coinMediator)
            .flow

    }

}