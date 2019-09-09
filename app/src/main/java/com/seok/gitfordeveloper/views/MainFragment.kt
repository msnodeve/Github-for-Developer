package com.seok.gitfordeveloper.views


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.seok.gitfordeveloper.BuildConfig

import com.seok.gitfordeveloper.R
import com.seok.gitfordeveloper.utils.AuthUserInfo
import com.seok.gitfordeveloper.utils.AuthUserToken
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    private lateinit var authUserToken: AuthUserToken
    private lateinit var authUserInfo: AuthUserInfo

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        authUserToken = AuthUserToken(this.activity?.application!!)


        MobileAds.initialize(this.activity!!.application, getString(R.string.banner_ad_unit_id_for_test))
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
    private fun checkForUserInfo(){
        val user = authUserInfo.getUser(
            getString(R.string.user_id),
            getString(R.string.user_url),
            getString(R.string.user_image)
        )
        if(user){

        }
    }
}
