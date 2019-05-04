package com.anz.currency.calculator;

import java.math.BigDecimal;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.anz.currency.calculator.model.InputRequest;
import com.anz.currency.calculator.service.CurrencyCalculator;
import com.anz.currency.calculator.util.InputlineParser;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App implements CommandLineRunner
{
	@Autowired
	private InputlineParser inputLineParser;
	
	@Autowired
	private CurrencyCalculator currencyCalculator;
	
    public static void main( String[] args ) throws Exception
    {
        SpringApplication app = new SpringApplication(App.class);
        app.run(args);
    }

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		String inputLine = scanner.nextLine();
		scanner.close();
		InputRequest parsedInputRequest = inputLineParser.parseInputLine(inputLine);
		BigDecimal outputAmount = currencyCalculator.getConvertedValueInTermCurrency(parsedInputRequest);
		if(outputAmount != null)
			System.out.println(parsedInputRequest.getBaseCurrency()+" "+parsedInputRequest.getInputAmount()+" = "+parsedInputRequest.getTermCurrency()+" "+ outputAmount);
		else
			System.out.println("Unable to find rate for "+parsedInputRequest.getBaseCurrency()+"/"+parsedInputRequest.getTermCurrency()); 
	}
    
    
}
