package com.example.a3zt.hyper.Retrofit;


import com.example.a3zt.hyper.ProductClass.Items;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {


    @GET("products?from=1")
    Call<List<Items>> Products(
            @Query("count") int count
    );


}
