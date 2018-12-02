package br.com.gda.business.person.model.decisionTree;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({RootPersonSelectTest.class,
			   RootPersonInsertTest.class,
			   RootPersonUpdateTest.class,
	           RootPersonDeleteTest.class})
public class PersonDecisionTreeSuiteTest {

}
