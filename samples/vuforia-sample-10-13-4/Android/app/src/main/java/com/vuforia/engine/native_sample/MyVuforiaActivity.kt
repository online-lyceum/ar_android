package com.vuforia.engine.native_sample

import android.os.Bundle
import com.vuforia.engine.native_sample.gson.UserJson
import com.vuforia.engine.native_sample.retrofit.RetrofitManager
import com.vuforia.engine.native_sample.util.BeaconHelper

class MyVuforiaActivity: VuforiaActivity() {
    private lateinit var userName: String
    private lateinit var jobTitle: String
    private lateinit var lastUserPosition: Pair<Double, Double>
    private var lastUserAzimuth: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.extras?.let {
            if(it.containsKey(INTENT_KEY_USER_NAME) && it.containsKey(INTENT_KEY_JOB_TITLE)) {
                userName = it.getString(INTENT_KEY_USER_NAME)!!
                jobTitle = it.getString(INTENT_KEY_JOB_TITLE)!!
            }
        }

        BeaconHelper.initFields(this)
        BeaconHelper.configureBeacons()

        BeaconHelper.startScan(this, { x, y ->
            sendToServer(x, y, lastUserAzimuth)
        }) { azimuth ->
            sendToServer(lastUserPosition.first, lastUserPosition.second, azimuth)
        }
    }

    override fun onStop() {
        super.onStop()
        BeaconHelper.stopScan()
    }

    private fun sendToServer(x: Double, y: Double, azimuth: Float) {
        val user = UserJson(userName, "$x $y $azimuth", jobTitle)
        RetrofitManager.putUser(user) {}
    }
}