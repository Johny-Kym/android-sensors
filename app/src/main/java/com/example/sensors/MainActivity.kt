package com.example.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private val TAG = "MainActivity"
    var accelerometerSensor: Sensor? = null
    private var xSide: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "OnCreate-- Initializing Sensor service")
        //init sensor service
        setUpSensorStuff()
        setContent {

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center


            ) {
                Row(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(25.dp)
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {

                    Text(text = "X: ")
                    Text(text = "Y: ")
                    Text(text = "Z:")


                }

            }

        }
    }

    private fun setUpSensorStuff() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        Log.d(TAG, deviceSensors.toString())
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        //register for accelerometer sensor event
        if (accelerometerSensor != null) {
            sensorManager.registerListener(
                this,
                accelerometerSensor,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST,

                )
            Log.d(TAG, "Event registered")
        } else {

            Log.e(TAG, "sensor not available")
        }


    }

    override fun onSensorChanged(event: SensorEvent?) {
        //listen for accelerometer sensor event
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            Log.d(TAG, "x: ${event.values[0]} Y:${event.values[1]} Z: ${event.values[2]}")
        }

    }

    override fun onAccuracyChanged(event: Sensor?, p1: Int) {
        return
    }

    override fun onDestroy() {
        //prevent memory leak
        sensorManager.unregisterListener(this)
        super.onDestroy()
    }
}

