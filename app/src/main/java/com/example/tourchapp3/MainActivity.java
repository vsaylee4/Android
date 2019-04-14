package com.example.tourchapp3;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity  implements  SensorEventListener {

    Sensor lightsensor;
    SensorManager sm;
    TextView textLIGHT_reading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textLIGHT_reading = (TextView) findViewById(R.id.LIGHT_reading);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightsensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void flashLightOn() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, true);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void flashLightOff() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStop() {
        super.onStop();
        flashLightOff();
    }

        @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener((SensorEventListener) this, lightsensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(MainActivity.this);
    }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onSensorChanged (SensorEvent event){

            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                textLIGHT_reading.setText(" Surrounding Light value :  " + event.values[0]);

                if (event.values[0] < 10) {
                    flashLightOn();
                } else {
                    flashLightOff();
                }
            }
        }

        @Override
        public void onAccuracyChanged (Sensor sensor,int i){
        }

}