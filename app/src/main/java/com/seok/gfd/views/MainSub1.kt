package com.seok.gfd.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ogaclejapan.smarttablayout.utils.v4.Bundler
import com.seok.gfd.R
import com.seok.gfd.retrofit.domain.resopnse.CommitsResponseDto
import com.seok.gfd.utils.CommonUtils

class MainSub1 : Fragment() {

    companion object{
        fun arguments(param : CommitsResponseDto) : Bundle{
            val str = CommonUtils.gson.toJson(param)
            return Bundler().putString("key", str).get()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_sub1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val t = arguments?.getString("key")
        val user = CommonUtils.gson.fromJson(t, CommitsResponseDto::class.java)
        user
    }
}