package com.ngocvu.example.view.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.RepositoryListQuery
import com.ngocvu.example.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_repository.view.*

class RepositoryAdapter(val context: Context,private var items: ArrayList<RepositoryListQuery.Node>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val publishSubject = PublishSubject.create<Int>()
    val observeEvent: Observable<Int> = publishSubject


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RepositoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_repository, parent, false)
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RepositoryViewHolder) {
            holder.tvRepositoryTitle.text = items[position].name
            if(items[position].issues.totalCount > 0)
            {
                holder.layoutRepository.setOnClickListener {
                    publishSubject.onNext(position)
                }
            }


        }
    }


    override fun getItemCount(): Int {
        return items.size
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}

private class RepositoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val tvRepositoryTitle = itemView.tv_repository_title
    val layoutRepository = itemView.layout_repository

}
