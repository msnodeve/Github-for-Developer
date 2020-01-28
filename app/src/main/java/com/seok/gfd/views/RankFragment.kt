package com.seok.gfd.views


import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.seok.gfd.R
import com.seok.gfd.adapter.CommitsAdapter
import com.seok.gfd.retrofit.domain.resopnse.CommitResponse
import com.seok.gfd.utils.SharedPreference
import com.seok.gfd.utils.SharedPreferencesForUser
import com.seok.gfd.viewmodel.RankFragmentViewModel
import com.seok.gfd.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_rank.*

/**
 * A simple [Fragment] subclass.
 */
class RankFragment : Fragment() {
    private var check : Boolean = false
    private var page : Int = 1
    private lateinit var userViewModel: UserViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: CommitsAdapter
    private lateinit var commits: ArrayList<CommitResponse>
    private var lastViesibleItemPosition: Int = 0
        get() = linearLayoutManager.findLastVisibleItemPosition()


    private lateinit var sharedPreference: SharedPreference


    private lateinit var sharedPreferencesForUser: SharedPreferencesForUser
    private lateinit var rankViewModel: RankFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initViewModelFun()
        setRecyclerViewScrollListener()
    }

    private fun setRecyclerViewScrollListener() {
        rv_rank.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = rv_rank.layoutManager!!.itemCount
                if (totalItemCount == lastViesibleItemPosition + 1) {
                    if(check) {
                        userViewModel.getCommitsRank(page++)
                        check = false
                    }
                }
            }
        })
    }

    private fun init() {
        commits = ArrayList()
        sharedPreference = SharedPreference(this.activity!!.application)
        linearLayoutManager = LinearLayoutManager(this.context)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        userViewModel.getCommitsRank(page++)


        rv_rank.layoutManager = linearLayoutManager
        tv_rv_commit.text = sharedPreference.getValue(getString(R.string.user_today))







        rankViewModel = ViewModelProviders.of(this).get(RankFragmentViewModel::class.java)
        sharedPreferencesForUser = SharedPreferencesForUser(this.activity?.application!!)
//        rv_rank.layoutManager = LinearLayoutManager(activity)
//        rv_rank.addItemDecoration(DividerItemDecoration(this.context, 1))
//        rv_rank.setHasFixedSize(true)
        img_rank_sync.setOnClickListener {
            rankViewModel.getTodayRankCommitList()
        }
        rankViewModel.getTodayRankCommitList()
    }

    //    override fun receivedNewCommit(commit: CommitResponseDto){
//
//    }
    private fun initViewModelFun() {
        userViewModel.commitList.observe(this, Observer {
            for(commit in it){
                commits.add(commit)
            }
            if (commits.isEmpty()) {
                // 정보 가져오기
                adapter.notifyItemInserted(commits.size)
            }
            adapter = CommitsAdapter(commits)
            rv_rank.adapter = adapter
            rv_rank.scrollToPosition(commits.size-2)
            check= true
        })






        rankViewModel.serverResult.observe(this, Observer {
            rankViewModel.getTodayRankCommitList()
        })

        rankViewModel.rankList.observe(this, Observer {
            //            val adapter = CommitsAdapter(this.activity!!.application, it, this)
//            rv_rank.adapter = adapter

            Glide.with(this).load(sharedPreferencesForUser.getValue(getString(R.string.user_image)))
                .apply(
                    RequestOptions.circleCropTransform()
                ).into(img_rv_profile)
//            this.activity!!.runOnUiThread {
//                for(commitNumber in it.indices){
//                    if(it[commitNumber].uid == sharedPreferencesForUser.getValue(getString(R.string.user_id))){
//                        rv_rank.scrollToPosition(commitNumber)
//                    }
//                }
//            }
        })
    }
}
