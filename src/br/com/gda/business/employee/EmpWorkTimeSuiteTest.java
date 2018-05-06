package br.com.gda.business.employee;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.gda.business.employee.dao.EmpWorkTimeDaoSuiteTest;
import br.com.gda.business.employee.model.EmpWorkTimeModelSuiteTest;

@RunWith(Suite.class)
@SuiteClasses({EmpWorkTimeModelSuiteTest.class,
			   EmpWorkTimeDaoSuiteTest.class})
public class EmpWorkTimeSuiteTest {

}
