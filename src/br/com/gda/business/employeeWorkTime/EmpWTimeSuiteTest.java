package br.com.gda.business.employeeWorkTime;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.gda.business.employeeWorkTime.dao.EmpWTimeDaoSuiteTest;
import br.com.gda.business.employeeWorkTime.info.EmpWTimeInfoSuiteTest;
import br.com.gda.business.employeeWorkTime.model.EmpWTimeModelSuiteTest;

@RunWith(Suite.class)
@SuiteClasses({EmpWTimeModelSuiteTest.class,
			   EmpWTimeInfoSuiteTest.class,
			   EmpWTimeDaoSuiteTest.class})
public class EmpWTimeSuiteTest {

}
