/*===============================================================================
Copyright (c) 2020 PTC Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other
countries.
===============================================================================*/

package com.vuforia.engine.native_sample

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.vuforia.engine.native_sample.databinding.ActivityChooseNameBinding
import com.vuforia.engine.native_sample.dialog.NoPermissionDialogFragment
import com.vuforia.engine.native_sample.gson.UserJson


class ChooseNameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
        checkPermissions()
    }

    private fun checkPermissions() {
        val allPermissions = if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.S) {
            listOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.BLUETOOTH_SCAN
            )
        }
        else {
            listOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }

        val permissionsToCheck = mutableListOf<String>()
        allPermissions.forEach {
            if(ActivityCompat.checkSelfPermission(this, it)!=PackageManager.PERMISSION_GRANTED) {
                permissionsToCheck.add(it)
            }
        }
        if(permissionsToCheck.isEmpty()) {
            onAllPermissionsGranted()
            return
        }

        ActivityCompat.requestPermissions(
            this,
            permissionsToCheck.toTypedArray(),
            0
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.isNotEmpty()){
            for(result in grantResults) {
                if(result!=PackageManager.PERMISSION_GRANTED) {
                    showNoPermissionDialog()
                    return
                }
            }
            onAllPermissionsGranted()
        }
        else {
            showNoPermissionDialog()
        }
    }

    private fun onAllPermissionsGranted() {
        binding = ActivityChooseNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonContinue.setOnClickListener {
            if(binding.editChooseName.text.isNotEmpty() && binding.editChooseJobTitle.text.isNotEmpty()) {
                startMyVuforiaActivity(binding.editChooseName.text.toString(), binding.editChooseJobTitle.text.toString())
            }
            else Toast.makeText(this, R.string.fields_can_not_be_empty, Toast.LENGTH_LONG).show()
        }
    }

    private fun showNoPermissionDialog() {
        val dialog = NoPermissionDialogFragment()
        dialog.show(supportFragmentManager, "noPermissionDialog")
    }

    private fun startMyVuforiaActivity(name: String, jobTitle: String) {
        val intent = Intent(this, MyVuforiaActivity::class.java)
        intent.putExtra(INTENT_KEY_USER_NAME, name)
            .putExtra(INTENT_KEY_JOB_TITLE, jobTitle)
        startActivity(intent)
    }

    companion object {
        private const val AMOUNT_PERMISSIONS_AFTER_S = 3
        private const val AMOUNT_PERMISSIONS_BEFORE_S = 2
        // Used to load the 'VuforiaSample' library on application startup.
        init {
            System.loadLibrary("VuforiaSample")
        }
    }
}
