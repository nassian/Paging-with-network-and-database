package com.example.coinpage.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.coinpage.R
import com.example.coinpage.data.CoinRepository
import com.example.coinpage.data.db.CoinDatabase
import com.example.coinpage.data.network.CoinService
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

@ExperimentalPagingApi
class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: CoinAdapter
    private lateinit var refreshLayout: SwipeRefreshLayout

    private val mainVmFactory by inject<MainVmFactory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, mainVmFactory)
            .get(MainViewModel::class.java)

        setupRecyclerView()
        setupRefreshLayout()

    }

    private fun setupRefreshLayout() {
        refreshLayout = findViewById(R.id.refresh_layout)
        refreshLayout.setOnRefreshListener {
            adapter.refresh()
        }

        lifecycleScope.launchWhenStarted {
            adapter.loadStateFlow.collectLatest {
                refreshLayout.isRefreshing = it.refresh is LoadState.Loading
            }
        }

    }

    private fun setupRecyclerView() {

        adapter = CoinAdapter()


        findViewById<RecyclerView>(R.id.coin_recyclerView).apply {

            layoutManager = LinearLayoutManager(this@MainActivity)
            val divider = DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL)
            addItemDecoration(divider)

            adapter = this@MainActivity.adapter
                .withLoadStateHeaderAndFooter(CoinLoadStateAdapter { this@MainActivity.adapter.refresh() },
                    CoinLoadStateAdapter { this@MainActivity.adapter.refresh() })

        }

        lifecycleScope.launch {
            viewModel.getCoins().collectLatest {
                adapter.submitData(it)
            }
        }

    }
}