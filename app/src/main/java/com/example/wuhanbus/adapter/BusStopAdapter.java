package com.example.wuhanbus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wuhanbus.R;
import com.example.wuhanbus.bean.BusStopInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BusStopAdapter extends RecyclerView.Adapter<BusStopAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<BusStopInfo.DataBean.StopsBean> mStopsBeanList;
    private List<String> mBusBeanList;
    private static String ARRIVED = "1";
    private static String RUNNING = "0";


    public BusStopAdapter(Context context) {
        mInflater=LayoutInflater.from(context);
    }

    public void setData(BusStopInfo.DataBean dataBean){
        mStopsBeanList = dataBean.getStops();
        mBusBeanList = dataBean.getBuses();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recycler_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTvBusStationOrder.setText(position+1+"");
        holder.mTvBusStationName.setText(mStopsBeanList.get(position).getStopName());
        holder.mTvBusStationOrder2.setText(position+1+"");
        holder.mTvBusStationName2.setText(mStopsBeanList.get(position).getStopName());
        if(position==mStopsBeanList.size()-1){
            holder.mLinearLayout2.setVisibility(View.GONE);
        }
        if(null!=mBusBeanList && mBusBeanList.size()!=0){
            int[] BusNumber = new int[50];
            for(String busBean:mBusBeanList){
                String[] busInfo = busBean.trim().split("\\|");
                if((position+1)==Integer.parseInt(busInfo[2]) && TextUtils.equals(busInfo[3],ARRIVED)){
                    int busStopNumber = Integer.parseInt(busInfo[2]);
                    holder.mImBus.setImageResource(R.drawable.bus_stop);
                    BusNumber[busStopNumber]+=1;
                    for(int number:BusNumber) {
                        if(number > 1){
                            holder.mTvBusNumber.setText(number+"");
                        }
                    }
                }else if((position+2)==Integer.parseInt(busInfo[2]) && TextUtils.equals(busInfo[3],RUNNING)){
                    int busStopNumber = Integer.parseInt(busInfo[2]);
                    holder.mImBus2.setImageResource(R.drawable.bus_move);
                    BusNumber[busStopNumber]+=1;
                    for(int number:BusNumber) {
                        if(number > 1){
                            holder.mTvBusNumber2.setText(number+"");
                        }
                    }
                }
            }
        }
        //holder.mImBus.setImageResource(R.drawable.bus_move_norm);
        holder.mImBusStation.setImageResource(R.drawable.bus_move_normal);
    }

    @Override
    public int getItemCount() {
        return mStopsBeanList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.im_bus)
        ImageView mImBus;
        @BindView(R.id.im_bus_station)
        ImageView mImBusStation;
        @BindView(R.id.tv_bus_station_name)
        TextView mTvBusStationName;
        @BindView(R.id.tv_bus_station_order)
        TextView mTvBusStationOrder;
        @BindView(R.id.im_bus_2)
        ImageView mImBus2;
        @BindView(R.id.im_bus_station_2)
        ImageView mImBusStation2;
        @BindView(R.id.tv_bus_station_name_2)
        TextView mTvBusStationName2;
        @BindView(R.id.tv_bus_station_order_2)
        TextView mTvBusStationOrder2;
        @BindView(R.id.ll_bus_station_info_2)
        LinearLayout mLinearLayout2;
        @BindView(R.id.tv_bus_number)
        TextView mTvBusNumber;
        @BindView(R.id.tv_bus_number_2)
        TextView mTvBusNumber2;

        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}

