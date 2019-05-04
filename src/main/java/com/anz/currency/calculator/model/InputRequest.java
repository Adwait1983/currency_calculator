package com.anz.currency.calculator.model;

public class InputRequest {
	
	private String baseCurrency;
	private String termCurrency;
	private Double inputAmount;
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
	public Double getInputAmount() {
		return inputAmount;
	}
	public void setInputAmount(Double inputAmount) {
		this.inputAmount = inputAmount;
	}
	
	

}
