package com.fuzhucheng.circlemenu;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {



       double b = Math.asin(-4 / 5f);

//        double b = Math.asin(s);

        double b1 =  (float) (b) * 180 / Math.PI;
//       System.out.println("11111111111111s:"+s);
        System.out.println("11111111111111b:"+b);
        System.out.println("11111111111111b1:"+b1);




    }
}