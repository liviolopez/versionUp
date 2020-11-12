package dev.all4.versionUp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.all4.versionUp.R
import dev.all4.versionUp.base.BaseViewHolder
import dev.all4.versionUp.data.model.MealCategory
import dev.all4.versionUp.utils.extentions.setImage
import kotlinx.android.synthetic.main.row_anything.view.*

/**
 * Created by Livio Lopez on 11/12/20.
 */
class MealAdapter(
        private val context: Context,
        private val mealCategoryList: List<MealCategory>,
        private val categoryClickListener: OnMealClickListener
    ) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnMealClickListener {
        fun onMealCategoryClick(mealCategory: MealCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MealCategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.row_anything, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is MealCategoryViewHolder -> holder.bind(mealCategoryList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return mealCategoryList.size
    }

    // inner class to be destroy after destroy MainAdapter
    inner class MealCategoryViewHolder(itemView: View) : BaseViewHolder<MealCategory>(itemView){
        override fun bind(item: MealCategory, position: Int) {
            itemView.thumbnail.setImage(item.thumbnail)
            itemView.name.text = item.name
            itemView.description.text = item.description

            itemView.setOnClickListener { categoryClickListener.onMealCategoryClick(item) }
        }
    }
}