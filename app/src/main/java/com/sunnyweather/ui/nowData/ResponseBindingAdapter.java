package com.sunnyweather.ui.nowData;

import static data.Weather.SkyKt.getSky;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.sunnyweather.LogUtil;
import com.sunnyweather.R;
import com.sunnyweather.SunnyWeatherApplication;

import data.Weather.DailyResponse;
import data.Weather.Sky;


public class ResponseBindingAdapter {

    @BindingAdapter("temperature")
    public static void setTemperature(TextView tempText,String temperature){
        LogUtil.v(SunnyWeatherApplication.TestToken,"setTemperature");
        tempText.setText(temperature+"℃");
    }

    @BindingAdapter("background")
    public static void setBackground(ImageView background,String weather){
        LogUtil.v(SunnyWeatherApplication.TestToken,"setBackground");
        if (weather!=null){
            background.setBackgroundResource(getSky(weather).getBg());
        }
    }

    @BindingAdapter("forecastItem")
    public static void addForecastItems(LinearLayout forecastLayout, DailyResponse.Result result){
        LogUtil.v(SunnyWeatherApplication.TestToken,"addForecastItems");
        if (result!=null){
                forecastLayout.removeAllViews();
                for (DailyResponse.Daily daily:result.getDaily()){
                        View view = LayoutInflater.from(SunnyWeatherApplication.context).inflate(R.layout.forecast_item,
                                forecastLayout, false);
                        TextView dateInfo = view.findViewById(R.id.dateInfo);
                        ImageView skyIcon = view.findViewById(R.id.skyIcon);
                        TextView skyInfo = view.findViewById(R.id.skyInfo);
                        TextView temperatureInfo = view.findViewById(R.id.temperatureInfo);
                        dateInfo.setText(daily.getDate());
                        Sky sky = getSky(daily.getCode_day());
                        skyIcon.setImageResource(sky.getIcon());
                        skyInfo.setText(daily.getText_day());
                        String tempText = daily.getLow()+"~"+daily.getHigh()+"℃";
                        //daily.low.toInt() ~ ${daily.high.toInt()} ℃"
                        temperatureInfo.setText(tempText);
                        forecastLayout.addView(view);
                }

    }
}
}
