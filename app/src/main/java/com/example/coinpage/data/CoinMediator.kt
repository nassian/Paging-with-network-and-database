package com.example.coinpage.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.coinpage.data.db.CoinDatabase
import com.example.coinpage.data.model.Coin
import com.example.coinpage.data.model.RemoteKeys
import com.example.coinpage.data.network.CoinService
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@ExperimentalPagingApi
class CoinMediator(
    private val coinService: CoinService,
    private val coinDatabase: CoinDatabase
) : RemoteMediator<Int, Coin>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Coin>): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToThePosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeysOdTheFirstPage(state)
                remoteKeys ?: throw InvalidObjectException("remote key should not be null")

                remoteKeys.previousKey
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.previousKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeysOfTheLastPage(state)
                if (remoteKeys?.nextKey == null) {
                    throw InvalidObjectException("remote key or nextKey should not be null for $loadType")
                }
                remoteKeys.nextKey
            }
        }

        try {

            val coins: List<Coin> = coinService.getCoins(page = page)
            val endOfPaging = coins.isEmpty()

            coinDatabase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    coinDatabase.coinDao().clearCoins()
                    coinDatabase.keysDao().clearKeys()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaging) null else page + 1
                val keys = coins.map {
                    RemoteKeys(it.id, prevKey, nextKey)
                }

                coinDatabase.coinDao().insertAll(coins)
                coinDatabase.keysDao().insertKeys(keys)

            }

            return MediatorResult.Success(endOfPaging)


        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        }


    }

    private suspend fun getRemoteKeysOfTheLastPage(state: PagingState<Int, Coin>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { coin ->
            coinDatabase.keysDao().keysById(coin.id)
        }
    }

    private suspend fun getRemoteKeysOdTheFirstPage(state: PagingState<Int, Coin>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { coin ->
            coinDatabase.keysDao().keysById(coin.id)
        }
    }

    private suspend fun getRemoteKeyClosestToThePosition(state: PagingState<Int, Coin>): RemoteKeys? {
        return state.anchorPosition?.let { anchor ->
            state.closestItemToPosition(anchor)?.let { coin ->
                coinDatabase.keysDao().keysById(coin.id)
            }

        }
    }

}