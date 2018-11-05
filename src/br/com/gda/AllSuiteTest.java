package br.com.gda;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.gda.business.address.AddressSuiteTest;
import br.com.gda.business.customer.CusSuiteTest;
import br.com.gda.business.employeWorkTimeConflict.model.EmpCoModelSuiteTest;
import br.com.gda.business.employee.EmpSuiteTest;
import br.com.gda.business.employeeLeaveDate.EmpLDateSuiteTest;
import br.com.gda.business.employeeWorkTime.EmpWTimeSuiteTest;
import br.com.gda.business.masterData.MasterDataSuiteTest;
import br.com.gda.business.material.MatSuiteTest;
import br.com.gda.business.materialEmployee.MatEmpSuiteTest;
import br.com.gda.business.planningTime.PlanSuiteTest;
import br.com.gda.business.store.StoreSuiteTest;
import br.com.gda.business.storeEmployee.StoreEmpSuiteTest;
import br.com.gda.business.storeLeaveDate.StoreLDateSuiteTest;
import br.com.gda.business.storeWorkTime.StoreWTimeSuiteTest;
import br.com.gda.business.storeWorkTimeConflict.model.StoreCoModelSuiteTest;
import br.com.gda.business.totalAmount.model.AmountSuiteTest;
import br.com.gda.common.CommonSuiteTest;
import br.com.gda.model.ModelSuiteTest;

@RunWith(Suite.class)
@SuiteClasses({EmpSuiteTest.class,
			   EmpWTimeSuiteTest.class,
			   StoreSuiteTest.class,
			   MatEmpSuiteTest.class,
			   StoreEmpSuiteTest.class,
			   MatSuiteTest.class,
			   EmpLDateSuiteTest.class,
			   StoreLDateSuiteTest.class,
			   StoreWTimeSuiteTest.class,
			   ModelSuiteTest.class,
			   EmpCoModelSuiteTest.class,
			   StoreCoModelSuiteTest.class,
			   CusSuiteTest.class,
			   MasterDataSuiteTest.class,
			   PlanSuiteTest.class,
			   AmountSuiteTest.class,
			   AddressSuiteTest.class,
			   CommonSuiteTest.class})
public class AllSuiteTest {

}
