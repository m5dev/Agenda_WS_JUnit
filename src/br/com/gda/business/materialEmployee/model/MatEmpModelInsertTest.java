package br.com.gda.business.materialEmployee.model;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
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

import br.com.gda.common.DbConnection;
import br.com.gda.model.Model;

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class MatEmpModelInsertTest {
	@Mock private Connection insertSoftDeletedConn;
	@Mock private Connection insertNewConn;
	//@Mock private Connection storeEmpAlreadyExistConn;
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement insertNewStmt;
	@Mock private PreparedStatement insertSoftDeletedStmt;
	//@Mock private PreparedStatement storeEmpAlreadyExistStmt;
	@Mock private ResultSet insertSoftDeletedRs;
	@Mock private ResultSet insertNewRs;
	//@Mock private ResultSet storeEmpAlreadyExistRs;
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioInsertNew();
		initializeScenarioInsertSoftDeleted();
		//initializeScenarioStoreEmpAlreadyExist();
		//initializeScenarioInvalidConnection();
	}
	
	
	
	private void initializeScenarioInsertNew() throws SQLException {
		insertNewConn = mock(Connection.class);
		insertNewStmt = mock(PreparedStatement.class);
		insertNewRs = mock(ResultSet.class);
		
		when(insertNewConn.prepareStatement(any(String.class))).thenReturn(insertNewStmt);
		when(insertNewStmt.executeUpdate()).thenReturn(1);
		
		when(insertNewStmt.executeQuery()).thenReturn(insertNewRs);
		when(insertNewRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	//CheckOwner
		                        .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckStore
		                        .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckEmp
		                        .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckMat
		                        .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckStoreEmp
		                        .thenReturn(true).thenReturn(false)						//CheckExist
		                        .thenReturn(true).thenReturn(false)						//CheckSoftDeleted
		                        														//Insert
		                        .thenReturn(true).thenReturn(true).thenReturn(false);	//Select
		when(insertNewRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(insertNewRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(insertNewRs.getString(any(String.class))).thenReturn(" ");
		when(insertNewRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInsertSoftDeleted() throws SQLException {
		insertSoftDeletedConn = mock(Connection.class);
		insertSoftDeletedStmt = mock(PreparedStatement.class);
		insertSoftDeletedRs = mock(ResultSet.class);
		
		when(insertSoftDeletedConn.prepareStatement(any(String.class))).thenReturn(insertSoftDeletedStmt);
		when(insertSoftDeletedStmt.executeUpdate()).thenReturn(1);
		
		when(insertSoftDeletedStmt.executeQuery()).thenReturn(insertSoftDeletedRs);
		when(insertSoftDeletedRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	//CheckOwner
		                        		.thenReturn(true).thenReturn(true).thenReturn(false)	//CheckStore
		                        		.thenReturn(true).thenReturn(true).thenReturn(false)	//CheckEmp
		                        		.thenReturn(true).thenReturn(true).thenReturn(false)	//CheckMat
		                        		.thenReturn(true).thenReturn(true).thenReturn(false)	//CheckStoreEmp
		                        		.thenReturn(true).thenReturn(false)						//CheckExist
		                        		.thenReturn(true).thenReturn(true).thenReturn(false)	//CheckSoftDeleted
		                        																//Update
		                        		.thenReturn(true).thenReturn(true).thenReturn(false);	//Select
		when(insertSoftDeletedRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(insertSoftDeletedRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(insertSoftDeletedRs.getString(any(String.class))).thenReturn(" ");
		when(insertSoftDeletedRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	@Test
	public void insertNewRecord() {
		initializeInsertNewRecord();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"codMat\":1,\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInsertNewRecord() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new MatEmpModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	protected String incomingDataOrdinaryUsage() {
		return "{\"codOwner\": 8,    \"codStore\": 15,    \"codEmployee\": 66,    \"codMat\": 72}";
	}
	
	
	
	@Test
	public void insertSoftDeletedecord() {
		initializeInsertSoftDeletedRecord();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"codMat\":1,\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInsertSoftDeletedRecord() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertSoftDeletedConn);
		model = new MatEmpModelInsert(incomingDataOrdinaryUsage());
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new MatEmpModelInsert(incomingDataMissingFieldCodOwner());
	}
	
	
	
	protected String incomingDataMissingFieldCodOwner() {
		return "{\"codStore\": 15,    \"codEmployee\": 66,    \"codMat\": 72}";
	}
	
	
	
	@Test
	public void missingFieldCodStore() {
		initializeMissingFieldCodStore();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMissingFieldCodStore() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new MatEmpModelInsert(incomingDataMissingFieldCodStore());
	}
	
	
	
	protected String incomingDataMissingFieldCodStore() {
		return "{\"codOwner\": 8,    \"codEmployee\": 66,    \"codMat\": 72}";
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new MatEmpModelInsert(incomingDataMissingFieldCodEmployee());
	}
	
	
	
	protected String incomingDataMissingFieldCodEmployee() {
		return "{\"codOwner\": 8,    \"codStore\": 15,    \"codMat\": 72}";
	}
	
	
	
	@Test
	public void missingFieldCodMat() {
		initializeMissingFieldCodMat();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMissingFieldCodMat() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new MatEmpModelInsert(incomingDataMissingFieldCodEmployee());
	}
	
	
	
	protected String incomingDataMissingFieldCodMat() {
		return "{\"codOwner\": 8,    \"codStore\": 15,    \"codEmployee\": 66}";
	}
}
