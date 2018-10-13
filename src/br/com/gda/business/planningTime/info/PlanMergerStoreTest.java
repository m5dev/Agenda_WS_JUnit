package br.com.gda.business.planningTime.info;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.gda.business.store.info.StoreInfo;
import br.com.gda.common.DbConnection;


@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class PlanMergerStoreTest {
	private PlanMergerStore merger;
	private PlanInfo planInfo;
	private PlanInfo planInfoTwo;
	private StoreInfo storeInfo;
	private StoreInfo storeInfoTwo;
	private List<PlanInfo> planInfos; 
	private List<StoreInfo> storeInfos;
	
	
	
	@Test (expected = NullPointerException.class)
	public void nullPlanInfo() {
		initNullPlanInfo();
		merger.merge(planInfo, storeInfo);
	}
	
	
	
	private void initNullPlanInfo() {
		planInfo = null;
		initStoreInfo();
		
		merger = new PlanMergerStore();
	}
	
	
	
	@Test (expected = NullPointerException.class)
	public void nullPlanInfos() {
		initNullPlanInfos();
		merger.merge(planInfos, storeInfos);
	}
	
	
	
	private void initNullPlanInfos() {
		planInfos = null;
		initStoreInfos();
		
		merger = new PlanMergerStore();
	}
	
	
	
	@Test (expected = IllegalArgumentException.class)
	public void emptyPlanInfos() {
		initEmptyPlanInfos();
		merger.merge(planInfos, storeInfos);
	}
	
	
	
	private void initEmptyPlanInfos() {
		planInfos = Collections.emptyList();
		initStoreInfos();
		
		merger = new PlanMergerStore();
	}
	
	
	
	@Test (expected = IllegalArgumentException.class)
	public void emptyStoreInfos() {
		initEmptyStoreInfos();
		merger.merge(planInfos, storeInfos);
	}
	
	
	
	private void initEmptyStoreInfos() {
		storeInfos = Collections.emptyList();
		initPlanInfos();
		
		merger = new PlanMergerStore();
	}
	
	
	
	@Test (expected = NullPointerException.class)
	public void nullStoreInfo() {
		initNullStorePlanInfo();
		merger.merge(planInfo, storeInfo);
	}
	
	
	
	private void initNullStorePlanInfo() {
		storeInfo = null;
		initPlanInfo();
		
		merger = new PlanMergerStore();
	}
	
	
	
	@Test (expected = NullPointerException.class)
	public void nullStoreInfos() {
		initNullStorePlanInfos();
		merger.merge(planInfos, storeInfos);
	}
	
	
	
	private void initNullStorePlanInfos() {
		storeInfos = null;
		initPlanInfos();
		
		merger = new PlanMergerStore();
	}
	
	
	
	@Test
	public void singleMerge() {
		initSingleMerge();
		PlanInfo merged = merger.merge(planInfo, storeInfo);
		
		assertTrue(merged.materials.isEmpty());
		assertTrue(merged.employees.isEmpty());
		assertTrue(merged.weekdays.isEmpty());
		assertTrue(merged.stores.size() == 1);
		assertTrue(merged.datas.size() == 1);
		
		assertTrue(merged.stores.get(0).equals(storeInfo));
		assertTrue(merged.datas.get(0).equals(planInfo.datas.get(0)));
	}
	
	
	
	private void initSingleMerge() {
		initStoreInfo();
		initPlanInfo();
		
		merger = new PlanMergerStore();
	}
	
	
	
	@Test (expected = IllegalArgumentException.class)
	public void singleCodOwnerMismatch() {
		initSingleCodOwnerMismatch();
		merger.merge(planInfo, storeInfo);
	}
	
	
	
	private void initSingleCodOwnerMismatch() {
		initStoreInfo();
		initPlanInfo();
		
		storeInfo.codOwner = 123;
		
		merger = new PlanMergerStore();
	}
	
	
	
	@Test (expected = IllegalArgumentException.class)
	public void singleCodOwnerMissing() {
		initSingleCodOwnerMissing();
		merger.merge(planInfo, storeInfo);
	}
	
	
	
	private void initSingleCodOwnerMissing() {
		initStoreInfo();
		initPlanInfo();
		
		planInfo.datas.get(0).codOwner = -1;
		
		merger = new PlanMergerStore();
	}
	
	
	
	@Test
	public void listCodOwnerMissing() {
		initListCodOwnerMissing();
		List<PlanInfo> merged = merger.merge(planInfos, storeInfos);
		
		assertTrue(merged.size() == 2);
		
		assertTrue(merged.get(0).materials.isEmpty());
		assertTrue(merged.get(0).employees.isEmpty());
		assertTrue(merged.get(0).weekdays.isEmpty());
		assertTrue(merged.get(0).stores.size() == 1);
		assertTrue(merged.get(0).datas.size() == 1);
		
		assertTrue(merged.get(0).stores.get(0).equals(storeInfo));
		assertTrue(merged.get(0).datas.get(0).equals(planInfoTwo.datas.get(0)));
		
		
		assertTrue(merged.get(1).materials.isEmpty());
		assertTrue(merged.get(1).employees.isEmpty());
		assertTrue(merged.get(1).weekdays.isEmpty());
		assertTrue(merged.get(1).stores.size() == 1);
		assertTrue(merged.get(1).datas.size() == 1);
		
		assertTrue(merged.get(1).stores.get(0).equals(storeInfoTwo));
		assertTrue(merged.get(1).datas.get(0).equals(planInfoTwo.datas.get(0)));
	}
	
	
	
	private void initListCodOwnerMissing() {
		initStoreInfos();
		initPlanInfos();
		
		planInfo.datas.get(0).codOwner = -1;
		
		merger = new PlanMergerStore();
	}
	
	
	
	@Test
	public void listCodOwnerMismatch() {
		initListCodOwnerMismatch();
		List<PlanInfo> merged = merger.merge(planInfos, storeInfos);
		
		assertTrue(merged.size() == 2);
		
		assertTrue(merged.get(0).materials.isEmpty());
		assertTrue(merged.get(0).employees.isEmpty());
		assertTrue(merged.get(0).weekdays.isEmpty());
		assertTrue(merged.get(0).stores.size() == 1);
		assertTrue(merged.get(0).datas.size() == 1);
		
		assertTrue(merged.get(0).stores.get(0).equals(storeInfo));
		assertTrue(merged.get(0).datas.get(0).equals(planInfoTwo.datas.get(0)));
		
		
		assertTrue(merged.get(1).materials.isEmpty());
		assertTrue(merged.get(1).employees.isEmpty());
		assertTrue(merged.get(1).weekdays.isEmpty());
		assertTrue(merged.get(1).stores.size() == 1);
		assertTrue(merged.get(1).datas.size() == 1);
		
		assertTrue(merged.get(1).stores.get(0).equals(storeInfoTwo));
		assertTrue(merged.get(1).datas.get(0).equals(planInfoTwo.datas.get(0)));
	}
	
	
	
	private void initListCodOwnerMismatch() {
		initStoreInfos();
		initPlanInfos();
		
		planInfo.datas.get(0).codOwner = 456;
		
		merger = new PlanMergerStore();
	}
	
	
	
	@Test
	public void listMerge() {
		initListMerge();
		List<PlanInfo> merged = merger.merge(planInfos, storeInfos);
		
		assertTrue(merged.size() == 4);
		
		assertTrue(merged.get(0).materials.isEmpty());
		assertTrue(merged.get(0).employees.isEmpty());
		assertTrue(merged.get(0).weekdays.isEmpty());
		assertTrue(merged.get(0).stores.size() == 1);
		assertTrue(merged.get(0).datas.size() == 1);
		
		assertTrue(merged.get(0).stores.get(0).equals(storeInfo));
		assertTrue(merged.get(0).datas.get(0).equals(planInfo.datas.get(0)));
		
		
		assertTrue(merged.get(1).materials.isEmpty());
		assertTrue(merged.get(1).employees.isEmpty());
		assertTrue(merged.get(1).weekdays.isEmpty());
		assertTrue(merged.get(1).stores.size() == 1);
		assertTrue(merged.get(1).datas.size() == 1);
		
		assertTrue(merged.get(1).stores.get(0).equals(storeInfoTwo));
		assertTrue(merged.get(1).datas.get(0).equals(planInfo.datas.get(0)));
		
		
		assertTrue(merged.get(2).materials.isEmpty());
		assertTrue(merged.get(2).employees.isEmpty());
		assertTrue(merged.get(2).weekdays.isEmpty());
		assertTrue(merged.get(2).stores.size() == 1);
		assertTrue(merged.get(2).datas.size() == 1);
		
		assertTrue(merged.get(2).stores.get(0).equals(storeInfo));
		assertTrue(merged.get(2).datas.get(0).equals(planInfoTwo.datas.get(0)));
		
		
		assertTrue(merged.get(3).materials.isEmpty());
		assertTrue(merged.get(3).employees.isEmpty());
		assertTrue(merged.get(3).weekdays.isEmpty());
		assertTrue(merged.get(3).stores.size() == 1);
		assertTrue(merged.get(3).datas.size() == 1);
		
		assertTrue(merged.get(3).stores.get(0).equals(storeInfoTwo));
		assertTrue(merged.get(3).datas.get(0).equals(planInfoTwo.datas.get(0)));
	}
	
	
	
	private void initListMerge() {
		initStoreInfos();
		initPlanInfos();
		
		merger = new PlanMergerStore();
	}
	
	
	
	
	
	
	
	private void initStoreInfo() {
		storeInfo = new StoreInfo();
		
		storeInfo.codOwner = 888;
		storeInfo.codStore = 111;	
		storeInfo.cnpj = "AAA";
		storeInfo.inscrMun = "AAA";
		storeInfo.inscrEst = "AAA";
		storeInfo.razaoSocial = "AAA";
		storeInfo.name = "AAA";
		storeInfo.address1 = "AAA";
		storeInfo.address2 = "AAA";
		storeInfo.postalCode = 111;
		storeInfo.city = "AAA";
		storeInfo.codCountry = "AAA";
		storeInfo.txtCountry = "AAA";
		storeInfo.stateProvince = "AAA";
		storeInfo.phone = "AAA";
		storeInfo.codCurr = "AAA";
		storeInfo.txtCurr = "AAA";
		storeInfo.codPayment = "AAA";
		storeInfo.latitude = 111;
		storeInfo.longitude = 111;
		storeInfo.codTimezone = "AAA";
		storeInfo.codLanguage = "AAA";
		storeInfo.recordMode = "X";
	}
	
	
	
	private void initStoreInfoTwo() {
		storeInfoTwo = new StoreInfo();
		
		storeInfoTwo.codOwner = 888;
		storeInfoTwo.codStore = 444;	
		storeInfoTwo.cnpj = "CCC";
		storeInfoTwo.inscrMun = "CCC";
		storeInfoTwo.inscrEst = "CCC";
		storeInfoTwo.razaoSocial = "CCC";
		storeInfoTwo.name = "CCC";
		storeInfoTwo.address1 = "CCC";
		storeInfoTwo.address2 = "CCC";
		storeInfoTwo.postalCode = 444;
		storeInfoTwo.city = "CCC";
		storeInfoTwo.codCountry = "CCC";
		storeInfoTwo.txtCountry = "CCC";
		storeInfoTwo.stateProvince = "CCC";
		storeInfoTwo.phone = "CCC";
		storeInfoTwo.codCurr = "CCC";
		storeInfoTwo.txtCurr = "CCC";
		storeInfoTwo.codPayment = "CCC";
		storeInfoTwo.latitude = 444;
		storeInfoTwo.longitude = 444;
		storeInfoTwo.codTimezone = "CCC";
		storeInfoTwo.codLanguage = "CCC";
		storeInfoTwo.recordMode = "R";
	}

	
	
	
	private void initStoreInfos() {
		initStoreInfo();
		initStoreInfoTwo();
		
		storeInfos = new ArrayList<>();
		storeInfos.add(storeInfo);
		storeInfos.add(storeInfoTwo);
	}
	
	
	
	private void initPlanInfos() {
		initPlanInfo();
		initPlanInfoTwo();
		
		planInfos = new ArrayList<>();
		planInfos.add(planInfo);
		planInfos.add(planInfoTwo);
	}
	
	
	
	private void initPlanInfo() {
		PlanDataInfo planData = new PlanDataInfo();
		
		planData.codOwner = 888;	
		planData.codWeekday = 1;
		planData.codStore = 2;
		planData.codEmployee = 2;
		planData.codMat = 2;
		planData.codLanguage = "BBB";
		planData.date = LocalDate.of(2018, 8, 8);
		planData.beginTime = LocalTime.of(22, 22);
		planData.endTime = LocalTime.of(22, 22);		
		
		planInfo = new PlanInfo();
		planInfo.datas.add(planData);
	}
	
	
	
	private void initPlanInfoTwo() {
		PlanDataInfo planData = new PlanDataInfo();
		
		planData.codOwner = 888;	
		planData.codWeekday = 3;
		planData.codStore = 3;
		planData.codEmployee = 3;
		planData.codMat = 3;
		planData.codLanguage = "CCC";
		planData.date = LocalDate.of(2017, 7, 7);
		planData.beginTime = LocalTime.of(11, 11);
		planData.endTime = LocalTime.of(11, 11);		
		
		planInfoTwo = new PlanInfo();
		planInfoTwo.datas.add(planData);
	}
}
