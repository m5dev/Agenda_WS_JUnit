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
import br.com.gda.business.form.dao.FormDbTableColumn;
import br.com.gda.common.DbConnection;
import br.com.gda.common.DbSchema;
import br.com.gda.model.decisionTree.DeciResult;
import br.com.gda.model.decisionTree.DeciTree;
import br.com.gda.model.decisionTree.DeciTreeOption;

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class RootAddressInsertTest {
	@Mock private Connection insertA00Conn;
	@Mock private PreparedStatement insertA00Stmt;
	@Mock private ResultSet insertA00Rs;
	
	@Mock private Connection insertA01Conn;
	@Mock private PreparedStatement insertA01Stmt;
	@Mock private ResultSet insertA01Rs;
	
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;
	
	@Mock private Connection invalidCountryConn;
	@Mock private PreparedStatement invalidCountryStmt;
	@Mock private ResultSet invalidCountryRs;
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		initializeScenarioInsertA00();
		initializeScenarioInsertA01();
		initializeScenarioInvalidConnection();
		initializeScenarioInvalidCountry();
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
	
	
	
	private void initializeScenarioInsertA00() throws SQLException {
		insertA00Conn = mock(Connection.class);
		insertA00Stmt = mock(PreparedStatement.class);
		insertA00Rs = mock(ResultSet.class);
		
		when(insertA00Conn.prepareStatement(any(String.class))).thenReturn(insertA00Stmt);
		when(insertA00Stmt.executeUpdate()).thenReturn(1);
		
		when(insertA00Stmt.executeQuery()).thenReturn(insertA00Rs);
		when(insertA00Rs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Country
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Check Country
						  	    .thenReturn(false).thenReturn(false)					// Address Form - Check Exist
						  	    .thenReturn(true);										// Insert
		when(insertA00Rs.getLong(any(String.class))).thenReturn(new Long(1));
		when(insertA00Rs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(insertA00Rs.getString(any(String.class))).thenReturn(" ");
		when(insertA00Rs.getString(any(String.class))).thenReturn(" ");
		when(insertA00Rs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInsertA01() throws SQLException {
		insertA01Conn = mock(Connection.class);
		insertA01Stmt = mock(PreparedStatement.class);
		insertA01Rs = mock(ResultSet.class);
		
		when(insertA01Conn.prepareStatement(any(String.class))).thenReturn(insertA01Stmt);
		when(insertA01Stmt.executeUpdate()).thenReturn(1);
		
		when(insertA01Stmt.executeQuery()).thenReturn(insertA01Rs);
		when(insertA01Rs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Country
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Check Country
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Check Exist
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Select
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Check State
						  	    .thenReturn(true);										// Insert
		when(insertA01Rs.getLong(any(String.class))).thenReturn(new Long(1));
		when(insertA01Rs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(insertA01Rs.getString(any(String.class))).thenReturn(" ");
		when(insertA01Rs.getString(FormDbTableColumn.COL_COD_FORM)).thenReturn("A01");
		when(insertA01Rs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	@Test
	public void missingReferenceMulti1() {
		DeciTree<AddressInfo> tree = initializeMissingReferenceMulti1();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1607);
		assertTrue(result.getFailMessage().equals("Address has multiple references"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeMissingReferenceMulti1() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesMissingReferenceMulti1();
		option.conn = insertA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressInsert(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesMissingReferenceMulti1() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
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
	public void missingReferenceMulti2() {
		DeciTree<AddressInfo> tree = initializeMissingReferenceMulti2();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1607);
		assertTrue(result.getFailMessage().equals("Address has multiple references"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeMissingReferenceMulti2() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesMissingReferenceMulti2();
		option.conn = insertA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressInsert(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesMissingReferenceMulti2() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
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
	public void missingReferenceMulti3() {
		DeciTree<AddressInfo> tree = initializeMissingReferenceMulti3();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1607);
		assertTrue(result.getFailMessage().equals("Address has multiple references"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeMissingReferenceMulti3() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesMissingReferenceMulti3();
		option.conn = insertA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressInsert(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesMissingReferenceMulti3() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
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
	public void missingReferenceMulti4() {
		DeciTree<AddressInfo> tree = initializeMissingReferenceMulti4();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1607);
		assertTrue(result.getFailMessage().equals("Address has multiple references"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeMissingReferenceMulti4() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesMissingReferenceMulti4();
		option.conn = insertA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressInsert(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesMissingReferenceMulti4() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
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
		option.conn = insertA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressInsert(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesMissingReference() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codCustomer = -1;
		address.codStore = -1;
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
		option.conn = insertA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressInsert(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesMissingFieldCodCountry() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
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
		option.conn = insertA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressInsert(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesMissingFieldCodOwner() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = -1;
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
		option.conn = insertA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressInsert(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesA00MissingFieldLine1() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
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
	public void a00MissingFieldCountry() {
		DeciTree<AddressInfo> tree = initializeA00MissingFieldCountry();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeA00MissingFieldCountry() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesA00MissingFieldCountry();
		option.conn = insertA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressInsert(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesA00MissingFieldCountry() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
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
	public void invalidConnection() {
		DeciTree<AddressInfo> tree = initializeInvalidConnection();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 500);
		assertTrue(result.getFailMessage().equals("Ops... something went wrong"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeInvalidConnection() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertAddresses();
		option.conn = invalidConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressSelect(option);
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
		
		option.recordInfos = buildInsertAddresses();
		option.conn = invalidCountryConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressInsert(option);
	}

	
	
	@Test
	public void insertA00() {
		DeciTree<AddressInfo> tree = initializeInsertA00();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());
		
		AddressInfo address = result.getResultset().get(0);
		assertTrue(address.codForm.equals("A00"));	
	}
		
	
	
	private DeciTree<AddressInfo> initializeInsertA00() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertAddresses();
		option.conn = insertA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressInsert(option);
	}
	
	
	
	private List<AddressInfo> buildInsertAddresses() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
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
	public void insertA01() {
		DeciTree<AddressInfo> tree = initializeInsertA01();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());
		
		AddressInfo address = result.getResultset().get(0);
		assertTrue(address.codForm.equals("A01"));	
	}
		
	
	
	private DeciTree<AddressInfo> initializeInsertA01() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertAddressesA01();
		option.conn = insertA01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressInsert(option);
	}
	
	
	
	private List<AddressInfo> buildInsertAddressesA01() {
		AddressInfo address = new AddressInfo();
		
		
		address.codOwner = 1;
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
		
		option.recordInfos = buildAddressesA01MissingFieldCodStat();
		option.conn = insertA01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressInsert(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesA01MissingFieldCodStat() {
		AddressInfo address = new AddressInfo();
		
		
		address.codOwner = 1;
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
	public void a01MissingFieldCodCity() {
		DeciTree<AddressInfo> tree = initializeA01MissingFieldCodCity();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();	

		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
		
	
	
	private DeciTree<AddressInfo> initializeA01MissingFieldCodCity() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesA01MissingFieldCodCity();
		option.conn = insertA01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressInsert(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesA01MissingFieldCodCity() {
		AddressInfo address = new AddressInfo();
		
		
		address.codOwner = 1;
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
		option.conn = insertA01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressInsert(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesA01MissingFieldDistrict() {
		AddressInfo address = new AddressInfo();
		
		
		address.codOwner = 1;
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
		option.conn = insertA01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressInsert(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesA01MissingFieldStreet() {
		AddressInfo address = new AddressInfo();
		
		
		address.codOwner = 1;
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
		option.conn = insertA01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressInsert(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesA01MissingFieldStreetNumber() {
		AddressInfo address = new AddressInfo();
		
		
		address.codOwner = 1;
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
	public void a01MissingFieldPostalcode() {
		DeciTree<AddressInfo> tree = initializeA01MissingFieldPostalcode();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();	

		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
		
	
	
	private DeciTree<AddressInfo> initializeA01MissingFieldPostalcode() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesA01MissingFieldPostalcode();
		option.conn = insertA01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressInsert(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesA01MissingFieldPostalcode() {
		AddressInfo address = new AddressInfo();
		
		
		address.codOwner = 1;
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
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullRecord() {
		DeciTree<AddressInfo> tree = initializeNullRecord();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<AddressInfo> initializeNullRecord() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = null;
		option.conn = insertA01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressInsert(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullConnection() {
		DeciTree<AddressInfo> tree = initializeNullConnection();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<AddressInfo> initializeNullConnection() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesA01MissingFieldPostalcode();
		option.conn = null;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressInsert(option);
	}
}
