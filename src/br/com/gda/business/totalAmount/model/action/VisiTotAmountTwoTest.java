package br.com.gda.business.totalAmount.model.action;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import br.com.gda.business.totalAmount.info.TotAmountInfo;
import br.com.gda.business.totalAmount.model.action.VisiTotAmountTwo;

public class VisiTotAmountTwoTest {
	private VisiTotAmountTwo visitor;
	private List<TotAmountInfo> results;
	private List<TotAmountInfo> arguments;
	
	
	@Test (expected = NullPointerException.class)
	public void nullArgument() {
		visitor = new VisiTotAmountTwo();
		visitor.executeTransformation(null);
	}
	
	
	
	@Test
	public void emptyArgument() {
		visitor = new VisiTotAmountTwo();
		results = visitor.executeTransformation(Collections.emptyList());
		
		assertTrue(results.get(0).amount == 0);
		assertTrue(results.get(0).decimalPlace == 0);
	}
	
	
	
	@Test (expected = IllegalArgumentException.class) 
	public void differentDecimalPlaces() {
		arguments = new ArrayList<>();
		TotAmountInfo eachAmount = new TotAmountInfo();
		eachAmount.amount = 1;
		eachAmount.decimalPlace = 2;
		arguments.add(eachAmount);
		
		eachAmount = new TotAmountInfo();
		eachAmount.amount = 1;
		eachAmount.decimalPlace = 3;
		arguments.add(eachAmount);
		
		
		visitor = new VisiTotAmountTwo();
		results = visitor.executeTransformation(arguments);
	}
	
	
	
	@Test
	public void makeTotal() {
		arguments = new ArrayList<>();
		TotAmountInfo eachAmount = new TotAmountInfo();
		eachAmount.amount = 1.11;
		eachAmount.decimalPlace = 2;
		arguments.add(eachAmount);
		
		eachAmount = new TotAmountInfo();
		eachAmount.amount = 2.22;
		eachAmount.decimalPlace = 2;
		arguments.add(eachAmount);
		
		
		visitor = new VisiTotAmountTwo();
		results = visitor.executeTransformation(arguments);
		
		
		assertTrue(results.get(0).decimalPlace == 2);
		assertTrue(results.get(0).amount == 3.33);
	}
}
