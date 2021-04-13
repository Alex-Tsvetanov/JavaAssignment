package com.alextsvetanov.REST.API;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class ITransaction {
    public abstract Integer getId ();
    public abstract Date getDate ();
    public abstract int compareTo (ITransaction x);
    public abstract boolean equals (ITransaction x);
    public abstract double getAmount();
    public abstract ICurrency getCurrency();
    public abstract IPerson getSender();
    public abstract IPerson getReceiver();

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("{");
        str.append("id: \""); str.append(this.getId()); str.append("\",");
        str.append("date: \""); str.append(new SimpleDateFormat("yyyy/MM/dd").format(this.getDate())); str.append("\",");
        str.append("amount: \""); str.append(this.getAmount()); str.append("\",");
        str.append("currency: \""); str.append(this.getCurrency().getAbbr()); str.append("\",");
        str.append("sender: \""); str.append(this.getSender().getName()); str.append("\",");
        str.append("receiver: \""); str.append(this.getReceiver().getName()); str.append("\"}");
        return str.toString();
    }
}
