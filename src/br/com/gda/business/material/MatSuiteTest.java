package br.com.gda.business.material;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.gda.business.material.info.MatInfoSuiteTest;
import br.com.gda.business.material.model.MatModelSuiteTest;

@RunWith(Suite.class)
@SuiteClasses({MatModelSuiteTest.class,
	           MatInfoSuiteTest.class})
public class MatSuiteTest {

}
