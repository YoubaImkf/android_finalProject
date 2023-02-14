package com.example.demofragments.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.demofragments.R;

public class FragmentB extends Fragment {
    private static String TEXT_KEY = "TEXT_KEY";

    private TextView textView;
    public FragmentB(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_b, container, false);

        // Get the TextView from the layout
        textView = result.findViewById(R.id.text_view);

        // Set the text value to the previously saved value, or to default.
        if (getArguments() != null){
            textView.setText(getArguments().getString(TEXT_KEY,"No text yet"));
        }


        // Listener for the Fragment modification
        getParentFragmentManager().setFragmentResultListener("TEXT_UPDATE", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // Get the string from the bundle received by the FragmentResult call
                String textA = bundle.getString("TEXT_VALUE","No text Yet");

                // Set the text
                textView.setText(textA);

                // Save the string value to the arguments bundle using setArguments()
                Bundle args = new Bundle();
                args.putString(TEXT_KEY, textA);
                setArguments(args);
            }
        });

        return result;
    }}


