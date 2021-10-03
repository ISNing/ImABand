package io.github.isning.imaband.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.github.isning.imaband.R;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link Fragment} subclass, used for main page function choosing.
 * Use the {@link MainChoiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainChoiceFragment extends Fragment {

    private static final String ARG_FUNC_ICON = "func_icon";
    private static final String ARG_FUNC_TEXT = "func_text";
    private static final String ARG_TGT_ACTIVITY = "tgt_activity";

    private int mFuncIcon;
    private String mFuncText;
    // Required Intent name
    private String mTgtActivity;

    private ConstraintLayout mContainer;
    private ImageView mIconView;
    private TextView mTextView;

    public MainChoiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param icon Icon in bytearray
     * @param text Text of the choice
     * @param target_activity The intent name of target Activity
     * @return A new instance of fragment MainChoiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainChoiceFragment newInstance(int icon, String text, String target_activity) {
        MainChoiceFragment fragment = new MainChoiceFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_FUNC_ICON, icon);
        args.putString(ARG_FUNC_TEXT, text);
        args.putString(ARG_TGT_ACTIVITY, target_activity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFuncIcon = getArguments().getInt(ARG_FUNC_ICON);
            mFuncText = getArguments().getString(ARG_FUNC_TEXT);
            mTgtActivity = getArguments().getString(ARG_TGT_ACTIVITY);
        }
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContainer = view.findViewById(R.id.frag_main_choice_container);
        mIconView = mContainer.findViewById(R.id.frag_main_choice_icon);
        mTextView = mContainer.findViewById(R.id.frag_main_choice_text);
        updateUI();
    }

    public void updateUI() {
        mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mTgtActivity));
            }
        });
        mIconView.setImageResource(mFuncIcon);
        mTextView.setText(mFuncText);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_choice, container, false);
    }
}