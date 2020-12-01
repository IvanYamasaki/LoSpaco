package com.ivangy.lospaco.model;

import android.content.Context;

import com.ivangy.lospaco.R;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Customer implements Serializable {

    private String nome, cpf, phoneNumber;
    private Account account;

    public Customer(String nome, String cpf, String phoneNumber) {
        this.nome = nome;
        this.cpf = cpf;
        this.phoneNumber = phoneNumber;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
