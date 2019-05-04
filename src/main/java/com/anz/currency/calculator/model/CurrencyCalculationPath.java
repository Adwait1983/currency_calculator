package com.anz.currency.calculator.model;

import java.util.ArrayList;
import java.util.List;

public class CurrencyCalculationPath {
	private String baseCurrency;
	private String termCurrency;
	private List<CurrencyPair> inPathCurrencies = new ArrayList<CurrencyPair>();

	public CurrencyCalculationPath(String baseCurrency, String termCurrency) {
		this.baseCurrency = baseCurrency;
		this.termCurrency = termCurrency;
	}

	public void addCurrencyPairInPath(CurrencyPair currencyPair) {
		if(inPathCurrencies.size() == 0) {
			if(!baseCurrency.equals(currencyPair.getBaseCurrency()) && !baseCurrency.equals(currencyPair.getTermCurrency()))
				throw new IllegalArgumentException("Disconnected currency pair in the path");
		} else if(!inPathCurrencies.get(inPathCurrencies.size() -1).isGivenPairConnected(currencyPair))
			throw new IllegalArgumentException("Disconnected currency pair in the path");
		inPathCurrencies.add(currencyPair);

	}

	public Double getConversionValue() {
		double currencyValue = 1;
		String currentCurrency = baseCurrency;
		for (CurrencyPair currencyPair : inPathCurrencies) {
			if (currentCurrency.equals(currencyPair.getBaseCurrency())) {
				currencyValue = currencyValue * currencyPair.getRate();
				currentCurrency = currencyPair.getTermCurrency();
			} else if (currentCurrency.equals(currencyPair.getTermCurrency())) {
				currencyValue = currencyValue / currencyPair.getRate();
				currentCurrency = currencyPair.getBaseCurrency();
			} else {
				throw new IllegalArgumentException("Disconnected currency pair in the path");
			}
		}
		if (!currentCurrency.equals(termCurrency))
			throw new IllegalArgumentException("Currency Pairs in the path doesn't lead to term currency");
		return currencyValue;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public String getTermCurrency() {
		return termCurrency;
	}
	
	

}
