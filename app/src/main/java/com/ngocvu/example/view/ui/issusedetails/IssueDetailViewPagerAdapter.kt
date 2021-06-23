package com.ngocvu.example.view.ui.issusedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.IssuesListQuery
import com.ngocvu.example.view.ui.issusellist.IssuseListFragment


class IssueDetailViewPagerAdapter(f: Fragment,
                                  private val tabCount: Int,
                                  private val commentArr: ArrayList<IssuesListQuery.Comments>,
                                  private val issueDetails: String,
                                  private val issueId: String,
                                  private val issueTitle: String
): FragmentStateAdapter(f) {

    override fun getItemCount(): Int {
        return tabCount
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> IssueDetailsSubFragment.newInstance(issueDetails,issueTitle, issueId)
            else -> IssueCommentFragment.newInstance(commentArr)
        }
    }
}