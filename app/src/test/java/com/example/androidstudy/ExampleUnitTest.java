package com.example.androidstudy;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    
    @Test
    public void test1() {
        String s1 = "ahkshdkahd";
        System.out.println("s1: " + s1 + " hashCode: " + s1.hashCode());
        test2(s1);
    }

    public void test2(String s2) {
        s2 = "1213313132";
        System.out.println("s2: " + s2 + " hashCode: " + s2.hashCode());
    }
}