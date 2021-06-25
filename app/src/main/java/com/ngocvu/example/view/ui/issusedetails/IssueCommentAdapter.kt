package com.ngocvu.example.view.ui.issusedetails

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.IssuesListQuery
import com.example.RepositoryListQuery
import com.example.fragment.IssuesFragment
import com.ngocvu.example.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_comment.view.*
import kotlinx.android.synthetic.main.item_issuse.view.*
import kotlinx.android.synthetic.main.item_repository.view.*

//class IssueCommentAdapter (val context: Context, private var items: ArrayList<IssuesFragment.Node>) :
//    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return CommentViewHolder(
//            LayoutInflater.from(parent.context)
//                .inflate(R.layout.item_comment, parent, false)
//        )
//    }
//
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if (holder is CommentViewHolder) {
//            holder.tvAuthorName.text = items[position].author?.login
//            holder.tvCommentBody.text = items[position].body
//
//        }
//    }
//
//
//    override fun getItemCount(): Int {
//        return items.size
//    }
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return position
//    }
//}
//
//private class CommentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//    val tvAuthorName = itemView.tv_user_author_name
//    val tvCommentBody = itemView.tv_comment_body
//}

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
