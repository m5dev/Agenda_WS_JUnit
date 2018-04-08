package br.com.gda.employee.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ EmpWorkTimeStmtInsertTest.class,
	            EmpWorkTimeStmtExecInsertTest.class,
	            EmpWorkTimeStmtExecSelectTest.class,
	            EmpWorkTimeStmtExecUpdateTest.class,
	            EmpWorkTimeStmtExecDeleteTest.class})
public class EmpWorkTimeDaoSuiteTest {

}
