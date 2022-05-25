package me.podlesnykh.graduationproject.presentation.activities

import android.os.Bundle
import me.podlesnykh.graduationproject.databinding.ActivityMainBinding
import me.podlesnykh.graduationproject.presentation.common.activities.BaseActivity
import me.podlesnykh.graduationproject.presentation.fragments.ArticlesFragment

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(binding.activityMainFragmentContainer.id, ArticlesFragment.newInstance())
            .commit()
    }
}