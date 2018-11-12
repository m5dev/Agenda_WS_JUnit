package br.com.gda.business.address.model.decisionTree;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({RootAddressInsertTest.class,
	           RootAddressSelectTest.class,
	           RootAddressDeleteTest.class,
	           RootAddressUpsertdelTest.class,
	           RootAddressUpdateTest.class})
public class AddressDecisionTreeSuiteTest {

}
