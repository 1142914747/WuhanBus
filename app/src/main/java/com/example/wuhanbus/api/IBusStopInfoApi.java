package com.example.wuhanbus.api;

import com.example.wuhanbus.bean.BusStopInfo;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface IBusStopInfoApi {

    @GET("027-{lineNo}-{direction}.do?Type=LineDetail")
    Observable<BusStopInfo> getBusStopInfo(@Path("lineNo") String lineNo, @Path("direction") int direction);
}
