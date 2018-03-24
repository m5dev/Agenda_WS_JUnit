package br.com.gda.employee.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.gda.employee.info.EmpWTimeInfo;

public class EmpWorkTimeBuilderDeleteTest {
	private EmpWTimeInfo workingTime;
	private EmpWtimeBuilderDelete builder;
	
	
	@Test
	public void testOrdinaryUsage() {
		initializeOrdinaryUsage();
		assertTrue(builder.checkStatementGeneration());
		
		String resultExpected = "DELETE FROM DUMMY_SCHEMA.employee_working_time WHERE (cod_owner = '8' AND cod_store = '15' AND cod_employee = '54' AND weekday = '1' AND record_mode = ' ');";
		String resultActual = builder.generateStatement();
		assertTrue(resultExpected.equals(resultActual));
	}
	
	
	
	private void initializeOrdinaryUsage() {
		workingTime = new EmpWTimeInfo();
		workingTime.codOwner = 8;
		workingTime.codStore = 15;
		workingTime.codEmployee = 54;
		workingTime.weekday = 1;
		builder = new EmpWtimeBuilderDelete(Common.SCHEMA_NAME, workingTime);
	}
	
	
	
	@Test (expected = NullPointerException.class)
	public void testNullArgumentSchemaName() {
		initializeNullArgumentSchemaName();
	}
	
	
	
	private void initializeNullArgumentSchemaName() {
		workingTime = new EmpWTimeInfo();
		workingTime.codOwner = 10;
		workingTime.codStore = 9;
		workingTime.codEmployee = 8;
		
		builder = new EmpWtimeBuilderDelete(null, workingTime);
	}
	
	
	
	@Test (expected = NullPointerException.class)
	public void testNullWorkingTime() {
		initializeNullWorkingTime();
	}
	
	
	
	private void initializeNullWorkingTime() {
		workingTime = new EmpWTimeInfo();
		workingTime.codOwner = 10;
		workingTime.codStore = 9;
		workingTime.codEmployee = 8;
		
		builder = new EmpWtimeBuilderDelete(Common.SCHEMA_NAME, null);
	}
	
	
	
	@Test
	public void testDefaultWorkingTime() {
		initializeDefaultWorkingTime();
		assertTrue(builder.checkStatementGeneration());
		
		String resultExpected = "DELETE FROM DUMMY_SCHEMA.employee_working_time WHERE (cod_owner IS NULL AND cod_store IS NULL AND cod_employee IS NULL AND weekday IS NULL AND record_mode = ' ');";
		String resultActual = builder.generateStatement();
		assertTrue(resultExpected.equals(resultActual));
	}
	
	
	
	private void initializeDefaultWorkingTime() {
		workingTime = new EmpWTimeInfo();		
		builder = new EmpWtimeBuilderDelete(Common.SCHEMA_NAME, workingTime);
	}
}
