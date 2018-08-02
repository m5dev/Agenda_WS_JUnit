package br.com.gda.business.storeEmployee.model;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.gda.business.storeEmployee.model.StoreEmpModelInsert;
import br.com.gda.common.DbConnection;
import br.com.gda.model.Model;

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class StoreEmpModelInsertTest {
	@Mock private Connection insertConn;
	@Mock private PreparedStatement insertStmt;
	@Mock private ResultSet insertRs;
	
	@Mock private Connection storeEmpAlreadyExistConn;	
	@Mock private ResultSet storeEmpAlreadyExistRs;
	@Mock private PreparedStatement storeEmpAlreadyExistStmt;
	
	@Mock private Connection invalidConn;	
	
	@Mock private Connection insertSoftDeletedConn;
	@Mock private ResultSet insertSoftDeletedRs;
	@Mock private PreparedStatement insertSoftDeletedStmt;
	
	@Mock private Connection ewtConflictConn;
	@Mock private PreparedStatement ewtConflictStmt;
	@Mock private ResultSet ewtConflictRs;	
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioInsert();
		initializeScenarioEwtConflict();
		initializeScenarioInsertSoftDeleted();
		initializeScenarioStoreEmpAlreadyExist();
		initializeScenarioInvalidConnection();
	}
	
	
	
	private void initializeScenarioInsert() throws SQLException {
		insertConn = mock(Connection.class);
		insertStmt = mock(PreparedStatement.class);
		insertRs = mock(ResultSet.class);
		
		when(insertConn.prepareStatement(any(String.class))).thenReturn(insertStmt);
		when(insertStmt.executeUpdate()).thenReturn(1);
		
		when(insertStmt.executeQuery()).thenReturn(insertRs);
		when(insertRs.next()).thenReturn(true).thenReturn(false)					// SE  - Already exist
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// SE  - Check EmpPos
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// SE  - Check Store
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// SE  - Check Emp
		                     .thenReturn(true).thenReturn(false)					// SE  - Check soft deleted
		                     .thenReturn(true)										// SE  - Insert
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// SE  - Check Store Work Time
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// SWT - Select
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// EWT - Check Owner
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// EWT - Check Emp
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// EWT - Check Store
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// EWT - Check Weekday
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// EWT - Check StoreEmp
		                     .thenReturn(true).thenReturn(false)					// EWT - Check already exist
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// EWT - Check Store Time
		                     .thenReturn(true).thenReturn(false)					// EWT - Check conflict
		                     .thenReturn(true).thenReturn(false)					// EWT - Check soft deleted
		                     .thenReturn(true)										// EWT - Insert
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// EWT - Select		                     
		                     .thenReturn(true).thenReturn(true).thenReturn(false);	// SE  - Select
		when(insertRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(insertRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(insertRs.getString(any(String.class))).thenReturn(" ");
		when(insertRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioEwtConflict() throws SQLException {
		ewtConflictConn = mock(Connection.class);
		ewtConflictStmt = mock(PreparedStatement.class);
		ewtConflictRs = mock(ResultSet.class);
		
		when(ewtConflictConn.prepareStatement(any(String.class))).thenReturn(ewtConflictStmt);
		when(ewtConflictStmt.executeUpdate()).thenReturn(1);
		
		when(ewtConflictStmt.executeQuery()).thenReturn(ewtConflictRs);
		when(ewtConflictRs.next()).thenReturn(true).thenReturn(false)					// SE  - Already exist
		 	                      .thenReturn(true).thenReturn(true).thenReturn(false)	// SE  - Check EmpPos
			                      .thenReturn(true).thenReturn(true).thenReturn(false)	// SE  - Check Store
			                      .thenReturn(true).thenReturn(true).thenReturn(false)	// SE  - Check Emp
			                      .thenReturn(true).thenReturn(false)					// SE  - Check soft deleted
			                      .thenReturn(true)										// SE  - Insert
			                      .thenReturn(true).thenReturn(true).thenReturn(false)	// SE  - Check Store Work Time
			                      .thenReturn(true).thenReturn(true).thenReturn(false)	// SWT - Select
			                      .thenReturn(true).thenReturn(true).thenReturn(false)	// EWT - Check Owner
			                      .thenReturn(true).thenReturn(true).thenReturn(false)	// EWT - Check Emp
			                      .thenReturn(true).thenReturn(true).thenReturn(false)	// EWT - Check Store
			                      .thenReturn(true).thenReturn(true).thenReturn(false)	// EWT - Check Weekday
			                      .thenReturn(true).thenReturn(true).thenReturn(false)	// EWT - Check StoreEmp
			                      .thenReturn(true).thenReturn(false)					// EWT - Check already exist
			                      .thenReturn(true).thenReturn(true).thenReturn(false)	// EWT - Check Store Time
			                      .thenReturn(true).thenReturn(true).thenReturn(false);	// EWT - Check conflict
		when(ewtConflictRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(ewtConflictRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(ewtConflictRs.getString(any(String.class))).thenReturn(" ");
		when(ewtConflictRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInsertSoftDeleted() throws SQLException {
		insertSoftDeletedConn = mock(Connection.class);
		insertSoftDeletedStmt = mock(PreparedStatement.class);
		insertSoftDeletedRs = mock(ResultSet.class);
		
		when(insertSoftDeletedConn.prepareStatement(any(String.class))).thenReturn(insertSoftDeletedStmt);
		when(insertSoftDeletedStmt.executeUpdate()).thenReturn(1);
		
		when(insertSoftDeletedStmt.executeQuery()).thenReturn(insertSoftDeletedRs);
		when(insertSoftDeletedRs.next()).thenReturn(true).thenReturn(false)						// SE  - Already exist
								        .thenReturn(true).thenReturn(true).thenReturn(false)	// SE  - Check EmpPos
								        .thenReturn(true).thenReturn(true).thenReturn(false)	// SE  - Check Store
								        .thenReturn(true).thenReturn(true).thenReturn(false)	// SE  - Check Emp
								        .thenReturn(true).thenReturn(true).thenReturn(false)	// SE  - Check soft deleted
								        .thenReturn(true).thenReturn(true).thenReturn(false)	// SE  - Check Store Work Time
								        .thenReturn(true).thenReturn(true).thenReturn(false)	// SWT - Select
								        .thenReturn(true).thenReturn(true).thenReturn(false)	// EWT - Check Owner
								        .thenReturn(true).thenReturn(true).thenReturn(false)	// EWT - Check Emp
								        .thenReturn(true).thenReturn(true).thenReturn(false)	// EWT - Check Store
								        .thenReturn(true).thenReturn(true).thenReturn(false)	// EWT - Check Weekday
								        .thenReturn(true).thenReturn(true).thenReturn(false)	// EWT - Check StoreEmp
								        .thenReturn(true).thenReturn(false)						// EWT - Check already exist
								        .thenReturn(true).thenReturn(true).thenReturn(false)	// EWT - Check Store Time
								        .thenReturn(true).thenReturn(false)						// EWT - Check conflict
								        .thenReturn(true).thenReturn(false)						// EWT - Check soft deleted
								        .thenReturn(true)										// EWT - Insert
								        .thenReturn(true).thenReturn(true).thenReturn(false)	// EWT - Select		                     
								        .thenReturn(true).thenReturn(true).thenReturn(false);	// SE  - Select
		when(insertSoftDeletedRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(insertSoftDeletedRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(insertSoftDeletedRs.getString(any(String.class))).thenReturn(" ");
		when(insertSoftDeletedRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioStoreEmpAlreadyExist() throws SQLException {
		storeEmpAlreadyExistConn = mock(Connection.class);
		storeEmpAlreadyExistStmt = mock(PreparedStatement.class);
		storeEmpAlreadyExistRs = mock(ResultSet.class);		
		
		when(storeEmpAlreadyExistConn.prepareStatement(any(String.class))).thenReturn(storeEmpAlreadyExistStmt);
		when(storeEmpAlreadyExistStmt.executeUpdate()).thenReturn(1);
		
		
		when(storeEmpAlreadyExistStmt.executeQuery()).thenReturn(storeEmpAlreadyExistRs);
		when(storeEmpAlreadyExistRs.next()).thenReturn(true).thenReturn(false);
		when(storeEmpAlreadyExistRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(storeEmpAlreadyExistRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(storeEmpAlreadyExistRs.getString(any(String.class))).thenReturn(" ");
		when(storeEmpAlreadyExistRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));	
	}
	
	
	
	private void initializeScenarioInvalidConnection() throws SQLException {
		invalidConn = mock(Connection.class);
		when(invalidConn.prepareStatement(anyString())).thenThrow(new SQLException());
	}
	
	
	
	@Test
	public void insertNewRecord() {
		initializeInsertNewRecord();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"nameStore\":\" \",\"codEmployee\":1,\"nameEmployee\":\" \",\"codPositionStore\":1,\"txtPositionStore\":\" \",\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInsertNewRecord() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);
		model = new StoreEmpModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void ewtConflict() {
		initializeEwtConflict();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"nameStore\":\" \",\"codEmployee\":1,\"nameEmployee\":\" \",\"codPositionStore\":1,\"txtPositionStore\":\" \",\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeEwtConflict() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(ewtConflictConn);
		model = new StoreEmpModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void insertStoreEmpSoftDeleted() {
		initializeInsertStoreEmpSoftDeleted();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"nameStore\":\" \",\"codEmployee\":1,\"nameEmployee\":\" \",\"codPositionStore\":1,\"txtPositionStore\":\" \",\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInsertStoreEmpSoftDeleted() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertSoftDeletedConn);
		model = new StoreEmpModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	protected String incomingDataOrdinaryUsage() {
		return "[{\"codOwner\": 1,\"codStore\": 1,\"codEmployee\": 1,\"codPositionStore\": 1}]";
		//return "[{\"codOwner\": 8,\"codStore\": 15,\"codEmployee\": 64,\"codPositionStore\": 4}]";
	}
	
	
	
	@Test
	public void missingFieldCodOwner() {
		initializeMissingFieldCodOwner();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	protected void initializeMissingFieldCodOwner() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);		
		model = new StoreEmpModelInsert(incomingDataMissingFieldCodOwner());
	}	
	
	
	
	protected String incomingDataMissingFieldCodOwner() {
		return "[{\"codStore\": 15,\"codEmployee\": 64,\"codPositionStore\": 4}]";
	}
	
	
	
	@Test
	public void missingFieldStore() {
		initializeMissingFieldStore();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	protected void initializeMissingFieldStore() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);		
		model = new StoreEmpModelInsert(incomingDataMissingFieldStore());
	}	
	
	
	
	protected String incomingDataMissingFieldStore() {
		return "[{\"codOwner\": 8,\"codEmployee\": 64,\"codPositionStore\": 4}]";
	}
	
	
	
	@Test
	public void missingFieldCodEmployee() {
		initializeMissingFieldCodEmployee();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	protected void initializeMissingFieldCodEmployee() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);		
		model = new StoreEmpModelInsert(incomingDataMissingFieldCodEmployee());
	}	
	
	
	
	protected String incomingDataMissingFieldCodEmployee() {
		return "[{\"codOwner\": 8,\"codStore\": 15,\"codPositionStore\": 4}]";
	}
	
	
	
	@Test
	public void missingFieldCodPositionStore() {
		initializeMissingFieldCodPositionStore();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	protected void initializeMissingFieldCodPositionStore() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);		
		model = new StoreEmpModelInsert(incomingDataMissingFieldCodPositionStore());
	}	
	
	
	
	protected String incomingDataMissingFieldCodPositionStore() {
		return "[{\"codOwner\": 8,\"codStore\": 15,\"codEmployee\": 64}]";
	}
	
	
	
	
	@Test
	public void invalidConnection() {
		initializeInvalidConnection();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
		
		String responseBody = "{\"selectCode\":500,\"selectMessage\":\"Ops... something went wrong\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidConnection() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidConn);
		model = new StoreEmpModelInsert(incomingDataOrdinaryUsage());
	}
}
