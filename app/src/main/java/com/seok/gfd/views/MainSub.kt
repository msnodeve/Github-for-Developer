package com.seok.gfd.views

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.ogaclejapan.smarttablayout.utils.v4.Bundler
import com.seok.gfd.R
import com.seok.gfd.retrofit.domain.resopnse.CommitsResponseDto
import com.seok.gfd.utils.CommonUtils
import kotlinx.android.synthetic.main.fragment_main_sub.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.textColor
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.ceil

class MainSub : Fragment() {

    private lateinit var commitResponse: CommitsResponseDto

    companion object {
        fun arguments(param: CommitsResponseDto): Bundle {
            val str = CommonUtils.gson.toJson(param)
            return Bundler().putString("year", str).get()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_sub, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        val yearContribution = arguments?.getString("year")
        commitResponse = CommonUtils.gson.fromJson(yearContribution, CommitsResponseDto::class.java)
    }

    private fun createContributionUI(commitSize: Int, startDay: Int, commits : List<CommitsResponseDto.Contribution>) {
        // Week 단위 표시
        var weekPosTop = 20f
        for (index in 1..ceil(commitSize.toDouble() / 7).toInt() step 2) {
            val weekText = TextView(activity)
            weekText.textColor = activity!!.getColor(R.color.userRankPos)
            weekText.text = index.toString() + "W"
            weekText.typeface = ResourcesCompat.getFont(context!!, R.font.spoqa_han_sans_regular)
            main_sub_scalable_layout.addView(weekText, 20f, weekPosTop, 100f, 50f)
            main_sub_scalable_layout.setScale_TextSize(weekText, 35f)
            weekPosTop += 55f
        }
        weekPosTop = 20f
        for (index in 2..commitSize / 7 step 2) {
            val weekText = TextView(activity)
            weekText.textColor = activity!!.getColor(R.color.userRankPos)
            weekText.typeface = ResourcesCompat.getFont(context!!, R.font.spoqa_han_sans_regular)
            weekText.text = index.toString() + "W"
            main_sub_scalable_layout.addView(weekText, 570f, weekPosTop, 100f, 50f)
            main_sub_scalable_layout.setScale_TextSize(weekText, 35f)
            weekPosTop += 55f
        }

        // Contribution 표시
        var layoutSize = 45f
        var layoutPScaleTop = 30f
        var layoutPScaleLeft = 110f
        var lineChange = 0

        layoutPScaleLeft += 55f * startDay
        lineChange += startDay

        for (index in commitSize - 1 downTo 0) {
            val commit = commits[index]
            val linearLayout = LinearLayout(activity)
            main_sub_scalable_layout.addView(linearLayout, layoutPScaleLeft, layoutPScaleTop, layoutSize, layoutSize)
            linearLayout.backgroundColor = Color.parseColor(commit.color)
            layoutPScaleLeft += 55f
            lineChange++
            if (lineChange % 14 == 0) {
                layoutPScaleTop += 55f
                layoutPScaleLeft = 110f
            } else if (lineChange % 7 == 0) {
                layoutPScaleLeft += 170f
            }
            linearLayout.setOnClickListener { Toast.makeText(activity, String.format("%s (%d)", commit.date, commit.count) , Toast.LENGTH_SHORT).show() }
        }
    }

    private fun initUI() {
        val commits = commitResponse.contributions!!
        val commitSize = commits.size
        val date = commits[commitSize - 1].date
        val localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE)
        val startDay = getDayStartCount(localDate.dayOfWeek)
        createContributionUI(commitSize, startDay, commits)
    }

    private fun getDayStartCount(dayOfWeek: DayOfWeek): Int = when (dayOfWeek) {
        DayOfWeek.MONDAY -> 0
        DayOfWeek.TUESDAY -> 1
        DayOfWeek.WEDNESDAY -> 2
        DayOfWeek.THURSDAY -> 3
        DayOfWeek.FRIDAY -> 4
        DayOfWeek.SATURDAY -> 5
        DayOfWeek.SUNDAY -> 6
        else -> 0
    }
}
