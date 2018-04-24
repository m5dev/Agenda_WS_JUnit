package br.com.gda.employee;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({EmpSuiteTest.class,
			   EmpWorkTimeSuiteTest.class})
public class AllSuiteTest {

}
