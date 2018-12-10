package br.com.gda.business.phone.model.decisionTree;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.gda.business.form.formPhone.dao.FormPhoneDbTableColumn;
import br.com.gda.business.phone.dao.PhoneDbTableColumn;
import br.com.gda.business.phone.info.PhoneInfo;
import br.com.gda.common.DbConnection;
import br.com.gda.common.DbSchema;
import br.com.gda.model.decisionTree.DeciResult;
import br.com.gda.model.decisionTree.DeciTree;
import br.com.gda.model.decisionTree.DeciTreeOption;

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class RootPhoneSelectTest {
	@Mock private Connection selectConn;
	@Mock private PreparedStatement selectStmt;
	@Mock private ResultSet selectRs;
	
	@Mock private Connection notFoundConn;
	@Mock private PreparedStatement notFoundStmt;
	@Mock private ResultSet notFoundRs;
	
	@Mock private Connection formNotFoundConn;
	@Mock private PreparedStatement formNotFoundStmt;
	@Mock private ResultSet formNotFoundRs;
	
	@Mock private Connection countryNotFoundConn;
	@Mock private PreparedStatement countryNotFoundStmt;
	@Mock private ResultSet countryNotFoundRs;
	
	@Mock private Connection countryPhoneNotFoundConn;
	@Mock private PreparedStatement countryPhoneNotFoundStmt;
	@Mock private ResultSet countryPhoneNotFoundRs;
	
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		initializeScenarioInvalidConnection();
		initializeScenarioSelect();
		initializeScenarioNotFound();
		initializeScenarioFormNotFound();
		initializeScenarioCountryNotFound();
		initializeScenarioCountryPhoneNotFound();
	}
	
	//TODO: Select precisa passar por T00 e T01
	
	private void initializeScenarioInvalidConnection() throws SQLException {
		invalidStmt = mock(PreparedStatement.class);
		invalidConn = mock(Connection.class);
		
		when(invalidConn.prepareStatement(anyString())).thenThrow(new SQLException());
		doThrow(new SQLException()).when(invalidStmt).setString(anyInt(), anyString());
		doThrow(new SQLException()).when(invalidStmt).setLong(anyInt(), anyLong());
		doThrow(new SQLException()).when(invalidStmt).setTime(anyInt(), any(Time.class)); 
		
		when(invalidStmt.executeQuery()).thenThrow(new SQLException());
	}
	
	
	
	private void initializeScenarioSelect() throws SQLException {
		selectConn = mock(Connection.class);
		selectStmt = mock(PreparedStatement.class);
		selectRs = mock(ResultSet.class);
		
		when(selectConn.prepareStatement(any(String.class))).thenReturn(selectStmt);
		when(selectStmt.executeUpdate()).thenReturn(1);
		
		when(selectStmt.executeQuery()).thenReturn(selectRs);
		when(selectRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Select Phone
							 .thenReturn(true).thenReturn(true).thenReturn(false)	// Select CountryPhone
							 .thenReturn(true).thenReturn(true).thenReturn(false)	// Form - Check Country
							 .thenReturn(true).thenReturn(true).thenReturn(false)	// Form - Check Exist
							 .thenReturn(true).thenReturn(true).thenReturn(false);	// Form - Select
		when(selectRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(selectRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(selectRs.getString(any(String.class))).thenReturn(" ");
		when(selectRs.getString(FormPhoneDbTableColumn.COL_COD_FORM)).thenReturn("T01");
		when(selectRs.getString(PhoneDbTableColumn.COL_FULL_NUMBER)).thenReturn("21925922592");
		when(selectRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioNotFound() throws SQLException {
		notFoundConn = mock(Connection.class);
		notFoundStmt = mock(PreparedStatement.class);
		notFoundRs = mock(ResultSet.class);
		
		when(notFoundConn.prepareStatement(any(String.class))).thenReturn(notFoundStmt);
		when(notFoundStmt.executeUpdate()).thenReturn(1);
		
		when(notFoundStmt.executeQuery()).thenReturn(notFoundRs);
		when(notFoundRs.next()).thenReturn(false);									// Select Phone
		when(notFoundRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(notFoundRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(notFoundRs.getString(any(String.class))).thenReturn(" ");
		when(notFoundRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioFormNotFound() throws SQLException {
		formNotFoundConn = mock(Connection.class);
		formNotFoundStmt = mock(PreparedStatement.class);
		formNotFoundRs = mock(ResultSet.class);
		
		when(formNotFoundConn.prepareStatement(any(String.class))).thenReturn(formNotFoundStmt);
		when(formNotFoundStmt.executeUpdate()).thenReturn(1);
		
		when(formNotFoundStmt.executeQuery()).thenReturn(formNotFoundRs);
		when(formNotFoundRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		// Select Phone
								   .thenReturn(true).thenReturn(true).thenReturn(false)		// Select CountryPhone
								   .thenReturn(true).thenReturn(true).thenReturn(false)		// Form - Check Country
								   .thenReturn(false);										// Form - Check Exist
		when(formNotFoundRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(formNotFoundRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(formNotFoundRs.getString(any(String.class))).thenReturn(" ");
		when(formNotFoundRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioCountryNotFound() throws SQLException {
		countryNotFoundConn = mock(Connection.class);
		countryNotFoundStmt = mock(PreparedStatement.class);
		countryNotFoundRs = mock(ResultSet.class);
		
		when(countryNotFoundConn.prepareStatement(any(String.class))).thenReturn(countryNotFoundStmt);
		when(countryNotFoundStmt.executeUpdate()).thenReturn(1);
		
		when(countryNotFoundStmt.executeQuery()).thenReturn(countryNotFoundRs);
		when(countryNotFoundRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		// Select Phone
								      .thenReturn(true).thenReturn(true).thenReturn(false)		// Select CountryPhone
								      .thenReturn(false);										// country - Check Country
		when(countryNotFoundRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(countryNotFoundRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(countryNotFoundRs.getString(any(String.class))).thenReturn(" ");
		when(countryNotFoundRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioCountryPhoneNotFound() throws SQLException {
		countryPhoneNotFoundConn = mock(Connection.class);
		countryPhoneNotFoundStmt = mock(PreparedStatement.class);
		countryPhoneNotFoundRs = mock(ResultSet.class);
		
		when(countryPhoneNotFoundConn.prepareStatement(any(String.class))).thenReturn(countryPhoneNotFoundStmt);
		when(countryPhoneNotFoundStmt.executeUpdate()).thenReturn(1);
		
		when(countryPhoneNotFoundStmt.executeQuery()).thenReturn(countryPhoneNotFoundRs);
		when(countryPhoneNotFoundRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		// Select Phone
										   .thenReturn(false).thenReturn(false)						// Select CountryPhone
										   .thenReturn(true).thenReturn(true).thenReturn(false)		// Form - Check Country
										   .thenReturn(true).thenReturn(true).thenReturn(false)		// Form - Check Exist
										   .thenReturn(true).thenReturn(true).thenReturn(false);	// Form - countryPhone
		when(countryPhoneNotFoundRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(countryPhoneNotFoundRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(countryPhoneNotFoundRs.getString(any(String.class))).thenReturn(" ");
		when(countryPhoneNotFoundRs.getString(FormPhoneDbTableColumn.COL_COD_FORM)).thenReturn("T01");
		when(countryPhoneNotFoundRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	@Test
	public void countryPhoneNotFound() {
		DeciTree<PhoneInfo> tree = initializeSelectCountryPhoneNotFound();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());		
		PhoneInfo phone = result.getResultset().get(0);
		assertTrue(phone.codCountry == null);	
		assertTrue(phone.codForm == null);	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeSelectCountryPhoneNotFound() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesSelectReference1();
		option.conn = countryPhoneNotFoundConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneSelect(option);
	}
	
	
	
	@Test
	public void countryNotFound() {
		DeciTree<PhoneInfo> tree = initializeSelectCountryNotFound();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());		
		PhoneInfo phone = result.getResultset().get(0);
		assertTrue(phone.codForm == null);	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeSelectCountryNotFound() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesSelectReference1();
		option.conn = countryNotFoundConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneSelect(option);
	}
	
	
	
	@Test
	public void formNotFound() {
		DeciTree<PhoneInfo> tree = initializeSelectFormNotFound();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());		
		PhoneInfo phone = result.getResultset().get(0);
		assertTrue(phone.codForm.equals("T00"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeSelectFormNotFound() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesSelectReference1();
		option.conn = formNotFoundConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneSelect(option);
	}
	
	
	
	@Test
	public void notFound() {
		DeciTree<PhoneInfo> tree = initializeNotFound();
		tree.makeDecision();
		
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());	
		assertTrue(result.getFailCode() == 10);
		assertTrue(result.getFailMessage().equals("Data not found"));
	}
		
	
	
	private DeciTree<PhoneInfo> initializeNotFound() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesSelectReference1();
		option.conn = notFoundConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneSelect(option);
	}
	
	
	
	@Test
	public void invalidConnection() {
		DeciTree<PhoneInfo> tree = initializeInvalidConnection();
		tree.makeDecision();
		
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());	
		assertTrue(result.getFailCode() == 500);
		assertTrue(result.getFailMessage().equals("Ops... something went wrong"));
	}
		
	
	
	private DeciTree<PhoneInfo> initializeInvalidConnection() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesSelectReference1();
		option.conn = invalidConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneSelect(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullOption() {
		DeciTree<PhoneInfo> tree = initializeNullOption();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PhoneInfo> initializeNullOption() {		
		return new RootPhoneSelect(null);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullSchema() {
		DeciTree<PhoneInfo> tree = initializeNullSchema();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PhoneInfo> initializeNullSchema() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesSelectReference1();
		option.conn = selectConn;
		option.schemaName = null;
		
		return new RootPhoneSelect(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullConnection() {
		DeciTree<PhoneInfo> tree = initializeNullConnection();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PhoneInfo> initializeNullConnection() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesSelectReference1();
		option.conn = null;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneSelect(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullRecord() {
		DeciTree<PhoneInfo> tree = initializeNullRecord();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PhoneInfo> initializeNullRecord() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = null;
		option.conn = selectConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneSelect(option);
	}
	
	
	
	@Test(expected = IllegalArgumentException.class)
	public void emptyRecords() {
		DeciTree<PhoneInfo> tree = initializeEmptyRecords();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PhoneInfo> initializeEmptyRecords() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesEmptyRecords();
		option.conn = selectConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneSelect(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesEmptyRecords() {
		return new ArrayList<>();
	}	
	
	
	
	@Test
	public void selectReference1() {
		DeciTree<PhoneInfo> tree = initializeSelectReference1();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());		
		PhoneInfo phone = result.getResultset().get(0);
		assertTrue(phone.codForm.equals("T01"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeSelectReference1() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesSelectReference1();
		option.conn = selectConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneSelect(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesSelectReference1() {
		PhoneInfo phone = new PhoneInfo();		
		
		phone.codOwner = 1;
		phone.codCustomer = 1;
		phone.codEmployee = -1;
		phone.codStore = -1;
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}	
	
	
	
	@Test
	public void selectReference2() {
		DeciTree<PhoneInfo> tree = initializeSelectReference2();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());		
		PhoneInfo phone = result.getResultset().get(0);
		assertTrue(phone.codForm.equals("T01"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeSelectReference2() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesSelectReference2();
		option.conn = selectConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneSelect(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesSelectReference2() {
		PhoneInfo phone = new PhoneInfo();		
		
		phone.codOwner = 1;
		phone.codCustomer = -1;
		phone.codEmployee = 1;
		phone.codStore = -1;
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}	
	
	
	
	@Test
	public void selectReference3() {
		DeciTree<PhoneInfo> tree = initializeSelectReference3();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());		
		PhoneInfo phone = result.getResultset().get(0);
		assertTrue(phone.codForm.equals("T01"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeSelectReference3() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesSelectReference3();
		option.conn = selectConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneSelect(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesSelectReference3() {
		PhoneInfo phone = new PhoneInfo();		
		
		phone.codOwner = 1;
		phone.codCustomer = -1;
		phone.codEmployee = -1;
		phone.codStore = 1;
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}	
	
	
	
	@Test
	public void missingFReference() {
		DeciTree<PhoneInfo> tree = initializeMissingReference();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1660);
		assertTrue(result.getFailMessage().equals("No reference added to Phone"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMissingReference() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesMissingReference();
		option.conn = selectConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneSelect(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesMissingReference() {
		PhoneInfo phone = new PhoneInfo();
		
		phone.codOwner = 1;
		phone.codCustomer = -1;
		phone.codEmployee = -1;
		phone.codStore = -1;
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void missingFieldCodOwner() {
		DeciTree<PhoneInfo> tree = initializeMissingFieldCodOwner();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMissingFieldCodOwner() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesMissingFieldCodOwner();
		option.conn = selectConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneSelect(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesMissingFieldCodOwner() {
		PhoneInfo phone = new PhoneInfo();
		
		phone.codOwner = -1;
		phone.codCustomer = 1;
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
}
