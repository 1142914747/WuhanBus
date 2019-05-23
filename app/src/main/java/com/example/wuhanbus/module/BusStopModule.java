package com.example.wuhanbus.module;

import com.example.wuhanbus.activity.MainActivity;
import com.example.wuhanbus.presenter.BusStopPresenter;
import com.example.wuhanbus.presenter.IBasePresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class BusStopModule {

    private MainActivity view;

    public BusStopModule(MainActivity view) {
        this.view = view;
    }

    @Provides
    public IBasePresenter provideBusStopPresenter(){
        return new BusStopPresenter(view);
    }
}
