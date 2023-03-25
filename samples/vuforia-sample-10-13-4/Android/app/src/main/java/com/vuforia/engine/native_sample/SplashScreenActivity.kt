/*===============================================================================
Copyright (c) 2020 PTC Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other
countries.
===============================================================================*/

package com.vuforia.engine.native_sample

import android.os.Bundle
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.vuforia.engine.native_sample.databinding.ActivitySplashScreenBinding


/**
 * Splash screen activity shows for a short time before moving to the MainActivity.
 */
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        Thread{
            Thread.sleep(4000)
            runOnUiThread {
                startActivity(Intent(this, GuideActivity::class.java))
            }
        }.start()
    }
}
