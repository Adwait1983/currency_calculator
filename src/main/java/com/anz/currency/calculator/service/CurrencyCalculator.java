package com.anz.currency.calculator.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

import com.anz.currency.calculator.model.Currency;
import com.anz.currency.calculator.model.CurrencyCalculationPath;
import com.anz.currency.calculator.model.CurrencyPair;
import com.anz.currency.calculator.model.InputRequest;

@Component
public class CurrencyCalculator implements InitializingBean{

	@Autowired
	private List<CurrencyPair> currencyPairs;
	
	@Autowired
	private List<Currency> currencies;
	private List<CurrencyCalculationPath> derivedCurrencyPaths;

	public List<CurrencyPair> getCurrencyPairs() {
		return currencyPairs;
	}

	public void setCurrencyPairs(List<CurrencyPair> currencyPairs) {
		this.currencyPairs = currencyPairs;
	}

	public List<Currency> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(List<Currency> currencies) {
		this.currencies = currencies;
	}

	public CurrencyPair findMatchingCurrencyPair(String baseCurrency, String termCurrency) {
		List<String> currencies = Arrays.asList(baseCurrency, termCurrency);
		List<CurrencyPair> matchedCurrencyPair = currencyPairs.stream()
				.filter(currencyPair -> currencies.contains(currencyPair.getBaseCurrency())
						&& currencies.contains(currencyPair.getTermCurrency()))
				.collect(Collectors.toList());
		if (matchedCurrencyPair != null && !matchedCurrencyPair.isEmpty())
			return matchedCurrencyPair.get(0);
		return null;
	}

	public List<CurrencyPair> findMatchingCurrencyPair(String currency) {
		return currencyPairs.stream().filter(currencyPair -> currency.equals(currencyPair.getBaseCurrency())
				|| currency.equals(currencyPair.getTermCurrency())).collect(Collectors.toList());
	}

	public List<CurrencyPair> findPath(String baseCurrency, String termCurrency, List<String> visitedCurrencies) {
		if (visitedCurrencies.contains(baseCurrency))
			return null;
		else {
			visitedCurrencies.add(baseCurrency);
			CurrencyPair matchingPair = findMatchingCurrencyPair(baseCurrency, termCurrency);
			if (matchingPair != null) {
				List<CurrencyPair> path = new ArrayList<>();
				path.add(matchingPair);
				return path;
			} else {
				List<CurrencyPair> partiallyMatchingPairs = findMatchingCurrencyPair(baseCurrency);
				if (partiallyMatchingPairs != null && !partiallyMatchingPairs.isEmpty()) {
					for (CurrencyPair partiallyMatchingPair : partiallyMatchingPairs) {
						List<CurrencyPair> path = findPath(baseCurrency.equals(partiallyMatchingPair.getBaseCurrency())
								? partiallyMatchingPair.getTermCurrency()
										: partiallyMatchingPair.getBaseCurrency(), termCurrency, visitedCurrencies);
						if (path != null) {
							path.add(0, partiallyMatchingPair);
							return path;
						}
					}
				}
			}
		}
		return null;
	}

	public CurrencyCalculationPath findConversionRate(String baseCurrency, String termCurrency) {
		List<String> visitedCurrencies = new ArrayList<String>();
		List<CurrencyPair> path = findPath(baseCurrency, termCurrency, visitedCurrencies);
		if(path == null)
			return null;
		else{
			CurrencyCalculationPath currencyCalculationPath = new CurrencyCalculationPath(baseCurrency, termCurrency);
			for(CurrencyPair currencyPair: path)
				currencyCalculationPath.addCurrencyPairInPath(currencyPair);
			return currencyCalculationPath;
		}
	}
	
	public Integer findCurrencyScale(String currencyCode) {
		List<Currency> filteredCurrencies = currencies.stream().filter(currency -> currencyCode.equals(currency.getCurrencyCode())).collect(Collectors.toList());
		if(filteredCurrencies != null && !filteredCurrencies.isEmpty())
			return filteredCurrencies.get(0).getDecimalPlaces();
		return null;
	}
	
	public BigDecimal getConvertedValueInTermCurrency(InputRequest parsedInputRequest) {
		Double conversionRate = null;
		if(parsedInputRequest.getBaseCurrency().equals(parsedInputRequest.getTermCurrency())) {
			conversionRate = 1.0;
		} else {
			
			List<CurrencyCalculationPath> matchingPathList = derivedCurrencyPaths.stream().filter(path -> 
			path.getBaseCurrency().equals(parsedInputRequest.getBaseCurrency())
					&&
					path.getTermCurrency().equals(parsedInputRequest.getTermCurrency())).collect(Collectors.toList());
			if(matchingPathList != null && !matchingPathList.isEmpty())
				conversionRate = matchingPathList.get(0).getConversionValue();
		}
		Integer currencyScale = findCurrencyScale(parsedInputRequest.getTermCurrency()); 
		return conversionRate == null || currencyScale == null? null: new BigDecimal(conversionRate * parsedInputRequest.getInputAmount()).setScale(currencyScale, BigDecimal.ROUND_HALF_UP); 
	}
	
	private void initDerivedCurrencyPaths() {
		this.derivedCurrencyPaths = new ArrayList<CurrencyCalculationPath>();
		for(Currency baseCurrency: currencies) {
			for(Currency termCurrency: currencies) {
				if(baseCurrency != termCurrency) {
					CurrencyCalculationPath currencyCalculationPath = findConversionRate(baseCurrency.getCurrencyCode(), termCurrency.getCurrencyCode());
					if(currencyCalculationPath != null)
						derivedCurrencyPaths.add(currencyCalculationPath);
				}
			}
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		initDerivedCurrencyPaths();
	}

}
