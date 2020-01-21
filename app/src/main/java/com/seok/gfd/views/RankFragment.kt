package com.seok.gfd.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.seok.gfd.R
import com.seok.gfd.adapter.CommitsAdapter
import com.seok.gfd.database.Commits
import com.seok.gfd.retrofit.domain.resopnse.CommitResponseDto
import com.seok.gfd.utils.SharedPreferencesForUser
import com.seok.gfd.viewmodel.RankFragmentViewModel
import kotlinx.android.synthetic.main.fragment_rank.*

/**
 * A simple [Fragment] subclass.
 */
class RankFragment : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: CommitsAdapter

    private lateinit var sharedPreferencesForUser: SharedPreferencesForUser
    private lateinit var rankViewModel: RankFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initViewModelFun()
    }
    private fun init(){
        linearLayoutManager = LinearLayoutManager(this.context)
        rv_rank.layoutManager = linearLayoutManager
        val commits = ArrayList<CommitResponseDto>()
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",3,"msnodeve"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",13,"asdf"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",32,"12"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",13,"vxc"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",343,"asbt"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",53,"zcxvq"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",37,"sdf"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",390,"fsadf"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",3987,"nhn"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",3,"msnodeve"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",13,"asdf"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",32,"12"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",13,"vxc"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",343,"asbt"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",53,"zcxvq"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",37,"sdf"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",390,"fsadf"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",3987,"nhn"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",3,"msnodeve"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",13,"asdf"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",32,"12"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",13,"vxc"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",343,"asbt"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",53,"zcxvq"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",37,"sdf"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",390,"fsadf"))
//        commits.add(CommitResponseDto("https://avatars0.githubusercontent.com/u/42924361?v=4",3987,"nhn"))
        if(commits.size == 0){
            // 정보 가져오
            adapter.notifyItemInserted(commits.size)
        }
        adapter = CommitsAdapter(commits)
        rv_rank.adapter = adapter
        rankViewModel = ViewModelProviders.of(this).get(RankFragmentViewModel::class.java)
        sharedPreferencesForUser = SharedPreferencesForUser(this.activity?.application!!)
//        rv_rank.layoutManager = LinearLayoutManager(activity)
        rv_rank.addItemDecoration(DividerItemDecoration(this.context, 1))
        rv_rank.setHasFixedSize(true)
        img_rank_sync.setOnClickListener {
            rankViewModel.getTodayRankCommitList()
        }
        rankViewModel.getTodayRankCommitList()
    }
//    override fun receivedNewCommit(commit: CommitResponseDto){
//
//    }
    private fun initViewModelFun(){
        rankViewModel.serverResult.observe(this, Observer {
            rankViewModel.getTodayRankCommitList()
        })

        rankViewModel.rankList.observe(this, Observer {
//            val adapter = CommitsAdapter(this.activity!!.application, it, this)
//            rv_rank.adapter = adapter

            Glide.with(this).load(sharedPreferencesForUser.getValue(getString(R.string.user_image))).apply(
                RequestOptions.circleCropTransform()).into(img_rv_profile)
            this.activity!!.runOnUiThread {
                for(commitNumber in it.indices){
                    if(it[commitNumber].uid == sharedPreferencesForUser.getValue(getString(R.string.user_id))){
                        rv_rank.scrollToPosition(commitNumber)
                    }
                }
            }
        })
    }
}
