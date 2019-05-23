package com.example.wuhanbus.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.wuhanbus.presenter.IBasePresenter;
import com.example.wuhanbus.service.RetrofitService;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class BaseActivity<T extends IBasePresenter> extends AppCompatActivity {

    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(attachResource());
        ButterKnife.bind(this);
        RetrofitService.init();
        initInjector();
        initView();
    }

    protected abstract int attachResource();

    protected abstract void initInjector();

    protected abstract void initView();
}
