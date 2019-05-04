package com.anz.currency.calculator.model;

public class CurrencyPair {
	
	private String baseCurrency;
	private String termCurrency;
	private Double rate;
	
	public String getBaseCurrency() {
		return baseCurrency;
	}
	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}
	public String getTermCurrency() {
		return termCurrency;
	}
	public void setTermCurrency(String termCurrency) {
		this.termCurrency = termCurrency;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	public boolean isGivenPairConnected(CurrencyPair currencyPair) {
		if(this.termCurrency.equals(currencyPair.baseCurrency) || this.baseCurrency.equals(currencyPair.baseCurrency))
			return true;
		else if(this.termCurrency.equals(currencyPair.termCurrency) || this.baseCurrency.equals(currencyPair.termCurrency))
			return true;
		return false;
	}

}
