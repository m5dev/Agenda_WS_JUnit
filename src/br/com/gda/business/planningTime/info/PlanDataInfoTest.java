package br.com.gda.business.planningTime.info;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.gda.common.DbConnection;


@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class PlanDataInfoTest {
	private PlanDataInfo planDataOne;
	private PlanDataInfo planDataTwo;
	
	@Test
	public void equalPositive() {
		initEqualPositive();
		
		assertTrue(planDataOne.equals(planDataTwo));
		assertTrue(planDataTwo.equals(planDataOne));
		
		assertTrue(planDataOne.equals(planDataOne));
		assertTrue(planDataTwo.equals(planDataTwo));
		
		assertTrue(planDataOne.hashCode() == planDataTwo.hashCode());
	}
	
	
	
	private void initEqualPositive() {
		planDataOne = new PlanDataInfo();
		planDataOne.codOwner = 8;
		planDataOne.codEmployee = 300;
		planDataOne.codMat = 123;
		planDataOne.codWeekday = 1;
		planDataOne.codStore = 10;
		planDataOne.date = LocalDate.of(2018, 8, 6);
		planDataOne.beginTime = LocalTime.of(9, 0);
		planDataOne.endTime = LocalTime.of(18, 0);
		
		planDataTwo = new PlanDataInfo();
		planDataTwo.codOwner = 8;
		planDataTwo.codEmployee = 300;
		planDataTwo.codMat = 123;
		planDataTwo.codWeekday = 1;
		planDataTwo.codStore = 10;
		planDataTwo.date = LocalDate.of(2018, 8, 6);
		planDataTwo.beginTime = LocalTime.of(9, 0);
		planDataTwo.endTime = LocalTime.of(18, 0);
	}
	
	
	
	@Test
	public void equalNegativeOwner() {
		initEqualNegativeOwner();
		
		assertFalse(planDataOne.equals(planDataTwo));
		assertFalse(planDataTwo.equals(planDataOne));
		
		assertFalse(planDataOne.hashCode() == planDataTwo.hashCode());
	}
	
	
	
	private void initEqualNegativeOwner() {
		planDataOne = new PlanDataInfo();
		planDataOne.codOwner = 7;
		planDataOne.codEmployee = 300;
		planDataOne.codMat = 123;
		planDataOne.codWeekday = 1;
		planDataOne.codStore = 10;
		planDataOne.date = LocalDate.of(2018, 8, 6);
		planDataOne.beginTime = LocalTime.of(9, 0);
		planDataOne.endTime = LocalTime.of(18, 0);
		
		planDataTwo = new PlanDataInfo();
		planDataTwo.codOwner = 8;
		planDataTwo.codEmployee = 300;
		planDataTwo.codMat = 123;
		planDataTwo.codWeekday = 1;
		planDataTwo.codStore = 10;
		planDataTwo.date = LocalDate.of(2018, 8, 6);
		planDataTwo.beginTime = LocalTime.of(9, 0);
		planDataTwo.endTime = LocalTime.of(18, 0);
	}
	
	
	
	@Test
	public void equalNegativeStore() {
		initEqualNegativeStore();
		
		assertFalse(planDataOne.equals(planDataTwo));
		assertFalse(planDataTwo.equals(planDataOne));
		
		assertFalse(planDataOne.hashCode() == planDataTwo.hashCode());
	}
	
	
	
	private void initEqualNegativeStore() {
		planDataOne = new PlanDataInfo();
		planDataOne.codOwner = 8;
		planDataOne.codEmployee = 300;
		planDataOne.codMat = 123;
		planDataOne.codWeekday = 1;
		planDataOne.codStore = 11;
		planDataOne.date = LocalDate.of(2018, 8, 6);
		planDataOne.beginTime = LocalTime.of(9, 0);
		planDataOne.endTime = LocalTime.of(18, 0);
		
		planDataTwo = new PlanDataInfo();
		planDataTwo.codOwner = 8;
		planDataTwo.codEmployee = 300;
		planDataTwo.codMat = 123;
		planDataTwo.codWeekday = 1;
		planDataTwo.codStore = 10;
		planDataTwo.date = LocalDate.of(2018, 8, 6);
		planDataTwo.beginTime = LocalTime.of(9, 0);
		planDataTwo.endTime = LocalTime.of(18, 0);
	}
	
	
	
	@Test
	public void equalNegativeEmployee() {
		initEqualNegativeEmployee();
		
		assertFalse(planDataOne.equals(planDataTwo));
		assertFalse(planDataTwo.equals(planDataOne));
		
		assertFalse(planDataOne.hashCode() == planDataTwo.hashCode());
	}
	
	
	
	private void initEqualNegativeEmployee() {
		planDataOne = new PlanDataInfo();
		planDataOne.codOwner = 8;
		planDataOne.codEmployee = 301;
		planDataOne.codMat = 123;
		planDataOne.codWeekday = 1;
		planDataOne.codStore = 10;
		planDataOne.date = LocalDate.of(2018, 8, 6);
		planDataOne.beginTime = LocalTime.of(9, 0);
		planDataOne.endTime = LocalTime.of(18, 0);
		
		planDataTwo = new PlanDataInfo();
		planDataTwo.codOwner = 8;
		planDataTwo.codEmployee = 300;
		planDataTwo.codMat = 123;
		planDataTwo.codWeekday = 1;
		planDataTwo.codStore = 10;
		planDataTwo.date = LocalDate.of(2018, 8, 6);
		planDataTwo.beginTime = LocalTime.of(9, 0);
		planDataTwo.endTime = LocalTime.of(18, 0);
	}
	
	
	
	@Test
	public void equalNegativeMat() {
		initEqualNegativeMat();
		
		assertFalse(planDataOne.equals(planDataTwo));
		assertFalse(planDataTwo.equals(planDataOne));
		
		assertFalse(planDataOne.hashCode() == planDataTwo.hashCode());
	}
	
	
	
	private void initEqualNegativeMat() {
		planDataOne = new PlanDataInfo();
		planDataOne.codOwner = 8;
		planDataOne.codEmployee = 300;
		planDataOne.codMat = 124;
		planDataOne.codWeekday = 1;
		planDataOne.codStore = 10;
		planDataOne.date = LocalDate.of(2018, 8, 6);
		planDataOne.beginTime = LocalTime.of(9, 0);
		planDataOne.endTime = LocalTime.of(18, 0);
		
		planDataTwo = new PlanDataInfo();
		planDataTwo.codOwner = 8;
		planDataTwo.codEmployee = 300;
		planDataTwo.codMat = 123;
		planDataTwo.codWeekday = 1;
		planDataTwo.codStore = 10;
		planDataTwo.date = LocalDate.of(2018, 8, 6);
		planDataTwo.beginTime = LocalTime.of(9, 0);
		planDataTwo.endTime = LocalTime.of(18, 0);
	}
	
	
	
	@Test
	public void equalNegativeWeekday() {
		initEqualNegativeWeekday();
		
		assertFalse(planDataOne.equals(planDataTwo));
		assertFalse(planDataTwo.equals(planDataOne));
		
		assertFalse(planDataOne.hashCode() == planDataTwo.hashCode());
	}
	
	
	
	private void initEqualNegativeWeekday() {
		planDataOne = new PlanDataInfo();
		planDataOne.codOwner = 8;
		planDataOne.codEmployee = 300;
		planDataOne.codMat = 123;
		planDataOne.codWeekday = 2;
		planDataOne.codStore = 10;
		planDataOne.date = LocalDate.of(2018, 8, 6);
		planDataOne.beginTime = LocalTime.of(9, 0);
		planDataOne.endTime = LocalTime.of(18, 0);
		
		planDataTwo = new PlanDataInfo();
		planDataTwo.codOwner = 8;
		planDataTwo.codEmployee = 300;
		planDataTwo.codMat = 123;
		planDataTwo.codWeekday = 1;
		planDataTwo.codStore = 10;
		planDataTwo.date = LocalDate.of(2018, 8, 6);
		planDataTwo.beginTime = LocalTime.of(9, 0);
		planDataTwo.endTime = LocalTime.of(18, 0);
	}
	
	
	
	@Test
	public void equalNegativeDate() {
		initEqualNegativeDate();
		
		assertFalse(planDataOne.equals(planDataTwo));
		assertFalse(planDataTwo.equals(planDataOne));
		
		assertFalse(planDataOne.hashCode() == planDataTwo.hashCode());
	}
	
	
	
	private void initEqualNegativeDate() {
		planDataOne = new PlanDataInfo();
		planDataOne.codOwner = 8;
		planDataOne.codEmployee = 300;
		planDataOne.codMat = 123;
		planDataOne.codWeekday = 1;
		planDataOne.codStore = 10;
		planDataOne.date = LocalDate.of(2018, 8, 7);
		planDataOne.beginTime = LocalTime.of(9, 0);
		planDataOne.endTime = LocalTime.of(18, 0);
		
		planDataTwo = new PlanDataInfo();
		planDataTwo.codOwner = 8;
		planDataTwo.codEmployee = 300;
		planDataTwo.codMat = 123;
		planDataTwo.codWeekday = 1;
		planDataTwo.codStore = 10;
		planDataTwo.date = LocalDate.of(2018, 8, 6);
		planDataTwo.beginTime = LocalTime.of(9, 0);
		planDataTwo.endTime = LocalTime.of(18, 0);
	}
	
	
	
	@Test
	public void equalNegativeNullDate() {
		initEqualNegativeNullDate();
		
		assertFalse(planDataOne.equals(planDataTwo));
		assertFalse(planDataTwo.equals(planDataOne));
		
		assertFalse(planDataOne.hashCode() == planDataTwo.hashCode());
	}
	
	
	
	private void initEqualNegativeNullDate() {
		planDataOne = new PlanDataInfo();
		planDataOne.codOwner = 8;
		planDataOne.codEmployee = 300;
		planDataOne.codMat = 123;
		planDataOne.codWeekday = 1;
		planDataOne.codStore = 10;
		planDataOne.date = null;
		planDataOne.beginTime = LocalTime.of(9, 0);
		planDataOne.endTime = LocalTime.of(18, 0);
		
		planDataTwo = new PlanDataInfo();
		planDataTwo.codOwner = 8;
		planDataTwo.codEmployee = 300;
		planDataTwo.codMat = 123;
		planDataTwo.codWeekday = 1;
		planDataTwo.codStore = 10;
		planDataTwo.date = LocalDate.of(2018, 8, 6);
		planDataTwo.beginTime = LocalTime.of(9, 0);
		planDataTwo.endTime = LocalTime.of(18, 0);
	}
	
	
	
	@Test
	public void equalNegativeBeginTime() {
		initEqualNegativeBeginTime();
		
		assertFalse(planDataOne.equals(planDataTwo));
		assertFalse(planDataTwo.equals(planDataOne));
		
		assertFalse(planDataOne.hashCode() == planDataTwo.hashCode());
	}
	
	
	
	private void initEqualNegativeBeginTime() {
		planDataOne = new PlanDataInfo();
		planDataOne.codOwner = 8;
		planDataOne.codEmployee = 300;
		planDataOne.codMat = 123;
		planDataOne.codWeekday = 1;
		planDataOne.codStore = 10;
		planDataOne.date = LocalDate.of(2018, 8, 6);
		planDataOne.beginTime = LocalTime.of(9, 1);
		planDataOne.endTime = LocalTime.of(18, 0);
		
		planDataTwo = new PlanDataInfo();
		planDataTwo.codOwner = 8;
		planDataTwo.codEmployee = 300;
		planDataTwo.codMat = 123;
		planDataTwo.codWeekday = 1;
		planDataTwo.codStore = 10;
		planDataTwo.date = LocalDate.of(2018, 8, 6);
		planDataTwo.beginTime = LocalTime.of(9, 0);
		planDataTwo.endTime = LocalTime.of(18, 0);
	}
	
	
	
	@Test
	public void equalNegativeNullBeginTime() {
		initEqualNegativeNullBeginTime();
		
		assertFalse(planDataOne.equals(planDataTwo));
		assertFalse(planDataTwo.equals(planDataOne));
		
		assertFalse(planDataOne.hashCode() == planDataTwo.hashCode());
	}
	
	
	
	private void initEqualNegativeNullBeginTime() {
		planDataOne = new PlanDataInfo();
		planDataOne.codOwner = 8;
		planDataOne.codEmployee = 300;
		planDataOne.codMat = 123;
		planDataOne.codWeekday = 1;
		planDataOne.codStore = 10;
		planDataOne.date = LocalDate.of(2018, 8, 6);
		planDataOne.beginTime = null;
		planDataOne.endTime = LocalTime.of(18, 0);
		
		planDataTwo = new PlanDataInfo();
		planDataTwo.codOwner = 8;
		planDataTwo.codEmployee = 300;
		planDataTwo.codMat = 123;
		planDataTwo.codWeekday = 1;
		planDataTwo.codStore = 10;
		planDataTwo.date = LocalDate.of(2018, 8, 6);
		planDataTwo.beginTime = LocalTime.of(9, 0);
		planDataTwo.endTime = LocalTime.of(18, 0);
	}
	
	
	
	@Test
	public void equalNegativeNullEndTime() {
		initEqualNegativeNullEndTime();
		
		assertFalse(planDataOne.equals(planDataTwo));
		assertFalse(planDataTwo.equals(planDataOne));
		
		assertFalse(planDataOne.hashCode() == planDataTwo.hashCode());
	}
	
	
	
	private void initEqualNegativeNullEndTime() {
		planDataOne = new PlanDataInfo();
		planDataOne.codOwner = 8;
		planDataOne.codEmployee = 300;
		planDataOne.codMat = 123;
		planDataOne.codWeekday = 1;
		planDataOne.codStore = 10;
		planDataOne.date = LocalDate.of(2018, 8, 6);
		planDataOne.beginTime = LocalTime.of(9, 0);
		planDataOne.endTime = null;
		
		planDataTwo = new PlanDataInfo();
		planDataTwo.codOwner = 8;
		planDataTwo.codEmployee = 300;
		planDataTwo.codMat = 123;
		planDataTwo.codWeekday = 1;
		planDataTwo.codStore = 10;
		planDataTwo.date = LocalDate.of(2018, 8, 6);
		planDataTwo.beginTime = LocalTime.of(9, 0);
		planDataTwo.endTime = LocalTime.of(18, 0);
	}
	
	
	
	@Test
	public void equalNegativeEndTime() {
		initEqualNegativeEndTime();
		
		assertFalse(planDataOne.equals(planDataTwo));
		assertFalse(planDataTwo.equals(planDataOne));
		
		assertFalse(planDataOne.hashCode() == planDataTwo.hashCode());
	}
	
	
	
	private void initEqualNegativeEndTime() {
		planDataOne = new PlanDataInfo();
		planDataOne.codOwner = 8;
		planDataOne.codEmployee = 300;
		planDataOne.codMat = 123;
		planDataOne.codWeekday = 1;
		planDataOne.codStore = 10;
		planDataOne.date = LocalDate.of(2018, 8, 6);
		planDataOne.beginTime = LocalTime.of(9, 0);
		planDataOne.endTime = LocalTime.of(18, 1);
		
		planDataTwo = new PlanDataInfo();
		planDataTwo.codOwner = 8;
		planDataTwo.codEmployee = 300;
		planDataTwo.codMat = 123;
		planDataTwo.codWeekday = 1;
		planDataTwo.codStore = 10;
		planDataTwo.date = LocalDate.of(2018, 8, 6);
		planDataTwo.beginTime = LocalTime.of(9, 0);
		planDataTwo.endTime = LocalTime.of(18, 0);
	}
	
	
	
	@Test
	public void equalNull() {
		initEqualNull();
		
		assertFalse(planDataOne.equals(planDataTwo));
	}
	
	
	
	private void initEqualNull() {
		planDataOne = new PlanDataInfo();
		planDataOne.codOwner = 8;
		planDataOne.codEmployee = 300;
		planDataOne.codMat = 123;
		planDataOne.codWeekday = 1;
		planDataOne.codStore = 10;
		planDataOne.date = LocalDate.of(2018, 8, 6);
		planDataOne.beginTime = LocalTime.of(9, 0);
		planDataOne.endTime = LocalTime.of(18, 1);
		
		planDataTwo = null;
	}
	
	
	@Test
	public void equalPositiveInit() {
		initEqualPositiveInit();
		
		assertTrue(planDataOne.equals(planDataTwo));
		assertTrue(planDataTwo.equals(planDataOne));
		
		assertTrue(planDataOne.equals(planDataOne));
		assertTrue(planDataTwo.equals(planDataTwo));
		
		assertTrue(planDataOne.hashCode() == planDataTwo.hashCode());
	}
	
	
	
	private void initEqualPositiveInit() {
		planDataOne = new PlanDataInfo();		
		planDataTwo = new PlanDataInfo();
	}
	
	
	
	@Test
	public void equalMaxLong() {
		initEqualMaxLong();
		
		assertFalse(planDataOne.equals(planDataTwo));
		assertFalse(planDataTwo.equals(planDataOne));
		
		assertTrue(planDataOne.equals(planDataOne));
		assertTrue(planDataTwo.equals(planDataTwo));
		
		assertFalse(planDataOne.hashCode() == 0);
		assertFalse(planDataOne.hashCode() == planDataTwo.hashCode());
	}
	
	
	
	private void initEqualMaxLong() {
		planDataOne = new PlanDataInfo();
		planDataOne.codOwner = Long.MAX_VALUE;
		planDataOne.codEmployee = Long.MAX_VALUE;
		planDataOne.codMat = Long.MAX_VALUE;
		planDataOne.codWeekday = Integer.MAX_VALUE;
		planDataOne.codStore = Long.MAX_VALUE;
		planDataOne.date = LocalDate.of(2018, 8, 6);
		planDataOne.beginTime = LocalTime.of(9, 0);
		planDataOne.endTime = LocalTime.of(18, 1);
		
		planDataTwo = new PlanDataInfo();
		planDataTwo.codOwner = 8;
		planDataTwo.codEmployee = 300;
		planDataTwo.codMat = 123;
		planDataTwo.codWeekday = 1;
		planDataTwo.codStore = 10;
		planDataTwo.date = LocalDate.of(2018, 8, 6);
		planDataTwo.beginTime = LocalTime.of(9, 0);
		planDataTwo.endTime = LocalTime.of(18, 0);
	}
	
	
	
	@Test
	public void equalMinLong() {
		initEqualMinLong();
		
		assertFalse(planDataOne.equals(planDataTwo));
		assertFalse(planDataTwo.equals(planDataOne));
		
		assertTrue(planDataOne.equals(planDataOne));
		assertTrue(planDataTwo.equals(planDataTwo));
		
		assertFalse(planDataOne.hashCode() == 0);
		assertFalse(planDataOne.hashCode() == planDataTwo.hashCode());
	}
	
	
	
	private void initEqualMinLong() {
		planDataOne = new PlanDataInfo();
		planDataOne.codOwner = Long.MIN_VALUE;
		planDataOne.codEmployee = Long.MIN_VALUE;
		planDataOne.codMat = Long.MIN_VALUE;
		planDataOne.codWeekday = Integer.MIN_VALUE;
		planDataOne.codStore = Long.MIN_VALUE;
		planDataOne.date = LocalDate.of(2018, 8, 6);
		planDataOne.beginTime = LocalTime.of(9, 0);
		planDataOne.endTime = LocalTime.of(18, 1);
		
		planDataTwo = new PlanDataInfo();
		planDataTwo.codOwner = 8;
		planDataTwo.codEmployee = 300;
		planDataTwo.codMat = 123;
		planDataTwo.codWeekday = 1;
		planDataTwo.codStore = 10;
		planDataTwo.date = LocalDate.of(2018, 8, 6);
		planDataTwo.beginTime = LocalTime.of(9, 0);
		planDataTwo.endTime = LocalTime.of(18, 0);
	}
}
