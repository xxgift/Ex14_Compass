package com.egco428.ex14_compass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var sensorService: SensorManager? = null
    private var compassView: CompassView? = null
    private var sensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compassView = CompassView(this);
        setContentView(compassView);

        sensorService = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorService!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if (sensor != null) {
            sensorService!!.registerListener(mySensorEventListener, sensor,
                SensorManager.SENSOR_DELAY_NORMAL);
            Log.i("Compass MainActivity", "Registerered for ORIENTATION Sensor");

        } else {
            Log.e("Compass MainActivity", "Registerered for ORIENTATION Sensor");
            Toast.makeText(this, "ORIENTATION Sensor not found",
                Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private val mySensorEventListener = object : SensorEventListener {

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

        }

        override fun onSensorChanged(event: SensorEvent?) {
            // angle between the magnetic north direction
            // 0=North, 90=East, 180=South, 270=West
            val azimuth = event!!.values[0]
            compassView!!.updateData(azimuth)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (sensor != null) {
            sensorService!!.unregisterListener(mySensorEventListener)
        }
    }

}
