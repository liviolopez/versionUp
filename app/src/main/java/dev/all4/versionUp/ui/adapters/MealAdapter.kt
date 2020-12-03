package dev.all4.versionUp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.all4.versionUp.R
import dev.all4.versionUp.base.BaseViewHolder
import dev.all4.versionUp.data.model.Meal
import dev.all4.versionUp.databinding.RowGridBinding
import dev.all4.versionUp.utils.extentions.setImage

/**
 * Created by Livio Lopez on 11/12/20.
 */
class MealAdapter(
        private val context: Context,
        private val mealList: List<Meal>,
        private val mealClickListener: OnMealClickListener
    ) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnMealClickListener {
        fun onMealClick(meal: Meal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MealViewHolder(LayoutInflater.from(context).inflate(R.layout.row_grid, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is MealViewHolder -> holder.bind(mealList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    // inner class to be destroy after destroy MainAdapter
    inner class MealViewHolder(itemView: View) : BaseViewHolder<Meal>(itemView){
        override fun bind(item: Meal, position: Int) {
            val binding = RowGridBinding.bind(itemView)

            binding.thumbnail.setImage(item.thumbnail)
            binding.name.text = item.name

            itemView.setOnClickListener { mealClickListener.onMealClick(item) }
        }
    }
}