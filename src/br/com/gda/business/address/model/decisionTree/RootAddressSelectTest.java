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
public class RootAddressSelectTest {
	@Mock private Connection selectA00Conn;
	@Mock private PreparedStatement selectA00Stmt;
	@Mock private ResultSet selectA00Rs;
	
	@Mock private Connection selectA01Conn;
	@Mock private PreparedStatement selectA01Stmt;
	@Mock private ResultSet selectA01Rs;
	
	@Mock private Connection notFoundConn;
	@Mock private PreparedStatement notFoundStmt;
	@Mock private ResultSet notFoundRs;
	
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		initializeScenarioSelectA00();
		initializeScenarioSelectA01();
		initializeScenarioNotFound();
		initializeScenarioInvalidConnection();
	}
	
	
	
	private void initializeScenarioSelectA00() throws SQLException {
		selectA00Conn = mock(Connection.class);
		selectA00Stmt = mock(PreparedStatement.class);
		selectA00Rs = mock(ResultSet.class);
		
		when(selectA00Conn.prepareStatement(any(String.class))).thenReturn(selectA00Stmt);
		when(selectA00Stmt.executeUpdate()).thenReturn(1);
		
		when(selectA00Stmt.executeQuery()).thenReturn(selectA00Rs);
		when(selectA00Rs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Select
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Check Country
						  	    .thenReturn(false).thenReturn(false);					// Address Form - Check Exist
		when(selectA00Rs.getLong(any(String.class))).thenReturn(new Long(1));
		when(selectA00Rs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(selectA00Rs.getString(any(String.class))).thenReturn(" ");
		when(selectA00Rs.getString(any(String.class))).thenReturn(" ");
		when(selectA00Rs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioSelectA01() throws SQLException {
		selectA01Conn = mock(Connection.class);
		selectA01Stmt = mock(PreparedStatement.class);
		selectA01Rs = mock(ResultSet.class);
		
		when(selectA01Conn.prepareStatement(any(String.class))).thenReturn(selectA01Stmt);
		when(selectA01Stmt.executeUpdate()).thenReturn(1);
		
		when(selectA01Stmt.executeQuery()).thenReturn(selectA01Rs);
		when(selectA01Rs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Select
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Check Country
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Check Exist
						  	    .thenReturn(true).thenReturn(true).thenReturn(false);	// Address Form - Select
		when(selectA01Rs.getLong(any(String.class))).thenReturn(new Long(1));
		when(selectA01Rs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(selectA01Rs.getString(any(String.class))).thenReturn(" ");
		when(selectA01Rs.getString(any(String.class))).thenReturn(" ");
		when(selectA01Rs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
		when(selectA01Rs.getString(FormAddressDbTableColumn.COL_COD_FORM)).thenReturn("A01");
	}
	
	
	
	private void initializeScenarioNotFound() throws SQLException {
		notFoundConn = mock(Connection.class);
		notFoundStmt = mock(PreparedStatement.class);
		notFoundRs = mock(ResultSet.class);
		
		when(notFoundConn.prepareStatement(any(String.class))).thenReturn(notFoundStmt);
		when(notFoundStmt.executeUpdate()).thenReturn(1);
		
		when(notFoundStmt.executeQuery()).thenReturn(notFoundRs);
		when(notFoundRs.next()).thenReturn(false);	// Select
		when(notFoundRs.getString(any(String.class))).thenReturn(" ");
		when(notFoundRs.getString(any(String.class))).thenReturn(" ");
		when(notFoundRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
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
	public void notFound() {
		DeciTree<AddressInfo> tree = initializeNotFound();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 10);
		assertTrue(result.getFailMessage().equals("Data not found"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeNotFound() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesSelectA00();
		option.conn = notFoundConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressSelect(option);
	}
	
	

	@Test
	public void selectA00() {
		DeciTree<AddressInfo> tree = initializeSelectA00();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());
		assertTrue(result.getResultset().get(0).codForm.equals("A00"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeSelectA00() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesSelectA00();
		option.conn = selectA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressSelect(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesSelectA00() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codCustomer = 1;
		address.codStore = 1;
		address.codEmployee = -1;
		address.codCountry = " ";
		
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
		option.conn = selectA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressSelect(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesMissingFieldCodOwner() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = -1;
		address.codCustomer = 1;
		address.codStore = 1;
		address.codEmployee = -1;
		address.codCountry = " ";
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}
	
	
	
	@Test
	public void missingReferense() {
		DeciTree<AddressInfo> tree = initializeMissingReferense();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1606);
		assertTrue(result.getFailMessage().equals("No reference added to Address"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeMissingReferense() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesMissingReferense();
		option.conn = selectA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressSelect(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesMissingReferense() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codCustomer = -1;
		address.codStore = -1;
		address.codEmployee = -1;
		address.codCountry = " ";
		
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
		
		option.recordInfos = buildAddressesSelectA01();
		option.conn = invalidConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressSelect(option);
	}
	
	
	
	@Test
	public void selectA01() {
		DeciTree<AddressInfo> tree = initializeSelectA01();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());
		assertTrue(result.getResultset().get(0).codForm.equals("A01"));	
	}
	
	
	
	private DeciTree<AddressInfo> initializeSelectA01() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesSelectA01();
		option.conn = selectA01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressSelect(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesSelectA01() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codCustomer = 1;
		address.codStore = 1;
		address.codEmployee = -1;
		address.codCountry = " ";
		
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
		option.conn = selectA01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressSelect(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullConnection() {
		DeciTree<AddressInfo> tree = initializeNullConnection();
		tree.makeDecision();	
	}
	
	
	
	private DeciTree<AddressInfo> initializeNullConnection() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesSelectA01();
		option.conn = null;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressSelect(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullSchema() {
		DeciTree<AddressInfo> tree = initializeNullSchema();
		tree.makeDecision();	
	}
	
	
	
	private DeciTree<AddressInfo> initializeNullSchema() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesSelectA01();
		option.conn = selectA01Conn;
		option.schemaName = null;
		
		return new RootAddressSelect(option);
	}
}
