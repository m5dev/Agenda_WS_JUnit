package br.com.gda.business.employeeWorkTime.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ EmpWTimeModelDeleteTest.class, 
				EmpWTimeModelInsertTest.class, 
				EmpWTimeModelSelectTest.class,
				EmpWTimeModelUpdateTest.class })
public class EmpWTimeModelSuiteTest {

}
