package br.com.gda.business.store.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({StoreModelDeleteTest.class,
			   StoreModelInsertTest.class,
			   StoreModelUpdateTest.class,
			   StoreModelSelectTest.class})
public class StoreModelSuiteTest {

}
