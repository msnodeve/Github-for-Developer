package com.seok.gitfordeveloper.views


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seok.gitfordeveloper.BuildConfig

import com.seok.gitfordeveloper.R
import com.seok.gitfordeveloper.retrofit.RetrofitClient
import com.seok.gitfordeveloper.retrofit.domain.GUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class SubFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getGUserService = RetrofitClient.gUserService()
        val getGUserCall = getGUserService.getUserList(BuildConfig.BASIC_AUTH_KEY)
        getGUserCall.enqueue(object : Callback<List<GUser>>{
            override fun onResponse(call: Call<List<GUser>>, response: Response<List<GUser>>) {
                if(response.isSuccessful){
                    val body = response.body()
                    body
                }else{
                    val body = 1
                    body
                }
            }
            override fun onFailure(call: Call<List<GUser>>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }
        })
    }
}
