package com.seok.gfd.views


import android.annotation.SuppressLint
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
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class RankFragment : Fragment() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: CommitsAdapter
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
            }
        })
    }

    @SuppressLint("NewApi")
    private fun init() {
        sharedPreference = SharedPreference(this.activity!!.application)
        linearLayoutManager = LinearLayoutManager(this.context)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        val user = sharedPreference.getValueObject(getString(R.string.user_info))
        Glide.with(this).load(user.avatar_url).apply(RequestOptions.circleCropTransform())
            .into(img_rv_profile)
        user.avatar_url
        rv_rank.layoutManager = linearLayoutManager
        tv_rv_commit.text = sharedPreference.getValue(getString(R.string.user_today))
        userViewModel.getCommitsRank()

        img_rank_sync.setOnClickListener {
            userViewModel.getCommitsRank()
        }

    }

    private fun initViewModelFun() {
        val user = sharedPreference.getValueObject(getString(R.string.user_info))
        userViewModel.commitList.observe(this, Observer {
            try {
                val user = it.find { it.user_id == user.login }
                Glide.with(this).load(user?.user_image).apply(RequestOptions.circleCropTransform())
                    .into(img_rv_profile)
                tv_rv_commit.text = user?.data_count.toString()
                adapter = CommitsAdapter(it as ArrayList<CommitResponse>)
                rv_rank.adapter = adapter
            } catch (e: Exception) {
                Log.e(this.javaClass.simpleName, e.toString())
            }
        })
    }
}
