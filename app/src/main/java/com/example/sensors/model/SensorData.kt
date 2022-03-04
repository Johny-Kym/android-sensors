package com.example.sensors.model

import kotlinx.coroutines.flow.MutableStateFlow

data class SensorData(
    val xAxis: Float = 0f,
    val yAxis: Float = 0f,
     val zAxis: Float = 0f,
)

class rememberSensordata(
    sensorData: SensorData = SensorData()
) {

    val latestData = MutableStateFlow(sensorData)

    fun updateData(newData: SensorData) {
        latestData.value = newData
    }
}
