package br.com.gda.business.employeeWorkTime.info;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.gda.business.storeWorkTime.info.StoreWTimeInfo;
import br.com.gda.common.DbConnection;

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public final class EmpWTimeInfoTest {
	private EmpWTimeInfo empWTimeInfo;
	private StoreWTimeInfo storeWTimeInfo;	
	
	
	@Test
	public void copyFromSameClass() {
		initEmpWTimeInfo();
		EmpWTimeInfo copy = EmpWTimeInfo.copyFrom(empWTimeInfo);
		
		assertTrue(copy.codOwner == 1);
		assertTrue(copy.codStore == 2);
		assertTrue(copy.codEmployee == 3);
		assertTrue(copy.codWeekday == 4);
		assertTrue(copy.txtWeekday.equals("text 123"));
		assertTrue(copy.beginTime.equals(LocalTime.of(9, 31)));
		assertTrue(copy.endTime.equals(LocalTime.of(17, 59)));
		assertTrue(copy.codTimezone.equals("America/Sao_Paulo"));
		assertTrue(copy.codLanguage.equals("PT"));
		assertTrue(copy.recordMode.equals("X"));
		
		assertFalse(copy.txtWeekday == empWTimeInfo.txtWeekday);
		assertFalse(copy.beginTime == empWTimeInfo.beginTime);
		assertFalse(copy.endTime == empWTimeInfo.endTime);
		assertFalse(copy.codTimezone == empWTimeInfo.codTimezone);
		assertFalse(copy.codLanguage == empWTimeInfo.codLanguage);
		assertFalse(copy.recordMode == empWTimeInfo.recordMode);
	}
	

	private void initEmpWTimeInfo() {
		empWTimeInfo = new EmpWTimeInfo();
		
		empWTimeInfo.codOwner = 1;
		empWTimeInfo.codStore = 2;
		empWTimeInfo.codEmployee = 3;
		empWTimeInfo.codWeekday = 4;
		empWTimeInfo.txtWeekday = "text 123";
		empWTimeInfo.beginTime = LocalTime.of(9, 31);
		empWTimeInfo.endTime = LocalTime.of(17, 59);
		empWTimeInfo.codTimezone = "America/Sao_Paulo";
		empWTimeInfo.codLanguage = "PT";
		empWTimeInfo.recordMode = "X";
	}
	
	
	
	@Test
	public void copyFromStoreWTime() {
		initStoreWTimeInfo();
		EmpWTimeInfo copy = EmpWTimeInfo.copyFrom(storeWTimeInfo);
		
		assertTrue(copy.codOwner == 1);
		assertTrue(copy.codStore == 2);
		assertTrue(copy.codEmployee == -1);
		assertTrue(copy.codWeekday == 4);
		assertTrue(copy.txtWeekday.equals("text 123"));
		assertTrue(copy.beginTime.equals(LocalTime.of(9, 31)));
		assertTrue(copy.endTime.equals(LocalTime.of(17, 59)));
		assertTrue(copy.codTimezone.equals("America/Sao_Paulo"));
		assertTrue(copy.codLanguage.equals("PT"));
		assertTrue(copy.recordMode.equals("X"));
		
		assertFalse(copy.txtWeekday == storeWTimeInfo.txtWeekday);
		assertFalse(copy.beginTime == storeWTimeInfo.beginTime);
		assertFalse(copy.endTime == storeWTimeInfo.endTime);
		assertFalse(copy.codTimezone == storeWTimeInfo.codTimezone);
		assertFalse(copy.codLanguage == storeWTimeInfo.codLanguage);
		assertFalse(copy.recordMode == storeWTimeInfo.recordMode);
	}
	
	
	
	private void initStoreWTimeInfo() {
		storeWTimeInfo = new StoreWTimeInfo();
		
		storeWTimeInfo.codOwner = 1;
		storeWTimeInfo.codStore = 2;
		storeWTimeInfo.codWeekday = 4;
		storeWTimeInfo.txtWeekday = "text 123";
		storeWTimeInfo.beginTime = LocalTime.of(9, 31);
		storeWTimeInfo.endTime = LocalTime.of(17, 59);
		storeWTimeInfo.codTimezone = "America/Sao_Paulo";
		storeWTimeInfo.codLanguage = "PT";
		storeWTimeInfo.recordMode = "X";
	}
	
	
	
	@Test
	public void copyFromNullTime() {
		initNullTimeInfo();
		EmpWTimeInfo copy = EmpWTimeInfo.copyFrom(storeWTimeInfo);
		
		assertTrue(copy.codOwner == 1);
		assertTrue(copy.codStore == 2);
		assertTrue(copy.codEmployee == -1);
		assertTrue(copy.codWeekday == 4);
		assertTrue(copy.txtWeekday.equals("text 123"));
		assertTrue(copy.beginTime == null);
		assertTrue(copy.endTime.equals(LocalTime.of(17, 59)));
		assertTrue(copy.codTimezone.equals("America/Sao_Paulo"));
		assertTrue(copy.codLanguage.equals("PT"));
		assertTrue(copy.recordMode.equals("X"));
		
		assertFalse(copy.txtWeekday == storeWTimeInfo.txtWeekday);
		assertFalse(copy.endTime == storeWTimeInfo.endTime);
		assertFalse(copy.codTimezone == storeWTimeInfo.codTimezone);
		assertFalse(copy.codLanguage == storeWTimeInfo.codLanguage);
		assertFalse(copy.recordMode == storeWTimeInfo.recordMode);
	}
	
	
	
	private void initNullTimeInfo() {
		storeWTimeInfo = new StoreWTimeInfo();
		
		storeWTimeInfo.codOwner = 1;
		storeWTimeInfo.codStore = 2;
		storeWTimeInfo.codWeekday = 4;
		storeWTimeInfo.txtWeekday = "text 123";
		storeWTimeInfo.beginTime = null;
		storeWTimeInfo.endTime = LocalTime.of(17, 59);
		storeWTimeInfo.codTimezone = "America/Sao_Paulo";
		storeWTimeInfo.codLanguage = "PT";
		storeWTimeInfo.recordMode = "X";
	}
	
	
	
	@Test
	public void copyFromNull() {
		EmpWTimeInfo copy = (EmpWTimeInfo) EmpWTimeInfo.copyFrom(null);
		assertTrue(copy == null);
		
		List<EmpWTimeInfo> copies = EmpWTimeInfo.copyFrom(null);
		assertTrue(copies == null);
	}
}
