package dev.all4.versionUp.utils.extentions

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.bumptech.glide.signature.ObjectKey
import dev.all4.versionUp.BuildConfig
import dev.all4.versionUp.R
import okhttp3.OkHttpClient
import java.io.InputStream

@GlideModule
class BaseGlideApp : AppGlideModule() {
  var factory: DrawableCrossFadeFactory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

  override fun applyOptions(context: Context, builder: GlideBuilder) {
    val decodeFormat = if(true){ //@ TODO Determine memory available to decide the right format
      DecodeFormat.PREFER_ARGB_8888 // Fast Devices
    } else {
      DecodeFormat.PREFER_RGB_565 // Slow Devices
    }

    builder.apply {
      //if(BuildConfig.DEBUG) setLogLevel(Log.DEBUG)

      val calculator = MemorySizeCalculator.Builder(context).build()
      setMemoryCache(LruResourceCache(calculator.memoryCacheSize.toLong()))
      setBitmapPool(LruBitmapPool(calculator.bitmapPoolSize.toLong()))

      RequestOptions()
        .format(decodeFormat)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .signature(ObjectKey(System.currentTimeMillis().toShort()))
    }
  }

  override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
    super.registerComponents(context, glide, registry)

    val okHttpClient = OkHttpClient()
    registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okHttpClient))
  }
}

fun ImageView.setImage(source: Any, errorSource: Drawable? = null){
  val imageView = this

  val initDrawable = context.getDrawable(R.drawable.broken_url)

  GlideApp.with(context)
    .load(source)
    .transition(withCrossFade(BaseGlideApp().factory))
    .error(errorSource ?: initDrawable)
    .centerCrop()
    .listener(object : RequestListener<Drawable> {
      override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
        imageView.setBackgroundResource(0)
        return false
      }

      override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
        imageView.setBackgroundResource(0)
        return false
      }
    })
    .into(imageView)
}
