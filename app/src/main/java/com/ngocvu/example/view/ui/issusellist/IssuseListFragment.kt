package com.ngocvu.example.view.ui.issusellist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.RepositoryListQuery
import com.ngocvu.example.R
import com.ngocvu.example.data.vo.Issues
import com.ngocvu.example.data.vo.Repository
import com.ngocvu.example.utils.BundleKeys
import com.ngocvu.example.view.state.ViewState
import com.ngocvu.example.view.ui.home.RepositoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar_full_button_and_text.*
import timber.log.Timber

@AndroidEntryPoint
class IssuseListFragment : Fragment() {


    private lateinit var viewModel: IssuesListViewModel
    private lateinit var navController: NavController
    private var issuesLst = ArrayList<RepositoryListQuery.Node1>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_issuse, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(IssuesListViewModel::class.java)
        navController = Navigation.findNavController(view)
//        setFragmentResultListener("requestKey") { requestKey, bundle ->
//            // We use a String here, but any type that can be put in a Bundle is supported
//            val result = bundle.get("bundleKey")
//            result to RepositoryListQuery.Node1
//
//            Log.d("git1", result.toString())
//
//            // Do something with the result
//
//        }

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
        viewModel.queryIssueList()
        viewModel.issuseList.observe(viewLifecycleOwner) { response ->
          Log.d("git", response.toString())
        }



//        var list = listOf(
//            Issues(
//                1,
//                "Issues 111",
//                12,
//                "This issues is ....."
//
//            ),
//            Issues(
//                2,
//                "Issues 112",
//                13,
//                "This issues is ....."
//            ),
//        )
//        val adapter = IssuseAdapter(requireContext(), list)
//        rv_repository.setHasFixedSize(true)
//        rv_repository.adapter = adapter
//        adapter.observeEvent.subscribe({
//            findNavController().navigate(
//                R.id.action_issuseFragment_to_issuseDetailsFragment )
//        } , { e ->
//            Timber.e(e)
//        })

    }
}