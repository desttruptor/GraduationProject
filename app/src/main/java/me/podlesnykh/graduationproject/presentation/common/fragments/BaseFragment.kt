package me.podlesnykh.graduationproject.presentation.common.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import me.podlesnykh.graduationproject.di.application.AppComponent
import me.podlesnykh.graduationproject.presentation.activities.MainActivity

open class BaseFragment : Fragment() {
    protected lateinit var appComponent: AppComponent

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent = (activity as MainActivity).appComponent
    }
}