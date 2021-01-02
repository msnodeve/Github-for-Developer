package com.seok.gfd.views

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import com.seok.gfd.R
import com.seok.gfd.room.AppDatabase
import com.seok.gfd.room.entity.SearchGithubId
import com.seok.gfd.viewmodel.GithubIdViewModel
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.runBlocking
import java.util.*

class SearchActivity : AppCompatActivity() {
    private lateinit var githubIdsViewModel: GithubIdViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        init()
        setAnimation()
    }

    private fun init() {
        githubIdsViewModel = ViewModelProviders.of(this).get(GithubIdViewModel::class.java)
        githubIdsViewModel.githubIds.observe(this, Observer {
            for(a in it){
                println("data${a.gid},${a.gidName},${a.created}")
            }
        })
        githubIdsViewModel.getGithubId("t")
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
    }

//    private fun iWantToKnowTheDatabaseIsFind() {
//        // 테스트 용으로 메모리상 생성
//        val database = Room.inMemoryDatabaseBuilder(
//            this,
//            AppDatabase::class.java
//        ).build()
//
//        // id를 0 으로 설정해주어서 id가 autoGeneration 되게 한다.
//        val test = SearchGithubId(gidName = "test")
//        runBlocking {
//            // 습관을 씁니다.
//            database.searchGithubIdDao().insert(test)
//
//            // 실제 DB에 써진 것을 확인합니다.
//            var dbHabitSchema = database.searchGithubIdDao().selectAll("t")[0]
//            Log.d(this.javaClass.name, "방금 넣은 것 $dbHabitSchema")
//
//            // 지우는 것도 잘 동작하는지 확인해 봅니다.
//            database.searchGithubIdDao().delete(dbHabitSchema)
//
//            Log.d(this.javaClass.name, "방금 지워서 아무것도 없음. ${database.searchGithubIdDao().selectAll("t")}")
//        }
//    }
}