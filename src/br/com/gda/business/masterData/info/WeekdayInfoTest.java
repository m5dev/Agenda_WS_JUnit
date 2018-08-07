package br.com.gda.business.masterData.info;


import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.gda.common.DbConnection;


@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class WeekdayInfoTest {
	private WeekdayInfo storeOne;
	private WeekdayInfo storeTwo;
	
	@Test
	public void equalPositive() {
		initEqualPositive();
		
		assertTrue(storeOne.equals(storeTwo));
		assertTrue(storeTwo.equals(storeOne));
		
		assertTrue(storeOne.equals(storeOne));
		assertTrue(storeTwo.equals(storeTwo));
		
		assertTrue(storeOne.hashCode() == storeTwo.hashCode());
	}
	
	
	
	private void initEqualPositive() {
		storeOne = new WeekdayInfo();
		storeOne.codWeekday = 8;
		
		storeTwo = new WeekdayInfo();
		storeTwo.codWeekday = 8;
	}
	
	
	
	@Test
	public void equalNegative() {
		initEqualNegative();
		
		assertFalse(storeOne.equals(storeTwo));
		assertFalse(storeTwo.equals(storeOne));
		
		assertFalse(storeOne.hashCode() == storeTwo.hashCode());
	}
	
	
	
	private void initEqualNegative() {
		storeOne = new WeekdayInfo();
		storeOne.codWeekday = 1;
		
		storeTwo = new WeekdayInfo();
		storeTwo.codWeekday = 2;
	}
	
	
	
	@Test
	public void equalNull() {
		initEqualNull();
		
		assertFalse(storeOne.equals(storeTwo));
	}
	
	
	
	private void initEqualNull() {
		storeOne = new WeekdayInfo();
		storeOne.codWeekday = 8;
		
		storeTwo = null;
	}
	
	
	@Test
	public void equalPositiveInit() {
		initEqualPositiveInit();
		
		assertTrue(storeOne.equals(storeTwo));
		assertTrue(storeTwo.equals(storeOne));
		
		assertTrue(storeOne.equals(storeOne));
		assertTrue(storeTwo.equals(storeTwo));
		
		assertTrue(storeOne.hashCode() == storeTwo.hashCode());
	}
	
	
	
	private void initEqualPositiveInit() {
		storeOne = new WeekdayInfo();		
		storeTwo = new WeekdayInfo();
	}
	
	
	
	@Test
	public void equalMaxLong() {
		initEqualMaxLong();
		
		assertFalse(storeOne.equals(storeTwo));
		assertFalse(storeTwo.equals(storeOne));
		
		assertTrue(storeOne.equals(storeOne));
		assertTrue(storeTwo.equals(storeTwo));
		
		assertFalse(storeOne.hashCode() == 0);
		assertFalse(storeOne.hashCode() == storeTwo.hashCode());
	}
	
	
	
	private void initEqualMaxLong() {
		storeOne = new WeekdayInfo();
		storeOne.codWeekday = Integer.MAX_VALUE;
		
		storeTwo = new WeekdayInfo();
		storeTwo.codWeekday = 8;
	}
	
	
	
	@Test
	public void equalMinLong() {
		initEqualMinLong();
		
		assertFalse(storeOne.equals(storeTwo));
		assertFalse(storeTwo.equals(storeOne));
		
		assertTrue(storeOne.equals(storeOne));
		assertTrue(storeTwo.equals(storeTwo));
		
		assertFalse(storeOne.hashCode() == 0);
		assertFalse(storeOne.hashCode() == storeTwo.hashCode());
	}
	
	
	
	private void initEqualMinLong() {
		storeOne = new WeekdayInfo();
		storeOne.codWeekday = Integer.MIN_VALUE;
		
		storeTwo = new WeekdayInfo();
		storeTwo.codWeekday = 8;
	}
}
