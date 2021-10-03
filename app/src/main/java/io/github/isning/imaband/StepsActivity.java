package io.github.isning.imaband;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import io.github.isning.imaband.databinding.ActivityHeartRateBinding;
import io.github.isning.imaband.databinding.ActivityStepsBinding;

public class StepsActivity extends AppCompatActivity {

    private static final String TAG = "StepsActivity";

    private ActivityStepsBinding binding;

    private SensorManager mSensorManager;
    private Sensor mStepsSensor;
    private SensorEventListener mSensorEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStepsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mSensorManager = ((SensorManager)getSystemService(SENSOR_SERVICE));
        mStepsSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(mStepsSensor == null) throw new RuntimeException("HRM Sensor not found");

        mSensorEventListener = new SensorEventListener(){

            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                String text = getString(R.string.steps_text);
                text = text.replace("{steps}", String.valueOf((int)sensorEvent.values[0]));
                binding.stepsValue.setText(text);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };

        mSensorManager.registerListener(mSensorEventListener, this.mStepsSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mSensorManager.unregisterListener(mSensorEventListener);
    }

}