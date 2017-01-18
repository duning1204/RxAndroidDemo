package com.zdu.simplenewsdemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zdu.simplenewsdemo.MainActivity;
import com.zdu.simplenewsdemo.R;
import com.zdu.simplenewsdemo.adapter.WeatherAdapter;
import com.zdu.simplenewsdemo.bean.WeatherBean;
import com.zdu.simplenewsdemo.network.Network;
import com.zdu.simplenewsdemo.utils.SpUtils;
import com.zdu.simplenewsdemo.utils.WeatherUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 天气
 * Created by duzongning on 2016/9/5.
 */
public class WeatherFragment extends BaseFragment {
    @Bind(R.id.city)
    TextView mCity;
    @Bind(R.id.today)
    TextView mToday;
    @Bind(R.id.weatherImage)
    ImageView mWeatherImage;
    @Bind(R.id.weatherTemp)
    TextView mWeatherTemp;
    @Bind(R.id.wind)
    TextView mWind;
    @Bind(R.id.weather)
    TextView mWeather;
    @Bind(R.id.weather_recyclerview)
    RecyclerView mWeatherRecyclerview;
    @Bind(R.id.weather_layout)
    LinearLayout mWeatherLayout;
    @Bind(R.id.progress)
    ProgressBar mProgress;
    @Bind(R.id.weather_et)
    EditText mWeatherEt;

    private Subscription subscribe;
    private MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_whether, container, false);
        ButterKnife.bind(this, view);
        activity = (MainActivity) getActivity();
        isPrepared = true;
        return view;
    }

    @Override
    protected void lazyLoad() {
        //判断是否 初始化完成，是否处于显示状态，是否已经加载完成过数据
        if (isPrepared && isVisible && mHasLoadedOnce && mHasNet) {
            activity.hideFAB();
            return;
        }
        if (isPrepared) {
            activity.hideFAB();
            mEt = SpUtils.getInstance(getActivity()).get("weather_city", null);
            if ("".equals(mEt) || mEt == null) {
                mEt = "北京";
            }
            loadWeather();
        }
    }

    private String mEt;
    private boolean mCityTrue;

    private void loadWeather() {
        unSubscribe();
        subscribe = Network.getWeatherApi()
                .getWeather(mEt)
                .subscribeOn(Schedulers.io())
                .map(new Func1<WeatherBean, ArrayList<WeatherBean.DataEntity.ForecastEntity>>() {
                    @Override
                    public ArrayList<WeatherBean.DataEntity.ForecastEntity> call(WeatherBean weatherBean) {
                        if (weatherBean.getStatus() == 1000) {
                            mHasNet = true;
                            return weatherBean.getData().getForecast();
                        } else {
                            mCityTrue = false;
                            Snackbar.make(mWeatherEt, getString(R.string.weather_error), Snackbar.LENGTH_SHORT).show();
                        }
                        return null;
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mProgress.setVisibility(View.VISIBLE);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private Observer<? super ArrayList<WeatherBean.DataEntity.ForecastEntity>> observer = new Observer<ArrayList<WeatherBean.DataEntity.ForecastEntity>>() {
        @Override
        public void onCompleted() {
            mProgress.setVisibility(View.GONE);
            mWeatherLayout.setVisibility(View.VISIBLE);
            mHasLoadedOnce = true;
            if (mCityTrue)
                SpUtils.getInstance(getActivity()).put("weather_city", mEt);
        }

        @Override
        public void onError(Throwable e) {
            mProgress.setVisibility(View.GONE);
            Toast.makeText(getActivity(), getString(R.string.error_date), Toast.LENGTH_SHORT).show();
            mHasNet = false;
            mHasLoadedOnce = false;
        }

        @Override
        public void onNext(ArrayList<WeatherBean.DataEntity.ForecastEntity> forecastEntities) {
            if (forecastEntities == null) {
                return;
            }
            mCity.setText(mEt);
            WeatherBean.DataEntity.ForecastEntity forecastEntity = forecastEntities.get(0);
            mToday.setText(forecastEntity.getDate());
            mWeatherImage.setImageResource(WeatherUtils.getWeatherImage(forecastEntity.getType()));
            mWeatherTemp.setText(forecastEntity.getHigh() + forecastEntity.getLow());
            mWind.setText(forecastEntity.getFengxiang());
            mWeather.setText(forecastEntity.getType());
            initRecyclerView(forecastEntities);
            mCityTrue = true;
        }
    };

    private WeatherAdapter adapter;
    private ArrayList<WeatherBean.DataEntity.ForecastEntity> mData;

    private void initRecyclerView(ArrayList<WeatherBean.DataEntity.ForecastEntity> list) {
        if (adapter != null) {
            if (!mData.isEmpty()) {
                mData.clear();
            }
            mData.addAll(list);
            adapter.notifyDataSetChanged();
            return;
        }
        mData = new ArrayList<>();
        mData.addAll(list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mWeatherRecyclerview.setLayoutManager(manager);
        adapter = new WeatherAdapter(mData);
        mWeatherRecyclerview.setAdapter(adapter);
    }

    /**
     * 取消订阅
     */
    private void unSubscribe() {
        if (subscribe != null && !subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        unSubscribe();
    }

    @OnClick(R.id.weather_search)
    public void onClick() {
        mEt = mWeatherEt.getText().toString().trim();
        if ("".equals(mEt)) {
            Snackbar.make(mWeatherEt, getString(R.string.weather_hint), Snackbar.LENGTH_SHORT).show();
            return;
        }
        loadWeather();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mWeatherEt.getWindowToken(), 0); //强制隐藏键盘
    }
}
