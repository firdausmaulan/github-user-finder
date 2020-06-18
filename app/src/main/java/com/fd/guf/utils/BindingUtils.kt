package com.fd.guf.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fd.guf.R

object BindingUtils {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(imageView: ImageView?, url: String?) {
        if (imageView == null || url == null) return
        val requestOptions = RequestOptions().centerCrop()
        Glide.with(imageView)
            .load(url)
            .placeholder(R.drawable.ic_github)
            .apply(requestOptions)
            .into(imageView)
    }
}