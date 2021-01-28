package com.example.coinpage.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coinpage.R

class CoinLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<CoinLoadStateAdapter.LoadStateVH>() {

    class LoadStateVH(itemView: View, retry: () -> Unit) : RecyclerView.ViewHolder(itemView) {

        private val progressBar = itemView.findViewById<ProgressBar>(R.id.load_state_progressBar)
        private val errorText = itemView.findViewById<TextView>(R.id.load_state_error_message)
        private val retryImage = itemView.findViewById<ImageView>(R.id.load_state_retry)

        init {
            retryImage.setOnClickListener { retry.invoke() }
        }


        fun bind(loadState: LoadState) {

            if (loadState is LoadState.Error) {
                errorText.text = loadState.error.localizedMessage
            }

            progressBar.isVisible = loadState is LoadState.Loading
            errorText.isVisible = loadState is LoadState.Error
            retryImage.isVisible = loadState is LoadState.Error

        }
    }

    override fun onBindViewHolder(holder: LoadStateVH, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.load_state_view_holder, parent, false)
        return LoadStateVH(view, retry)
    }
}