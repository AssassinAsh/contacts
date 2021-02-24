package com.example.contacts.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contacts.R;
import com.example.contacts.entities.Contact;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactHolder> {

    private final List<Contact> contacts;
    private final Onclick onclick;

    public ContactsAdapter(final Context context,
                           final List<Contact> contacts,
                           final Onclick onclick) {
        this.contacts = contacts;
        this.onclick = onclick;
    }

    public interface Onclick {
        void onEvent(final int pos);
    }

    public static class ContactHolder extends RecyclerView.ViewHolder {

        private final TextView contactName;
        private final TextView contactNumber;
        private final ImageButton deleteContact;

        public ContactHolder(@NonNull final View itemView) {
            super(itemView);

            contactName = itemView.findViewById(R.id.contact_name);
            contactNumber = itemView.findViewById(R.id.contact_number);
            deleteContact = itemView.findViewById(R.id.delete_contact);
        }

        private TextView getContactName() {
            return contactName;
        }

        private TextView getContactNumber() {
            return contactNumber;
        }

        private ImageButton getDeleteContact() {
            return deleteContact;
        }
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list, parent, false);

        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactHolder holder, final int position) {
        holder.getContactName().setText(contacts.get(position).getName());
        holder.getContactNumber().setText(contacts.get(position).getNumber());

        holder.getDeleteContact().setOnClickListener(v -> {
            Toast.makeText(v.getContext(),
                    "Removed " + contacts.get(position).getName(),
                    Toast.LENGTH_SHORT).show();
            this.onclick.onEvent(contacts.get(position).getId());
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

}
