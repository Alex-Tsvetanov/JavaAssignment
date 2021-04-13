package com.alextsvetanov.REST.API;

import org.springframework.stereotype.Component;

import java.util.*;

@Component("ITransactionContainer")
public class TransactionContainer implements ITransactionContainer {
    private Map<Integer, ITransaction> transactions;

    public TransactionContainer(Map<Integer, ITransaction> transactions) {
        this.transactions = transactions;
    }

    public TransactionContainer () {
        this.transactions = new HashMap<>();
    }

    @Override
    public ITransaction getById (String id) throws IllegalArgumentException {
        if(transactions.containsKey(id))
            return transactions.get(id);
        else
            throw new IllegalArgumentException();
    }

    private int days (int yyyy, int mm) throws IllegalArgumentException {
        if (mm == 2) {
            if (yyyy % 4 == 0) { return 29; }
            else { return 28; }
        } else if (mm == 1 || mm == 3 || mm == 5 || mm == 7 || mm == 8 || mm == 10 || mm == 12) {
            return 31;
        } else if (mm == 4 || mm == 6 || mm == 11){
            return 30;
        }
        throw new IllegalArgumentException("Invalid month - " + Integer.toString(mm));
    }

    @Override
    public List<ITransaction> getByDate (int yyyy, int mm, int dd) throws IllegalArgumentException {
        if (yyyy < 0 || mm < 0 || mm > 12 || dd < 0 || dd > days(yyyy, mm)) {
            throw new IllegalArgumentException();
        }
        List <ITransaction> answer = new ArrayList<>();
        Date date = new GregorianCalendar(yyyy, mm - 1, dd).getTime();
        for (ITransaction x : transactions.values()) {
            if (date.compareTo(x.getDate()) == 0) {
                answer.add(x);
            }
        }
        return answer;
    }
}
