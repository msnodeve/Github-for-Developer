package com.seok.gfd.v1.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.seok.gfd.R
import com.seok.gfd.retrofit.domain.User
import com.seok.gfd.retrofit.domain.resopnse.CommitsResponseDto
import com.seok.gfd.utils.ProgressbarDialog
import com.seok.gfd.utils.SharedPreference
import com.seok.gfd.utils.ValidationCheck
import com.seok.gfd.viewmodel.GithubContributionViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import java.time.LocalDate

class MainFragment : Fragment() {
    private lateinit var sharedPreference: SharedPreference
    private lateinit var progressbar: ProgressbarDialog
    private lateinit var githubContributionViewModel: GithubContributionViewModel

    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initSetUI()
        initViewModelFun()
    }

    private fun init() {
        progressbar =  ProgressbarDialog(context!!)
        progressbar.show()
        sharedPreference = SharedPreference(this.activity!!.application)
        user = sharedPreference.getValueObject(getString(R.string.user_info))
        githubContributionViewModel =
            ViewModelProviders.of(this).get(GithubContributionViewModel::class.java)
        githubContributionViewModel.getContributions(user.login)
    }

    private fun initViewModelFun() {
        githubContributionViewModel.contributions.observe(this, Observer {
            val fragmentPagerItems = FragmentPagerItems.with(activity)
            for (element in it.years!!) {
                val commitResponseDto = getYearContributionData(element.year, it)
                fragmentPagerItems.add(element.year + "(" + element.total + ")", MainSub::class.java,
                    MainSub.arguments(commitResponseDto)
                )
            }
            val adapter = FragmentPagerItemAdapter(
                activity?.supportFragmentManager,
                fragmentPagerItems.create()
            )
            main_view_pager.adapter = adapter
            main_tab_smart_layout.setViewPager(main_view_pager)
            progressbar.hide()
        })
    }

    private fun initSetUI() {
        main_tv_today.text = LocalDate.now().toString()
        main_tv_user_name.text = user.login

        val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(50))
        Glide.with(this).load(user.avatar_url).apply(requestOptions).into(main_image_profile)
        main_tv_user_bio.text = user.bio
    }

    private fun getYearContributionData(year: String, commitsResponseDto: CommitsResponseDto): CommitsResponseDto {
        var yearContributionDtoItem = CommitsResponseDto(year)
        var resultList = yearContributionDtoItem.contributions as ArrayList
        for (element in commitsResponseDto.contributions!!) {
            if (element.date.contains(year)) {
                resultList.add(element)
            }
        }
        yearContributionDtoItem.contributions = resultList
        return yearContributionDtoItem
    }
}