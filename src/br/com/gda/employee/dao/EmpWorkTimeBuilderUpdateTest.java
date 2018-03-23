package br.com.gda.employee.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.gda.employee.info.EmpWtimeInfo;

public class EmpWorkTimeBuilderUpdateTest {
	private EmpWtimeInfo workingTime;
	private EmpWtimeBuilderUpdate builder;
	
	
	@Test
	public void testOrdinaryUsage() {
		initializeOrdinaryUsage();
		assertTrue(builder.checkStatementGeneration());
		
		String resultExpected = "UPDATE DUMMY_SCHEMA.employee_working_time SET begin_time = ?, end_time = ?, record_mode = ? WHERE (cod_owner = '10' AND cod_store = '9' AND cod_employee = '8' AND weekday = '1' AND record_mode = ' ');";
		String resultActual = builder.generateStatement();
		assertTrue(resultExpected.equals(resultActual));
	}
	
	
	
	private void initializeOrdinaryUsage() {
		workingTime = new EmpWtimeInfo();
		workingTime.codOwner = 10;
		workingTime.codStore = 9;
		workingTime.codEmployee = 8;
		workingTime.weekday = 1;
		
		builder = new EmpWtimeBuilderUpdate(Common.SCHEMA_NAME, workingTime);
	}
	
	
	
	@Test (expected = NullPointerException.class)
	public void testNullArgumentSchemaName() {
		initializeNullArgumentSchemaName();
	}
	
	
	
	private void initializeNullArgumentSchemaName() {
		workingTime = new EmpWtimeInfo();
		workingTime.codOwner = 10;
		workingTime.codStore = 9;
		workingTime.codEmployee = 8;
		
		builder = new EmpWtimeBuilderUpdate(null, workingTime);
	}
	
	
	
	@Test (expected = NullPointerException.class)
	public void testNullWorkingTime() {
		initializeNullWorkingTime();
	}
	
	
	
	private void initializeNullWorkingTime() {
		workingTime = new EmpWtimeInfo();
		workingTime.codOwner = 10;
		workingTime.codStore = 9;
		workingTime.codEmployee = 8;
		
		builder = new EmpWtimeBuilderUpdate(Common.SCHEMA_NAME, null);
	}
	
	
	
	@Test
	public void testDefaultWorkingTime() {
		initializeDefaultWorkingTime();
		assertTrue(builder.checkStatementGeneration());
		
		String resultExpected = "UPDATE DUMMY_SCHEMA.employee_working_time SET begin_time = ?, end_time = ?, record_mode = ? WHERE (cod_owner IS NULL AND cod_store IS NULL AND cod_employee IS NULL AND weekday IS NULL AND record_mode = ' ');";
		String resultActual = builder.generateStatement();
		assertTrue(resultExpected.equals(resultActual));
	}
	
	
	
	private void initializeDefaultWorkingTime() {
		workingTime = new EmpWtimeInfo();		
		builder = new EmpWtimeBuilderUpdate(Common.SCHEMA_NAME, workingTime);
	}
}
