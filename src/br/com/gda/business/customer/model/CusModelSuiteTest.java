package br.com.gda.business.customer.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({CusModelInsertTest.class,
			   CusModelSelectTest.class,
			   CusModelUpdateTest.class,
			   CusModelDeleteTest.class})
public class CusModelSuiteTest {

}
