package com.example.capstone

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import java.lang.Exception

@BindingAdapter("gifLoad")
fun loadImage(imageView: ImageView, path: String){
    Picasso
        .get()
        .load(path)
        //.placeholder(R.drawable.ic_launcher_foreground)
        .error(R.drawable.ic_launcher_foreground)
        .into(imageView, object: com.squareup.picasso.Callback {
            override fun onSuccess() {
                TODO("Not yet implemented")
            }

            override fun onError(e: Exception?) {
                Log.e("loadimage", "Error: " + e?.message)
            }

        })
}