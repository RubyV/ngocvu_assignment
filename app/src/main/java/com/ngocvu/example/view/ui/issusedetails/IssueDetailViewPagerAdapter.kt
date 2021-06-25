package com.ngocvu.example.view.ui.issusedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.IssuesListQuery
import com.example.fragment.IssuesFragment



class IssueDetailViewPagerAdapter(f: Fragment,
                                  private val tabCount: Int,
                                  private val commentArr: ArrayList<IssuesFragment.Node>,
                                  private val issueDetails: String,
                                  private val issueId: String,
                                  private val issueTitle: String,
                                  private val issueStatus: Boolean
): FragmentStateAdapter(f) {

    override fun getItemCount(): Int {
        return tabCount
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> IssueDetailsSubFragment.newInstance(issueDetails,issueTitle, issueId, issueStatus)
            else -> IssueCommentFragment.newInstance(commentArr,issueId)
        }
    }
}