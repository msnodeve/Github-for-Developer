package com.seok.gfd.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.seok.gfd.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val fragmentManager = supportFragmentManager

    private val mainFragment = Main2Fragment()
    private val rankFragment = RankFragment()
    private val optionFragment = OptionFragment()

    private var activeFragment : Fragment = mainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager.beginTransaction().add(R.id.host_fragment, mainFragment, "1").commit()
        fragmentManager.beginTransaction().add(R.id.host_fragment, rankFragment, "2").hide(rankFragment).commit()
        fragmentManager.beginTransaction().add(R.id.host_fragment, optionFragment, "3").hide(optionFragment).commit()
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_menu_m -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(mainFragment).commit()
                    activeFragment = mainFragment
                }
                R.id.nav_menu_r -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(rankFragment).commit()
                    activeFragment = rankFragment
                }
                R.id.nav_menu_o -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(optionFragment).commit()
                    activeFragment = optionFragment
                }
            }
            true
        }

        MobileAds.initialize(
            this.application,
            getString(R.string.admob_app_id)
        )
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
}