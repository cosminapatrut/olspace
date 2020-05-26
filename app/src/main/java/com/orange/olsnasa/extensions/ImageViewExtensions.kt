package com.orange.olsnasa.extensions

import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.GrayscaleTransformation
import timber.log.Timber

fun ImageView.loadCropped(url: String, failCallback: (() -> Unit?)? = null) {
    Glide.with(context)
        .load(url)
        .listener(object : RequestListener<Drawable> {
            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean = false

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                failCallback?.invoke()
                return false
            }
        })
        .apply(RequestOptions.bitmapTransform(PositionedCropTransformation(0f, 0.5f)))
        .into(this)
}

fun ImageView.load(
    url: String,
    completionCallback: ((resource: Drawable?) -> Unit)? = null,
    failCallback: (() -> Unit?)? = null
) {
    Glide.with(context)
        .load(url)
        .listener(object : RequestListener<Drawable> {
            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                completionCallback?.invoke(resource)
                return false
            }

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                failCallback?.invoke()
                return false
            }
        })
        .into(this)
}

fun ImageView.load(@DrawableRes resourceId: Int) {
    Glide.with(context)
        .load(resourceId)
        .apply(RequestOptions.noTransformation())
        .into(this)
}

fun ImageView.loadCropped(@DrawableRes resourceId: Int, failCallback: (() -> Unit?)? = null) {
    Glide.with(context)
        .load(resourceId)
        .listener(object : RequestListener<Drawable> {
            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean = false

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                failCallback?.invoke()
                return false
            }
        })
        .apply(RequestOptions.bitmapTransform(PositionedCropTransformation(0f, 0.5f)))
        .into(this)
}

fun ImageView.loadWithBlur(url: String, failCallback: (() -> Unit?)? = null) {
    val multiTransformation = MultiTransformation(
        BlurTransformation(50),
        PositionedCropTransformation(0f, 0.5f)
    )
    Glide.with(context)
        .load(url)
        .listener(object : RequestListener<Drawable> {
            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean = false

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                failCallback?.invoke()
                return false
            }
        })
        .apply(RequestOptions.bitmapTransform(multiTransformation))
        .into(this)
}

fun ImageView.loadWithCircle(url: String, failCallback: (() -> Unit?)? = null) {
    val multiTransformation = MultiTransformation(
        CircleCrop(),
        GrayscaleTransformation()
    )
    Glide.with(context)
        .load(url)
        .listener(object : RequestListener<Drawable> {
            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean = false

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                failCallback?.invoke()
                return false
            }
        })
        .apply(RequestOptions.bitmapTransform(multiTransformation))
        .into(this)
}
