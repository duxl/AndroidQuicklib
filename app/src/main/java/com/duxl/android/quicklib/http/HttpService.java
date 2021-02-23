package com.duxl.android.quicklib.http;

import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * HttpService
 * create by duxl 2020/8/18
 */
public interface HttpService {

    /**
     * 获取接口地址配置
     */
    @GET("app/mock/258579/jz_serverUrlConfig")
    Observable<Root<LinkedHashMap<String, String>>> getServerUrlConfig();

    @POST("app/mock/258579/testPost")
    @FormUrlEncoded
    Observable<Object> postTest(@Field("id") String id, @Field("type") int type);

    /**
     * 测试分页获取数据
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("app/mock/258579/test_list_data")
    Observable<Root<List<String>>> getList(
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );
}
