package com.anz.currency.calculator.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.anz.currency.calculator.model.Currency;
import com.anz.currency.calculator.model.CurrencyPair;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class CurrencyAppConfig {
	
	private List <Currency> currencies = new ArrayList<>();
	private List<CurrencyPair> currencyPairs = new ArrayList<>();
	
	public List<Currency> getCurrencies() {
		return currencies;
	}



	public void setCurrencies(List<Currency> currencies) {
		this.currencies = currencies;
	}
	

	public List<CurrencyPair> getCurrencyPairs() {
		return currencyPairs;
	}



	public void setCurrencyPairs(List<CurrencyPair> currencyPairs) {
		this.currencyPairs = currencyPairs;
	}



	@Bean("currencies")
	public List<Currency> currencies() {
		return currencies;
	}
	
	@Bean("currencyPairs")
	public List<CurrencyPair> currencyPairs(){
		return currencyPairs;
	}

}
