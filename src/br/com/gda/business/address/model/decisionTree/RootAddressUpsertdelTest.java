package br.com.gda.business.address.model.decisionTree;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
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

//TODO: Adcionar script teste para Insert

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class RootAddressUpsertdelTest {
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;
	
	@Mock private Connection deleteA00Conn;
	@Mock private PreparedStatement deleteA00Stmt;
	@Mock private ResultSet deleteA00Rs;
	
	@Mock private Connection deleteA01Conn;
	@Mock private PreparedStatement deleteA01Stmt;
	@Mock private ResultSet deleteA01Rs;
	
	@Mock private Connection notFoundConn;
	@Mock private PreparedStatement notFoundStmt;
	@Mock private ResultSet notFoundRs;
	
	@Mock private Connection updateA00Conn;
	@Mock private PreparedStatement updateA00Stmt;
	@Mock private ResultSet updateA00Rs;
	
	@Mock private Connection updateA01Conn;
	@Mock private PreparedStatement updateA01Stmt;
	@Mock private ResultSet updateA01Rs;
	
	@Mock private Connection dontExistConn;
	@Mock private PreparedStatement dontExistStmt;
	@Mock private ResultSet dontExistRs;
	
	@Mock private Connection invalidCountryConn;
	@Mock private PreparedStatement invalidCountryStmt;
	@Mock private ResultSet invalidCountryRs;
	
	@Mock private Connection invalidStateConn;
	@Mock private PreparedStatement invalidStateStmt;
	@Mock private ResultSet invalidStateRs;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioDeleteA00();
		initializeScenarioDeleteA01();
		initializeScenarioNotFound();
		initializeScenarioUpdateA00();
		initializeScenarioUpdateA01();
		initializeScenarioInvalidConnection();
		initializeScenarioDontExist();
		initializeScenarioInvalidCountry();
		initializeScenarioInvalidState();
	}
	
	
	
	private void initializeScenarioDeleteA00() throws SQLException {
		deleteA00Conn = mock(Connection.class);
		deleteA00Stmt = mock(PreparedStatement.class);
		deleteA00Rs = mock(ResultSet.class);
		
		when(deleteA00Conn.prepareStatement(any(String.class))).thenReturn(deleteA00Stmt);			
		doNothing().when(deleteA00Stmt).setString(anyInt(), anyString());
		doNothing().when(deleteA00Stmt).setLong(anyInt(), anyLong());
		doNothing().when(deleteA00Stmt).setTime(anyInt(), any(Time.class));		
		
		when(deleteA00Stmt.executeUpdate()).thenReturn(1);
		
		when(deleteA00Stmt.executeQuery()).thenReturn(deleteA00Rs);		
		when(deleteA00Rs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Update - Check Country
					  	        .thenReturn(true).thenReturn(true).thenReturn(false)	// Update - Check Exist
					  	        .thenReturn(true).thenReturn(true).thenReturn(false)	// Update - Address Form - Check Country
					  	        .thenReturn(false).thenReturn(false)					// Update - Address Form - Check Exist
					  	     	  														// Update
					  	        .thenReturn(true).thenReturn(true).thenReturn(false);	// Delete - Check Exist
		                     															// Delete
		
		when(deleteA00Rs.getLong(any(String.class))).thenReturn(new Long(1));
		when(deleteA00Rs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(deleteA00Rs.getString(any(String.class))).thenReturn(" ");
		when(deleteA00Rs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
	}
	
	
	
	private void initializeScenarioDeleteA01() throws SQLException {
		deleteA01Conn = mock(Connection.class);
		deleteA01Stmt = mock(PreparedStatement.class);
		deleteA01Rs = mock(ResultSet.class);
		
		when(deleteA01Conn.prepareStatement(any(String.class))).thenReturn(deleteA01Stmt);			
		doNothing().when(deleteA01Stmt).setString(anyInt(), anyString());
		doNothing().when(deleteA01Stmt).setLong(anyInt(), anyLong());
		doNothing().when(deleteA01Stmt).setTime(anyInt(), any(Time.class));		
		
		when(deleteA01Stmt.executeUpdate()).thenReturn(1);
		
		when(deleteA01Stmt.executeQuery()).thenReturn(deleteA01Rs);		
		when(deleteA01Rs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Update - Check Country
								.thenReturn(true).thenReturn(true).thenReturn(false)	// Update - Check Exist
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Update - Address Form - Check Country
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Update - Address Form - Check Exist
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Update - Address Form - Select
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Update - Check State
						  	    														// Update
					  	        .thenReturn(true).thenReturn(true).thenReturn(false);	// Delete - Check Exist
		                     															// Delete
		
		when(deleteA01Rs.getLong(any(String.class))).thenReturn(new Long(1));
		when(deleteA01Rs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(deleteA01Rs.getString(any(String.class))).thenReturn(" ");
		when(deleteA01Rs.getString(FormAddressDbTableColumn.COL_COD_FORM)).thenReturn("A01");
		when(deleteA01Rs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));	
	}
	
	
	
	private void initializeScenarioNotFound() throws SQLException {
		notFoundConn = mock(Connection.class);
		notFoundStmt = mock(PreparedStatement.class);
		notFoundRs = mock(ResultSet.class);
		
		when(notFoundConn.prepareStatement(any(String.class))).thenReturn(notFoundStmt);			
		doNothing().when(notFoundStmt).setString(anyInt(), anyString());
		doNothing().when(notFoundStmt).setLong(anyInt(), anyLong());
		doNothing().when(notFoundStmt).setTime(anyInt(), any(Time.class));		
		
		when(notFoundStmt.executeUpdate()).thenReturn(1);
		
		when(notFoundStmt.executeQuery()).thenReturn(notFoundRs);		
		when(notFoundRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Update - Check Country
						       .thenReturn(false);									// Update - Check Exist
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
	
	
	
	private void initializeScenarioInvalidConnection() throws SQLException {
		invalidStmt = mock(PreparedStatement.class);
		invalidConn = mock(Connection.class);
		
		when(invalidConn.prepareStatement(anyString())).thenThrow(new SQLException());
		doThrow(new SQLException()).when(invalidStmt).setString(anyInt(), anyString());
		doThrow(new SQLException()).when(invalidStmt).setLong(anyInt(), anyLong());
		doThrow(new SQLException()).when(invalidStmt).setTime(anyInt(), any(Time.class)); 
		
		when(invalidStmt.executeQuery()).thenThrow(new SQLException());
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
		
		option.recordInfos = buildDeleteAddressesMissingFieldCodOwner();
		option.conn = deleteA01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpsertdel(option);
	}
	
	
	
	private List<AddressInfo> buildDeleteAddressesMissingFieldCodOwner() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = -1;
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
		address.isDeleted = true;	
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}
	
	
	
	@Test
	public void notFound() {
		DeciTree<AddressInfo> tree = initializeNotFound();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1602);
		assertTrue(result.getFailMessage().equals("Address not found on DB"));
	}
		
	
	
	private DeciTree<AddressInfo> initializeNotFound() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildDeleteAddressesA01();
		option.conn = notFoundConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpsertdel(option);
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
		
		option.recordInfos = buildDeleteAddressesA01();
		option.conn = invalidConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpsertdel(option);
	}	
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullSchema() {
		DeciTree<AddressInfo> tree = initializeNullSchema();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<AddressInfo> initializeNullSchema() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildDeleteAddressesA01();
		option.conn = deleteA01Conn;
		option.schemaName = null;
		
		return new RootAddressUpsertdel(option);
	}	
	

	
	@Test(expected = NullPointerException.class)
	public void nullOption() {
		DeciTree<AddressInfo> tree = initializeNullOption();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<AddressInfo> initializeNullOption() {		
		return new RootAddressUpsertdel(null);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullRecord() {
		DeciTree<AddressInfo> tree = initializeNullRecord();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<AddressInfo> initializeNullRecord() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = null;
		option.conn = deleteA01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpsertdel(option);
	} 
	
	
	
	@Test
	public void deleteA00() {
		DeciTree<AddressInfo> tree = initializeDeleteA00();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());
		assertTrue(result.getResultset().get(0).codOwner == -1);
	}
		
	
	
	private DeciTree<AddressInfo> initializeDeleteA00() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildDeleteAddressesA00();
		option.conn = deleteA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpsertdel(option);
	}
	
	
	
	private List<AddressInfo> buildDeleteAddressesA00() {
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
		address.isDeleted = true;
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}
	
	
	
	@Test
	public void deleteA01() {
		DeciTree<AddressInfo> tree = initializeDeleteA01();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());
		assertTrue(result.getResultset().get(0).codOwner == -1);
	}
		
	
	
	private DeciTree<AddressInfo> initializeDeleteA01() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildDeleteAddressesA01();
		option.conn = deleteA01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpsertdel(option);
	}
	
	
	
	private List<AddressInfo> buildDeleteAddressesA01() {
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
		address.isDeleted = true;
		
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
		option.conn = updateA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpsertdel(option);
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
		
		return new RootAddressUpsertdel(option);
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
		
		return new RootAddressUpsertdel(option);
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
		
		return new RootAddressUpsertdel(option);
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
		
		return new RootAddressUpsertdel(option);
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
		
		return new RootAddressUpsertdel(option);
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
		
		return new RootAddressUpsertdel(option);
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
		
		return new RootAddressUpsertdel(option);
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
		
		return new RootAddressUpsertdel(option);
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
		
		return new RootAddressUpsertdel(option);
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
		
		return new RootAddressUpsertdel(option);
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
		
		return new RootAddressUpsertdel(option);
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
		
		return new RootAddressUpsertdel(option);
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
		
		return new RootAddressUpsertdel(option);
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
		
		return new RootAddressUpsertdel(option);
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
		
		return new RootAddressUpsertdel(option);
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
		
		return new RootAddressUpsertdel(option);
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
		
		return new RootAddressUpsertdel(option);
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
		
		return new RootAddressUpsertdel(option);
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
}
