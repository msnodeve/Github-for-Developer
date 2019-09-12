package com.seok.gitfordeveloper.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.seok.gitfordeveloper.BuildConfig
import com.seok.gitfordeveloper.R
import com.seok.gitfordeveloper.utils.AuthUserInfo
import com.seok.gitfordeveloper.utils.AuthUserToken
import com.seok.gitfordeveloper.utils.SharedPreferencesForUser
import com.seok.gitfordeveloper.viewmodel.MainFragmentViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var sharedPreferencesForUser : SharedPreferencesForUser

    private lateinit var authUserInfo: AuthUserInfo
    private lateinit var viewModel: MainFragmentViewModel

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
        initViewModelFun()
        checkForUserInfo()
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this).get(MainFragmentViewModel::class.java)
        sharedPreferencesForUser = SharedPreferencesForUser(this.activity?.application!!)

        authUserInfo = AuthUserInfo(this.activity?.application!!)

        MobileAds.initialize(
            this.activity!!.application,
            getString(R.string.banner_ad_unit_id_for_test)
        )
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
    private fun initViewModelFun(){
        viewModel.user.observe(this, Observer {
            setUserInfoUI(it.login, it.html_url, it.avatar_url)
        })
    }
    private fun checkForUserInfo() {
        viewModel.setUserInfo()
    }

    private fun setUserInfoUI(userId: String, userUrl: String, userImage: String) {
        user_id.text = userId
        user_url.text = userUrl
        Glide.with(this).load(userImage).into(user_profile)
    }
}
