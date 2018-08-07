package br.com.gda.business.material.info;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.gda.common.DbConnection;


@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class MatInfoTest {
	private MatInfo matOne;
	private MatInfo matTwo;
	
	@Test
	public void equalPositive() {
		initEqualPositive();
		
		assertTrue(matOne.equals(matTwo));
		assertTrue(matTwo.equals(matOne));
		
		assertTrue(matOne.equals(matOne));
		assertTrue(matTwo.equals(matTwo));
		
		assertTrue(matOne.hashCode() == matTwo.hashCode());
	}
	
	
	
	private void initEqualPositive() {
		matOne = new MatInfo();
		matOne.codOwner = 8;
		matOne.codMat = 10;
		
		matTwo = new MatInfo();
		matTwo.codOwner = 8;
		matTwo.codMat = 10;
	}
	
	
	
	@Test
	public void equalNegativeOwner() {
		initEqualNegativeOwner();
		
		assertFalse(matOne.equals(matTwo));
		assertFalse(matTwo.equals(matOne));
		
		assertFalse(matOne.hashCode() == matTwo.hashCode());
	}
	
	
	
	private void initEqualNegativeOwner() {
		matOne = new MatInfo();
		matOne.codOwner = 8;
		matOne.codMat = 10;
		
		matTwo = new MatInfo();
		matTwo.codOwner = 7;
		matTwo.codMat = 10;
	}
	
	
	
	@Test
	public void equalNegativeStore() {
		initEqualNegativeStore();
		
		assertFalse(matOne.equals(matTwo));
		assertFalse(matTwo.equals(matOne));
		
		assertFalse(matOne.hashCode() == matTwo.hashCode());
	}
	
	
	
	private void initEqualNegativeStore() {
		matOne = new MatInfo();
		matOne.codOwner = 8;
		matOne.codMat = 10;
		
		matTwo = new MatInfo();
		matTwo.codOwner = 8;
		matTwo.codMat = 11;
	}
	
	
	
	@Test
	public void equalNull() {
		initEqualNull();
		
		assertFalse(matOne.equals(matTwo));
	}
	
	
	
	private void initEqualNull() {
		matOne = new MatInfo();
		matOne.codOwner = 8;
		matOne.codMat = 10;
		
		matTwo = null;
	}
	
	
	@Test
	public void equalPositiveInit() {
		initEqualPositiveInit();
		
		assertTrue(matOne.equals(matTwo));
		assertTrue(matTwo.equals(matOne));
		
		assertTrue(matOne.equals(matOne));
		assertTrue(matTwo.equals(matTwo));
		
		assertTrue(matOne.hashCode() == matTwo.hashCode());
	}
	
	
	
	private void initEqualPositiveInit() {
		matOne = new MatInfo();		
		matTwo = new MatInfo();
	}
	
	
	
	@Test
	public void equalMaxLong() {
		initEqualMaxLong();
		
		assertFalse(matOne.equals(matTwo));
		assertFalse(matTwo.equals(matOne));
		
		assertTrue(matOne.equals(matOne));
		assertTrue(matTwo.equals(matTwo));
		
		assertFalse(matOne.hashCode() == 0);
		assertFalse(matOne.hashCode() == matTwo.hashCode());
	}
	
	
	
	private void initEqualMaxLong() {
		matOne = new MatInfo();
		matOne.codOwner = Long.MAX_VALUE;
		matOne.codMat = Long.MAX_VALUE;
		
		matTwo = new MatInfo();
		matTwo.codOwner = 8;
		matTwo.codMat = 10;
	}
	
	
	
	@Test
	public void equalMinLong() {
		initEqualMinLong();
		
		assertFalse(matOne.equals(matTwo));
		assertFalse(matTwo.equals(matOne));
		
		assertTrue(matOne.equals(matOne));
		assertTrue(matTwo.equals(matTwo));
		
		assertFalse(matOne.hashCode() == 0);
		assertFalse(matOne.hashCode() == matTwo.hashCode());
	}
	
	
	
	private void initEqualMinLong() {
		matOne = new MatInfo();
		matOne.codOwner = Long.MIN_VALUE;
		matOne.codMat = Long.MIN_VALUE;
		
		matTwo = new MatInfo();
		matTwo.codOwner = 8;
		matTwo.codMat = 10;
	}
}
