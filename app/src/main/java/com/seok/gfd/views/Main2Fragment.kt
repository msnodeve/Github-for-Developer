package com.seok.gfd.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.seok.gfd.R
import com.seok.gfd.utils.CommonUtils
import kotlinx.android.synthetic.main.fragment_main2.*

class Main2Fragment : Fragment() {
    lateinit var commonUtils: CommonUtils

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
    }

    private fun init() {
        commonUtils = CommonUtils.instance

        val adapter = FragmentPagerItemAdapter(
            activity?.supportFragmentManager, FragmentPagerItems.with(activity)
                .add("2020", MainSub1::class.java)
                .add("2019", MainSub2::class.java)
                .add("2018", MainSub3::class.java).create()
        )
        main_view_pager.adapter = adapter

        main_tab_smart_layout.setViewPager(main_view_pager)
    }

    private fun initSetUI() {
        main_top_scalable_layout.scaleWidth = commonUtils.getScreenWidth()
        main_top_scalable_layout.scaleHeight = commonUtils.getScreenHeight()
    }
}