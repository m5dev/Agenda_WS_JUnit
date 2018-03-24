package br.com.gda.employee.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ EmpWtimeModelDeleteTest.class, 
				EmpWtimeModelInsertTest.class, 
				EmpWtimeModelSelectTest.class,
				EmpWtimeModelUpdateTest.class })
public class EmpWorkTimeModelSuiteTest {

}
