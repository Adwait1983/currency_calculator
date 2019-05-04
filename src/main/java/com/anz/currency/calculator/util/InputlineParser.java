package com.anz.currency.calculator.util;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.anz.currency.calculator.model.InputRequest;

@Component
public class InputlineParser {
	private static Pattern inputStringFormat = Pattern.compile("[a-zA-Z]{3} [0-9]+(\\.[0-9]+)? in [a-zA-Z]{3}");
	
	public InputRequest parseInputLine(String inputLine) {
		if(!inputStringFormat.matcher(inputLine).matches())
			throw new IllegalArgumentException("Incorrect input line format");
		String[] tokens = inputLine.split(" ");
		InputRequest inputRequest = new InputRequest();
		inputRequest.setBaseCurrency(tokens[0]);
		inputRequest.setTermCurrency(tokens[3]);
		inputRequest.setInputAmount(Double.parseDouble(tokens[1]));
		return inputRequest;
	}

}
