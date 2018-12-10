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
import br.com.gda.business.phone.info.PhoneInfo;
import br.com.gda.common.DbConnection;
import br.com.gda.common.DbSchema;
import br.com.gda.model.decisionTree.DeciResult;
import br.com.gda.model.decisionTree.DeciTree;
import br.com.gda.model.decisionTree.DeciTreeOption;

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class RootPhoneUpdateTest {
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;
	
	@Mock private Connection updateT01Conn;
	@Mock private PreparedStatement updateT01Stmt;
	@Mock private ResultSet updateT01Rs;
	
	@Mock private Connection updateT00Conn;
	@Mock private PreparedStatement updateT00Stmt;
	@Mock private ResultSet updateT00Rs;
	
	@Mock private Connection invalidCountryPhoneConn;
	@Mock private PreparedStatement invalidCountryPhoneStmt;
	@Mock private ResultSet invalidCountryPhoneRs;
	
	@Mock private Connection dontExistConn;
	@Mock private PreparedStatement dontExistStmt;
	@Mock private ResultSet dontExistRs;
	
	@Mock private Connection invalidAreaPhoneConn;
	@Mock private PreparedStatement invalidAreaPhoneStmt;
	@Mock private ResultSet invalidAreaPhoneRs;
	
	@Mock private Connection invalidOwnerConn;
	@Mock private PreparedStatement invalidOwnerStmt;
	@Mock private ResultSet invalidOwnerRs;

	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		initializeScenarioInvalidConnection();
		initializeScenarioUpdateT00();
		initializeScenarioUpdateT01();
		initializeScenarioDontExist();
		initializeScenarioInvalidCountryPhone();
		initializeScenarioInvalidAreaPhone();
		initializeScenarioInvalidOwner();
	}
	
	
	
	private void initializeScenarioInvalidConnection() throws SQLException {
		invalidStmt = mock(PreparedStatement.class);
		invalidConn = mock(Connection.class);
		
		when(invalidConn.prepareStatement(anyString())).thenThrow(new SQLException());
		doThrow(new SQLException()).when(invalidStmt).setString(anyInt(), anyString());
		doThrow(new SQLException()).when(invalidStmt).setLong(anyInt(), anyLong());
		doThrow(new SQLException()).when(invalidStmt).setTime(anyInt(), any(Time.class)); 
		
		when(invalidStmt.executeQuery()).thenThrow(new SQLException());
	}
	
	
	
	private void initializeScenarioUpdateT00() throws SQLException {
		updateT00Conn = mock(Connection.class);
		updateT00Stmt = mock(PreparedStatement.class);
		updateT00Rs = mock(ResultSet.class);
		
		when(updateT00Conn.prepareStatement(any(String.class))).thenReturn(updateT00Stmt);
		when(updateT00Stmt.executeUpdate()).thenReturn(1);
		
		when(updateT00Stmt.executeQuery()).thenReturn(updateT00Rs);
		when(updateT00Rs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
		                        .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Country Phone
								.thenReturn(true).thenReturn(true).thenReturn(false)	// Check Exist
								.thenReturn(true).thenReturn(true).thenReturn(false)	// Select Country Phone							
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Phone Form - Check Country
						  	    .thenReturn(false).thenReturn(false);					// Phone Form - Check Exist
						  	    														// Update
		when(updateT00Rs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateT00Rs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateT00Rs.getString(any(String.class))).thenReturn(" ");
		when(updateT00Rs.getString(FormPhoneDbTableColumn.COL_COD_FORM)).thenReturn("T00");
		when(updateT00Rs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioUpdateT01() throws SQLException {
		updateT01Conn = mock(Connection.class);
		updateT01Stmt = mock(PreparedStatement.class);
		updateT01Rs = mock(ResultSet.class);
		
		when(updateT01Conn.prepareStatement(any(String.class))).thenReturn(updateT01Stmt);
		when(updateT01Stmt.executeUpdate()).thenReturn(1);
		
		when(updateT01Stmt.executeQuery()).thenReturn(updateT01Rs);
		when(updateT01Rs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
		                        .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Country Phone
								.thenReturn(true).thenReturn(true).thenReturn(false)	// Check Exist
								.thenReturn(true).thenReturn(true).thenReturn(false)	// Select Country Phone							
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Phone Form - Check Country
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Phone Form - Check Exist
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Phone Form - Select						  	    
						  	    .thenReturn(true).thenReturn(true).thenReturn(false);	// Check Phone Area
						  	    														// Update
		when(updateT01Rs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateT01Rs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateT01Rs.getString(any(String.class))).thenReturn(" ");
		when(updateT01Rs.getString(FormPhoneDbTableColumn.COL_COD_FORM)).thenReturn("T01");
		when(updateT01Rs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioDontExist() throws SQLException {
		dontExistConn = mock(Connection.class);
		dontExistStmt = mock(PreparedStatement.class);
		dontExistRs = mock(ResultSet.class);
		
		when(dontExistConn.prepareStatement(any(String.class))).thenReturn(dontExistStmt);
		when(dontExistStmt.executeUpdate()).thenReturn(1);
		
		when(dontExistStmt.executeQuery()).thenReturn(dontExistRs);
		when(dontExistRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
		                        .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Country Phone
								.thenReturn(false);										// Check Exist
		when(dontExistRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(dontExistRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(dontExistRs.getString(any(String.class))).thenReturn(" ");
		when(dontExistRs.getString(FormPhoneDbTableColumn.COL_COD_FORM)).thenReturn("T01");
		when(dontExistRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidCountryPhone() throws SQLException {
		invalidCountryPhoneConn = mock(Connection.class);
		invalidCountryPhoneStmt = mock(PreparedStatement.class);
		invalidCountryPhoneRs = mock(ResultSet.class);
		
		when(invalidCountryPhoneConn.prepareStatement(any(String.class))).thenReturn(invalidCountryPhoneStmt);
		when(invalidCountryPhoneStmt.executeUpdate()).thenReturn(1);
		
		when(invalidCountryPhoneStmt.executeQuery()).thenReturn(invalidCountryPhoneRs);
		when(invalidCountryPhoneRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
		                                  .thenReturn(false);									// Check Country Phone
		when(invalidCountryPhoneRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidCountryPhoneRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidCountryPhoneRs.getString(any(String.class))).thenReturn(" ");
		when(invalidCountryPhoneRs.getString(FormPhoneDbTableColumn.COL_COD_FORM)).thenReturn("T01");
		when(invalidCountryPhoneRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidAreaPhone() throws SQLException {
		invalidAreaPhoneConn = mock(Connection.class);
		invalidAreaPhoneStmt = mock(PreparedStatement.class);
		invalidAreaPhoneRs = mock(ResultSet.class);
		
		when(invalidAreaPhoneConn.prepareStatement(any(String.class))).thenReturn(invalidAreaPhoneStmt);
		when(invalidAreaPhoneStmt.executeUpdate()).thenReturn(1);
		
		when(invalidAreaPhoneStmt.executeQuery()).thenReturn(invalidAreaPhoneRs);
		when(invalidAreaPhoneRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
									   .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Country Phone
									   .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Exist
									   .thenReturn(true).thenReturn(true).thenReturn(false)	// Select Country Phone							
							  	       .thenReturn(true).thenReturn(true).thenReturn(false)	// Phone Form - Check Country
							  	       .thenReturn(true).thenReturn(true).thenReturn(false)	// Phone Form - Check Exist
							  	       .thenReturn(true).thenReturn(true).thenReturn(false)	// Phone Form - Select						  	    
							  	       .thenReturn(false);									// Check Phone Area
		when(invalidAreaPhoneRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidAreaPhoneRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidAreaPhoneRs.getString(any(String.class))).thenReturn(" ");
		when(invalidAreaPhoneRs.getString(FormPhoneDbTableColumn.COL_COD_FORM)).thenReturn("T01");
		when(invalidAreaPhoneRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidOwner() throws SQLException {
		invalidOwnerConn = mock(Connection.class);
		invalidOwnerStmt = mock(PreparedStatement.class);
		invalidOwnerRs = mock(ResultSet.class);
		
		when(invalidOwnerConn.prepareStatement(any(String.class))).thenReturn(invalidOwnerStmt);
		when(invalidOwnerStmt.executeUpdate()).thenReturn(1);
		
		when(invalidOwnerStmt.executeQuery()).thenReturn(invalidOwnerRs);
		when(invalidOwnerRs.next()).thenReturn(false);									// Check Owner
		when(invalidOwnerRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidOwnerRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidOwnerRs.getString(any(String.class))).thenReturn(" ");
		when(invalidOwnerRs.getString(FormPhoneDbTableColumn.COL_COD_FORM)).thenReturn("T01");
		when(invalidOwnerRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	@Test
	public void invalidCountryPhone() {
		DeciTree<PhoneInfo> tree = initializeInvalidCountryPhone();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1171);
		assertTrue(result.getFailMessage().equals("Phone Country code not found on DB"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeInvalidCountryPhone() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildUpdatePhonesT01();
		option.conn = invalidCountryPhoneConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	@Test
	public void dontExist() {
		DeciTree<PhoneInfo> tree = initializeDontExist();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1564);
		assertTrue(result.getFailMessage().equals("Phone not found on DB"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeDontExist() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildUpdatePhonesT01();
		option.conn = dontExistConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	@Test
	public void invalidAreaPhone() {
		DeciTree<PhoneInfo> tree = initializeInvalidAreaPhone();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1179);
		assertTrue(result.getFailMessage().equals("Phone Area not found on DB"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeInvalidAreaPhone() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildUpdatePhonesT01();
		option.conn = invalidAreaPhoneConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}	
	
	
	
	@Test
	public void invalidOwner() {
		DeciTree<PhoneInfo> tree = initializeInvalidOwner();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1251);
		assertTrue(result.getFailMessage().equals("Owner data not found on DB"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeInvalidOwner() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildUpdatePhonesT01();
		option.conn = invalidOwnerConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	@Test(expected = IllegalStateException.class)
	public void invalidConnection() {
		DeciTree<PhoneInfo> tree = initializeInvalidConnection();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PhoneInfo> initializeInvalidConnection() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildUpdatePhonesT01();
		option.conn = invalidConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullOption() {
		DeciTree<PhoneInfo> tree = initializeNullOption();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PhoneInfo> initializeNullOption() {		
		return new RootPhoneUpdate(null);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullSchema() {
		DeciTree<PhoneInfo> tree = initializeNullSchema();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PhoneInfo> initializeNullSchema() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildUpdatePhonesT01();
		option.conn = updateT01Conn;
		option.schemaName = null;
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullRecord() {
		DeciTree<PhoneInfo> tree = initializeNullRecord();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PhoneInfo> initializeNullRecord() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = null;
		option.conn = updateT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	

	
	@Test
	public void updateT01() {
		DeciTree<PhoneInfo> tree = initializeUpdateT01();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());		
		PhoneInfo phone = result.getResultset().get(0);
		assertTrue(phone.codForm.equals("T01"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeUpdateT01() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildUpdatePhonesT01();
		option.conn = updateT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	private List<PhoneInfo> buildUpdatePhonesT01() {
		PhoneInfo phone = new PhoneInfo();
		
		phone.codOwner = 1;
		phone.codPhone = 1;
		phone.codCustomer = 1;
		phone.fullNumber = "2125696969";
		phone.codCountryPhone = 1;
		phone.complement = "teste";		
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void missingFieldCodCountryPhone() {
		DeciTree<PhoneInfo> tree = initializeMissingFieldCodCountryPhone();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMissingFieldCodCountryPhone() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesMissingFieldCodCountryPhone();
		option.conn = updateT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesMissingFieldCodCountryPhone() {
		PhoneInfo phone = new PhoneInfo();
		
		phone.codOwner = 1;
		phone.codPhone = 1;
		phone.codCustomer = 1;
		phone.fullNumber = "2125696969";
		phone.codCountryPhone = -1;
		phone.complement = "teste";		
		
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
		option.conn = updateT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesMissingFieldCodOwner() {
		PhoneInfo phone = new PhoneInfo();
		
		phone.codOwner = -1;
		phone.codPhone = 1;
		phone.codCustomer = 1;
		phone.fullNumber = "2125696969";
		phone.codCountryPhone = 1;
		phone.complement = "teste";		
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void missingFieldCodPhone() {
		DeciTree<PhoneInfo> tree = initializeMissingFieldCodPhone();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMissingFieldCodPhone() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesMissingFieldCodPhone();
		option.conn = updateT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesMissingFieldCodPhone() {
		PhoneInfo phone = new PhoneInfo();
		
		phone.codOwner = 1;
		phone.codPhone = -1;
		phone.codCustomer = 1;
		phone.fullNumber = "2125696969";
		phone.codCountryPhone = 1;
		phone.complement = "teste";		
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void invalidLength() {
		DeciTree<PhoneInfo> tree = initializeInvalidLength();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1563);
		assertTrue(result.getFailMessage().equals("Invalid phone length"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeInvalidLength() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInvalidLength();
		option.conn = updateT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	private List<PhoneInfo> buildInvalidLength() {
		PhoneInfo phone = new PhoneInfo();
		
		phone.codOwner = 1;
		phone.codPhone = 1;
		phone.codCustomer = 1;
		phone.fullNumber = "2";
		phone.codCountryPhone = 1;
		phone.complement = "teste";		
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void missingReference() {
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
		option.conn = updateT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesMissingReference() {
		PhoneInfo phone = new PhoneInfo();
		
		phone.codOwner = 1;
		phone.codPhone = 1;
		phone.codCustomer = -1;
		phone.codStore = -1;
		phone.codEmployee = -1;
		phone.fullNumber = "2125922592";
		phone.codCountryPhone = 1;
		phone.complement = "teste";	
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void multiReference1() {
		DeciTree<PhoneInfo> tree = initializeMultiReference1();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1661);
		assertTrue(result.getFailMessage().equals("Phone has multiple references"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMultiReference1() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesMultiReference1();
		option.conn = updateT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesMultiReference1() {
		PhoneInfo phone = new PhoneInfo();
		
		phone.codOwner = 1;
		phone.codPhone = 1;
		phone.codCustomer = 1;
		phone.codStore = 1;
		phone.codEmployee = -1;
		phone.codUser = -1;
		phone.fullNumber = "2125922592";
		phone.codCountryPhone = 1;
		phone.complement = "teste";	
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void multiReference2() {
		DeciTree<PhoneInfo> tree = initializeMultiReference2();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1661);
		assertTrue(result.getFailMessage().equals("Phone has multiple references"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMultiReference2() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesMultiReference2();
		option.conn = updateT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesMultiReference2() {
		PhoneInfo phone = new PhoneInfo();
		
		phone.codOwner = 1;
		phone.codPhone = 1;
		phone.codCustomer = 1;
		phone.codStore = -1;
		phone.codEmployee = 1;
		phone.codUser = -1;
		phone.fullNumber = "2125922592";
		phone.codCountryPhone = 1;
		phone.complement = "teste";	
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void multiReference3() {
		DeciTree<PhoneInfo> tree = initializeMultiReference3();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1661);
		assertTrue(result.getFailMessage().equals("Phone has multiple references"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMultiReference3() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesMultiReference3();
		option.conn = updateT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesMultiReference3() {
		PhoneInfo phone = new PhoneInfo();
		
		phone.codOwner = 1;
		phone.codPhone = 1;
		phone.codCustomer = -1;
		phone.codStore = 1;
		phone.codEmployee = 1;
		phone.codUser = -1;
		phone.fullNumber = "2125922592";
		phone.codCountryPhone = 1;
		phone.complement = "teste";	
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void multiReference4() {
		DeciTree<PhoneInfo> tree = initializeMultiReference4();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1661);
		assertTrue(result.getFailMessage().equals("Phone has multiple references"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMultiReference4() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesMultiReference4();
		option.conn = updateT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesMultiReference4() {
		PhoneInfo phone = new PhoneInfo();
		
		phone.codOwner = 1;
		phone.codPhone = 1;
		phone.codCustomer = 1;
		phone.codStore = 1;
		phone.codEmployee = 1;
		phone.codUser = 1;
		phone.fullNumber = "2125922592";
		phone.codCountryPhone = 1;
		phone.complement = "teste";	
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void multiReference5() {
		DeciTree<PhoneInfo> tree = initializeMultiReference5();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1661);
		assertTrue(result.getFailMessage().equals("Phone has multiple references"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMultiReference5() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesMultiReference5();
		option.conn = updateT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesMultiReference5() {
		PhoneInfo phone = new PhoneInfo();
		
		phone.codOwner = 1;
		phone.codPhone = 1;
		phone.codCustomer = 1;
		phone.codStore = -1;
		phone.codEmployee = -1;
		phone.codUser = 1;
		phone.fullNumber = "2125922592";
		phone.codCountryPhone = 1;
		phone.complement = "teste";	
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}	
	
	
	
	@Test
	public void multiReference6() {
		DeciTree<PhoneInfo> tree = initializeMultiReference6();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1661);
		assertTrue(result.getFailMessage().equals("Phone has multiple references"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMultiReference6() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesMultiReference6();
		option.conn = updateT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesMultiReference6() {
		PhoneInfo phone = new PhoneInfo();
		
		phone.codOwner = 1;
		phone.codPhone = 1;
		phone.codCustomer = -1;
		phone.codStore = 1;
		phone.codEmployee = -1;
		phone.codUser = 1;
		phone.fullNumber = "2125922592";
		phone.codCountryPhone = 1;
		phone.complement = "teste";	
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void multiReference7() {
		DeciTree<PhoneInfo> tree = initializeMultiReference7();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1661);
		assertTrue(result.getFailMessage().equals("Phone has multiple references"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMultiReference7() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesMultiReference7();
		option.conn = updateT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesMultiReference7() {
		PhoneInfo phone = new PhoneInfo();
		
		phone.codOwner = 1;
		phone.codPhone = 1;
		phone.codCustomer = -1;
		phone.codStore = -1;
		phone.codEmployee = 1;
		phone.codUser = 1;
		phone.fullNumber = "2125922592";
		phone.codCountryPhone = 1;
		phone.complement = "teste";	
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void t01InvalidLength1() {
		DeciTree<PhoneInfo> tree = initializeT01InvalidLength1();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1556);
		assertTrue(result.getFailMessage().equals("Invalid phone length. Area code is expected"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeT01InvalidLength1() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesT01InvalidLength1();
		option.conn = updateT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesT01InvalidLength1() {
		PhoneInfo phone = new PhoneInfo();
		
		phone.codOwner = 1;
		phone.codPhone = 1;
		phone.codCustomer = 1;
		phone.codStore = -1;
		phone.codEmployee = -1;
		phone.fullNumber = "2125696969999";
		phone.codCountryPhone = 1;
		phone.complement = "teste";	
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void t01InvalidLength2() {
		DeciTree<PhoneInfo> tree = initializeT01InvalidLength2();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1556);
		assertTrue(result.getFailMessage().equals("Invalid phone length. Area code is expected"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeT01InvalidLength2() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesT01InvalidLength2();
		option.conn = updateT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesT01InvalidLength2() {
		PhoneInfo phone = new PhoneInfo();
		
		phone.codOwner = 1;
		phone.codPhone = 1;
		phone.codCustomer = 1;
		phone.codStore = -1;
		phone.codEmployee = -1;
		phone.fullNumber = "212569696";
		phone.codCountryPhone = 1;
		phone.complement = "teste";	
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void t01InvalidString() {
		DeciTree<PhoneInfo> tree = initializeT01InvalidString();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1557);
		assertTrue(result.getFailMessage().equals("Only numbers are allowed for phone"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeT01InvalidString() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesT01InvalidString();
		option.conn = updateT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesT01InvalidString() {
		PhoneInfo phone = new PhoneInfo();
		
		phone.codOwner = 1;
		phone.codPhone = 1;
		phone.codCustomer = 1;
		phone.codStore = -1;
		phone.codEmployee = -1;
		phone.fullNumber = "212569696A";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void t01InvalidSequence() {
		DeciTree<PhoneInfo> tree = initializeT01InvalidSequence();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1559);
		assertTrue(result.getFailMessage().equals("Invalid sequence for phone number"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeT01InvalidSequence() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesT01InvalidSequence();
		option.conn = updateT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesT01InvalidSequence() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codPhone = 1;
		phone.codCustomer = 1;
		phone.fullNumber = "2111111111";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void t01InvalidMobile() {
		DeciTree<PhoneInfo> tree = initializeT01InvalidMobile();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1554);
		assertTrue(result.getFailMessage().equals("Phone number is invalid"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeT01InvalidMobile() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesT01InvalidMobile();
		option.conn = updateT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesT01InvalidMobile() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codPhone = 1;
		phone.codCustomer = 1;
		phone.fullNumber = "2198522852";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void updateT00() {
		DeciTree<PhoneInfo> tree = initializeUpdateT00();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());		
		PhoneInfo phone = result.getResultset().get(0);
		assertTrue(phone.codForm.equals("T00"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeUpdateT00() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildUpdatePhonesT00();
		option.conn = updateT00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	private List<PhoneInfo> buildUpdatePhonesT00() {
		PhoneInfo phone = new PhoneInfo();
		
		phone.codOwner = 1;
		phone.codPhone = 1;
		phone.codCustomer = 1;
		phone.fullNumber = "2125696969";
		phone.codCountryPhone = 1;
		phone.complement = "teste";		
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void t00InvalidLenght1() {
		DeciTree<PhoneInfo> tree = initializeT00InvalidLenght1();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1563);
		assertTrue(result.getFailMessage().equals("Invalid phone length"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeT00InvalidLenght1() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildT00InvalidLenght1();
		option.conn = updateT00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	private List<PhoneInfo> buildT00InvalidLenght1() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codPhone = 1;
		phone.codCustomer = 1;
		phone.fullNumber = "1234";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}	
	
	
	
	@Test
	public void t00InvalidLenght2() {
		DeciTree<PhoneInfo> tree = initializeT00InvalidLenght2();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1563);
		assertTrue(result.getFailMessage().equals("Invalid phone length"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeT00InvalidLenght2() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildT00InvalidLenght2();
		option.conn = updateT00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	private List<PhoneInfo> buildT00InvalidLenght2() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codPhone = 1;
		phone.codCustomer = 1;
		phone.fullNumber = "1111111111222222222233333333334";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}	
	
	
	
	@Test(expected = IllegalArgumentException.class)
	public void emptyRecords() {
		DeciTree<PhoneInfo> tree = initializeEmptyRecords();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PhoneInfo> initializeEmptyRecords() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildEmptyRecords();
		option.conn = updateT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneUpdate(option);
	}
	
	
	
	private List<PhoneInfo> buildEmptyRecords() {
		return new ArrayList<>();
	}	
}
