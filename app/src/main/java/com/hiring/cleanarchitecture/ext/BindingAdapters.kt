package com.hiring.cleanarchitecture.ext

import android.graphics.drawable.Drawable
import android.webkit.WebView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.request.transition.TransitionFactory
import javax.xml.transform.TransformerFactory

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.setImageUrl(imageUrl: String) {
        if (tag == imageUrl) {
            return
        }
        val options = DrawableTransitionOptions()
        options.transition { dataSource, isFirstResource ->
            if (dataSource == DataSource.REMOTE) {
                DrawableCrossFadeTransition(100, true)
            } else {
                null
            }
        }
        Glide.with(context)
            .load(imageUrl)
            .transition(options)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean = false

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    tag = imageUrl
                    return false
                }

            })
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("url")
    fun WebView.setWebUrl(url: String) {
        if (url.isNotEmpty() && originalUrl != url) {
            loadUrl(url)
        }
    }
}
