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
import com.example.IssuesListQuery
import com.example.fragment.IssuesFragment
import com.ngocvu.example.R
import com.ngocvu.example.databinding.FragmentHomeBinding
import com.ngocvu.example.utils.BundleKeys
import com.ngocvu.example.utils.DialogFragmentUtil
import com.ngocvu.example.view.state.ViewState
import com.ngocvu.example.view.ui.issusedetails.IssuseDetailsFragment
import com.ngocvu.example.view.ui.issusellist.IssuesAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.schedulers.Schedulers.start
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
   private var sendLst = ArrayList<IssuesFragment.Node>()
   private val issuesAdapter by lazy { IssuesAdapter() }



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

        binding.rvRepository.adapter = issuesAdapter
        setupToolbar()
        setUpRecyclerView()
        fab.setOnClickListener {
            DialogFragmentUtil.showSendEmailDialog(requireActivity())
        }
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
                is ViewState.Loading -> {
                    binding.rvRepository.visibility = View.GONE
                    binding.issuesFetchProgress.visibility = View.VISIBLE
                }
                is ViewState.Success -> {
                    var resData = response.value?.data?.repository?.issues!!
                    var resDataSize = response.value?.data?.repository?.issues?.totalCount
                    if (resDataSize == 0) {
                        issuesAdapter.submitList(emptyList())
                        binding.issuesFetchProgress.visibility = View.GONE
                        binding.issuesFetchProgress.visibility = View.GONE
                        binding.charactersEmptyText.visibility = View.VISIBLE
                    } else {
                        binding.rvRepository.visibility = View.VISIBLE
                        binding.charactersEmptyText.visibility = View.GONE
                    }
                    for (i in 0 until resDataSize!!) {
                        issuseList.add(resData?.nodes?.get(i)!!)
                    }
                    issuesAdapter.submitList(issuseList)
                    binding.issuesFetchProgress.visibility = View.GONE

                    issuesAdapter.observeEvent.subscribe({
                        for(comments in issuseList[it].fragments.issuesFragment.comments.nodes!!)
                        {
                            sendLst.add(comments!!)
                        }
                        setFragmentResult(
                            "requestKey", bundleOf(
                                "bundleKey" to sendLst,
                                BundleKeys.IssueDetails to issuseList[it].fragments.issuesFragment.author?.login,
                                BundleKeys.Issue to issuseList[it].fragments.issuesFragment.title,
                                BundleKeys.IssueId to issuseList[it].fragments.issuesFragment.id,
                                BundleKeys.IssueStatus to issuseList[it].fragments.issuesFragment.closed,
                            )
                        )
                        navController.navigate(
                            R.id.action_homeFragment_to_issuseDetailsFragment
                        )
                    }, { e ->
                        Timber.e(e)
                    })


                }
                is ViewState.Error -> {
                    issuesAdapter.submitList(emptyList())
                    binding.issuesFetchProgress.visibility = View.GONE
                    binding.rvRepository.visibility = View.GONE
                    binding.charactersEmptyText.visibility = View.VISIBLE
                }
            }
        }

    }

}