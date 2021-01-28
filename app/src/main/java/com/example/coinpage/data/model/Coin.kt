package com.example.coinpage.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Coin (
    @PrimaryKey
    val id:String,
    val symbol:String,
    val name:String,
    val image:String,
    @SerializedName("current_price")val currentPrice :Double,
    @SerializedName("price_change_percentage_24h") val todayPriceChangeP:Double
    )