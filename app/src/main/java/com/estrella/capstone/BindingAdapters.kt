package com.estrella.capstone

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.ImageViewTarget

@BindingAdapter("gifLoad")
fun loadImage(imageView: ImageView, path: String) {
    Glide.with(imageView).asGif()
        .load(path)
        .placeholder(R.drawable.ic_load_sign)
        .error(R.drawable.ic_error_sign)
        .into(object : ImageViewTarget<GifDrawable>(imageView) {
            override fun setResource(resource: GifDrawable?) {
                imageView.setImageDrawable(resource)
            }
        })
}