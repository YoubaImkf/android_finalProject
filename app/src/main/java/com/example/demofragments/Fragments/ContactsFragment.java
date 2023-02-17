package com.example.demofragments.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.demofragments.Models.Contact;
import com.example.demofragments.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


// SOURCE :
// DEPRECATED PERMISSION : https://www.youtube.com/watch?v=oP-zXjkT0C0&ab_channel=GoogleDevelopersSpace
// https://stackoverflow.com/questions/29915919/permission-denial-opening-provider-com-android-providers-contacts-contactsprovi
// https://www.youtube.com/watch?v=xuMuNNyL85Y&ab_channel=NekoCode
// https://developer.android.com/training/permissions/requesting?hl=fr
public class ContactsFragment extends Fragment {
    private ListView listView;
    private ArrayAdapter<Contact> arrayAdapter;

    public ContactsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment layout
        View result = inflater.inflate(R.layout.fragment_contacts, container, false);

        // Find the list view
        //listView = (ListView) requireActivity().findViewById(R.id.list_view); // LA PUTAIN DE LIGNE DE TA CHEVRE la chevre ouais ...
        listView = result.findViewById(R.id.list_view);

        // Read and show the contacts
        showContacts();
        setHasOptionsMenu(true); // make the options appear

        return result;
    }


    /*
     * Show the contacts in the ListView.
     */
    private void showContacts() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.READ_CONTACTS) ==
                PackageManager.PERMISSION_GRANTED) {

            List<Contact> contacts = getContacts();
            arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_multiple_choice, contacts);
            listView.setAdapter(arrayAdapter);

        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(
                    Manifest.permission.READ_CONTACTS);

        }
    }



    // Register the permissions callback, which handles the user's response to the
    // system permissions dialog. Save the return value, an instance of
    // ActivityResultLauncher, as an instance variable.
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                    showContacts();
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // feature requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                    Toast.makeText(getContext(), "Until you grant the permission, we cannot display your contacts", Toast.LENGTH_SHORT).show();

                }
            });



    /*
     * Read the name/phone number of all the contacts.
     */
    @SuppressLint("Range")
    public List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();

        ContentResolver cr = requireActivity().getContentResolver();
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        // Check if the cursor has any data
        if (cursor != null && cursor.getCount() > 0) {
            // Iterate through the cursor
            while (cursor.moveToNext()) {

                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                // Create a new Contact object
                Contact contact = new Contact(name, phoneNumber);
                // Add the contact to the list of contacts
                contacts.add(contact);
            }
            cursor.close();
        }
        // Return the list of contacts
        return contacts;
    }


    /* -------------------------
     *     CheckBox Section
     * -------------------------
     *Source:
     *///https://stackoverflow.com/a/42338455/17003007
     // https://stackoverflow.com/questions/15653737/oncreateoptionsmenu-inside-fragments
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        //return super.onOptionsItemSelected(item);
//        int id = item.getItemId();
//
//        if (id == R.id.item_done) {
//            String itemSelected = "Selected item: \n";
//
//            for (int i=0; i < listView.getCount(); i++) {
//                if ( listView.isItemChecked(i)) {
//                    itemSelected += listView.getItemAtPosition(i) + "\n";
//
//                }
//            }
//            Toast.makeText(getContext(), itemSelected, Toast.LENGTH_SHORT).show();
//            // Create a bundle to be sent to the other fragment
//            Bundle bundle = new Bundle();
//            bundle.putString("TEXT_VALUE",itemSelected);
//
//            // Send the bundle through the Fragment Manager
//            getParentFragmentManager().setFragmentResult("CONTACT_NAME",bundle);
//
//        }
//        return super.onOptionsItemSelected(item);
//    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_done) {
            // Create a bundle to be sent to the other fragment

            // Create a String array to store the names of the selected contacts
            ArrayList<String> selectedContacts = new ArrayList<>();

            for (int i=0; i < listView.getCount(); i++) {
                if (listView.isItemChecked(i)) {
                    selectedContacts.add(listView.getItemAtPosition(i).toString());
                }
            }

            // Add the String array to the bundle
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("SELECTED_CONTACTS", selectedContacts);
            Log.d("ContactsFragment", "Setting result with selectedContacts: " + selectedContacts);
            getParentFragmentManager().setFragmentResult("SELECTED_CONTACTS", bundle);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
