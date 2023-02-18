package com.example.demofragments.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.demofragments.Adapters.ResponseAdapter;
import com.example.demofragments.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

// SOURCES :
// First course project - AndroidStudioProjects\MyApp
// Alert dialog : https://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
// https://developer.android.com/training/data-storage/shared-preferences?hl=fr
public class ResponsesFragment extends Fragment {

    private ArrayList<String> responses = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    public ResponsesFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true); // make the options appear

        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        String responsesJson = sharedPreferences.getString("responses", "");

        if(!responsesJson.equals("")) {
            Gson gson = new Gson();
            String[] responsesArray = gson.fromJson(responsesJson, String[].class);
            responses = new ArrayList<>(java.util.Arrays.asList(responsesArray));
        } else {
            responses = new ArrayList<>();
            responses.add("Hello, how are you?");
            responses.add("I'm good, thanks for asking.");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_responses, container, false);

        // Find the RecyclerView view in the layout and set its layout manager and adapter
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ResponseAdapter adapter = new ResponseAdapter(responses);
        adapter.setOnDeleteClickListener(new ResponseAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int position) {
                responses.remove(position);
                adapter.notifyItemRemoved(position);
                saveResponses();
            }
        });

        recyclerView.setAdapter(adapter);


        Button addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddResponseDialog();
            }
        });

        return view;

    }

    private void showAddResponseDialog() {
        // Create a new EditText view for user input
        final EditText responseEditText = new EditText(getActivity());

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Add a custom response")
                .setMessage("Enter your custom response:")
                .setView(responseEditText) // Add the EditText view to the dialog

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String response = responseEditText.getText().toString(); // get user input
                        responses.add(response);
                        RecyclerView recyclerView = requireView().findViewById(R.id.recycler_view);
                        Objects.requireNonNull(recyclerView.getAdapter()).notifyItemInserted(responses.size() - 1);
                        saveResponses();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }



    private void saveResponses(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String responsesJson = gson.toJson(responses.toArray(new String[0]));
        editor.putString("responses", responsesJson);
        editor.apply();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }




}