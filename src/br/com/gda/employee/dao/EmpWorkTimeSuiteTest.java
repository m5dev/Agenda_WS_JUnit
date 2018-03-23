package br.com.gda.employee.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ EmpWorkTimeStmtInsertTest.class, 
	            EmpWorkTimeBuilderInsertTest.class, 
	            EmpWorkTimeBuilderWhereTest.class,
	            EmpWorkTimeStmtExecInsertTest.class,
	            EmpWorkTimeBuilderSelectTest.class,
	            EmpWorkTimeStmtExecSelectTest.class,
	            EmpWorkTimeBuilderUpdateTest.class,
	            EmpWorkTimeStmtExecUpdateTest.class,
	            EmpWorkTimeBuilderDeleteTest.class,
	            EmpWorkTimeStmtExecDeleteTest.class})
public class EmpWorkTimeSuiteTest {

}
