package dev.all4.versionUp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dev.all4.versionUp.R
import dev.all4.versionUp.data.DataSource
import dev.all4.versionUp.data.model.Meal
import dev.all4.versionUp.data.model.MealCategory
import dev.all4.versionUp.domain.RepositoryImpl
import dev.all4.versionUp.ui.adapters.MealAdapter
import dev.all4.versionUp.ui.vmodel.MainViewModel
import dev.all4.versionUp.ui.vmodel.VMFactory
import dev.all4.versionUp.utils.extentions.setGone
import dev.all4.versionUp.utils.extentions.setImage
import dev.all4.versionUp.utils.extentions.setVisible
import dev.all4.versionUp.utils.extentions.toast
import dev.all4.versionUp.vo.Resource
import kotlinx.android.synthetic.main.fragment_meal_list.*

/**
 * Created by Livio Lopez on 11/12/20.
 */
class MealListFragment : Fragment(), MealAdapter.OnMealClickListener {
    private lateinit var mealCategory: MealCategory
    private val viewModel by viewModels<MainViewModel> { VMFactory(RepositoryImpl(DataSource())) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireArguments().let {
            mealCategory = it.getParcelable("mealCategory")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meal_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDefaultView()

        setupMealRecyclerView()
        setupMealObservers()
    }

    // Basic
    private fun setupDefaultView(){
        thumbnail.setImage(mealCategory.thumbnail)
        name.text = mealCategory.name
        description.text = mealCategory.description
    }

    // Meal
    private fun setupMealRecyclerView(){
        rv_list_meal.layoutManager = GridLayoutManager(context, 2)
    }

    private fun setupMealObservers(){
        viewModel.liveDataMealsByCategory(mealCategory.name).observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.Loading -> {
                    progress_bar_container.setVisible()
                    error_container.setGone()
                }
                is Resource.Success -> {
                    progress_bar_container.setGone()
                    error_container.setGone()
                    rv_list_meal.adapter = MealAdapter(requireContext(), result.data!!, this)
                }
                is Resource.Failure -> {
                    progress_bar_container.setGone()
                    error_container.setVisible()
                    view?.toast("An error occurred when try to load the data. ${result.throwable.message}")
                }
            }
        })
    }

    override fun onMealClick(meal: Meal) {
        val bundle = Bundle()
        bundle.putParcelable("details", meal)
        findNavController().navigate(R.id.detailsFragment, bundle)
    }
}