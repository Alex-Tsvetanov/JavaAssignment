package com.alextsvetanov.REST.API;

import org.springframework.stereotype.Component;

@Component("IExchangeRates")
public class ExchangeRate implements IExchangeRates {
    @Override
    public double getExchangeRate(ICurrency A, ICurrency B) {
        if(A.getAbbr().compareTo("BGN") == 0 && B.getAbbr().compareTo("USD") == 0)
            return 1.75;
        return Double.NaN;
    }
}
