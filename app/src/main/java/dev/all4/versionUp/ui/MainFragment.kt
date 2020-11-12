package dev.all4.versionUp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dev.all4.versionUp.R
import dev.all4.versionUp.data.DataSource
import dev.all4.versionUp.domain.RepositoryImpl
import dev.all4.versionUp.ui.vmodel.MainViewModel
import dev.all4.versionUp.ui.vmodel.VMFactory
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
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

        btn_about.setOnClickListener {
            findNavController().navigate(R.id.aboutFragment)
        }
    }
}