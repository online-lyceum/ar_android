package com.vuforia.engine.native_sample.util

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.mrx.indoorservice.api.IndoorService
import com.mrx.indoorservice.domain.model.BeaconsEnvironmentInfo
import com.mrx.indoorservice.domain.model.EnvironmentInfo
import com.mrx.indoorservice.domain.model.StateEnvironment
import org.altbeacon.beacon.BeaconManager
import com.mrx.indoorservice.domain.model.Point

object BeaconHelper {
    private lateinit var beaconManager: BeaconManager
    private lateinit var indoorService: IndoorService

    private val beaconsPositions = listOf(
        StateEnvironment("E4:C1:3F:EF:49:D7", Point(0.0, 10.0)),
        StateEnvironment("D3:81:75:66:79:B8", Point(0.0, 0.0)),
        StateEnvironment("CF:CA:06:0F:D0:F9", Point(10.0, 0.0)),
        StateEnvironment("E6:96:DA:5C:82:59", Point(10.0, 10.0))
    )

    private val beaconsDistances = mutableMapOf<String, Double>()



    fun initFields(context: Context) {
        beaconManager = BeaconManager.getInstanceForApplication(context)
        indoorService = IndoorService.getInstance(context)
    }

    fun configureBeacons() {
        indoorService.Position.setEnvironment(beaconsPositions)
    }

    fun startScan(
        lifecycleOwner: LifecycleOwner,
        positionListener: (Double, Double) -> Unit,
        azimuthListener: (Float) -> Unit,
    ) {
        indoorService.BeaconsEnvironment.getRangingViewModel().observe(lifecycleOwner) { beacons ->
            beacons.forEach { beacon ->
                beaconsDistances[beacon.beaconId] = beacon.distance
            }

            if(beacons.size>2) {
                val userPosition = indoorService.Position.getPosition(beaconsDistances.map { EnvironmentInfo(it.key, it.value) })
                    .position
                val convertedPosition = convertToMeters(userPosition.x, userPosition.y)
                positionListener(convertedPosition.first, convertedPosition.second)
            }
        }

        // Азимут: Подписались на обновление данных и выводим на текстовое поле
        indoorService.AzimuthManager.getAzimuthViewModel().observe(lifecycleOwner) {
            azimuthListener(it)
        }

        // Запустили обнаружение маячков и прослушивание сенсора
        indoorService.BeaconsEnvironment.startRanging()
        indoorService.AzimuthManager.startListen()
    }

    fun stopScan() {
        indoorService.BeaconsEnvironment.stopRanging()
        indoorService.AzimuthManager.stopListen()
    }

    private fun convertToMeters(x: Double, y: Double): Pair<Double, Double> {
        val newX = (x-2.0)*12/6+1
        val newY = (y-3.0)*12/4+1
        return newX to newY
    }
}