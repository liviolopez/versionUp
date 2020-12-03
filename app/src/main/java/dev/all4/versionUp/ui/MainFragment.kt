package dev.all4.versionUp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
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
import dev.all4.versionUp.databinding.FragmentMainBinding
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

class MainFragment : Fragment(R.layout.fragment_main),
    MainAdapter.OnAnythingClickListener,
    MealCategoryAdapter.OnMealCategoryClickListener,
    MealAdapter.OnMealClickListener {
    private val viewModel by viewModels<MainViewModel> { VMFactory(RepositoryImpl(DataSource())) }
    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

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
        binding.btnAbout.setOnClickListener { findNavController().navigate(R.id.action_mainFragment_to_aboutFragment) }
    }

    // Anything
    private fun setupAnythingRecyclerView(){
        binding.rvListAnything.layoutManager = LinearLayoutManager(context)
        //binding.rv_list_anything.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    private fun setupAnythingObservers(){
        viewModel.liveDataAnything.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.Loading -> {
                    binding.progressBarContainer.setVisible()
                    binding.errorContainer.setGone()
                }
                is Resource.Success -> {
                    binding.progressBarContainer.setGone()
                    binding.errorContainer.setGone()
                    binding.rvListAnything.adapter = MainAdapter(requireContext(), result.data!!, this)
                }
                is Resource.Failure -> {
                    binding.progressBarContainer.setGone()
                    binding.errorContainer.setVisible()
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
        binding.rvListMealCategories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvListMealCategories.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
    }

    private fun setupMealCategoriesObservers(){
        viewModel.liveDataMealCategory.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.Loading -> {
                    binding.progressBarContainer.setVisible()
                    binding.errorContainer.setGone()
                }
                is Resource.Success -> {
                    binding.progressBarContainer.setGone()
                    binding.errorContainer.setGone()
                    binding.rvListMealCategories.adapter = MealCategoryAdapter(requireContext(), result.data!!, this)
                }
                is Resource.Failure -> {
                    binding.progressBarContainer.setGone()
                    binding.errorContainer.setVisible()
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
        binding.rvAllMeals.layoutManager = GridLayoutManager(context, 2)
    }

    private fun setupMealsObservers(){
        viewModel.searchMeal("")
        viewModel.liveDataMealsByName.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.Loading -> {
                    binding.progressBarContainer.setVisible()
                    binding.errorContainer.setGone()
                }
                is Resource.Success -> {
                    binding.progressBarContainer.setGone()
                    binding.errorContainer.setGone()
                    binding.rvAllMeals.adapter = MealAdapter(requireContext(), result.data!!, this)
                }
                is Resource.Failure -> {
                    binding.progressBarContainer.setGone()
                    binding.errorContainer.setVisible()
                    view?.toast("An error occurred when try to load the data. ${result.throwable.message}")
                }
            }
        })
    }

    private fun setupSearchMeals(){
        binding.searchMeal.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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