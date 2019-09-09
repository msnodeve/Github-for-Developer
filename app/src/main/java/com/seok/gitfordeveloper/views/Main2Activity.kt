package com.seok.gitfordeveloper.views

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.seok.gitfordeveloper.R
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {
    private val fragmentManager = supportFragmentManager

    private val mainFragment = MainFragment()
    private val subFragment = SubFragment()

    private var activeFragment : Fragment = mainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        fragmentManager.beginTransaction().add(R.id.host_fragment, subFragment, "2").hide(subFragment).commit()
        fragmentManager.beginTransaction().add(R.id.host_fragment, mainFragment, "1").commit()

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_menu_f -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(mainFragment).commit()
                    activeFragment = mainFragment
                }
                R.id.nav_menu_s -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(subFragment).commit()
                    activeFragment = subFragment
                }
            }
            true
        }
    }
}