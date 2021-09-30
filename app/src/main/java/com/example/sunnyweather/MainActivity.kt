package com.example.sunnyweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.example.sunnyweather.databinding.ActivityMainBinding
import com.example.sunnyweather.logic.model.getSky
import com.example.sunnyweather.ui.nowData.NowDataViewModel


class MainActivity : AppCompatActivity(){

    private lateinit var nowViewModel: NowDataViewModel
    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var forecastLayout:LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initial()
        setStatusBarTransparent()
    }

    private fun initial(){

        nowViewModel=ViewModelProvider(this).get(NowDataViewModel::class.java)
        lifecycle.addObserver(nowViewModel)

        dataBinding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        dataBinding.lifecycleOwner = this
        dataBinding.nowViewModel=nowViewModel
        forecastLayout=findViewById<LinearLayout>(R.id.forecastLayout)
    }

    private fun setStatusBarTransparent(){

            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

    }

    override fun onResume() {

        LogUtil.v(SunnyWeatherApplication.TestToken,"onResume")
        super.onResume()

        nowViewModel.nowData.observe(this){
            if (it!=null){
                dataBinding.apply {
                    temperature=it.now.temperature
                    weather=it.now.text
                    code=it.now.code
                    location=it.location.name
                }

            }
        }

        nowViewModel.dailyData.observe(this){
            LogUtil.v(SunnyWeatherApplication.TestToken,"nowViewModel.dailyData.observe")
            if (it!=null){
                dataBinding.dailyResponse=it
            }
        }

        nowViewModel.suggestionData.observe(this){
            LogUtil.v(SunnyWeatherApplication.TestToken,"nowViewModel.suggestionData.observe")
            if (it!=null){
                dataBinding.apply {
                    carWashing=it.suggestion.car_washing.brief
                    uv=it.suggestion.uv.brief
                    flu=it.suggestion.flu.brief
                    dressing=it.suggestion.dressing.brief
                }

            }
        }

    }

}