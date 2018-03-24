package br.com.gda.employee;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.gda.employee.dao.EmpWorkTimeDaoSuiteTest;
import br.com.gda.employee.model.EmpWorkTimeModelSuiteTest;

@RunWith(Suite.class)
@SuiteClasses({EmpWorkTimeModelSuiteTest.class,
			   EmpWorkTimeDaoSuiteTest.class})
public class EmpWorkTimeSuiteTest {

}
