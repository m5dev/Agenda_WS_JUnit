package br.com.gda.common;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({DateRangeTest.class,
	           TimeRangeTest.class})
public final class CommonSuiteTest {

}
