/*===============================================================================
Copyright (c) 2020 PTC Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other
countries.
===============================================================================*/

package com.exception.ufofind.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.exception.dialog.NoPermissionDialogFragment
import com.exception.ufofind.R
import com.exception.ufofind.databinding.ActivityChooseNameBinding
import com.exception.ufofind.viewmodel.ChooseNameViewModel
import com.vuforia.engine.native_sample.INTENT_KEY_JOB_TITLE
import com.vuforia.engine.native_sample.INTENT_KEY_USER_NAME


class ChooseNameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseNameBinding
    private lateinit var viewModel: ChooseNameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ChooseNameViewModel.ChooseNameViewModelFactory(application))[ChooseNameViewModel::class.java]
        setContentView(R.layout.activity_download)
        if (viewModel.permissionsChecked) {
            onAllPermissionsGranted()
        }
        else {
            checkPermissions()
        }
    }

    private fun checkPermissions() {
        val allPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            listOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.BLUETOOTH_SCAN)
        }
        else {
            listOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        val permissionsToCheck = mutableListOf<String>()
        allPermissions.forEach {
            if (ActivityCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED) {
                permissionsToCheck.add(it)
            }
        }
        if (permissionsToCheck.isEmpty()) {
            onAllPermissionsGranted()
            return
        }
        else {
            ActivityCompat.requestPermissions(this, permissionsToCheck.toTypedArray(), 0)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty()) {
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    showNoPermissionDialog()
                    return
                }
            }
            viewModel.permissionsChecked = true
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
            if (binding.editChooseName.text.isNotEmpty() && binding.editChooseJobTitle.text.isNotEmpty()) {
                startAllUsersActivity(binding.editChooseName.text.toString(), binding.editChooseJobTitle.text.toString())
            }
            else Toast.makeText(this, R.string.fields_can_not_be_empty, Toast.LENGTH_LONG).show()
        }
    }

    private fun showNoPermissionDialog() {
        val dialog = NoPermissionDialogFragment()
        dialog.show(supportFragmentManager, "noPermissionDialog")
    }

    private fun startAllUsersActivity(name: String, jobTitle: String) {
        val intent = Intent(this, AllUsersActivity::class.java)
        intent.putExtra(INTENT_KEY_USER_NAME, name).putExtra(INTENT_KEY_JOB_TITLE, jobTitle)
        startActivity(intent)
    }
}
