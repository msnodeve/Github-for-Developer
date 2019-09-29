package com.seok.gitfordeveloper.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.seok.gitfordeveloper.R
import com.seok.gitfordeveloper.adapter.TRCommitListAdapter
import com.seok.gitfordeveloper.viewmodel.RankFragmentViewModel
import kotlinx.android.synthetic.main.fragment_rank.*

/**
 * A simple [Fragment] subclass.
 */
class RankFragment : Fragment() {

    private lateinit var viewModel: RankFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initViewModelFun()
        viewModel.getTodayRankList()
    }
    private fun init(){
        viewModel = ViewModelProviders.of(this).get(RankFragmentViewModel::class.java)
        rv_rank.layoutManager = LinearLayoutManager(activity)
        rv_rank.setHasFixedSize(true)
    }
    private fun initViewModelFun(){
        viewModel.rankList.observe(this, Observer {
            it
            val adapter = TRCommitListAdapter(it)
            rv_rank.adapter = adapter

        })
    }
}
