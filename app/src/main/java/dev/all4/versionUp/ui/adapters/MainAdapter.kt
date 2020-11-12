package dev.all4.versionUp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.all4.versionUp.R
import dev.all4.versionUp.base.BaseViewHolder
import dev.all4.versionUp.data.model.Anything
import dev.all4.versionUp.utils.extentions.setImage
import kotlinx.android.synthetic.main.row_vertical.view.*

/**
 * Created by Livio Lopez on 11/12/20.
 */
class MainAdapter(
        private val context: Context,
        private val anythingList: List<Anything>,
        private val anythingClickListener: OnAnythingClickListener
    ) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnAnythingClickListener {
        fun onAnythingClick(anything: Anything)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MainViewHolder(LayoutInflater.from(context).inflate(R.layout.row_vertical, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is MainViewHolder -> holder.bind(anythingList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return anythingList.size
    }

    // inner class to be destroy after destroy MainAdapter
    inner class MainViewHolder(itemView: View) : BaseViewHolder<Anything>(itemView){
        override fun bind(item: Anything, position: Int) {
            itemView.thumbnail.setImage(item.thumbnail)
            itemView.name.text = item.name
            itemView.description.text = item.description

            itemView.setOnClickListener { anythingClickListener.onAnythingClick(item) }
        }
    }
}