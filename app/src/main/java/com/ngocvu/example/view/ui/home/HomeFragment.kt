package com.ngocvu.example.view.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.ngocvu.example.R
import com.ngocvu.example.base.BaseFragment
import com.ngocvu.example.data.vo.Repository
import com.ngocvu.example.utils.BundleKeys
import com.ngocvu.example.view.ui.login.LogInViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_log_in.*
import kotlinx.android.synthetic.main.toolbar_full_button_and_text.*
import timber.log.Timber

class HomeFragment : BaseFragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        navController = Navigation.findNavController(view)
        setupToolbar()
        setUpRecyclerView()
    }

    private fun setupToolbar() {
        tv_toolbar_title.text = resources.getString(R.string.home_header_title)
        iv_toolbar_back_arrow.setOnClickListener {
            navController.navigateUp()
        }
    }
    private fun setUpRecyclerView() {
        var list = listOf(
                Repository(
                    id = 1,
                    repositoryName = "Repository 1",
                    desc ="repository desc"
                ),
                Repository(
                    id = 2,
                    repositoryName = "Repository 2",
                    desc ="repository descdesc"
                ),
            )
        val adapter = RepositoryAdapter(requireContext(), list)
        rv_repository.setHasFixedSize(true)
        rv_repository.adapter = adapter
        adapter.observeEvent.subscribe({
            findNavController().navigate(
                R.id.action_homeFragment_to_issuseFragment )
        } , { e ->
            Timber.e(e)
        })

    }


}