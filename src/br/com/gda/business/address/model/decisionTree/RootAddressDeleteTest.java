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
import br.com.gda.common.DbConnection;
import br.com.gda.common.DbSchema;
import br.com.gda.model.decisionTree.DeciResult;
import br.com.gda.model.decisionTree.DeciTree;
import br.com.gda.model.decisionTree.DeciTreeOption;

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class RootAddressDeleteTest {
	@Mock private Connection deleteConn;
	@Mock private PreparedStatement deleteStmt;
	@Mock private ResultSet deleteRs;
	
	@Mock private Connection notFoundConn;
	@Mock private PreparedStatement notFoundStmt;
	@Mock private ResultSet notFoundRs;
	
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioDelete();
		initializeScenarioNotFound();		
		initializeScenarioInvalidConnection();
	}
	
	
	
	private void initializeScenarioDelete() throws SQLException {
		deleteConn = mock(Connection.class);
		deleteStmt = mock(PreparedStatement.class);
		deleteRs = mock(ResultSet.class);
		
		when(deleteConn.prepareStatement(any(String.class))).thenReturn(deleteStmt);			
		doNothing().when(deleteStmt).setString(anyInt(), anyString());
		doNothing().when(deleteStmt).setLong(anyInt(), anyLong());
		doNothing().when(deleteStmt).setTime(anyInt(), any(Time.class));		
		
		when(deleteStmt.executeUpdate()).thenReturn(1);
		
		when(deleteStmt.executeQuery()).thenReturn(deleteRs);		
		when(deleteRs.next()).thenReturn(true).thenReturn(true).thenReturn(false);	// Check Exist
		                     														// Delete
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
		when(notFoundRs.next()).thenReturn(false);					// Check Exist
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
		assertTrue(result.getFailCode() == 1602);
		assertTrue(result.getFailMessage().equals("Address not found on DB"));
	}
		
	
	
	private DeciTree<AddressInfo> initializeNotFound() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildDeleteAddresses();
		option.conn = notFoundConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressDelete(option);
	}
	
	

	@Test
	public void delete() {
		DeciTree<AddressInfo> tree = initializeDelete();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());
	}
		
	
	
	private DeciTree<AddressInfo> initializeDelete() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildDeleteAddresses();
		option.conn = deleteConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressDelete(option);
	}
	
	
	
	private List<AddressInfo> buildDeleteAddresses() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codAddress = 1;		
		
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
		option.conn = deleteConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressDelete(option);
	}
	
	
	
	private List<AddressInfo> buildDeleteAddressesMissingFieldCodOwner() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = -1;
		address.codAddress = 1;		
		
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
		
		option.recordInfos = buildDeleteAddressesMissingFieldCodAddress();
		option.conn = deleteConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressDelete(option);
	}
	
	
	
	private List<AddressInfo> buildDeleteAddressesMissingFieldCodAddress() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codAddress = -1;		
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}	
	
	
	
	@Test(expected = IllegalStateException.class)
	public void invalidConnection() {
		DeciTree<AddressInfo> tree = initializeInvalidConnection();
		tree.makeDecision();	
	}
	
	
	
	private DeciTree<AddressInfo> initializeInvalidConnection() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildDeleteAddresses();
		option.conn = invalidConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressDelete(option);
	}	
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullOption() {
		DeciTree<AddressInfo> tree = initializeNullOption();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<AddressInfo> initializeNullOption() {		
		return new RootAddressDelete(null);
	}	
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullRecord() {
		DeciTree<AddressInfo> tree = initializeNullRecord();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<AddressInfo> initializeNullRecord() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = null;
		option.conn = deleteConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressDelete(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullSchema() {
		DeciTree<AddressInfo> tree = initializeNullSchema();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<AddressInfo> initializeNullSchema() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildDeleteAddresses();
		option.conn = deleteConn;
		option.schemaName = null;
		
		return new RootAddressDelete(option);
	}	
}
