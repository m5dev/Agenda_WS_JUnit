package br.com.gda.business.employee.model.checker;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.gda.business.employeeWorkTime.info.EmpWTimeInfo;
import br.com.gda.business.employeeWorkTime.model.checker.EmpWTimeCheckRead;

public class CheckerEmpWtimeMandatoryReadTest {
	protected EmpWTimeCheckRead checker;
	protected EmpWTimeInfo recordInfo;
	
	@Test
	public void ordinaryUsageSuccess() {
		initializeForOrdinaryUsageSuccess();
		assertTrue(checker.check(recordInfo));
		assertTrue(checker.getResult());
	}
	
	
	
	@Test  (expected = IllegalStateException.class)
	public void ordinaryUsageNoErrorMsg() {
		initializeForOrdinaryUsageSuccess();
		assertTrue(checker.check(recordInfo));
		assertTrue(checker.getResult());
		checker.getFailMessage();
	}
	
	
	
	@Test  (expected = IllegalStateException.class)
	public void ordinaryUsageNoErrorCode() {
		initializeForOrdinaryUsageSuccess();
		assertTrue(checker.check(recordInfo));
		assertTrue(checker.getResult());
		checker.getFailCode();
	}
	
	
	
	protected void initializeForOrdinaryUsageSuccess() {
		checker = new EmpWTimeCheckRead();
		
		recordInfo = new EmpWTimeInfo();
		recordInfo.codOwner = 1;
		recordInfo.codStore = 1;
		recordInfo.codEmployee = 1;
	}
	
	
	
	@Test
	public void ordinaryUsageFailure1() {
		initializeForOrdinaryUsageFailure();
		
		recordInfo.codOwner = 1;
		assertFalse(checker.check(recordInfo));
		assertFalse(checker.getResult());
	}
	
	
	
	@Test
	public void ordinaryUsageFailure2() {
		initializeForOrdinaryUsageFailure();
		
		recordInfo.codOwner = 1;
		recordInfo.codStore = 1;
		assertFalse(checker.check(recordInfo));
		assertFalse(checker.getResult());
	}
	
	
	
	@Test
	public void ordinaryUsageFailure3() {
		initializeForOrdinaryUsageFailure();
		
		recordInfo.codEmployee = 1;
		assertFalse(checker.check(recordInfo));
		assertFalse(checker.getResult());
	}
	
	
	@Test
	public void ordinaryUsageFailure4() {
		initializeForOrdinaryUsageFailure();
		
		recordInfo.codOwner = 1;
		recordInfo.codEmployee = 1;
		assertFalse(checker.check(recordInfo));
		assertFalse(checker.getResult());
	}
	
	
	
	@Test
	public void ordinaryUsageFailure5() {
		initializeForOrdinaryUsageFailure();
		
		recordInfo.codStore = 1;
		recordInfo.codEmployee = 1;
		assertFalse(checker.check(recordInfo));
		assertFalse(checker.getResult());
	}
	
	
	
	protected void initializeForOrdinaryUsageFailure() {
		checker = new EmpWTimeCheckRead();
		
		recordInfo = new EmpWTimeInfo();
	}
}
