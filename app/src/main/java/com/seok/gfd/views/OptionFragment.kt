package com.seok.gfd.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.seok.gfd.R
import com.seok.gfd.retrofit.RetrofitClient
import com.seok.gfd.retrofit.domain.User
import com.seok.gfd.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_option.*
import retrofit2.Call
import retrofit2.Response

class OptionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_option, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()
    }

    private fun init() {

    }
    private fun initViewModelFun() {

    }
    private fun setUI() {
        val getUserInfoService = RetrofitClient.githubApiService()

        var getUserInfoCall = getUserInfoService.getUserInfo("ellapresso")
        getUserInfoCall.enqueue(object  : retrofit2.Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val userInfo = response.body()!!
                Glide.with(activity!!.applicationContext).load(userInfo.avatar_url).apply(RequestOptions.circleCropTransform()).into(ot_img_user_1)
                ot_tv_user_name_1.text = userInfo.login
                ot_tv_user_github_1.text = userInfo.html_url
                ot_tv_user_etc_1.text = userInfo.location
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }
        })

        getUserInfoCall = getUserInfoService.getUserInfo("9992")
        getUserInfoCall.enqueue(object  : retrofit2.Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val userInfo = response.body()!!
                Glide.with(activity!!.applicationContext).load(userInfo.avatar_url).apply(RequestOptions.circleCropTransform()).into(ot_img_user_2)
                ot_tv_user_name_2.text = userInfo.login
                ot_tv_user_github_2.text = userInfo.html_url
                ot_tv_user_etc_2.text = userInfo.location
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }
        })
        getUserInfoCall = getUserInfoService.getUserInfo("dogcolley")
        getUserInfoCall.enqueue(object  : retrofit2.Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val userInfo = response.body()!!
                Glide.with(activity!!.applicationContext).load(userInfo.avatar_url).apply(RequestOptions.circleCropTransform()).into(ot_img_user_3)
                ot_tv_user_name_3.text = userInfo.login
                ot_tv_user_github_3.text = userInfo.html_url
                ot_tv_user_etc_3.text = userInfo.location
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }
        })
    }
}
