package dev.all4.versionUp.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Livio Lopez on 11/12/20.
 */
abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T, position: Int)
}