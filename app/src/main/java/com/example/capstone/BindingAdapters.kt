package com.example.capstone

import android.util.Log
import android.widget.ImageView
import android.widget.VideoView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.ImageViewTarget
import com.squareup.picasso.Picasso
import java.lang.Exception

@BindingAdapter("gifLoad")
fun loadImage(imageView: ImageView, path: String){
    Glide.with(imageView).asGif()
        .load(path)
        .placeholder(R.drawable.ic_launcher_foreground)
        .error(R.drawable.ic_launcher_foreground)
        .into(object: ImageViewTarget<GifDrawable>(imageView) {
            override fun setResource(resource: GifDrawable?) {
                imageView.setImageDrawable(resource)
            }
        })
}