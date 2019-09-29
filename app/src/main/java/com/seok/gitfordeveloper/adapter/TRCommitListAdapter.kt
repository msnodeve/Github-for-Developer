package com.seok.gitfordeveloper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.seok.gitfordeveloper.R
import com.seok.gitfordeveloper.retrofit.request.TRCommitResponseDto
import kotlinx.android.synthetic.main.rv_rank_item.view.*

class TRCommitListAdapter(private val items: List<TRCommitResponseDto>) :
    RecyclerView.Adapter<TRCommitListAdapter.TRCommitRankViewHolder>() {

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TRCommitRankViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_rank_item, parent, false)
        return TRCommitListAdapter.TRCommitRankViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: TRCommitRankViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener {
            Toast.makeText(it.context, "Clicked: ${item.userId}", Toast.LENGTH_SHORT)
        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }

    class TRCommitRankViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var view: View = view
        fun bind(listener: View.OnClickListener, item: TRCommitResponseDto) {
            view.tv_rv_rank_username.text = item.userId
            view.tv_rank_commit.text = item.dataCount.toString()
            view.setOnClickListener(listener)
//            Glide.with(view.context).load(item.userProfile).into(view.img_rv_profile)
        }

    }
}