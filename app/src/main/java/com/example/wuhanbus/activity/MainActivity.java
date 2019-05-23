package com.example.wuhanbus.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wuhanbus.R;
import com.example.wuhanbus.adapter.BusStopAdapter;
import com.example.wuhanbus.bean.BusStopInfo;
import com.example.wuhanbus.component.DaggerBusStopComponent;
import com.example.wuhanbus.module.BusStopModule;
import com.example.wuhanbus.presenter.BusStopPresenter;
import com.example.wuhanbus.view.IBaseView;
import com.trello.rxlifecycle.LifecycleTransformer;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity implements IBaseView {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.btn_search)
    Button mBtnSearch;

    @BindView(R.id.btn_reverse)
    Button mBtnReverse;

    @BindView(R.id.btn_refresh)
    Button mBtnRefresh;

    @BindView(R.id.et_bus_line_num)
    EditText mEtBusLineNum;

    private int mOrientation=1;
    private String mBusLineNum;

    @BindView(R.id.start_end_stop_name)
    TextView mBusStart2EndStopName;

    @BindView(R.id.service_time)
    TextView mServiceTime;

    @BindView(R.id.price)
    TextView mPrice;

    @Override
    protected void initView(){

        ((BusStopPresenter) mPresenter).setLineDirection("536",mOrientation);
        mPresenter.getData(true);

        //开始设置RecyclerView
        mRecyclerView.setItemViewCacheSize(40);
        //创建线性布局
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        //垂直方向
        mLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        //给RecyclerView设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        //创建适配器，并且设置
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int attachResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInjector() {
        DaggerBusStopComponent.builder()
                .busStopModule(new BusStopModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showNetError() {

    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return null;
    }

    @Override
    public void finishRefresh() {

    }

    @Override
    public void loadData(BusStopInfo busStopInfo) {
        BusStopInfo.DataBean mDataBean = busStopInfo.getData();
        BusStopAdapter mAdapter = new BusStopAdapter(this);
        mAdapter.setData(mDataBean);
        mRecyclerView.setAdapter(mAdapter);
        mBusStart2EndStopName.setText(mDataBean.getStartStopName()+"->"+ mDataBean.getEndStopName());
        mServiceTime.setText("运行时间 "+ mDataBean.getFirstTime()+"-"+ mDataBean.getLastTime());
        mPrice.setText("票价 约"+ mDataBean.getPrice());
    }

    @OnClick({R.id.btn_refresh,R.id.btn_reverse,R.id.btn_search})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.btn_refresh:
                mPresenter.getData(true);
                break;
            case R.id.btn_reverse:
                if(TextUtils.isEmpty(mBusLineNum)){
                    mBusLineNum = "536";
                }
                mOrientation=mOrientation==1?0:1;
                ((BusStopPresenter) mPresenter).setLineDirection(mBusLineNum,mOrientation);
                mPresenter.getData(true);
                break;
            case R.id.btn_search:
                mBusLineNum=mEtBusLineNum.getText().toString().trim();
                if(TextUtils.isEmpty(mBusLineNum)){
                    mBusLineNum = "536";
                }
                ((BusStopPresenter) mPresenter).setLineDirection(mBusLineNum,mOrientation);
                mPresenter.getData(true);
                break;
        }
    }
}
