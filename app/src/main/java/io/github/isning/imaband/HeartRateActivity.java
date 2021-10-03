package io.github.isning.imaband;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import io.github.isning.imaband.databinding.ActivityHeartRateBinding;

public class HeartRateActivity extends AppCompatActivity {

    private static final String TAG = "HeartRateActivity";

    private ActivityHeartRateBinding binding;

    private SensorManager mSensorManager;
    private Sensor mHeartRateSensor;
    private SensorEventListener mSensorEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHeartRateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mSensorManager = ((SensorManager)getSystemService(SENSOR_SERVICE));
        mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        if(mHeartRateSensor == null) throw new RuntimeException("HRM Sensor not found");
        Animator anim = AnimatorInflater.loadAnimator(this,R.animator.heartbeat);
        anim.setTarget(binding.heartRateImage);
        anim.start();

        mSensorEventListener = new SensorEventListener(){

            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                String text = getString(R.string.heart_rate_text);
                if(sensorEvent.values[0] == 0) text = text.replace("{heart_rate}", "--");
                else text = text.replace("{heart_rate}", String.valueOf((int)sensorEvent.values[0]));
                binding.heartRateValue.setText(text);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };

        mSensorManager.registerListener(mSensorEventListener, this.mHeartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mSensorManager.unregisterListener(mSensorEventListener);
    }

}