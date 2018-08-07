package br.com.gda.business.employee;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.gda.business.employee.info.EmpInfoSuiteTest;
import br.com.gda.business.employee.model.EmpModelSuiteTest;

@RunWith(Suite.class)
@SuiteClasses({EmpModelSuiteTest.class,
	           EmpInfoSuiteTest.class})
public class EmpSuiteTest {

}
