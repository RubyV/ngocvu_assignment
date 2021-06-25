package com.ngocvu.example.view.ui.issusellist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.IssuesListQuery
import com.ngocvu.example.R
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_issuse.view.*


class IssuesAdapter
    : ListAdapter<IssuesListQuery.Node, CharacterViewHolder>(IssuesDiffUtil()) {
    private val publishSubject = PublishSubject.create<Int>()
    val observeEvent: Observable<Int> = publishSubject
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding: View = LayoutInflater.from(parent.context).inflate(R.layout.item_issuse,parent,false)
        return CharacterViewHolder(binding)

    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.binding.tv_issue_title.text = getItem(position).fragments.issuesFragment.title
        holder.binding.tv_author_name.text = getItem(position).fragments.issuesFragment.author?.login
        holder.binding.tv_comment_total.text = getItem(position).fragments.issuesFragment.comments.nodes?.size.toString()

        if(getItem(position).fragments.issuesFragment.closed)
        {
            holder.binding.chip_issue_status_tag.text = "Closed"
        }
        else
        {
            holder.binding.chip_issue_status_tag.text = "Opened"
        }
        holder.binding.layout_issuse.setOnClickListener {
            publishSubject.onNext(position)
        }


    }

}

class CharacterViewHolder(val binding: View) : RecyclerView.ViewHolder(binding.rootView)

class IssuesDiffUtil : DiffUtil.ItemCallback<IssuesListQuery.Node>() {
    override fun areItemsTheSame(
        oldItem: IssuesListQuery.Node,
        newItem: IssuesListQuery.Node
    ): Boolean {
        return oldItem.fragments.issuesFragment.id == newItem.fragments.issuesFragment.id
    }

    override fun areContentsTheSame(
        oldItem: IssuesListQuery.Node,
        newItem: IssuesListQuery.Node
    ): Boolean {
        return oldItem == newItem
    }


}
