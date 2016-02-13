package com.example.base.base;

/**
 * Created by Administrator on 2016/1/13.
 */
public class Conn {

    //服务器地址
    public static final String BASE_URL = "http://120.24.101.52:8083/";

    public static final String OAUTH_TOKEN = BASE_URL + "oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=client_credentials";
    public static final String SAVE = BASE_URL + "v1.0/feedbacks/save";

    public static final String REGIST = BASE_URL + "v1.0/users/emailExist";

    //更改头像
    public static final String UPDATE_PICTURE = BASE_URL + "v1.0/users/upHeadThumb";
    //用户反馈
    public static final String USER_SAVE = BASE_URL + "v1.0/feedbacks/save";
    // 获取邮箱验证码
    public static final String EMAIL_CODE = BASE_URL+"v1.0/users/getEmail";
    //分类搜索
    public static final String SEARCH = BASE_URL+"v1.0/categorySon/search";
    //商家的评价
    public static final String BUSINESS_COMMENT = BASE_URL+"v1.0/orders/businessComment";
    //新开店铺一
    public static final String SAVEOS_YI = BASE_URL+"/v1.0/stores/saveos";
}
