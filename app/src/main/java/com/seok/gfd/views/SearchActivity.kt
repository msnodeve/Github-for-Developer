package com.seok.gfd.views

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.seok.gfd.R
import com.seok.gfd.adapter.GithubIdAdapter
import com.seok.gfd.room.entity.GithubId
import com.seok.gfd.utils.ValidationCheck
import com.seok.gfd.viewmodel.GithubIdViewModel
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : AppCompatActivity() {
    private lateinit var githubIdsViewModel: GithubIdViewModel
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var githubIdAdapter: GithubIdAdapter
    private lateinit var githubIds: ArrayList<GithubId>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        // 초기 설정
        init()
        initViewModel()
        setAnimation()
        setListener()
    }

    private fun setListener() {
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
            val githubId = search_edt_id.text.toString()
            if(ValidationCheck.validIsEmptyString(githubId)){
                Snackbar.make(search_main_layout, "아이디칸이 비어있네요!", Snackbar.LENGTH_SHORT).show()
            }else{
                if(ValidationCheck.isExistSite(githubId)){
                    val githubIdDto = GithubId(githubId)
                    githubIdsViewModel.insertGithubId(githubIdDto)
                }else{
                    Snackbar.make(search_main_layout, "존재하지 않는 아이디 같아요.\n문제가 생겼다면 개발자에게 문의해주세요!", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        // githubIdsViewModel.closeDatabase() db 컨넥션 끊기
    }

    private fun init() {
        githubIds = ArrayList()
        githubIdAdapter = GithubIdAdapter(githubIds, search_edt_id)
        gridLayoutManager = GridLayoutManager(this, 1)
        search_recycler_view.layoutManager = gridLayoutManager
        search_recycler_view.adapter = githubIdAdapter
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
            githubIdAdapter.notifyDataSetChanged()
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