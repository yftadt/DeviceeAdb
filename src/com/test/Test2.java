package com.test;

import com.utile.IPS;

/**
 * Created by guom on 2020/2/28.
 */
public class Test2 {
    @org.junit.Test
    public void test1() {
        boolean isIp = IPS.isIp("192.168.137.8");
        System.out.println(isIp);
    }

}
