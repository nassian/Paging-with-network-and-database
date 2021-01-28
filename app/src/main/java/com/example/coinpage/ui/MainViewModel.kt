package com.example.coinpage.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.coinpage.data.CoinRepository
import com.example.coinpage.data.model.Coin
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class MainViewModel(private val coinRepository: CoinRepository) : ViewModel() {


    fun getCoins(): Flow<PagingData<Coin>> = coinRepository
        .getCoinsStream()
        .cachedIn(viewModelScope)


}

class MainVmFactory(private val coinRepository: CoinRepository) : ViewModelProvider.Factory {
    @ExperimentalPagingApi
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(coinRepository) as T
    }

}