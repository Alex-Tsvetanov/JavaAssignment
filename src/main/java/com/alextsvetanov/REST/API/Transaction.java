package com.alextsvetanov.REST.API;

import java.util.Date;
import java.util.GregorianCalendar;

public class Transaction extends ITransaction {
    private int id;
    private Date date;
    private double amount;
    private ICurrency currency;
    private IPerson sender;
    private IPerson receiver;

    public Transaction (int id, int yyyy, int mm, int dd, double amount, ICurrency currency, IPerson sender, IPerson receiver) {
        this.id = id;
        this.date = new GregorianCalendar(yyyy, mm - 1, dd).getTime();
        this.amount = amount;
        this.currency = currency;
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public Date getDate() {
        return this.date;
    }

    @Override
    public int compareTo(ITransaction x) {
        return this.getId() - x.getId();
    }

    @Override
    public boolean equals(ITransaction x) {
        return this.getId() == x.getId();
    }

    @Override
    public double getAmount() {
        return this.amount;
    }

    @Override
    public ICurrency getCurrency() {
        return this.currency;
    }

    @Override
    public IPerson getSender() {
        return this.sender;
    }

    @Override
    public IPerson getReceiver() {
        return this.receiver;
    }
}
