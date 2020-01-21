package com.seok.gfd.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.seok.gfd.R
import com.seok.gfd.retrofit.domain.resopnse.CommitResponseDto
import kotlinx.android.synthetic.main.rv_rank_item.view.*

class CommitsAdapter(private val commit: ArrayList<CommitResponseDto>) :
    RecyclerView.Adapter<CommitsAdapter.CommitsHolder>() {

    override fun getItemCount() = commit.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommitsHolder {
        val inflatedView = parent.inflate(R.layout.rv_rank_item, false)
        return CommitsHolder(inflatedView)
    }

    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    override fun onBindViewHolder(holder: CommitsHolder, position: Int) {
        val itemCommit = commit[position]
        holder.bindCommit(itemCommit)
    }

    class CommitsHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var commit: CommitResponseDto? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d("RecyclerView", v.id.toString())
        }

        companion object {
            private val COMMIT_KEY = "COMMIT"
        }

        fun bindCommit(commitResponseDto: CommitResponseDto) {
            this.commit = commitResponseDto
            view.tv_rank_commit.text = commitResponseDto.data_count.toString()
            view.tv_rv_rank_username.text = commitResponseDto.uid
//            view.tv_rv_rank_num.text = rank
            Glide.with(view).load(commitResponseDto.profile_image)
                .apply(RequestOptions.circleCropTransform()).into(view.img_rv_user_profile)
        }
    }

}
//class CommitsAdapter(private val application: Application, private val items: List<TRCommitResponseDto>, private val fragment: Fragment) :
//    RecyclerView.Adapter<CommitsAdapter.TRCommitRankViewHolder>() {
//
//    private lateinit var sharedPreferencesForUser: SharedPreferencesForUser
//
//    override fun getItemCount() = items.size
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TRCommitRankViewHolder {
//        sharedPreferencesForUser = SharedPreferencesForUser(application)
//        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.rv_rank_item, parent, false)
//        return TRCommitRankViewHolder(inflatedView)
//    }
//
//    override fun onBindViewHolder(holder: TRCommitRankViewHolder, position: Int) {
//        val item = items[position]
//        val rank = (position+1).toString()
//        if(item.uid == sharedPreferencesForUser.getValue(application.getString(R.string.user_id))){
//            fragment.tv_rv_rank.text = rank
//            fragment.tv_rv_commit.text = item.data_count.toString()
//            Glide.with(fragment).load(item.profile_image).apply(RequestOptions.circleCropTransform()).into(fragment.img_rv_profile)
//        }
//        holder.apply {
//            bind(rank, item, sharedPreferencesForUser.getValue(application.getString(R.string.user_id)))
//            itemView.tag = item
//        }
//    }
//
//    class TRCommitRankViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {
//        fun bind(rank: String, item: TRCommitResponseDto, userId: String) {
//            view.tv_rv_rank_num.text = rank
//            view.tv_rv_rank_username.text = item.uid
//            view.tv_rank_commit.text = item.data_count.toString()
//            Glide.with(view.context).load(item.profile_image).apply(RequestOptions.circleCropTransform()).into(view.img_rv_user_profile)
//            if(item.uid == userId){
//                view.layout_rv_back.setBackgroundResource(R.color.nonCommit)
//            }
//        }
//    }
//}