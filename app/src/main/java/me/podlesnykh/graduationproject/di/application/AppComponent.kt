package me.podlesnykh.graduationproject.di.application

import android.app.Application
import dagger.Component

@ApplicationScope
@Component(modules = [AppModule::class])
interface AppComponent