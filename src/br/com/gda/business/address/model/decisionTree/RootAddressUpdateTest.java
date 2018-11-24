package br.com.gda.business.address.model.decisionTree;

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

import br.com.gda.business.address.info.AddressInfo;
import br.com.gda.business.form.formAddress.dao.FormAddressDbTableColumn;
import br.com.gda.common.DbConnection;
import br.com.gda.common.DbSchema;
import br.com.gda.model.decisionTree.DeciResult;
import br.com.gda.model.decisionTree.DeciTree;
import br.com.gda.model.decisionTree.DeciTreeOption;

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class RootAddressUpdateTest {
	@Mock private Connection updateA00Conn;
	@Mock private PreparedStatement updateA00Stmt;
	@Mock private ResultSet updateA00Rs;
	
	@Mock private Connection updateA01Conn;
	@Mock private PreparedStatement updateA01Stmt;
	@Mock private ResultSet updateA01Rs;
	
	@Mock private Connection invalidCountryConn;
	@Mock private PreparedStatement invalidCountryStmt;
	@Mock private ResultSet invalidCountryRs;
	
	@Mock private Connection invalidStateConn;
	@Mock private PreparedStatement invalidStateStmt;
	@Mock private ResultSet invalidStateRs;
	
	@Mock private Connection dontExistConn;
	@Mock private PreparedStatement dontExistStmt;
	@Mock private ResultSet dontExistRs;
	
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		initializeScenarioUpdateA00();
		initializeScenarioUpdateA01();
		initializeScenarioInvalidCountry();
		initializeScenarioInvalidState();
		initializeScenarioDontExist();		
		initializeScenarioInvalidConnection();
	}
	
	

	private void initializeScenarioUpdateA00() throws SQLException {
		updateA00Conn = mock(Connection.class);
		updateA00Stmt = mock(PreparedStatement.class);
		updateA00Rs = mock(ResultSet.class);
		
		when(updateA00Conn.prepareStatement(any(String.class))).thenReturn(updateA00Stmt);
		when(updateA00Stmt.executeUpdate()).thenReturn(1);
		
		when(updateA00Stmt.executeQuery()).thenReturn(updateA00Rs);
		when(updateA00Rs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Country
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Exist
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Check Country
						  	    .thenReturn(false).thenReturn(false);					// Address Form - Check Exist
						  	     														// Update
		when(updateA00Rs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateA00Rs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateA00Rs.getString(any(String.class))).thenReturn(" ");
		when(updateA00Rs.getString(any(String.class))).thenReturn(" ");
		when(updateA00Rs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioUpdateA01() throws SQLException {
		updateA01Conn = mock(Connection.class);
		updateA01Stmt = mock(PreparedStatement.class);
		updateA01Rs = mock(ResultSet.class);
		
		when(updateA01Conn.prepareStatement(any(String.class))).thenReturn(updateA01Stmt);
		when(updateA01Stmt.executeUpdate()).thenReturn(1);
		
		when(updateA01Stmt.executeQuery()).thenReturn(updateA01Rs);
		when(updateA01Rs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Country
								.thenReturn(true).thenReturn(true).thenReturn(false)	// Check Exist
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Check Country
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Check Exist
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Select
						  	    .thenReturn(true).thenReturn(true).thenReturn(false);	// Check State
						  	    														// Update
		when(updateA01Rs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateA01Rs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateA01Rs.getString(any(String.class))).thenReturn(" ");
		when(updateA01Rs.getString(FormAddressDbTableColumn.COL_COD_FORM)).thenReturn("A01");
		when(updateA01Rs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidState() throws SQLException {
		invalidStateConn = mock(Connection.class);
		invalidStateStmt = mock(PreparedStatement.class);
		invalidStateRs = mock(ResultSet.class);
		
		when(invalidStateConn.prepareStatement(any(String.class))).thenReturn(invalidStateStmt);
		when(invalidStateStmt.executeUpdate()).thenReturn(1);
		
		when(invalidStateStmt.executeQuery()).thenReturn(invalidStateRs);
		when(invalidStateRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Country
								   .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Exist
							 	   .thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Check Country
							 	   .thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Check Exist
							  	   .thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Select
							  	   .thenReturn(false);									// Check State
		when(invalidStateRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidStateRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidStateRs.getString(any(String.class))).thenReturn(" ");
		when(invalidStateRs.getString(FormAddressDbTableColumn.COL_COD_FORM)).thenReturn("A01");
		when(invalidStateRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidCountry() throws SQLException {
		invalidCountryConn = mock(Connection.class);
		invalidCountryStmt = mock(PreparedStatement.class);
		invalidCountryRs = mock(ResultSet.class);
		
		when(invalidCountryConn.prepareStatement(any(String.class))).thenReturn(invalidCountryStmt);
		when(invalidCountryStmt.executeUpdate()).thenReturn(1);
		
		when(invalidCountryStmt.executeQuery()).thenReturn(invalidCountryRs);
		when(invalidCountryRs.next()).thenReturn(false);					// Check Country
		when(invalidCountryRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidCountryRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidCountryRs.getString(any(String.class))).thenReturn(" ");
		when(invalidCountryRs.getString(any(String.class))).thenReturn(" ");
		when(invalidCountryRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioDontExist() throws SQLException {
		dontExistConn = mock(Connection.class);
		dontExistStmt = mock(PreparedStatement.class);
		dontExistRs = mock(ResultSet.class);
		
		when(dontExistConn.prepareStatement(any(String.class))).thenReturn(dontExistStmt);
		when(dontExistStmt.executeUpdate()).thenReturn(1);
		
		when(dontExistStmt.executeQuery()).thenReturn(dontExistRs);
		when(dontExistRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Country
						  	    .thenReturn(false);										// Check Exist
		when(dontExistRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(dontExistRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(dontExistRs.getString(any(String.class))).thenReturn(" ");
		when(dontExistRs.getString(any(String.class))).thenReturn(" ");
		when(dontExistRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
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
	
	
	
	@Test
	public void invalidCountry() {
		DeciTree<AddressInfo> tree = initializeInvalidCountry();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1171);
		assertTrue(result.getFailMessage().equals("Country not found on DB"));	
	}
		
	
	
	private DeciTree<AddressInfo> initializeInvalidCountry() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildUpdateAddressesA00();
		option.conn = invalidCountryConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpdate(option);
	}
	
	
	
	@Test(expected = IllegalStateException.class)
	public void invalidConnection() {
		DeciTree<AddressInfo> tree = initializeInvalidConnection();
		tree.makeDecision();	
	}
		
	
	
	private DeciTree<AddressInfo> initializeInvalidConnection() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildUpdateAddressesA00();
		option.conn = invalidConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpdate(option);
	}
	
	
	
	@Test
	public void updateA00() {
		DeciTree<AddressInfo> tree = initializeUpdateA00();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());
		
		AddressInfo address = result.getResultset().get(0);
		assertTrue(address.codForm.equals("A00"));	
	}
		
	
	
	private DeciTree<AddressInfo> initializeUpdateA00() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildUpdateAddressesA00();
		option.conn = updateA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpdate(option);
	}
	
	
	
	private List<AddressInfo> buildUpdateAddressesA00() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codAddress = 1;
		address.codCustomer = 1;
		address.codCountry = " ";
		address.line1 = "X";
		address.line2 = "X";
		address.line3 = "X";
		address.line4 = "X";
		address.line5 = "X";
		address.line6 = "X";
		address.line7 = "X";		
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}
	
	
	
	@Test
	public void missingFieldCodCountry() {
		DeciTree<AddressInfo> tree = initializeMissingFieldCodCountry();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeMissingFieldCodCountry() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesMissingFieldCodCountry();
		option.conn = updateA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpdate(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesMissingFieldCodCountry() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codAddress = 1;
		address.codCustomer = 1;
		address.codCountry = null;
		address.line1 = "X";
		address.line2 = "X";
		address.line3 = "X";
		address.line4 = "X";
		address.line5 = "X";
		address.line6 = "X";
		address.line7 = "X";		
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}
	
	
	
	@Test
	public void missingFieldCodOwner() {
		DeciTree<AddressInfo> tree = initializeMissingFieldCodOwner();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeMissingFieldCodOwner() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesMissingFieldCodOwner();
		option.conn = updateA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpdate(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesMissingFieldCodOwner() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = -1;
		address.codAddress = 1;
		address.codCustomer = 1;
		address.codCountry = "X";
		address.line1 = "X";
		address.line2 = "X";
		address.line3 = "X";
		address.line4 = "X";
		address.line5 = "X";
		address.line6 = "X";
		address.line7 = "X";		
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}
	
	
	
	@Test
	public void missingFieldCodAddress() {
		DeciTree<AddressInfo> tree = initializeMissingFieldCodAddress();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeMissingFieldCodAddress() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesMissingFieldCodAddress();
		option.conn = updateA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpdate(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesMissingFieldCodAddress() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codAddress = -1;
		address.codCustomer = 1;
		address.codCountry = "X";
		address.line1 = "X";
		address.line2 = "X";
		address.line3 = "X";
		address.line4 = "X";
		address.line5 = "X";
		address.line6 = "X";
		address.line7 = "X";		
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}
	
	
	
	@Test
	public void missingReference() {
		DeciTree<AddressInfo> tree = initializeMissingReference();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1606);
		assertTrue(result.getFailMessage().equals("No reference added to Address"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeMissingReference() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesMissingReference();
		option.conn = updateA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpdate(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesMissingReference() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codAddress = 1;
		address.codCustomer = -1;
		address.codCountry = "X";
		address.line1 = "X";
		address.line2 = "X";
		address.line3 = "X";
		address.line4 = "X";
		address.line5 = "X";
		address.line6 = "X";
		address.line7 = "X";		
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}
	
	
	
	@Test
	public void multipleReferencePart1() {
		DeciTree<AddressInfo> tree = initializeMultipleReferencePart1();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1607);
		assertTrue(result.getFailMessage().equals("Address has multiple references"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeMultipleReferencePart1() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesMultipleReferencePart1();
		option.conn = updateA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpdate(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesMultipleReferencePart1() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codAddress = 1;
		address.codCustomer = 1;
		address.codStore = 1;
		address.codEmployee = 1;
		address.codCountry = "X";
		address.line1 = "X";
		address.line2 = "X";
		address.line3 = "X";
		address.line4 = "X";
		address.line5 = "X";
		address.line6 = "X";
		address.line7 = "X";		
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}
	
	
	
	@Test
	public void multipleReferencePart2() {
		DeciTree<AddressInfo> tree = initializeMultipleReferencePart2();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1607);
		assertTrue(result.getFailMessage().equals("Address has multiple references"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeMultipleReferencePart2() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesMultipleReferencePart2();
		option.conn = updateA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpdate(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesMultipleReferencePart2() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codAddress = 1;
		address.codCustomer = 1;
		address.codStore = -1;
		address.codEmployee = 1;
		address.codCountry = "X";
		address.line1 = "X";
		address.line2 = "X";
		address.line3 = "X";
		address.line4 = "X";
		address.line5 = "X";
		address.line6 = "X";
		address.line7 = "X";		
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}
	
	
	
	@Test
	public void multipleReferencePart3() {
		DeciTree<AddressInfo> tree = initializeMultipleReferencePart3();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1607);
		assertTrue(result.getFailMessage().equals("Address has multiple references"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeMultipleReferencePart3() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesMultipleReferencePart3();
		option.conn = updateA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpdate(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesMultipleReferencePart3() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codAddress = 1;
		address.codCustomer = -1;
		address.codStore = 1;
		address.codEmployee = 1;
		address.codCountry = "X";
		address.line1 = "X";
		address.line2 = "X";
		address.line3 = "X";
		address.line4 = "X";
		address.line5 = "X";
		address.line6 = "X";
		address.line7 = "X";		
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}
	
	
	
	@Test
	public void multipleReferencePart4() {
		DeciTree<AddressInfo> tree = initializeMultipleReferencePart4();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1607);
		assertTrue(result.getFailMessage().equals("Address has multiple references"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeMultipleReferencePart4() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesMultipleReferencePart4();
		option.conn = updateA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpdate(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesMultipleReferencePart4() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codAddress = 1;
		address.codCustomer = 1;
		address.codStore = 1;
		address.codEmployee = -1;
		address.codCountry = "X";
		address.line1 = "X";
		address.line2 = "X";
		address.line3 = "X";
		address.line4 = "X";
		address.line5 = "X";
		address.line6 = "X";
		address.line7 = "X";		
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}
	
	
	
	@Test
	public void dontExist() {
		DeciTree<AddressInfo> tree = initializeDontExist();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1602);
		assertTrue(result.getFailMessage().equals("Address not found on DB"));	
	}
		
	
	
	private DeciTree<AddressInfo> initializeDontExist() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildUpdateAddressesA00();
		option.conn = dontExistConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpdate(option);
	}
	
	
	
	@Test
	public void a00MissingFieldLine1() {
		DeciTree<AddressInfo> tree = initializeA00MissingFieldLine1();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeA00MissingFieldLine1() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesA00MissingFieldLine1();
		option.conn = updateA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpdate(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesA00MissingFieldLine1() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codAddress = 1;
		address.codCustomer = 1;
		address.codCountry = "X";
		address.line1 = null;
		address.line2 = "X";
		address.line3 = "X";
		address.line4 = "X";
		address.line5 = "X";
		address.line6 = "X";
		address.line7 = "X";		
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}
	
	
	
	@Test
	public void updateA01() {
		DeciTree<AddressInfo> tree = initializeUpdateA01();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());
		
		AddressInfo address = result.getResultset().get(0);
		assertTrue(address.codForm.equals("A01"));	
	}
		
	
	
	private DeciTree<AddressInfo> initializeUpdateA01() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildUpdateAddressesA01();
		option.conn = updateA01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpdate(option);
	}
	
	
	
	private List<AddressInfo> buildUpdateAddressesA01() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codAddress = 1;
		address.codCustomer = 1;
		address.codCountry = " ";
		address.codState = "X";
		address.city = "X";
		address.district = "X";
		address.street = "X";
		address.streetNumber = "X";
		address.complement = "X";
		address.postalCode = "X";			
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}
	
	
	
	@Test
	public void a01MissingFieldCodState() {
		DeciTree<AddressInfo> tree = initializeA01MissingFieldCodState();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeA01MissingFieldCodState() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesA01MissingFieldCodState();
		option.conn = updateA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpdate(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesA01MissingFieldCodState() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codAddress = 1;
		address.codCustomer = 1;
		address.codCountry = " ";
		address.codState = null;
		address.city = "X";
		address.district = "X";
		address.street = "X";
		address.streetNumber = "X";
		address.complement = "X";
		address.postalCode = "X";		
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}
	
	
	
	@Test
	public void a01MissingFieldCity() {
		DeciTree<AddressInfo> tree = initializeA01MissingFieldCity();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeA01MissingFieldCity() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesA01MissingFieldCity();
		option.conn = updateA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpdate(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesA01MissingFieldCity() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codAddress = 1;
		address.codCustomer = 1;
		address.codCountry = " ";
		address.codState = "X";
		address.city = null;
		address.district = "X";
		address.street = "X";
		address.streetNumber = "X";
		address.complement = "X";
		address.postalCode = "X";		
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}
	
	
	
	@Test
	public void a01MissingFieldDistrict() {
		DeciTree<AddressInfo> tree = initializeA01MissingFieldDistrict();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeA01MissingFieldDistrict() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesA01MissingFieldDistrict();
		option.conn = updateA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpdate(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesA01MissingFieldDistrict() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codAddress = 1;
		address.codCustomer = 1;
		address.codCountry = " ";
		address.codState = "X";
		address.city = "X";
		address.district = null;
		address.street = "X";
		address.streetNumber = "X";
		address.complement = "X";
		address.postalCode = "X";		
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}
	
	
	
	@Test
	public void a01MissingFieldStreet() {
		DeciTree<AddressInfo> tree = initializeA01MissingFieldStreet();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeA01MissingFieldStreet() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesA01MissingFieldStreet();
		option.conn = updateA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpdate(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesA01MissingFieldStreet() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codAddress = 1;
		address.codCustomer = 1;
		address.codCountry = " ";
		address.codState = "X";
		address.city = "X";
		address.district = "X";
		address.street = null;
		address.streetNumber = "X";
		address.complement = "X";
		address.postalCode = "X";		
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}
	
	
	
	@Test
	public void a01MissingFieldStreetNumber() {
		DeciTree<AddressInfo> tree = initializeA01MissingFieldStreetNumber();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeA01MissingFieldStreetNumber() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesA01MissingFieldStreetNumber();
		option.conn = updateA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpdate(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesA01MissingFieldStreetNumber() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codAddress = 1;
		address.codCustomer = 1;
		address.codCountry = " ";
		address.codState = "X";
		address.city = "X";
		address.district = "X";
		address.street = "X";
		address.streetNumber = null;
		address.complement = "X";
		address.postalCode = "X";		
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}
	
	
	
	@Test
	public void a01MissingFieldPostalCode() {
		DeciTree<AddressInfo> tree = initializeA01MissingFieldPostalCode();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeA01MissingFieldPostalCode() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesA01MissingFieldPostalCode();
		option.conn = updateA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpdate(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesA01MissingFieldPostalCode() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codAddress = 1;
		address.codCustomer = 1;
		address.codCountry = " ";
		address.codState = "X";
		address.city = "X";
		address.district = "X";
		address.street = "X";
		address.streetNumber = "X";
		address.complement = "X";
		address.postalCode = null;		
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}
	
	
	
	@Test
	public void invalidState() {
		DeciTree<AddressInfo> tree = initializeInvalidState();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1177);
		assertTrue(result.getFailMessage().equals("State not found on DB"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeInvalidState() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesInvalidState();
		option.conn = invalidStateConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpdate(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesInvalidState() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codAddress = 1;
		address.codCustomer = 1;
		address.codCountry = " ";
		address.codState = "X";
		address.city = "X";
		address.district = "X";
		address.street = "X";
		address.streetNumber = "X";
		address.complement = "X";
		address.postalCode = "X";		
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullRecord() {
		DeciTree<AddressInfo> tree = initializeNullRecord();
		tree.makeDecision();	
	}
	
	
	
	private DeciTree<AddressInfo> initializeNullRecord() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = null;
		option.conn = updateA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpdate(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullConnection() {
		DeciTree<AddressInfo> tree = initializeNullConnection();
		tree.makeDecision();	
	}
		
	
	
	private DeciTree<AddressInfo> initializeNullConnection() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildUpdateAddressesA00();
		option.conn = null;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpdate(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullSchema() {
		DeciTree<AddressInfo> tree = initializeNullSchema();
		tree.makeDecision();	
	}
		
	
	
	private DeciTree<AddressInfo> initializeNullSchema() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildUpdateAddressesA00();
		option.conn = updateA00Conn;
		option.schemaName = null;
		
		return new RootAddressUpdate(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullOption() {
		DeciTree<AddressInfo> tree = initializeNullOption();
		tree.makeDecision();	
	}
		
	
	
	private DeciTree<AddressInfo> initializeNullOption() {		
		return new RootAddressUpdate(null);
	}
}
