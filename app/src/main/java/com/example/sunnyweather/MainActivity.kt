package com.example.sunnyweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.example.sunnyweather.databinding.ActivityMainBinding
import com.example.sunnyweather.ui.nowData.NowDataViewModel

class MainActivity : AppCompatActivity(){

    private lateinit var nowViewModel: NowDataViewModel
    private lateinit var dataBinding: ActivityMainBinding

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

    }

    private fun setStatusBarTransparent(){

            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

    }

    override fun onResume() {

        LogUtil.v(SunnyWeatherApplication.TestToken,"onResume")
        super.onResume()
        nowViewModel.nowData.observe(this){
            if (it!=null){
                dataBinding.temperature=it.now.temperature
                dataBinding.weather=it.now.text
                dataBinding.location=it.location.name
            }
        }
    }

}