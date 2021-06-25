package com.ngocvu.example.view.ui.issusedetails

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent.getIntent
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.example.IssuesListQuery
import com.example.fragment.IssuesFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.tabs.TabLayoutMediator
import com.ngocvu.example.R
import com.google.firebase.messaging.FirebaseMessaging
import com.ngocvu.example.utils.BundleKeys
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_issuse_details.*
import kotlinx.android.synthetic.main.toolbar_full_button_and_text.*
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class IssuseDetailsFragment : Fragment() {


    private lateinit var viewModel: IssuseDetailsViewModel
    private lateinit var navController: NavController
    private var res = ArrayList<IssuesFragment.Node>()
    private var issuesName = ""
    private lateinit var issueBody: String
    private lateinit var issueId: String
    private var issueStatus: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_issuse_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(IssuseDetailsViewModel::class.java)
        navController = Navigation.findNavController(view)
        setFragmentResultListener("requestKey") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            var results = bundle.get("bundleKey") as ArrayList<IssuesFragment.Node>
            issuesName = bundle.get(BundleKeys.Issue) as String
            issueBody = bundle.get(BundleKeys.IssueDetails) as String
            issueId = bundle.get(BundleKeys.IssueId) as String
            issueStatus = bundle.get(BundleKeys.IssueStatus) as Boolean

            results.forEach {
                res.add(it)

            }
            setUpToolbar(issuesName)
            setupViewPager()
        }


    }

    private fun setUpToolbar(issuesNameHeader: String){
        tv_toolbar_title.text = issuesNameHeader
        iv_toolbar_back_arrow.setOnClickListener {
            navController.navigateUp()
        }
    }
    private fun setupViewPager(){
        val adapter = IssueDetailViewPagerAdapter(
            this,
            2,
            res,
            issueBody,
            issueId,
            issuesName,
            issueStatus
        )
        pager!!.adapter = adapter

        TabLayoutMediator(tab_details, pager) { tab, position ->
            when (position) {
                0 -> tab.text = "Details"
                1 -> tab.text = "Comment"
            }
        }.attach()
    }
}