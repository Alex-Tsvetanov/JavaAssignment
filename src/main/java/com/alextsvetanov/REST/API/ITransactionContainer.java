package com.alextsvetanov.REST.API;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component("ITransactionContainer")
public interface ITransactionContainer {
    public ITransaction getById (String id);
    public List<ITransaction> getByDate (int yyyy, int mm, int dd);
}
