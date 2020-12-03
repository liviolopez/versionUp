package dev.all4.versionUp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dev.all4.versionUp.R
import dev.all4.versionUp.data.DataSource
import dev.all4.versionUp.data.model.Meal
import dev.all4.versionUp.data.model.MealCategory
import dev.all4.versionUp.databinding.FragmentMealListBinding
import dev.all4.versionUp.domain.RepositoryImpl
import dev.all4.versionUp.ui.adapters.MealAdapter
import dev.all4.versionUp.ui.vmodel.MainViewModel
import dev.all4.versionUp.ui.vmodel.VMFactory
import dev.all4.versionUp.utils.extentions.setGone
import dev.all4.versionUp.utils.extentions.setImage
import dev.all4.versionUp.utils.extentions.setVisible
import dev.all4.versionUp.utils.extentions.toast
import dev.all4.versionUp.vo.Resource

/**
 * Created by Livio Lopez on 11/12/20.
 */
class MealListFragment : Fragment(R.layout.fragment_meal_list),
    MealAdapter.OnMealClickListener {
    private lateinit var mealCategory: MealCategory
    private val viewModel by viewModels<MainViewModel> { VMFactory(RepositoryImpl(DataSource())) }
    private lateinit var binding: FragmentMealListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireArguments().let {
            mealCategory = it.getParcelable("mealCategory")!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMealListBinding.bind(view)

        setupDefaultView()

        setupMealRecyclerView()
        setupMealObservers()
    }

    // Basic
    private fun setupDefaultView(){
        binding.thumbnail.setImage(mealCategory.thumbnail)
        binding.name.text = mealCategory.name
        binding.description.text = mealCategory.description
    }

    // Meal
    private fun setupMealRecyclerView(){
        binding.rvListMeal.layoutManager = GridLayoutManager(context, 2)
    }

    private fun setupMealObservers(){
        viewModel.liveDataMealsByCategory(mealCategory.name).observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.Loading -> {
                    binding.progressBarContainer.setVisible()
                    binding.errorContainer.setGone()
                }
                is Resource.Success -> {
                    binding.progressBarContainer.setGone()
                    binding.errorContainer.setGone()
                    binding.rvListMeal.adapter = MealAdapter(requireContext(), result.data!!, this)
                }
                is Resource.Failure -> {
                    binding.progressBarContainer.setGone()
                    binding.errorContainer.setVisible()
                    view?.toast("An error occurred when try to load the data. ${result.throwable.message}")
                }
            }
        })
    }

    override fun onMealClick(meal: Meal) {
        val bundle = Bundle()
        bundle.putParcelable("details", meal)
        findNavController().navigate(R.id.action_mealListFragment_to_detailsFragment, bundle)
    }
}