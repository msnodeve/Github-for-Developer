package com.seok.gfd.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seok.gfd.R
import com.seok.gfd.room.entity.SearchGithubId
import kotlinx.android.synthetic.main.item_search_github_id.view.*

class SearchGithubIdAdapter(list: ArrayList<SearchGithubId>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val githubIds: ArrayList<SearchGithubId> = list

    class SearchGithubIdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(searchGithubId: SearchGithubId){
            itemView.item_search_txt_github_id.text = searchGithubId.gidName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_github_id, parent, false)
        return SearchGithubIdViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as SearchGithubIdViewHolder
        viewHolder.bind(githubIds[position])
    }

    override fun getItemCount(): Int {
        return githubIds.size
    }
}