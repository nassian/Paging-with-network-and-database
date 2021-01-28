package com.example.coinpage.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeys(
    @PrimaryKey
    val coinId:String,
    val previousKey:Int?,
    val nextKey:Int?
)