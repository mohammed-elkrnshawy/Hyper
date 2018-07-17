package com.example.a3zt.hyper.Retrofit;

public class ApiUtlis {
    public static final String Base_Url="http://grapesnberries.getsandbox.com/";

    public static UserService getUserService()
    {
        return RetrofitClient.getClient(Base_Url).create(UserService.class);
    }
}
