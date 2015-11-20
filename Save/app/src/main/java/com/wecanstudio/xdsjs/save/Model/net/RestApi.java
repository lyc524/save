package com.wecanstudio.xdsjs.save.Model.net;

import com.wecanstudio.xdsjs.save.Model.Register;
import com.wecanstudio.xdsjs.save.Model.Response;
import com.wecanstudio.xdsjs.save.Model.User;
import com.wecanstudio.xdsjs.save.Model.UserInfo;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {
    @POST("/sparkjava/login")
    Observable<Response> getResponse(@Body User user);

    @GET("/sparkjava/userMessage/{username}")
    Observable<UserInfo> getUserInfo(@Header("access_token") String token, @Path("username") String username);

    @POST("/sparkjava/user")
    Observable<Response> regist(@Body Register register);

    @GET("/sparkjava/billsMessage/{username}")
    Observable<String> getBillMessage(@Header("access_token") String token, @Path("username") String username);

    @POST("/sparkjava/type/{username}")
    Observable<String> addType(@Header("access_token") String token, @Path("username") String username, @Body MyBillType myBillType);

    @POST("/sparkjava/userMessage/")
    Observable<Response> updateUserInfo(@Header("access_token") String token, @Body UserInfo userInfo);
}
