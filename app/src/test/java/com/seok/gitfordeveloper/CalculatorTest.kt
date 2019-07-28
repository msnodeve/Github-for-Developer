package com.seok.gitfordeveloper

import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals


class CalculatorTest {

    private val calculator : Calculator? = Calculator()

    @Test
    fun addTest(){
        var result : Int = 0
        if (calculator != null) {
            result = calculator.add(15, 10)
        }
        assertEquals(result, 25)
    }
    @Test
    fun subTest(){
        var result : Int = 0
        if (calculator != null) {
            result = calculator.sub(15, 10)
        }
        assertEquals(result, 5)
    }
}