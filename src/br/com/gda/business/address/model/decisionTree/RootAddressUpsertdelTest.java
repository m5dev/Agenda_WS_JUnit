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
import br.com.gda.business.form.dao.FormDbTableColumn;
import br.com.gda.common.DbConnection;
import br.com.gda.common.DbSchema;
import br.com.gda.model.decisionTree.DeciResult;
import br.com.gda.model.decisionTree.DeciTree;
import br.com.gda.model.decisionTree.DeciTreeOption;


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
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioDeleteA00();
		initializeScenarioDeleteA01();
		initializeScenarioNotFound();		
		initializeScenarioInvalidConnection();
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
		when(deleteA01Rs.getString(FormDbTableColumn.COL_COD_FORM)).thenReturn("A01");
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
		
		option.recordInfos = buildDeleteAddressesMissingFieldCodAddress();
		option.conn = deleteA01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressUpsertdel(option);
	}
	
	
	
	private List<AddressInfo> buildDeleteAddressesMissingFieldCodAddress() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codAddress = -1;
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
}
