package br.com.gda.business.store.info;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.gda.business.store.info.StoreInfo;
import br.com.gda.common.DbConnection;


@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class StoreInfoTest {
	private StoreInfo storeOne;
	private StoreInfo storeTwo;
	
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
		storeOne = new StoreInfo();
		storeOne.codOwner = 8;
		storeOne.codStore = 10;
		
		storeTwo = new StoreInfo();
		storeTwo.codOwner = 8;
		storeTwo.codStore = 10;
	}
	
	
	
	@Test
	public void equalNegativeOwner() {
		initEqualNegativeOwner();
		
		assertFalse(storeOne.equals(storeTwo));
		assertFalse(storeTwo.equals(storeOne));
		
		assertFalse(storeOne.hashCode() == storeTwo.hashCode());
	}
	
	
	
	private void initEqualNegativeOwner() {
		storeOne = new StoreInfo();
		storeOne.codOwner = 8;
		storeOne.codStore = 10;
		
		storeTwo = new StoreInfo();
		storeTwo.codOwner = 7;
		storeTwo.codStore = 10;
	}
	
	
	
	@Test
	public void equalNegativeStore() {
		initEqualNegativeStore();
		
		assertFalse(storeOne.equals(storeTwo));
		assertFalse(storeTwo.equals(storeOne));
		
		assertFalse(storeOne.hashCode() == storeTwo.hashCode());
	}
	
	
	
	private void initEqualNegativeStore() {
		storeOne = new StoreInfo();
		storeOne.codOwner = 8;
		storeOne.codStore = 10;
		
		storeTwo = new StoreInfo();
		storeTwo.codOwner = 8;
		storeTwo.codStore = 11;
	}
	
	
	
	@Test
	public void equalNull() {
		initEqualNull();
		
		assertFalse(storeOne.equals(storeTwo));
	}
	
	
	
	private void initEqualNull() {
		storeOne = new StoreInfo();
		storeOne.codOwner = 8;
		storeOne.codStore = 10;
		
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
		storeOne = new StoreInfo();		
		storeTwo = new StoreInfo();
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
		storeOne = new StoreInfo();
		storeOne.codOwner = Long.MAX_VALUE;
		storeOne.codStore = Long.MAX_VALUE;
		
		storeTwo = new StoreInfo();
		storeTwo.codOwner = 8;
		storeTwo.codStore = 10;
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
		storeOne = new StoreInfo();
		storeOne.codOwner = Long.MIN_VALUE;
		storeOne.codStore = Long.MIN_VALUE;
		
		storeTwo = new StoreInfo();
		storeTwo.codOwner = 8;
		storeTwo.codStore = 10;
	}
}
