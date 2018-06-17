package br.com.gda.business.employeeLeaveDate.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ EmpLDateModelDeleteTest.class,
				EmpLDateModelUpdateTest.class,
				EmpLDateModelSelectTest.class,
				EmpLDateModelInsertTest.class})
public class EmpLDateModelSuiteTest {

}
