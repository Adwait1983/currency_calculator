package com.anz.currency.calculator.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anz.currency.calculator.config.CurrencyAppConfig;
import com.anz.currency.calculator.model.InputRequest;
import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CurrencyAppConfig.class, CurrencyCalculator.class},
initializers = ConfigFileApplicationContextInitializer.class)
public class CurrencyCalculatorTest {
	@Autowired
	private CurrencyCalculator currencyCalculator;
	
	private Object[][] testInputs = {
			{"AUD", "USD", 100.00, 83.71},
			{"AUD", "AUD", 100.00, 100.00},
			{"AUD", "DKK", 100.00, 505.76},
			{"JPY", "USD", 100.00, 0.83}
	};
	
	@Test
	public void testGetConvertedValueInTermCurrency() {
		for(Object[] testInput: testInputs) {
			InputRequest inputRequest = new InputRequest();
			inputRequest.setBaseCurrency((String)testInput[0]);
			inputRequest.setTermCurrency((String)testInput[1]);
			inputRequest.setInputAmount((Double)testInput[2]);
			assertThat(currencyCalculator.getConvertedValueInTermCurrency(inputRequest)).isEqualTo(new BigDecimal((Double)testInput[3]).setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		
	}
	
}
