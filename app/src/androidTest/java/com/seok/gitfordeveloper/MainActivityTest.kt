package com.seok.gitfordeveloper

import android.support.test.runner.AndroidJUnit4
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    @Rule
    @JvmField
    var activityRule = IntentsTestRule(MainActivity::class.java)


}