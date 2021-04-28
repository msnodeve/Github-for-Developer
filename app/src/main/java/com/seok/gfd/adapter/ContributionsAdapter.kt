package com.seok.gfd.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seok.gfd.R
import com.seok.gfd.utils.Contribution
import java.time.LocalDate
import java.time.Month

class ContributionsAdapter(list: ArrayList<Contribution>, context: Context) :
    RecyclerView.Adapter<ContributionsAdapter.MyViewHolder>() {
    var list = list
    var context: Context = context

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.contributions_layout, parent, false)
        return MyViewHolder(v)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val contribution = list[position]
        val formatYear =
            String.format("%s: %d Contributions", contribution.year, contribution.total)
        holder.yearContribution.text = formatYear

        var monthFlag = ""
        val size = contribution.list!!.size
        var lineLayout = LinearLayout(this.context)
        lineLayout.orientation = LinearLayout.VERTICAL
        val params = LinearLayout.LayoutParams(35, 35)
//        params.margin = 4
        params.gravity = Gravity.CENTER
        for (index in 0 until size) {
            var tempLayout = LinearLayout(this.context)
            if (index % 7 == 0) {
                val monthDataStr = getDayStartCount(LocalDate.parse(contribution.list!![index].date).month)
                lineLayout = LinearLayout(this.context)
                lineLayout.orientation = LinearLayout.VERTICAL
//                tempLayout.backgroundColor = Color.WHITE
                tempLayout.layoutParams = params
                if(monthFlag != monthDataStr){
                    val month = TextView(this.context)
                    month.textSize = 7f
                    month.text = monthDataStr
                    tempLayout.addView(month)
                    monthFlag = monthDataStr
                }
                lineLayout.addView(tempLayout)
                tempLayout = LinearLayout(this.context)
//                tempLayout.backgroundColor = Color.parseColor(contribution.list!![index].color)
                tempLayout.layoutParams = params
                lineLayout.addView(tempLayout)
            } else {
//                tempLayout.backgroundColor = Color.parseColor(contribution.list!![index].color)
                tempLayout.layoutParams = params
                lineLayout.addView(tempLayout)
            }
            if (index % 7 == 0) {
//                holder.contributionCanvas.addView(lineLayout)
            }
            if (index == size) {
//                holder.contributionCanvas.addView(lineLayout)
            }
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var yearContribution: TextView = itemView.findViewById(R.id.guest_year_contribution)
//        var contributionCanvas: GridLayout = itemView.findViewById(R.id.canvas)
    }

    private fun getDayStartCount(dayOfMonth: Month): String = when (dayOfMonth) {
        Month.JANUARY -> "Jan"
        Month.FEBRUARY -> "Feb"
        Month.MARCH -> "Mar"
        Month.APRIL -> "Apr"
        Month.MAY -> "May"
        Month.JUNE -> "Jun"
        Month.JULY -> "Jul"
        Month.AUGUST-> "Aug"
        Month.SEPTEMBER-> "Sep"
        Month.OCTOBER -> "Oct"
        Month.NOVEMBER-> "Nov"
        Month.DECEMBER -> "Dec"
        else -> "X"
    }
}

