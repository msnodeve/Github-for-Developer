package com.seok.gitfordeveloper.adapter

import android.app.Activity
import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.seok.gitfordeveloper.R
import com.seok.gitfordeveloper.retrofit.domain.resopnse.TRCommitResponseDto
import com.seok.gitfordeveloper.utils.SharedPreferencesForUser
import kotlinx.android.synthetic.main.fragment_rank.*
import kotlinx.android.synthetic.main.fragment_rank.view.*
import kotlinx.android.synthetic.main.fragment_rank.view.img_rv_profile
import kotlinx.android.synthetic.main.rv_rank_item.*
import kotlinx.android.synthetic.main.rv_rank_item.view.*
import kotlinx.android.synthetic.main.rv_rank_item.view.layout_rv_back
import org.jetbrains.anko.backgroundColor

class TRCommitListAdapter(private val application: Application, private val items: List<TRCommitResponseDto>, private val fragment: Fragment) :
    RecyclerView.Adapter<TRCommitListAdapter.TRCommitRankViewHolder>() {

    private lateinit var sharedPreferencesForUser: SharedPreferencesForUser

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TRCommitRankViewHolder {
        sharedPreferencesForUser = SharedPreferencesForUser(application)
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.rv_rank_item, parent, false)
        return TRCommitRankViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: TRCommitRankViewHolder, position: Int) {
        val item = items[position]
        val rank = (position+1).toString()
        if(item.uid == sharedPreferencesForUser.getValue(application.getString(R.string.user_id))){
            fragment.tv_rv_rank.text = rank
            fragment.tv_rv_commit.text = item.data_count.toString()
            Glide.with(fragment).load(item.profile_image).apply(RequestOptions.circleCropTransform()).into(fragment.img_rv_profile)
        }
        holder.apply {
            bind(rank, item, sharedPreferencesForUser.getValue(application.getString(R.string.user_id)))
            itemView.tag = item
        }
    }

    class TRCommitRankViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {
        fun bind(rank: String, item: TRCommitResponseDto, userId: String) {
            view.tv_rv_rank_num.text = rank
            view.tv_rv_rank_username.text = item.uid
            view.tv_rank_commit.text = item.data_count.toString()
            Glide.with(view.context).load(item.profile_image).apply(RequestOptions.circleCropTransform()).into(view.img_rv_user_profile)
            if(item.uid == userId){
                view.layout_rv_back.setBackgroundResource(R.drawable.profile_gradation)
            }
        }
    }
}