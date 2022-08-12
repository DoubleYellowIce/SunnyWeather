package com.sunnyweather.main;

import static data.weather.model.SkyKt.getSky;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.sunnyweather.R;
import com.sunnyweather.SunnyWeatherApplication;

import data.weather.json.WeatherForecast;
import data.weather.model.Sky;
import utils.LogUtil;


/**
 * @author doubleyellowice
 */
public class MainBindingAdapter {


    @BindingAdapter("background")
    public static void setBackground(ImageView background, String weatherCode) {
        LogUtil.v(SunnyWeatherApplication.TestToken, "setBackground");
        if (weatherCode != null) {
            background.setBackgroundResource(getSky(weatherCode).getBg());
        }
    }

    @BindingAdapter("forecastItem")
    public static void addForecastItems(LinearLayout forecastLayout, WeatherForecast.Result result) {
        LogUtil.v(SunnyWeatherApplication.TestToken, "addForecastItems");
        if (result != null) {
            forecastLayout.removeAllViews();
            for (WeatherForecast.Daily daily : result.getDaily()) {
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
                String tempText = daily.getLow() + "~" + daily.getHigh() + "℃";
                temperatureInfo.setText(tempText);
                forecastLayout.addView(view);
            }

        }
    }
}
