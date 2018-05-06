package br.com.gda.business.employee.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ EmpModelSelectTest.class,
				EmpModelDeleteTest.class,
				EmpModelInsertTest.class,
				EmpModelUpdateTest.class})
public class EmpModelSuiteTest {

}
