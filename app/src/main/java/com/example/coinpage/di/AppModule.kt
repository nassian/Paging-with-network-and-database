package com.example.coinpage.di

import androidx.paging.ExperimentalPagingApi
import com.example.coinpage.data.CoinMediator
import com.example.coinpage.data.CoinRepository
import com.example.coinpage.data.db.CoinDatabase
import com.example.coinpage.data.network.CoinService
import com.example.coinpage.ui.MainVmFactory
import org.koin.dsl.module

@ExperimentalPagingApi
val appModule= module {

    single { CoinDatabase.getInstance(get())}

    single { CoinService.create() }

    factory { CoinMediator(get(),get()) }

    factory { CoinRepository(get(),get()) }

    factory { MainVmFactory(get()) }

}