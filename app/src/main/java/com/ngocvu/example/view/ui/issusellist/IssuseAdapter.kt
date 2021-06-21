package com.ngocvu.example.view.ui.issusellist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.IssuesListQuery
import com.ngocvu.example.R
import com.ngocvu.example.data.vo.Issues
import com.ngocvu.example.data.vo.Repository
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_issuse.view.*
import kotlinx.android.synthetic.main.item_repository.view.*

class IssuseAdapter(val context: Context, private var items: List<IssuesListQuery.Node>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val publishSubject = PublishSubject.create<Int>()
    val observeEvent: Observable<Int> = publishSubject


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return IssuseViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_issuse, parent, false)
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is IssuseViewHolder) {
            holder.tvIssuesTitle.text = items[position].title
            holder.tvIssuesSubTitle.text = items[position].body
            holder.layoutIssues.setOnClickListener {
                publishSubject.onNext(position)
            }

        }
    }


    override fun getItemCount(): Int {
        return items.size
    }
}

private class IssuseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val tvIssuesTitle = itemView.tv_issuse_title
    val tvIssuesSubTitle = itemView.tv_issuse_about
    val layoutIssues = itemView.layout_issuse
}
