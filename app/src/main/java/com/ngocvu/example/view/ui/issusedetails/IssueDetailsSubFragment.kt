package com.ngocvu.example.view.ui.issusedetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ngocvu.example.R
import com.ngocvu.example.view.ui.issusellist.IssuseListFragment

class IssueDetailsSubFragment : Fragment() {

    companion object {
        fun newInstance(
            walletTransactionViewPagerTab: Int
        ): IssueDetailsSubFragment {
            return IssueDetailsSubFragment()
        }
    }

    private lateinit var viewModel: IssueDetailsSubViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_issue_details_sub, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(IssueDetailsSubViewModel::class.java)
        // TODO: Use the ViewModel
    }

}