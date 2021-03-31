package com.liu;

import com.liu.utils.RateLimiter;

public class RateLimiterTest {

    public static void main(String[] args) {
        RateLimiter  re = new RateLimiter("limit.lua");
//        for (int i = 0; i < 5; i++) {
            re.acquire("test", 2);
//
//        }
        System.out.printf("pppp");
    }

}
