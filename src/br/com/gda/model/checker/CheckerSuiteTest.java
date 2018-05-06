package br.com.gda.model.checker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ModelCheckerCpfTest.class,
			   ModelCheckerCnpjTest.class})
public class CheckerSuiteTest {

}
