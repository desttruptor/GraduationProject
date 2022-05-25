package me.podlesnykh.graduationproject

import android.app.Application
import me.podlesnykh.graduationproject.di.application.AppComponent
import me.podlesnykh.graduationproject.di.application.AppModule
import me.podlesnykh.graduationproject.di.application.DaggerAppComponent

class GraduationProjectApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}