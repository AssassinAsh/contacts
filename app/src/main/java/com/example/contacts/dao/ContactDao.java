package com.example.contacts.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.contacts.entities.Contact;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface ContactDao {
    @Insert
    Completable addContact(Contact contact);

    @Query("select * from contact")
    Flowable<List<Contact>> getAllContacts();

    @Query("SELECT * FROM CONTACT WHERE ID Like :id")
    Maybe<Contact> getContact(int id);

    @Delete
    Single<Integer> deleteUser(Contact contact);
}
