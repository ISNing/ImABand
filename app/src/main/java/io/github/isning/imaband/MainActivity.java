package io.github.isning.imaband;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;
import androidx.viewpager2.widget.ViewPager2;
import io.github.isning.imaband.databinding.ActivityMainBinding;
import io.github.isning.imaband.ui.fragments.MainChoiceFragment;
import io.github.isning.imaband.ui.fragments.WatchFaceFragment;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends FragmentActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final ViewPager2 mainViewPager = binding.mainViewPager;

        ArrayList<Fragment> fragmentList = new ArrayList<>();

        String[] texts = getResources().getStringArray(R.array.main_choices_text);
        String[] icons = getResources().getStringArray(R.array.main_choices_icon);
        String[] activities = getResources().getStringArray(R.array.main_choices_activity);
        if (texts.length != icons.length | texts.length != activities.length) throw new RuntimeException("Bad Data");
        fragmentList.add(WatchFaceFragment.newInstance(getResources().getIdentifier(PreferenceManager.getDefaultSharedPreferences(this).getString("appearance_watch_face_id", getResources().getStringArray(R.array.p_appearance_watch_face_values)[0]), "layout", "io.github.isning.imaband")));
        for (int i = 0; i < texts.length; i++) {
            fragmentList.add(MainChoiceFragment.newInstance(getResources().getIdentifier(icons[i], "drawable", "io.github.isning.imaband"), texts[i], activities[i]));
        }

        BaseFragmentStateAdapter stateAdapter = new BaseFragmentStateAdapter(this, fragmentList);
        mainViewPager.setAdapter(stateAdapter);
    }
}