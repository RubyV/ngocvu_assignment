package com.ngocvu.example.view.ui.issuedetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ngocvu.example.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_issue_details_sub.*

@AndroidEntryPoint
class IssueDetailsSubFragment : Fragment() {

    companion object {
        fun newInstance(
            issueBody: String,
            issueTitle: String,
            issueId: String,
            issueStatus: Boolean
        ): IssueDetailsSubFragment {
            val IssueDetailsSubFragment = IssueDetailsSubFragment()
            IssueDetailsSubFragment.issueBody = issueBody
            IssueDetailsSubFragment.issueTitle = issueTitle
            IssueDetailsSubFragment.issueId = issueId
            IssueDetailsSubFragment.issueStatus = issueStatus
            return IssueDetailsSubFragment
        }
    }

    private lateinit var viewModel: IssueDetailsSubViewModel
    private lateinit var issueBody: String
    private lateinit var issueTitle: String
    private lateinit var issueId: String
    private  var issueStatus: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_issue_details_sub, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(IssueDetailsSubViewModel::class.java)
        // TODO: Use the ViewModel
        setUpView()
    }


    private fun setUpView() {
        tv_issue_title.text = issueTitle
        tv_issue_body.text = issueBody
        if(issueStatus)
        {
            btn_close.visibility = View.GONE
        }
        else
        {
            btn_close.setOnClickListener {
                viewModel.closeIssue(issueId)
            }
        }


    }

}