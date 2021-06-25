package com.ngocvu.example.view.ui.issuedetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.example.fragment.IssuesFragment
import com.ngocvu.example.R

import kotlinx.android.synthetic.main.item_comment.view.*


class IssueCommentAdapter
    : ListAdapter<IssuesFragment.Node, IssueCommentViewHolder>(IssuesDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueCommentViewHolder {
        val binding: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return IssueCommentViewHolder(binding)

    }

    override fun onBindViewHolder(holder: IssueCommentViewHolder, position: Int) {
        holder.binding.tv_user_author_name.text = getItem(position).author?.login
        holder.binding.tv_comment_body.text = getItem(position).body


    }

}

class IssueCommentViewHolder(val binding: View) : RecyclerView.ViewHolder(binding.rootView)

class IssuesDiffUtil : DiffUtil.ItemCallback<IssuesFragment.Node>() {
    override fun areItemsTheSame(
        oldItem: IssuesFragment.Node,
        newItem: IssuesFragment.Node
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: IssuesFragment.Node,
        newItem: IssuesFragment.Node
    ): Boolean {
        return oldItem == newItem
    }


}
