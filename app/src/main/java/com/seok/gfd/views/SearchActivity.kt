package com.seok.gfd.views

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.OnFocusChangeListener
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.seok.gfd.R
import com.seok.gfd.adapter.SearchGithubIdAdapter
import com.seok.gfd.room.entity.SearchGithubId
import com.seok.gfd.viewmodel.GithubIdViewModel
import kotlinx.android.synthetic.main.activity_guest_main.*
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : AppCompatActivity() {
    private lateinit var githubIdsViewModel: GithubIdViewModel
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var searchGithubIdAdapter: SearchGithubIdAdapter
    private lateinit var githubIds: ArrayList<SearchGithubId>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        init()
        initViewModel()
        setAnimation()
        setListener()
    }

    private fun setListener() {
        // EditText 포커싱 되었을 때
//        search_edt_id.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
//            if (hasFocus) {
//                githubIdsViewModel.getGithubId("")
//            }
//        }
        search_edt_id.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                githubIdsViewModel.getGithubId(search_edt_id.text.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        search_btn_ok.setOnClickListener {
            githubIdsViewModel.insertGithubId(SearchGithubId(search_edt_id.text.toString()))
        }

        // githubIdsViewModel.closeDatabase() db 컨넥션 끊기
    }

    private fun init() {
        githubIds = ArrayList()
        searchGithubIdAdapter = SearchGithubIdAdapter(githubIds)
        gridLayoutManager = GridLayoutManager(this, 1)
        search_recycler_view.layoutManager = gridLayoutManager
        search_recycler_view.adapter = searchGithubIdAdapter
        search_recycler_view.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    private fun initViewModel() {
        githubIdsViewModel = ViewModelProviders.of(this).get(GithubIdViewModel::class.java)
        githubIdsViewModel.githubIds.observe(this, Observer {
            githubIds.clear()
            githubIds.addAll(it)
            searchGithubIdAdapter.notifyDataSetChanged()
            println(githubIds)
        })
        // 전체 검색해놓은 것 가져오기
        githubIdsViewModel.getGithubId("")
    }

    private fun setAnimation() {
        val bottomToTop = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top)
        search_txt_info2.startAnimation(bottomToTop)
        val topToBottom = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom)
        topToBottom.startOffset = 300
        search_txt_info1.startAnimation(topToBottom)
        val leftToRight = AnimationUtils.loadAnimation(this, R.anim.left_to_right)
        leftToRight.startOffset = 800
        search_layout_id.startAnimation(leftToRight)
        val rightToLeft = AnimationUtils.loadAnimation(this, R.anim.right_to_left)
        rightToLeft.startOffset = 1000
        search_recycler_view.startAnimation(rightToLeft)
    }
}