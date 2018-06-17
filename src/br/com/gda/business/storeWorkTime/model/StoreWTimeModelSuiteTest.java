package br.com.gda.business.storeWorkTime.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ StoreWTimeModelDeleteTest.class, 
				StoreWTimeModelInsertTest.class, 				
				StoreWTimeModelUpdateTest.class,
				StoreWTimeModelSelectTest.class})
public class StoreWTimeModelSuiteTest {

}
