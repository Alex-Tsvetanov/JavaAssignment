package com.alextsvetanov.REST.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/1.0")
public class API {
	@Autowired
	private ICurrencyContainer currencies;

	@Autowired
	private IExchangeRates exchangeRates;

	@Autowired
	private ITransactionContainer transactions;

	@RequestMapping(value="/exchange_rate/get/{fromCurrency}/{toCurrency}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> exchangeRate(
			@PathVariable(required = true) String fromCurrency,
			@PathVariable(required = true) String toCurrency) {

		ICurrency source, target;
		try {
			source = currencies.get(fromCurrency);
		} catch (Exception ex) {
			return new ResponseEntity<String>("Source currency (" + fromCurrency + ") was not found.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			target = currencies.get(toCurrency);
		} catch (Exception ex) {
			return new ResponseEntity<String>("Target currency (" + toCurrency + ") was not found.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			return new ResponseEntity<String>(Double.toString(exchangeRates.getExchangeRate(source, target)), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<String>("Exchange rate getter crashed.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value="/conversion/get/{amount}/{fromCurrency}/{toCurrency}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> conversion(
			@PathVariable(required = true) double amount,
			@PathVariable(required = true) String fromCurrency,
			@PathVariable(required = true) String toCurrency) {
		ICurrency source, target;
		try {
			source = currencies.get(fromCurrency);
		} catch (Exception ex) {
			return new ResponseEntity<String>("Source currency (" + fromCurrency + ") was not found.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			target = currencies.get(toCurrency);
		} catch (Exception ex) {
			return new ResponseEntity<String>("Target currency (" + toCurrency + ") was not found.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			return new ResponseEntity<String>(Double.toString(amount * exchangeRates.getExchangeRate(source, target)), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<String>("Exchange rate getter crashed.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value="/conversion_list/get/{id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> getByTransactionId(
			@PathVariable(required = true) String id) {
		try {
			return new ResponseEntity<String>(transactions.getById(id).toString(), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<String>("Transaction not found.", HttpStatus.NOT_FOUND);
		}
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

	@RequestMapping(value="/conversion_list/get/{yyyy}/{mm}/{dd}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> getByTransactionDate(
			@PathVariable(required = true) int yyyy,
			@PathVariable(required = true) int mm,
			@PathVariable(required = true) int dd) {
		if (yyyy < 0 || mm < 0 || mm > 12 || dd < 0 || dd > days(yyyy, mm)) {
			return new ResponseEntity<>("Invalid date", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(transactions.getByDate(yyyy, mm, dd).toString(), HttpStatus.OK);
	}
}
