package dev.all4.versionUp.ui

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dev.all4.versionUp.R
import dev.all4.versionUp.data.DataSource
import dev.all4.versionUp.data.model.Anything
import dev.all4.versionUp.data.model.Meal
import dev.all4.versionUp.databinding.FragmentDetailsBinding
import dev.all4.versionUp.domain.RepositoryImpl
import dev.all4.versionUp.ui.vmodel.MainViewModel
import dev.all4.versionUp.ui.vmodel.VMFactory
import dev.all4.versionUp.utils.extentions.setImage
import dev.all4.versionUp.utils.extentions.toast
import dev.all4.versionUp.vo.Resource

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val viewModel by viewModels<MainViewModel> { VMFactory(RepositoryImpl(DataSource())) }
    private lateinit var binding: FragmentDetailsBinding
    private var details: Any? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireArguments().let {
            details = it.getParcelable("details")!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)

        binding.description.movementMethod = ScrollingMovementMethod()
        view.toast("I am in Details fragment")

        when(details){
            is Anything -> {
                (details as Anything).let {
                    binding.thumbnail.setImage(it.thumbnail)
                    binding.name.text = it.name
                    binding.description.text = it.description
                }
            }
            is Meal -> {
                (details as Meal).let {
                    binding.thumbnail.setImage(it.thumbnail)
                    binding.name.text = it.name

                    viewModel.liveDataMeal(it.id).observe(viewLifecycleOwner, Observer { result ->
                        binding.description.text = when (result) {
                            is Resource.Loading -> "..."
                            is Resource.Success -> result.data?.description
                            is Resource.Failure -> "An error occurred when try to load the data. ${result.throwable.message}"
                        }
                    })
                }
            }
        }
    }
}