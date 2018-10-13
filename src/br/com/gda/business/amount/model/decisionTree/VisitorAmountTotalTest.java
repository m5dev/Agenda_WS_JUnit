package br.com.gda.business.amount.model.decisionTree;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import br.com.gda.business.amount.info.AmountInfo;

public class VisitorAmountTotalTest {
	private VisitorAmountTotal visitor;
	private List<AmountInfo> results;
	private List<AmountInfo> arguments;
	
	
	@Test (expected = NullPointerException.class)
	public void nullArgument() {
		visitor = new VisitorAmountTotal();
		visitor.executeTransformation(null);
	}
	
	
	
	@Test
	public void emptyArgument() {
		visitor = new VisitorAmountTotal();
		results = visitor.executeTransformation(Collections.emptyList());
		
		assertTrue(results.get(0).amount == 0);
		assertTrue(results.get(0).decimalPlace == 0);
	}
	
	
	
	@Test (expected = IllegalArgumentException.class) 
	public void differentDecimalPlaces() {
		arguments = new ArrayList<>();
		AmountInfo eachAmount = new AmountInfo();
		eachAmount.amount = 1;
		eachAmount.decimalPlace = 2;
		arguments.add(eachAmount);
		
		eachAmount = new AmountInfo();
		eachAmount.amount = 1;
		eachAmount.decimalPlace = 3;
		arguments.add(eachAmount);
		
		
		visitor = new VisitorAmountTotal();
		results = visitor.executeTransformation(arguments);
	}
	
	
	
	@Test
	public void makeTotal() {
		arguments = new ArrayList<>();
		AmountInfo eachAmount = new AmountInfo();
		eachAmount.amount = 1.11;
		eachAmount.decimalPlace = 2;
		arguments.add(eachAmount);
		
		eachAmount = new AmountInfo();
		eachAmount.amount = 2.22;
		eachAmount.decimalPlace = 2;
		arguments.add(eachAmount);
		
		
		visitor = new VisitorAmountTotal();
		results = visitor.executeTransformation(arguments);
		
		
		assertTrue(results.get(0).decimalPlace == 2);
		assertTrue(results.get(0).amount == 3.33);
	}
}
