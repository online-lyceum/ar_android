/*===============================================================================
Copyright (c) 2020 PTC Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other
countries.
===============================================================================*/

package com.vuforia.engine.native_sample

import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.vuforia.engine.native_sample.databinding.ActivityChooseNameBinding
import com.vuforia.engine.native_sample.dialog.NoPermissionDialogFragment
import com.vuforia.engine.native_sample.gson.UserJson
import com.vuforia.engine.native_sample.retrofit.RetrofitManager


class ChooseNameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(!isLocationPermissionGranted()) {
            val dialog = NoPermissionDialogFragment()
            dialog.show(supportFragmentManager, "noPermissionDialog")
        }

        binding = ActivityChooseNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonContinue.setOnClickListener {
            if(binding.editChooseName.text.isNotEmpty()) {
                setContentView(R.layout.activity_download)
                RetrofitManager.putUser(UserJson(binding.editChooseName.text, )){
                    if(it!=null) startActivity(Intent(this, VuforiaActivity::class.java))
                }
                startActivity(Intent(this, VuforiaActivity::class.java))
            }
        }
    }

    private fun isLocationPermissionGranted(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                0
            )
            false
        } else {
            true
        }
    }

    private fun generateUserObj(name: String, lat: Float, lon: Float, jobTitle: String)
        = UserJson(name, "$lat $lon", jobTitle)


    companion object {

        // Used to load the 'VuforiaSample' library on application startup.
        init {
            System.loadLibrary("VuforiaSample")
        }
    }
}
