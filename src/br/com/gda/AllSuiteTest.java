package br.com.gda;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.gda.business.employee.EmpSuiteTest;
import br.com.gda.business.employee.EmpWorkTimeSuiteTest;
import br.com.gda.business.material.MatSuiteTest;
import br.com.gda.business.materialEmployee.MatEmpSuiteTest;
import br.com.gda.business.store.StoreSuiteTest;
import br.com.gda.business.storeEmployee.model.StoreEmpSuiteTest;
import br.com.gda.model.checker.CheckerSuiteTest;

@RunWith(Suite.class)
@SuiteClasses({EmpSuiteTest.class,
			   EmpWorkTimeSuiteTest.class,
			   StoreSuiteTest.class,
			   MatEmpSuiteTest.class,
			   StoreEmpSuiteTest.class,
			   MatSuiteTest.class,
			   CheckerSuiteTest.class})
public class AllSuiteTest {

}
