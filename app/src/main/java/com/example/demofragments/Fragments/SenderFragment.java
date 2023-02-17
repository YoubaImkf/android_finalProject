package com.example.demofragments.Fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.demofragments.Adapters.ContactAdapter;
import com.example.demofragments.Adapters.ResponseAdapter;
import com.example.demofragments.Models.Contact;
import com.example.demofragments.R;

import java.util.ArrayList;
import java.util.List;

public class SenderFragment extends Fragment {
    private Spinner responsesSpinner;
    private Spinner contactsSpinner;

    public SenderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sender, container, false);

        // Find the spinner views in the layout and set their adapters
        responsesSpinner = view.findViewById(R.id.responses_spinner);
        contactsSpinner = view.findViewById(R.id.contacts_spinner);

        getParentFragmentManager().setFragmentResultListener("SELECTED_CONTACTS", this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {

                        if (requestKey.equals("SELECTED_CONTACTS")) {
                            ArrayList<String> selectedContacts = bundle.getStringArrayList("SELECTED_CONTACTS");
                            Log.d("SenderFragment", "Received selectedContacts: " + selectedContacts);

                            if (selectedContacts != null && selectedContacts.size() > 0) {
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, selectedContacts);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                contactsSpinner.setAdapter(adapter);
                            }
                        }else{
                            Toast.makeText(getContext(), "not working", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


//        ResponseAdapter responseAdapter = new ResponseAdapter(responses);
//        responsesSpinner.setAdapter((SpinnerAdapter) responseAdapter);
//
//        ContactAdapter contactAdapter = new ContactAdapter(contacts, getContext());
//        contactsSpinner.setAdapter(contactAdapter);
//
//        // Find the send button view in the layout and set its click listener
//        sendButton = view.findViewById(R.id.send_button);
//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sendSelectedMessage();
//            }
//        });

        return view;
    }


//    private void sendSelectedMessage() {
//        // Get the selected response and contact
//        String selectedResponse = responsesSpinner.getSelectedItem().toString();
//        Contact selectedContact = (Contact) contactsSpinner.getSelectedItem();
//
//        // Create an Intent to send an SMS message
//        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + selectedContact.getPhoneNumber()));
//        smsIntent.putExtra("sms_body", selectedResponse);
//
//        // Verify that the device has a messaging app installed
//        PackageManager packageManager = requireContext().getPackageManager();
//        List<ResolveInfo> activities = packageManager.queryIntentActivities(smsIntent, 0);
//        boolean isIntentSafe = activities.size() > 0;
//
//        // Start the messaging app to send the message
//        if (isIntentSafe) {
//            startActivity(smsIntent);
//        } else {
//            Toast.makeText(getContext(), "No messaging app installed", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//    public void setResponses(ArrayList<String> responses) {
//        this.responses = responses;
//    }
//
//    public void setContacts(ArrayList<Contact> contacts) {
//        this.contacts = contacts;
//    }
}
