package com.zdu.simplenewsdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zdu.simplenewsdemo.R;
import com.zdu.simplenewsdemo.bean.WeatherBean;
import com.zdu.simplenewsdemo.utils.WeatherUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by duzongning on 2016/9/12.
 */
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {
    private ArrayList<WeatherBean.DataEntity.ForecastEntity> list;

    public WeatherAdapter(ArrayList<WeatherBean.DataEntity.ForecastEntity> list) {
        this.list = list;
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        WeatherBean.DataEntity.ForecastEntity entity = list.get(position + 1);
        holder.mDate.setText(entity.getDate());
        holder.mWeather.setText(entity.getType());
        holder.mWeatherImage.setImageResource(WeatherUtils.getWeatherImage(entity.getType()));
        holder.mWeatherTemp.setText(entity.getHigh()+entity.getLow());
        holder.mWind.setText(entity.getFengxiang());
    }

    @Override
    public int getItemCount() {
        return list.size() - 1;
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.date)
        TextView mDate;
        @Bind(R.id.weatherImage)
        ImageView mWeatherImage;
        @Bind(R.id.weatherTemp)
        TextView mWeatherTemp;
        @Bind(R.id.wind)
        TextView mWind;
        @Bind(R.id.weather)
        TextView mWeather;

        public WeatherViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
