package com.anz.currency.calculator.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anz.currency.calculator.model.InputRequest;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class InputlineParserTest {
	
	@Test
	public void testInvalidInputLine() {
		final InputlineParser inputLineParser = new InputlineParser();
		
		final String inputLine = "Any invalid format input";
		assertThatThrownBy(() -> {
			inputLineParser.parseInputLine(inputLine);
		});
		
	}
	
	@Test
	public void testValidInputLine() {
		final InputlineParser inputLineParser = new InputlineParser();
		
		final String inputLine = "USD 100 in AUD";
		InputRequest inputRequest = inputLineParser.parseInputLine(inputLine);
		assertThat(inputRequest.getBaseCurrency()).isEqualTo("USD");
		assertThat(inputRequest.getTermCurrency()).isEqualTo("AUD");
		assertThat(inputRequest.getInputAmount()).isEqualTo(100);
		
	}

}
