package com.example.sunnyweather.ui.nowData;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.example.sunnyweather.LogUtil;
import com.example.sunnyweather.R;
import com.example.sunnyweather.SunnyWeatherApplication;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class NowResponseBindingAdapter {

    @BindingAdapter("temperature")
    public static void setTemperature(TextView tempText,String temperature){
        LogUtil.v(SunnyWeatherApplication.TestToken,"setTemperature");
        tempText.setText(temperature+"℃");
    }

    @BindingAdapter("background")
    public static void setBackground(ImageView background,String weather){
        LogUtil.v(SunnyWeatherApplication.TestToken,"setBackground");
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        Date curTime=new Date(System.currentTimeMillis());
        String time=simpleDateFormat.format(curTime);
        Boolean beforeNight=time.compareTo("18:00:00")>=0;
        if (weather!=null){
                if (weather.equals("晴间多云")){
                    background.setBackgroundResource(beforeNight ? R.drawable.bg_partly_cloudy_day:R.drawable.bg_partly_cloudy_night);
                }
                else if (weather.equals("晴")){
                    background.setBackgroundResource(beforeNight ?
                            R.drawable.bg_clear_day :R.drawable.bg_clear_night);
                }
                else if (weather.equals("多云")){
                    background.setBackgroundResource(R.drawable.bg_cloudy);
                }
                else if (weather.contains("雾")){
                    background.setBackgroundResource(R.drawable.bg_fog);
                }else if (weather.contains("雨")){
                    background.setBackgroundResource(R.drawable.bg_rain);
                }else if (weather.contains("风")){
                    background.setBackgroundResource(R.drawable.bg_wind);
                }else if (weather.contains("雪")){
                    background.setBackgroundResource(R.drawable.bg_snow);
                }else {
                    background.setBackgroundResource(R.drawable.bg_place);
                }
        }else {
                background.setBackgroundResource(R.drawable.bg_place);
        }

    }
}
