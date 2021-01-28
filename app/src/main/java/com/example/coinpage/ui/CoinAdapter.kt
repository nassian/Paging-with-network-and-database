package com.example.coinpage.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.coinpage.R
import com.example.coinpage.colorValue
import com.example.coinpage.data.model.Coin
import com.example.coinpage.fromUrl

class CoinAdapter:PagingDataAdapter<Coin, CoinAdapter.CoinVm> (CoinDiff) {

    class CoinVm(itemView:View):RecyclerView.ViewHolder(itemView) {

        private val nameText=itemView.findViewById<TextView>(R.id.name_textView)
        private val changeText=itemView.findViewById<TextView>(R.id.change_textView)
        private val priceText=itemView.findViewById<TextView>(R.id.price_textView)
        private val image=itemView.findViewById<ImageView>(R.id.coin_imageView)

        fun bind(item:Coin?){
            nameText.text=item?.name
            priceText.text=String.format("%.2f$",item?.currentPrice)
            changeText.apply {
                text= String.format("%.2f%%",item?.todayPriceChangeP)
                colorValue(item?.todayPriceChangeP)
            }
            image.fromUrl(item?.image)
        }
    }

    override fun onBindViewHolder(holder: CoinVm, position: Int) {
        val item=getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinVm {
        val view=LayoutInflater.from(parent.context)
            .inflate(R.layout.coin_view_holder,parent,false)
        return CoinVm(view)
    }

    companion object{
        object CoinDiff:DiffUtil.ItemCallback<Coin>(){
            override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
                return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
                return oldItem==newItem
            }

        }
    }


}