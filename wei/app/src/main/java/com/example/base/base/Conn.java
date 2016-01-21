package com.example.base.base;

/**
 * Created by Administrator on 2016/1/13.
 */
public class Conn {

    public static final String BASE_URL = "http://120.24.101.52:8083/";

    public static final String OAUTH_TOKEN = BASE_URL + "oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=client_credentials";
    public static final String SAVE = BASE_URL + "v1.0/feedbacks/save";

    public static final String REGIST = BASE_URL + "v1.0/users/emailExist";

}
