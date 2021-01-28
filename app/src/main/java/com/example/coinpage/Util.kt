package com.example.coinpage

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

fun TextView.colorValue(value: Double?) {

    value?.let { change ->
        val color = when {
            change > 0 -> { R.color.green }
            change < 0 -> { R.color.red }
            else -> { R.color.black }
        }
        this.setTextColor(ContextCompat.getColor(this.context, color))
    }
}

fun ImageView.fromUrl(url: String?) {
    url?.let {

        Glide.with(this)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_attach_money_24)
            .into(this)
    }

}