package br.com.gda.employee.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.gda.employee.info.EmpWtimeInfo;

public class EmpWorkTimeBuilderInsertTest {
	private EmpWtimeInfo workingTime;
	private EmpWtimeBuilderInsert builder;
	
	
	@Test
	public void testOrdinaryUsage() {
		initializeOrdinaryUsage();
		assertTrue(builder.checkStatementGeneration());
		
		String resultExpected = "INSERT INTO DUMMY_SCHEMA.employee_working_time (cod_owner, cod_store, cod_employee, weekday, begin_time, end_time, record_mode) VALUES (?, ?, ?, ?, ?, ?, ?);";
		String resultActual = builder.generateStatement();
		assertTrue(resultExpected.equals(resultActual));
	}
	
	
	
	private void initializeOrdinaryUsage() {
		workingTime = new EmpWtimeInfo();
		workingTime.codOwner = 10;
		workingTime.codStore = 9;
		workingTime.codEmployee = 8;
		
		builder = new EmpWtimeBuilderInsert(Common.SCHEMA_NAME, workingTime);
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
		
		builder = new EmpWtimeBuilderInsert(null, workingTime);
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
		
		builder = new EmpWtimeBuilderInsert(Common.SCHEMA_NAME, null);
	}
}