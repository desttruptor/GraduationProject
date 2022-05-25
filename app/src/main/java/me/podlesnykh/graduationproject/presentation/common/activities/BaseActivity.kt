package me.podlesnykh.graduationproject.presentation.common.activities

import androidx.appcompat.app.AppCompatActivity
import me.podlesnykh.graduationproject.GraduationProjectApplication

open class BaseActivity : AppCompatActivity() {
    protected val appComponent get() = (application as GraduationProjectApplication).appComponent
}