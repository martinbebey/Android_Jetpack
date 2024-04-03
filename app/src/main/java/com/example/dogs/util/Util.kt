package com.example.dogs.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dogs.R

val PERMISSION_SEND_SMS = 234
fun getProgessDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

/**
 * extends the functionality of the ImageView class and adds a new function
 *
 */
fun ImageView.loadImage(uri: String?, progessDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progessDrawable)
        .error(R.mipmap.ic_dog_icon)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

@BindingAdapter("android:imageUrl") // makes the function available to our layout/binding
fun loadImage(view: ImageView, url: String?){
    view.loadImage(url, getProgessDrawable(view.context))
}