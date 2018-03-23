package br.com.gda.employee.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.gda.employee.info.EmpWtimeInfo;


public class EmpWorkTimeBuilderWhereTest {
	private EmpWtimeInfo workingTime;
	private EmpWtimeBuilderWhere builder;
	
	@Test
	public void testOrdinaryUsage() {
		initializeToPass();
		assertTrue(builder.checkClauseGeneration());				
		
		String resultExpected = "(cod_owner = '10' AND cod_store = '9' AND cod_employee = '8' AND record_mode = ' ')";
		String resultActual = builder.generateClause();
		
		assertNotNull(resultActual);
		assertTrue(resultExpected.equals(resultActual));
	}
	
	
	@Test (expected = NullPointerException.class)
	public void testNullArgument() {
		initializeToFail();
		assertFalse(builder.checkClauseGeneration());
	}	
	
	
	private void initializeToPass() {
		workingTime = new EmpWtimeInfo();
		workingTime.codOwner = 10;
		workingTime.codStore = 9;
		workingTime.codEmployee = 8;
		
		builder = new EmpWtimeBuilderWhere(workingTime);
	}
	
	
	private void initializeToFail() {	
		workingTime = null;
		builder = new EmpWtimeBuilderWhere(workingTime);
	}
}
