package com.example.base.base;

/**
 * Created by Administrator on 2016/1/13.
 */
public class Conn {

    public static final String BASE_URL = "http://120.24.101.52:8083/";

    public static final String OAUTH_TOKEN = BASE_URL + "oauth/token?";
    public static final String SAVE = BASE_URL + "v1.0/feedbacks/save?access_token=932d9439-00e4-4ec5-84ac-de2e9c6fa5ce&userId=2&feedback=a";

//    public static final String SAVES = BASE_URL + "v1.0/trade/trades?access_token={932d9439-00e4-4ec5-84ac-de2e9c6fa5ce&userId}";

    public static final String REGIST = BASE_URL + "v1.0/users/register";

}
