package com.ngocvu.example.view.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngocvu.example.R
import com.ngocvu.example.data.vo.Repository
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_repository.view.*

class RepositoryAdapter(val context: Context,private var items: List<Repository>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val publishSubject = PublishSubject.create<Repository>()
    val observeEvent: Observable<Repository> = publishSubject


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RepositoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_repository, parent, false)
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RepositoryViewHolder) {
            holder.tvRepositoryTitle.text = items[position].repositoryName
            holder.tvRepositorySubTitle.text = items[position].desc
            holder.layoutRepository.setOnClickListener {
                publishSubject.onNext(items[position])
            }

        }
    }


    override fun getItemCount(): Int {
        return items.size
    }
}

private class RepositoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val tvRepositoryTitle = itemView.tv_repository_title
    val tvRepositorySubTitle = itemView.tv_repository_title
    val layoutRepository = itemView.layout_repository

}
