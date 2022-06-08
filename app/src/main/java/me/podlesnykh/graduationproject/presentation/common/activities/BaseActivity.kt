package me.podlesnykh.graduationproject.presentation.common.activities

import androidx.appcompat.app.AppCompatActivity
import me.podlesnykh.graduationproject.presentation.application.GraduationProjectApplication

open class BaseActivity : AppCompatActivity() {
    val appComponent get() = (application as GraduationProjectApplication).appComponent
}