package br.com.gda.business.store;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.gda.business.store.info.StoreInfoSuiteTest;
import br.com.gda.business.store.model.StoreModelSuiteTest;

@RunWith(Suite.class)
@SuiteClasses({StoreModelSuiteTest.class,
	           StoreInfoSuiteTest.class})
public class StoreSuiteTest {

}
