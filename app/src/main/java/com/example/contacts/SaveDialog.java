package com.example.contacts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.contacts.entities.Contact;

public class SaveDialog extends AppCompatDialogFragment {

    private EditText input_name;
    private EditText input_number;
    private DialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.input_contact, null);

        input_name = view.findViewById(R.id.input_name);
        input_number = view.findViewById(R.id.input_number);

        builder.setView(view)
                .setTitle("New Contact").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input_name.getText().toString();
                String number = input_number.getText().toString();
                listener.saveContact(new Contact(name, number));
            }
        });

        return builder.create();
    }

    public interface DialogListener {
        void saveContact(final Contact contact);
    }
}
