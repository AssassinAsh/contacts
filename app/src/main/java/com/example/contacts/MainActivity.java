package com.example.contacts;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contacts.adpaters.ContactsAdapter;
import com.example.contacts.database.AppDatabase;
import com.example.contacts.entities.Contact;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements SaveDialog.DialogListener {

    private List<Contact> contacts;
    private AppDatabase database;
    private ContactsAdapter contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = AppDatabase.getDatabase(getApplicationContext());
        contacts = new ArrayList<>();

        ImageButton addContacts = findViewById(R.id.add_contact);

        database.contactDao().getAllContacts()
                .flatMap(Flowable::fromIterable)
                .subscribe(contact -> contacts.add(contact));

        RecyclerView recyclerView = findViewById(R.id.recycle_view);

        addContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsAdapter = new ContactsAdapter(getApplicationContext(), contacts,
                this::deleteContact);

        recyclerView.setAdapter(contactsAdapter);
    }

    private void openDialog() {
        Toast.makeText(getApplicationContext(), "Dialog", Toast.LENGTH_SHORT);
        SaveDialog saveDialog = new SaveDialog();
        saveDialog.show(getSupportFragmentManager(), "Save Dialog");
    }

    private void deleteContact(final int pos) {
        Contact contact = getById(pos);
        if (contact != null) contacts.remove(contact);
    }

    private Contact getById(final int id) {
        for (Contact contact : contacts) {
            if (contact.getId() == id) return contact;
        }
        return null;
    }

    @Override
    public void saveContact(Contact contact) {
        database.contactDao().addContact(contact);
        contacts.add(contact);
        contactsAdapter.notifyDataSetChanged();
    }
}