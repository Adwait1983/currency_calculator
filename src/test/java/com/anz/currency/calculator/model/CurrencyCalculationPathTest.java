package com.anz.currency.calculator.model;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anz.currency.calculator.model.CurrencyCalculationPath;
import com.anz.currency.calculator.model.CurrencyPair;

import static org.assertj.core.api.Assertions.*;
/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CurrencyCalculationPathTest {
	

	
	@org.junit.Test
	public void testExcpetionThrownByAddCurrencyPairInPathWhenBaseCurrencyDoesnotMatch() {
		final CurrencyCalculationPath currencyCalculationPath = new CurrencyCalculationPath("USD", "JPY");
		final CurrencyPair currencyPair = new CurrencyPair();
		currencyPair.setBaseCurrency("AUD");
		currencyPair.setTermCurrency("DKK");
		assertThatThrownBy(() -> {
			currencyCalculationPath.addCurrencyPairInPath(currencyPair);
		});
	}
	
	@org.junit.Test
	public void testConversionInCaseOfInversion() {
		CurrencyCalculationPath currencyCalculationPath = new CurrencyCalculationPath("USD", "AUD");
		CurrencyPair currencyPair = new CurrencyPair();
		currencyPair.setBaseCurrency("AUD");
		currencyPair.setTermCurrency("USD");
		currencyPair.setRate(0.8371);
		currencyCalculationPath.addCurrencyPairInPath(currencyPair);
		assertThat(Math.abs(currencyCalculationPath.getConversionValue()-1.1946)).isLessThanOrEqualTo(0.01);
	}
	
	@org.junit.Test
	public void testConversionInCaseOfMultiplePairs() {
		CurrencyCalculationPath currencyCalculationPath = new CurrencyCalculationPath("AUD", "JPY");
		CurrencyPair currencyPair = new CurrencyPair();
		currencyPair.setBaseCurrency("AUD");
		currencyPair.setTermCurrency("USD");
		currencyPair.setRate(0.8371);
		currencyCalculationPath.addCurrencyPairInPath(currencyPair);
		
		currencyPair = new CurrencyPair();
		currencyPair.setBaseCurrency("USD");
		currencyPair.setTermCurrency("JPY");
		currencyPair.setRate(119.95);
		currencyCalculationPath.addCurrencyPairInPath(currencyPair);
		
		assertThat(Math.abs(currencyCalculationPath.getConversionValue() - 100.41)).isLessThanOrEqualTo(0.01);
	}
	
}
