package br.com.gda.business.employee.info;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.gda.common.DbConnection;


@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class EmpInfoTest {
	private EmpInfo empOne;
	private EmpInfo empTwo;
	
	@Test
	public void equalPositive() {
		initEqualPositive();
		
		assertTrue(empOne.equals(empTwo));
		assertTrue(empTwo.equals(empOne));
		
		assertTrue(empOne.equals(empOne));
		assertTrue(empTwo.equals(empTwo));
		
		assertTrue(empOne.hashCode() == empTwo.hashCode());
	}
	
	
	
	private void initEqualPositive() {
		empOne = new EmpInfo();
		empOne.codOwner = 8;
		empOne.codEmployee = 10;
		
		empTwo = new EmpInfo();
		empTwo.codOwner = 8;
		empTwo.codEmployee = 10;
	}
	
	
	
	@Test
	public void equalNegativeOwner() {
		initEqualNegativeOwner();
		
		assertFalse(empOne.equals(empTwo));
		assertFalse(empTwo.equals(empOne));
		
		assertFalse(empOne.hashCode() == empTwo.hashCode());
	}
	
	
	
	private void initEqualNegativeOwner() {
		empOne = new EmpInfo();
		empOne.codOwner = 8;
		empOne.codEmployee = 10;
		
		empTwo = new EmpInfo();
		empTwo.codOwner = 7;
		empTwo.codEmployee = 10;
	}
	
	
	
	@Test
	public void equalNegativeStore() {
		initEqualNegativeStore();
		
		assertFalse(empOne.equals(empTwo));
		assertFalse(empTwo.equals(empOne));
		
		assertFalse(empOne.hashCode() == empTwo.hashCode());
	}
	
	
	
	private void initEqualNegativeStore() {
		empOne = new EmpInfo();
		empOne.codOwner = 8;
		empOne.codEmployee = 10;
		
		empTwo = new EmpInfo();
		empTwo.codOwner = 8;
		empTwo.codEmployee = 11;
	}
	
	
	
	@Test
	public void equalNull() {
		initEqualNull();
		
		assertFalse(empOne.equals(empTwo));
	}
	
	
	
	private void initEqualNull() {
		empOne = new EmpInfo();
		empOne.codOwner = 8;
		empOne.codEmployee = 10;
		
		empTwo = null;
	}
	
	
	@Test
	public void equalPositiveInit() {
		initEqualPositiveInit();
		
		assertTrue(empOne.equals(empTwo));
		assertTrue(empTwo.equals(empOne));
		
		assertTrue(empOne.equals(empOne));
		assertTrue(empTwo.equals(empTwo));
		
		assertTrue(empOne.hashCode() == empTwo.hashCode());
	}
	
	
	
	private void initEqualPositiveInit() {
		empOne = new EmpInfo();		
		empTwo = new EmpInfo();
	}
	
	
	
	@Test
	public void equalMaxLong() {
		initEqualMaxLong();
		
		assertFalse(empOne.equals(empTwo));
		assertFalse(empTwo.equals(empOne));
		
		assertTrue(empOne.equals(empOne));
		assertTrue(empTwo.equals(empTwo));
		
		assertFalse(empOne.hashCode() == 0);
		assertFalse(empOne.hashCode() == empTwo.hashCode());
	}
	
	
	
	private void initEqualMaxLong() {
		empOne = new EmpInfo();
		empOne.codOwner = Long.MAX_VALUE;
		empOne.codEmployee = Long.MAX_VALUE;
		
		empTwo = new EmpInfo();
		empTwo.codOwner = 8;
		empTwo.codEmployee = 10;
	}
	
	
	
	@Test
	public void equalMinLong() {
		initEqualMinLong();
		
		assertFalse(empOne.equals(empTwo));
		assertFalse(empTwo.equals(empOne));
		
		assertTrue(empOne.equals(empOne));
		assertTrue(empTwo.equals(empTwo));
		
		assertFalse(empOne.hashCode() == 0);
		assertFalse(empOne.hashCode() == empTwo.hashCode());
	}
	
	
	
	private void initEqualMinLong() {
		empOne = new EmpInfo();
		empOne.codOwner = Long.MIN_VALUE;
		empOne.codEmployee = Long.MIN_VALUE;
		
		empTwo = new EmpInfo();
		empTwo.codOwner = 8;
		empTwo.codEmployee = 10;
	}
}
