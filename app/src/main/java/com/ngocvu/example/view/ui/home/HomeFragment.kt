package com.ngocvu.example.view.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.apollographql.apollo.cache.normalized.NormalizedCache
import com.example.IssuesListQuery
import com.ngocvu.example.R
import com.ngocvu.example.databinding.FragmentHomeBinding
import com.ngocvu.example.networking.GithubApi
import com.ngocvu.example.utils.BundleKeys
import com.ngocvu.example.view.state.ViewState
import com.ngocvu.example.view.ui.issusellist.IssuseAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar_full_button_and_text.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var navController: NavController
    private lateinit var binding: FragmentHomeBinding
    private var issuseList = ArrayList<IssuesListQuery.Node>()
    private var sendLst = ArrayList<IssuesListQuery.Comments>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
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

        viewModel.queryIssueList()
        viewModel.issuseList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Success -> {
                    var resData = response.value?.data?.repository?.issues!!
                    var resDataSize = response.value?.data?.repository?.issues?.nodes?.size!!
                    for (i in 0 until resDataSize) {
                        issuseList.add(resData?.nodes?.get(i)!!)
                    }
                    val adapter = IssuseAdapter(requireContext(), issuseList)
                    rv_repository.setHasFixedSize(false)
                    rv_repository.adapter = adapter

                    adapter.observeEvent.subscribe({
                        for(element in resData.nodes!!)
                        {
                            sendLst.add(element?.comments!!)

                        }
                        // send comment to issuse details

                        setFragmentResult(
                            "requestKey", bundleOf(
                                "bundleKey" to sendLst,
                                BundleKeys.Issue to "Issue 1112"
                            )
                        )
                        navController.navigate(
                            R.id.action_homeFragment_to_issuseDetailsFragment
                        )
                    }, { e ->
                        Timber.e(e)
                    })


                }
            }
        }

    }
}