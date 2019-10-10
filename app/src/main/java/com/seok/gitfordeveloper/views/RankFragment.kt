package com.seok.gitfordeveloper.views


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
import com.seok.gitfordeveloper.R
import com.seok.gitfordeveloper.adapter.TRCommitListAdapter
import com.seok.gitfordeveloper.utils.SharedPreferencesForUser
import com.seok.gitfordeveloper.viewmodel.RankFragmentViewModel
import kotlinx.android.synthetic.main.fragment_rank.*

/**
 * A simple [Fragment] subclass.
 */
class RankFragment : Fragment() {
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
        rankViewModel.getTodayRankList()
    }
    private fun init(){
        rankViewModel = ViewModelProviders.of(this).get(RankFragmentViewModel::class.java)
        sharedPreferencesForUser = SharedPreferencesForUser(this.activity?.application!!)
        rv_rank.layoutManager = LinearLayoutManager(activity)
        rv_rank.addItemDecoration(DividerItemDecoration(this.context, 1))
        rv_rank.setHasFixedSize(true)
    }
    private fun initViewModelFun(){
        rankViewModel.serverResult.observe(this, Observer {
            rankViewModel.getTodayRankCommitList()
        })

        rankViewModel.rankList.observe(this, Observer {
            val adapter = TRCommitListAdapter(this.activity!!.application, it, this)
            rv_rank.adapter = adapter
            Glide.with(this).load(sharedPreferencesForUser.getValue(getString(R.string.user_image))).into(img_rv_profile)
        })
    }
}
