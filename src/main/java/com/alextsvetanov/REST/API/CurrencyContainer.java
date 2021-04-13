package com.alextsvetanov.REST.API;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("ICurrencyContainer")
public class CurrencyContainer implements ICurrencyContainer {
    public Map <String, ICurrency> currencies;
    public CurrencyContainer () {
        this.currencies = new HashMap<>();
    }
    public CurrencyContainer (Map <String, ICurrency> currencies) {
        this.currencies = currencies;
    }

    public void addCurrency(ICurrency A) {
        this.currencies.put(A.getName(), A);
    }
    public void removeCurrency(ICurrency A) {
        this.currencies.remove(A.getName());
    }

    @Override
    public ICurrency get(String name) {
        return currencies.getOrDefault(name, null);
    }
}
