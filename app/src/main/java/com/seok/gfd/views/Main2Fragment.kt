package com.seok.gfd.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.seok.gfd.R
import com.seok.gfd.retrofit.domain.User
import com.seok.gfd.retrofit.domain.YearContributionDto
import com.seok.gfd.retrofit.domain.resopnse.CommitResponseDto
import com.seok.gfd.retrofit.domain.resopnse.CommitsResponseDto
import com.seok.gfd.utils.CommonUtils
import com.seok.gfd.utils.SharedPreference
import com.seok.gfd.viewmodel.GithubContributionViewModel
import kotlinx.android.synthetic.main.fragment_main2.*
import java.time.LocalDate

class Main2Fragment : Fragment() {
    private lateinit var commonUtils: CommonUtils
    private lateinit var sharedPreference: SharedPreference

    private lateinit var githubContributionViewModel: GithubContributionViewModel

    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initSetUI()
        initViewModelFun()
    }

    private fun init() {
        sharedPreference = SharedPreference(this.activity!!.application)
        user = sharedPreference.getValueObject(getString(R.string.user_info))
        commonUtils = CommonUtils.instance
        githubContributionViewModel =
            ViewModelProviders.of(this).get(GithubContributionViewModel::class.java)
        githubContributionViewModel.getContributionData(user.login)


    }

    private fun initViewModelFun() {
        githubContributionViewModel.commits.observe(this, Observer {
            val fragmentPagerItems = FragmentPagerItems.with(activity)
            for(element in it.years!!){
                fragmentPagerItems.add(element.year +"(" + element.total + ")", MainSub::class.java, MainSub.arguments(it))
            }
            val adapter = FragmentPagerItemAdapter(activity?.supportFragmentManager, fragmentPagerItems.create())
            main_view_pager.adapter = adapter
            main_tab_smart_layout.setViewPager(main_view_pager)
        })
    }

    private fun initSetUI() {
        main_tv_today.text = LocalDate.now().toString()
        main_top_scalable_layout.scaleWidth = commonUtils.getScreenWidth()
        main_top_scalable_layout.scaleHeight = commonUtils.getScreenHeight()

        main_tv_user_name.text = user.login
        Glide.with(this).load(user.avatar_url).apply(RequestOptions.circleCropTransform()).into(main_image_profile)
        main_tv_user_bio.text = user.bio
    }

    private fun getYearContributionData(commitsResponseDto: CommitsResponseDto): ArrayList<YearContributionDto>{
        var result = YearContributionDto()
        var yearContributionDtoItem = null
        for (year in commitsResponseDto.years!!){
            for (element in commitsResponseDto.contributions!!){
                if(element.date.contains(year.toString())){
                    // 데이터 찾아서 넣기
                }
            }
        }

    }
}