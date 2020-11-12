package dev.all4.versionUp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dev.all4.versionUp.R
import dev.all4.versionUp.data.DataSource
import dev.all4.versionUp.data.model.Anything
import dev.all4.versionUp.domain.RepositoryImpl
import dev.all4.versionUp.ui.adapters.MainAdapter
import dev.all4.versionUp.ui.vmodel.MainViewModel
import dev.all4.versionUp.ui.vmodel.VMFactory
import dev.all4.versionUp.utils.extentions.setGone
import dev.all4.versionUp.utils.extentions.setVisible
import dev.all4.versionUp.utils.extentions.toast
import dev.all4.versionUp.vo.Resource
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), MainAdapter.OnAnythingClickListener {
    private val viewModel by viewModels<MainViewModel> { VMFactory(RepositoryImpl(DataSource())) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        btn_about.setOnClickListener { findNavController().navigate(R.id.aboutFragment) }

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
                    view.toast("An error occurred when try to load the data. ${result.throwable.message}")
                }
            }
        })
    }

    private fun setupRecyclerView(){
        rv_list_anything.layoutManager = LinearLayoutManager(context)
        //rv_list_anything.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    override fun onAnythingClick(anything: Anything) {
        val bundle = Bundle()
        bundle.putParcelable("anything", anything)
        findNavController().navigate(R.id.detailsFragment, bundle)
    }
}