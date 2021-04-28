package com.seok.gfd.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.seok.gfd.R
import com.seok.gfd.room.entity.GithubId
import com.seok.gfd.viewmodel.GithubIdViewModel
import kotlinx.android.synthetic.main.item_search_github_id.view.*

class GithubIdAdapter(list: ArrayList<GithubId>, edtText : EditText) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val githubIds: ArrayList<GithubId> = list
    private val searchEditText = edtText
    private lateinit var githubIdsViewModel: GithubIdViewModel

    inner class SearchGithubIdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(githubId: GithubId){
            itemView.item_search_txt_github_id.text = githubId.githubId
            itemView.item_search_img_close.setOnClickListener {
                githubIdsViewModel.deleteGithubId(githubId)
                githubIds.removeAt(this.adapterPosition)
                notifyItemRemoved(this.adapterPosition)
                notifyDataSetChanged()
            }
            itemView.item_search_card_view.setOnClickListener {
                searchEditText.setText(githubId.githubId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_github_id, parent, false)
        githubIdsViewModel = ViewModelProviders.of(parent.context as FragmentActivity).get(GithubIdViewModel::class.java)
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