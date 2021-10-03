package io.github.isning.imaband.ui.fragments;

import android.Manifest;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.github.isning.imaband.R;
import io.github.isning.imaband.misc.Util;
import org.jetbrains.annotations.NotNull;

import static android.content.Context.SENSOR_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WatchFaceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WatchFaceFragment extends Fragment {

    private static final String ARG_FACE_LAYOUT = "face_layout";

    private int mFaceLayoutId;

    private SensorManager mSensorManager;

    private Sensor mHeartRateSensor;
    private final SensorEventListener mHRMSensorListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            String text = getString(R.string.main_face_heart_rate_text);
            if(sensorEvent.values[0] == 0) text = text.replace("{heart_rate}", "--");
            else text = text.replace("{heart_rate}", String.valueOf((int)sensorEvent.values[0]));
            mHRMTextView.setText(text);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };


    private Sensor mStepsSensor;
    private final SensorEventListener mStepsSensorListener  = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            String text = getString(R.string.main_face_steps_text);
            text = text.replace("{steps}", String.valueOf((int)sensorEvent.values[0]));
            mStepsTextView.setText(text);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    private TextView mHRMTextView;
    private TextView mStepsTextView;

    public WatchFaceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param faceLayoutId The id of requested watch face
     * @return A new instance of fragment WatchFaceFragment.
     */
    public static WatchFaceFragment newInstance(int faceLayoutId) {
        WatchFaceFragment fragment = new WatchFaceFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_FACE_LAYOUT, faceLayoutId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFaceLayoutId = getArguments().getInt(ARG_FACE_LAYOUT);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mFaceLayoutId == 0) throw new RuntimeException("mFaceLayoutId must be set");
        // Inflate the layout for this fragment
        return inflater.inflate(mFaceLayoutId, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHRMTextView = view.findViewById(R.id.main_face_heart_rate);
        if(mHRMTextView != null) {
            mSensorManager = ((SensorManager) this.requireActivity().getSystemService(SENSOR_SERVICE));
            mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
            if (mHeartRateSensor == null) {
                mHRMTextView.setVisibility(View.INVISIBLE);
                mHRMTextView.setText("");
            }
            else {
                if (!Util.checkPer(this.getActivity(), Manifest.permission.BODY_SENSORS)) {
                    ActivityCompat.requestPermissions(this.requireActivity(), new String[]{Manifest.permission.BODY_SENSORS}, 100);
                }
                mHRMTextView.setText(getString(R.string.main_face_heart_rate_text).replace("{heart_rate}", "--"));

                mSensorManager.registerListener(mHRMSensorListener, this.mHeartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }

        mStepsTextView = view.findViewById(R.id.main_face_steps);
        if(mStepsTextView != null) {
            mSensorManager = ((SensorManager) this.requireActivity().getSystemService(SENSOR_SERVICE));
            mStepsSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            if (mStepsSensor == null) {
                mStepsTextView.setVisibility(View.INVISIBLE);
                mStepsTextView.setText("");
            }
            else {
                if (!Util.checkPer(this.getActivity(), Manifest.permission.BODY_SENSORS)) {
                    ActivityCompat.requestPermissions(this.requireActivity(), new String[]{Manifest.permission.BODY_SENSORS}, 100);
                }
                mStepsTextView.setText(getString(R.string.main_face_steps_text).replace("{steps}", "--"));
            }
        }
        registerListeners();
    }

    private void registerListeners(){
        if(mHRMTextView != null & mHeartRateSensor != null) mSensorManager.registerListener(mHRMSensorListener, mHeartRateSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        if(mStepsTextView != null & mStepsSensor != null) mSensorManager.registerListener(mStepsSensorListener, mStepsSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void unregisterListeners(){
        if(mHRMTextView != null & mHeartRateSensor != null) mSensorManager.unregisterListener(mHRMSensorListener);
        if(mStepsTextView != null & mStepsSensor != null) mSensorManager.unregisterListener(mStepsSensorListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerListeners();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(mHRMSensorListener);
    }
}