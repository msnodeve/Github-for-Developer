package com.seok.gfd.v1.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.seok.gfd.R
import kotlinx.android.synthetic.main.fragment_option.*

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
        Glide.with(activity!!.applicationContext).load("https://avatars0.githubusercontent.com/u/32855880?v=4").apply(RequestOptions.circleCropTransform()).into(ot_img_user_1)
        ot_tv_user_name_1.text = "ellapresso"
        ot_tv_user_github_1.text = "https://github.com/ellapresso"
        ot_tv_user_etc_1.text = "Seoul"

//        val getUserInfoService = RetrofitClient.githubApiService()
//        var getUserInfoCall1 = getUserInfoService.getUserInfo("ellapresso")
//        getUserInfoCall1.enqueue(object  : retrofit2.Callback<User>{
//            override fun onResponse(call: Call<User>, response: Response<User>) {
//                val userInfo = response.body()
//                Glide.with(activity!!.applicationContext).load(userInfo?.avatar_url).apply(RequestOptions.circleCropTransform()).into(ot_img_user_1)
//                ot_tv_user_name_1.text = userInfo?.login
//                ot_tv_user_github_1.text = userInfo?.html_url
//                ot_tv_user_etc_1.text = userInfo?.location
//            }
//            override fun onFailure(call: Call<User>, t: Throwable) {
//                Log.e(this.javaClass.simpleName, t.message.toString())
//            }
//        })

        Glide.with(activity!!.applicationContext).load("https://avatars0.githubusercontent.com/u/28593727?v=4").apply(RequestOptions.circleCropTransform()).into(ot_img_user_2)
        ot_tv_user_name_2.text = "9992"
        ot_tv_user_github_2.text = "https://github.com/9992"
        ot_tv_user_etc_2.text = "Seoul"

//        val getUserInfoCall2 = getUserInfoService.getUserInfo("9992")
//        getUserInfoCall2.enqueue(object  : retrofit2.Callback<User>{
//            override fun onResponse(call: Call<User>, response: Response<User>) {
//                val userInfo = response.body()
//                Glide.with(activity!!.applicationContext).load(userInfo?.avatar_url).apply(RequestOptions.circleCropTransform()).into(ot_img_user_2)
//                ot_tv_user_name_2.text = userInfo?.login
//                ot_tv_user_github_2.text = userInfo?.html_url
//                ot_tv_user_etc_2.text = userInfo?.location
//            }
//            override fun onFailure(call: Call<User>, t: Throwable) {
//                Log.e(this.javaClass.simpleName, t.message.toString())
//            }
//        })

        Glide.with(activity!!.applicationContext).load("https://avatars1.githubusercontent.com/u/41931979?v=4").apply(RequestOptions.circleCropTransform()).into(ot_img_user_3)
        ot_tv_user_name_3.text = "dogcolley"
        ot_tv_user_github_3.text = "https://github.com/dogcolley"
        ot_tv_user_etc_3.text = "Seoul"

//        val getUserInfoCall3 = getUserInfoService.getUserInfo("dogcolley")
//        getUserInfoCall3.enqueue(object  : retrofit2.Callback<User>{
//            override fun onResponse(call: Call<User>, response: Response<User>) {
//                val userInfo = response.body()
//                Glide.with(activity!!.applicationContext).load(userInfo?.avatar_url).apply(RequestOptions.circleCropTransform()).into(ot_img_user_3)
//                ot_tv_user_name_3.text = userInfo?.login
//                ot_tv_user_github_3.text = userInfo?.html_url
//                ot_tv_user_etc_3.text = userInfo?.location
//            }
//            override fun onFailure(call: Call<User>, t: Throwable) {
//                Log.e(this.javaClass.simpleName, t.message.toString())
//            }
//        })
    }
}
