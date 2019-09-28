package com.seok.gitfordeveloper.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.seok.gitfordeveloper.R
import com.seok.gitfordeveloper.viewmodel.RankFragmentViewModel

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
    }
    private fun initViewModelFun(){
        viewModel.rankList.observe(this, Observer {
            it
        })
    }
}
