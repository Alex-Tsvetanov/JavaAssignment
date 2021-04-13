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

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ExchangeRate_Test {
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
        ICurrency USD = new ICurrency() {
            @Override
            public String getName() {
                return "United States Dollar";
            }

            @Override
            public String getAbbr() {
                return "USD";
            }
        };

        when(currencies.get("BGN")).thenReturn(BGN);
        when(currencies.get("USD")).thenReturn(USD);
        when(currencies.get("EUR")).thenThrow(new IllegalArgumentException("Unknown currency"));
        when(exchangeRates.getExchangeRate(BGN, USD)).thenReturn(1.75);
    }

    @Test
    public void WHEN_BGN_to_USD_RETURNS_1_75() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/1.0/exchange_rate/get/BGN/USD"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("1.75")));
    }
    @Test
    public void WHEN_BGN_to_EUR_INTERNALSERVERERROR() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/1.0/exchange_rate/get/BGN/EUR"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
}