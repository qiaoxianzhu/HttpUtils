package com.test.httputils;

/**
 * @author Joe
 * @date 2019/9/18.
 * description：
 */
public class HttpConfig {
    public static final String RESPONSE_OK = "";
    public static final String TOKEN_OVERDUE = "";
    public static final int HTTP_TIME = 15;
    //端口号
    public static String BASE_URL = "XXX";


    public static String getURL() {
        String url;
//        if (UserRequest.getInstance().isLogin()) {
            url = "XXX";
//        } else {
//            url = BASE_URL;
//        }
        return url;
    }
}
