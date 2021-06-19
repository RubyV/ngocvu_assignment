package com.ngocvu.example.view.ui.issusellist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.ngocvu.example.R
import com.ngocvu.example.data.vo.Issues
import com.ngocvu.example.data.vo.Repository
import com.ngocvu.example.view.ui.home.RepositoryAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar_full_button_and_text.*
import timber.log.Timber

class IssuseListFragment : Fragment() {

    companion object {
        fun newInstance() = IssuseListFragment()
    }

    private lateinit var viewModel: IssuseListViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_issuse, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(IssuseListViewModel::class.java)
        navController = Navigation.findNavController(view)
        setupToolbar()
        setUpRecyclerView()
    }

    private fun setupToolbar() {
        tv_toolbar_title.text = resources.getString(R.string.issues_header_title)
        iv_toolbar_back_arrow.setOnClickListener {
            navController.navigateUp()
        }
    }
    private fun setUpRecyclerView() {
        var list = listOf(
            Issues(
                1,
                "Issues 111",
                12,
                "This issues is ....."

            ),
            Issues(
                2,
                "Issues 112",
                13,
                "This issues is ....."
            ),
        )
        val adapter = IssuseAdapter(requireContext(), list)
        rv_repository.setHasFixedSize(true)
        rv_repository.adapter = adapter
        adapter.observeEvent.subscribe({
            findNavController().navigate(
                R.id.action_issuseFragment_to_issuseDetailsFragment )
        } , { e ->
            Timber.e(e)
        })

    }
}