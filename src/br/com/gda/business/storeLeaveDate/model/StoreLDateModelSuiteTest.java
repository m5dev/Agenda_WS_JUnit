package br.com.gda.business.storeLeaveDate.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ StoreLDateModelDeleteTest.class,
				//StoreLDateModelUpdateTest.class,				
				StoreLDateModelInsertTest.class,
	            StoreLDateModelSelectTest.class})
public class StoreLDateModelSuiteTest {

}
