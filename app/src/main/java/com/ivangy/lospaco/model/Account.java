package com.ivangy.lospaco.model;

import android.content.Context;

import com.ivangy.lospaco.R;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Account implements Serializable {
    public int Id;
    public String Email;
    public String Password;
    public Role Role;

    public Account(int id, String email, String password, Role role) {
        Id = id;
        Email = email;
        Password = password;
        Role = role;
    }

    public int getId() {
        return Id;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public com.ivangy.lospaco.model.Role getRole() {
        return Role;
    }

    public static Account getInternalAccount(Context context) {
        Account account = null;
        try {
            FileInputStream fis = new FileInputStream(context.getFileStreamPath(context.getResources().getString(R.string.fos_account)));
            ObjectInputStream ois = new ObjectInputStream(fis);
            account = (Account) ois.readObject();

            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return account;
    }
}
