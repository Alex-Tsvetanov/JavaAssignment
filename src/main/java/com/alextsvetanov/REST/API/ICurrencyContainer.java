package com.alextsvetanov.REST.API;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component("ICurrencyContainer")
public interface ICurrencyContainer {
    public ICurrency get (String name);
}
