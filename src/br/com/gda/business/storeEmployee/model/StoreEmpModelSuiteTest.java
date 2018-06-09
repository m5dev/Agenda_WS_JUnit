package br.com.gda.business.storeEmployee.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({StoreEmpModelDeleteTest.class,
	           StoreEmpModelInsertTest.class,
	           StoreEmpModelSelectTest.class,
	           StoreEmpModelUpdateTest.class})
public class StoreEmpModelSuiteTest {

}
