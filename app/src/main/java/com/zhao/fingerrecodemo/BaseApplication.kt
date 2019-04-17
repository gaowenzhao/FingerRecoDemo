package com.zhao.fingerrecodemo

import android.app.Application

class BaseApplication :Application(){
    override fun onCreate() {
        super.onCreate()
//        ForegroundCallback().init(this)
    }
}