package com.example.wuhanbus.presenter;

import android.util.Log;

import com.example.wuhanbus.activity.MainActivity;
import com.example.wuhanbus.bean.BusStopInfo;
import com.example.wuhanbus.service.RetrofitService;
import com.example.wuhanbus.view.IBaseView;
import com.orhanobut.logger.Logger;

import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

public class BusStopPresenter implements IBasePresenter{
    private final IBaseView mView;
    private String mLine;
    private int mDirection;

    public BusStopPresenter(IBaseView mView) {
        this.mView = mView;
    }

    public void setLineDirection(String mLine, int mDirection){
        this.mLine = mLine;
        this.mDirection = mDirection;
    }

    @Override
    public void getData(boolean isRefresh) {
        RetrofitService.getBusStopInfo(mLine,mDirection)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .doOnNext(new Action1<BusStopInfo>() {
                    @Override
                    public void call(BusStopInfo newsDetailBean) {

                    }
                })
                //.compose(mView.<BusStopInfo>bindToLife())
                .subscribe(new Subscriber<BusStopInfo>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString());
                        mView.showNetError();
                    }

                    @Override
                    public void onNext(BusStopInfo busStopInfo) {
                        Log.e("daibin",busStopInfo.getResultCode());
                        mView.loadData(busStopInfo);
                    }
                });
    }

    @Override
    public void getMoreData() {

    }
}
