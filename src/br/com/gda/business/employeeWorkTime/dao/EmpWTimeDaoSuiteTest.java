package br.com.gda.business.employeeWorkTime.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ EmpWTimeStmtInsertSingleTest.class,
	            EmpWTimeStmtInsertTest.class,
	            EmpWTimeStmtSelectTest.class,
	            EmpWTimeStmtUpdateTest.class,
	            EmpWTimeStmtDeleteTest.class})
public class EmpWTimeDaoSuiteTest {

}
