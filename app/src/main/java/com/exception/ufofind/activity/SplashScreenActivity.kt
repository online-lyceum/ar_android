/*===============================================================================
Copyright (c) 2020 PTC Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other
countries.
===============================================================================*/

package com.exception.ufofind.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.exception.ufofind.databinding.ActivitySplashScreenBinding
import com.exception.ufofind.viewmodel.SplashScreenViewModel


class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var viewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, SplashScreenViewModel.SplashScreenViewModelFactory(application))[SplashScreenViewModel::class.java]
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel.liveDataCanContinue.observe(this) {
            if (it) {
                startActivity(Intent(this@SplashScreenActivity, GuideActivity::class.java))
            }
        }

    }
}
