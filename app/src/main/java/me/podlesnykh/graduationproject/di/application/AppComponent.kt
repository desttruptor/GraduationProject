package me.podlesnykh.graduationproject.di.application

import dagger.Component
import me.podlesnykh.graduationproject.presentation.viewmodels.ArticlesViewModel

@ApplicationScope
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(viewModel: ArticlesViewModel)
}