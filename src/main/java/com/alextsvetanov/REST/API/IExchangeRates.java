package com.alextsvetanov.REST.API;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component("IExchangeRates")
public interface IExchangeRates {
    public double getExchangeRate(ICurrency A, ICurrency B);
}
