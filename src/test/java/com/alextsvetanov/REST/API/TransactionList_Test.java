package com.alextsvetanov.REST.API;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TransactionList_Test {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICurrencyContainer currencies;

    @MockBean
    private IExchangeRates exchangeRates;

    @MockBean
    private ITransactionContainer transactionContainer;

    @BeforeEach
    public void setup() {
        ICurrency BGN = new ICurrency() {
            @Override
            public String getName() {
                return "Bulgarian Lev";
            }

            @Override
            public String getAbbr() {
                return "BGN";
            }
        };
        IPerson A = new IPerson() {
            @Override
            public int getId() {
                return 0;
            }

            @Override
            public String getName() {
                return "A";
            }
        };
        IPerson B = new IPerson() {
            @Override
            public int getId() {
                return 1;
            }

            @Override
            public String getName() {
                return "B";
            }
        };
        ITransaction transaction = new ITransaction() {
            @Override
            public Integer getId() {
                return 1;
            }

            @Override
            public Date getDate() {
                return new GregorianCalendar(2021, 04 - 1, 13).getTime();
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
                return 1000;
            }

            @Override
            public ICurrency getCurrency() {
                return BGN;
            }

            @Override
            public IPerson getSender() {
                return A;
            }

            @Override
            public IPerson getReceiver() {
                return B;
            }
        };
        ITransaction transaction1 = new ITransaction() {
            @Override
            public Integer getId() {
                return 1;
            }

            @Override
            public Date getDate() {
                return new GregorianCalendar(2021, 04 - 1, 14).getTime();
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
                return 1000;
            }

            @Override
            public ICurrency getCurrency() {
                return BGN;
            }

            @Override
            public IPerson getSender() {
                return A;
            }

            @Override
            public IPerson getReceiver() {
                return B;
            }
        };
        ITransaction transaction2 = new ITransaction() {
            @Override
            public Integer getId() {
                return 1;
            }

            @Override
            public Date getDate() {
                return new GregorianCalendar(2021, 04 - 1, 14).getTime();
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
                return 1000;
            }

            @Override
            public ICurrency getCurrency() {
                return BGN;
            }

            @Override
            public IPerson getSender() {
                return A;
            }

            @Override
            public IPerson getReceiver() {
                return B;
            }
        };

        when(transactionContainer.getByDate(2021, 04, 13)).thenReturn(Arrays.asList(new ITransaction[]{transaction}));
        when(transactionContainer.getByDate(2021, 04, 12)).thenReturn(Arrays.asList(new ITransaction[]{}));
        when(transactionContainer.getByDate(2021, 04, 14)).thenReturn(Arrays.asList(new ITransaction[]{transaction1, transaction2}));
    }

    @Test
    public void WHEN_2021_04_13_RETURNS_1_transaction() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/1.0/conversion_list/get/2021/04/13"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[{id: \"1\",date: \"2021/04/13\",amount: \"1000.0\",currency: \"BGN\",sender: \"A\",receiver: \"B\"}]"));
    }

    @Test
    public void WHEN_2021_04_12_RETURNS_0_transactions() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/1.0/conversion_list/get/2021/04/12"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void WHEN_2021_04_14_RETURNS_2_transactions() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/1.0/conversion_list/get/2021/04/14"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[{id: \"1\",date: \"2021/04/14\",amount: \"1000.0\",currency: \"BGN\",sender: \"A\",receiver: \"B\"}, {id: \"1\",date: \"2021/04/14\",amount: \"1000.0\",currency: \"BGN\",sender: \"A\",receiver: \"B\"}]"));
    }

    @Test
    public void WHEN_Invalid_month_INTERNALSERVERERROR() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/1.0/conversion_list/get/2021/13/14"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Invalid date"));
    }

    @Test
    public void WHEN_Invalid_day_32_INTERNALSERVERERROR() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/1.0/conversion_list/get/2021/12/32"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Invalid date"));
    }

    @Test
    public void WHEN_Invalid_day_31_INTERNALSERVERERROR() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/1.0/conversion_list/get/2021/11/31"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Invalid date"));
    }

    @Test
    public void WHEN_Invalid_day_29_INTERNALSERVERERROR() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/1.0/conversion_list/get/2021/02/29"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Invalid date"));
    }
}