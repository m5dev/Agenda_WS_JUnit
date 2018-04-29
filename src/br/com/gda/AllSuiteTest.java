package br.com.gda;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.gda.employee.EmpSuiteTest;
import br.com.gda.employee.EmpWorkTimeSuiteTest;
import br.com.gda.model.checker.CheckerSuiteTest;

@RunWith(Suite.class)
@SuiteClasses({EmpSuiteTest.class,
			   EmpWorkTimeSuiteTest.class,
			   CheckerSuiteTest.class})
public class AllSuiteTest {

}