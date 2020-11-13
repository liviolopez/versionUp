package dev.all4.versionUp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dev.all4.versionUp.R
import dev.all4.versionUp.data.DataSource
import dev.all4.versionUp.data.model.Anything
import dev.all4.versionUp.data.model.Meal
import dev.all4.versionUp.data.model.MealCategory
import dev.all4.versionUp.domain.RepositoryImpl
import dev.all4.versionUp.ui.adapters.MainAdapter
import dev.all4.versionUp.ui.adapters.MealAdapter
import dev.all4.versionUp.ui.adapters.MealCategoryAdapter
import dev.all4.versionUp.ui.vmodel.MainViewModel
import dev.all4.versionUp.ui.vmodel.VMFactory
import dev.all4.versionUp.utils.extentions.setGone
import dev.all4.versionUp.utils.extentions.setVisible
import dev.all4.versionUp.utils.extentions.toast
import dev.all4.versionUp.vo.Resource
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), MainAdapter.OnAnythingClickListener, MealCategoryAdapter.OnMealCategoryClickListener, MealAdapter.OnMealClickListener {
    private val viewModel by viewModels<MainViewModel> { VMFactory(RepositoryImpl(DataSource())) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDefaultView()

        setupAnythingRecyclerView()
        setupAnythingObservers()

        setupMealCategoriesRecyclerView()
        setupMealCategoriesObservers()

        setupMealsRecyclerView()
        setupSearchMeals()
        setupMealsObservers()
    }

    // Basic
    private fun setupDefaultView(){
        btn_about.setOnClickListener { findNavController().navigate(R.id.action_mainFragment_to_aboutFragment) }
    }

    // Anything
    private fun setupAnythingRecyclerView(){
        rv_list_anything.layoutManager = LinearLayoutManager(context)
        //rv_list_anything.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    private fun setupAnythingObservers(){
        viewModel.liveDataAnything.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.Loading -> {
                    progress_bar_container.setVisible()
                    error_container.setGone()
                }
                is Resource.Success -> {
                    progress_bar_container.setGone()
                    error_container.setGone()
                    rv_list_anything.adapter = MainAdapter(requireContext(), result.data!!, this)
                }
                is Resource.Failure -> {
                    progress_bar_container.setGone()
                    error_container.setVisible()
                    view?.toast("An error occurred when try to load the data. ${result.throwable.message}")
                }
            }
        })
    }

    override fun onAnythingClick(anything: Anything) {
        val bundle = Bundle()
        bundle.putParcelable("details", anything)
        findNavController().navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
    }


    // Meal Category
    private fun setupMealCategoriesRecyclerView(){
        rv_list_meal_categories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_list_meal_categories.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
    }

    private fun setupMealCategoriesObservers(){
        viewModel.liveDataMealCategory.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.Loading -> {
                    progress_bar_container.setVisible()
                    error_container.setGone()
                }
                is Resource.Success -> {
                    progress_bar_container.setGone()
                    error_container.setGone()
                    rv_list_meal_categories.adapter = MealCategoryAdapter(requireContext(), result.data!!, this)
                }
                is Resource.Failure -> {
                    progress_bar_container.setGone()
                    error_container.setVisible()
                    view?.toast("An error occurred when try to load the data. ${result.throwable.message}")
                }
            }
        })
    }

    override fun onMealCategoryClick(mealCategory: MealCategory) {
        val bundle = Bundle()
        bundle.putParcelable("mealCategory", mealCategory)
        findNavController().navigate(R.id.action_mainFragment_to_mealListFragment, bundle)
    }

    // Meal and Search
    private fun setupMealsRecyclerView(){
        rv_all_meals.layoutManager = GridLayoutManager(context, 2)
    }

    private fun setupMealsObservers(){
        viewModel.searchMeal("")
        viewModel.liveDataMealsByName.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.Loading -> {
                    progress_bar_container.setVisible()
                    error_container.setGone()
                }
                is Resource.Success -> {
                    progress_bar_container.setGone()
                    error_container.setGone()
                    rv_all_meals.adapter = MealAdapter(requireContext(), result.data!!, this)
                }
                is Resource.Failure -> {
                    progress_bar_container.setGone()
                    error_container.setVisible()
                    view?.toast("An error occurred when try to load the data. ${result.throwable.message}")
                }
            }
        })
    }

    private fun setupSearchMeals(){
        search_meal.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchMeal(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

    override fun onMealClick(meal: Meal) {
        val bundle = Bundle()
        bundle.putParcelable("details", meal)
        findNavController().navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
    }
}