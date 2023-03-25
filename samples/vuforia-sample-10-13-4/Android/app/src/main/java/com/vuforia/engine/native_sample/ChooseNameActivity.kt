/*===============================================================================
Copyright (c) 2020 PTC Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other
countries.
===============================================================================*/

package com.vuforia.engine.native_sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vuforia.engine.native_sample.databinding.ActivityChooseNameBinding


class ChooseNameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonContinue.setOnClickListener {
            if(binding.editChooseName.text.isNotEmpty()) {
                startActivity(Intent(this, VuforiaActivity::class.java))
            }
        }
    }

    companion object {

        // Used to load the 'VuforiaSample' library on application startup.
        init {
            System.loadLibrary("VuforiaSample")
        }
    }
}
