package br.com.gda.business.storeLeaveDate.model;


import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.gda.common.DbConnection;
import br.com.gda.model.Model;
import javax.ws.rs.core.Response;



@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class StoreLDateModelInsertTest {
	@Mock private Connection alreadyExistConn;
	@Mock private PreparedStatement alreadyExistStmt;
	@Mock private ResultSet alreadyExistRs;
	
	@Mock private Connection insertNewConn;
	@Mock private PreparedStatement insertNewStmt;
	@Mock private ResultSet insertNewRs;
	
	@Mock private Connection insertSoftDeletedConn;
	@Mock private PreparedStatement insertSoftDeletedStmt;
	@Mock private ResultSet insertSoftDeletedRs;
	
	@Mock private Connection invalidStoreConn;
	@Mock private PreparedStatement invalidStoreStmt;
	@Mock private ResultSet invalidStoreRs;
	
	@Mock private Connection invalidOwnerConn;
	@Mock private PreparedStatement invalidOwnerStmt;
	@Mock private ResultSet invalidOwnerRs;
	
	@Mock private Connection invalidConn;	
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioInsertNew();
		initializeScenarioInsertSoftDeleted();
		initializeScenarioDataAlreadyExist();
		initializeScenarioInvalidConnection();
		initializeScenarioInvalidStore();
		initializeScenarioInvalidOwner();
	}
	
	
	
	private void initializeScenarioInsertNew() throws SQLException {
		insertNewConn = mock(Connection.class);
		insertNewStmt = mock(PreparedStatement.class);
		insertNewRs = mock(ResultSet.class);
		
		when(insertNewConn.prepareStatement(any(String.class))).thenReturn(insertNewStmt);
		when(insertNewStmt.executeUpdate()).thenReturn(1);
		
		when(insertNewStmt.executeQuery()).thenReturn(insertNewRs);
		when(insertNewRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
							    .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Store
							    .thenReturn(true).thenReturn(false)						// Check Exist
							    .thenReturn(true).thenReturn(false)						// Check Soft Deleted
							 															// Insert
							    .thenReturn(true).thenReturn(true).thenReturn(false);	// Select
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
		when(insertSoftDeletedRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
									    .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Store
									    .thenReturn(true).thenReturn(false)						// Check Exist
									    .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Soft Deleted
									 															// Update
									    .thenReturn(true).thenReturn(true).thenReturn(false);	// Select
		when(insertSoftDeletedRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(insertSoftDeletedRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(insertSoftDeletedRs.getString(any(String.class))).thenReturn(" ");
		when(insertSoftDeletedRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));	
	}
	
	
	
	private void initializeScenarioDataAlreadyExist() throws SQLException {
		alreadyExistConn = mock(Connection.class);
		alreadyExistStmt = mock(PreparedStatement.class);
		alreadyExistRs = mock(ResultSet.class);	
		
		when(alreadyExistConn.prepareStatement(any(String.class))).thenReturn(alreadyExistStmt);	
		
		when(alreadyExistStmt.executeUpdate()).thenReturn(1);
		doNothing().when(alreadyExistStmt).setString(anyInt(), anyString());
		doNothing().when(alreadyExistStmt).setLong(anyInt(), anyLong());
		doNothing().when(alreadyExistStmt).setTime(anyInt(), any(Time.class));
		
		when(alreadyExistStmt.executeQuery()).thenReturn(alreadyExistRs);				
		when(alreadyExistRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		// Check Owner
	    						   .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Store
	    						   .thenReturn(true).thenReturn(true).thenReturn(false);	// Check Exist
		when(alreadyExistRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(alreadyExistRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(alreadyExistRs.getString(any(String.class))).thenReturn(" ");
		when(alreadyExistRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));	
	}
	
	
	
	private void initializeScenarioInvalidConnection() throws SQLException {
		invalidConn = mock(Connection.class);
		when(invalidConn.prepareStatement(anyString())).thenThrow(new SQLException());
	}
	
	
	
	private void initializeScenarioInvalidStore() throws SQLException {
		invalidStoreConn = mock(Connection.class);
		invalidStoreStmt = mock(PreparedStatement.class);
		invalidStoreRs = mock(ResultSet.class);
		
		when(invalidStoreConn.prepareStatement(any(String.class))).thenReturn(invalidStoreStmt);
		when(invalidStoreStmt.executeUpdate()).thenReturn(1);
		
		when(invalidStoreStmt.executeQuery()).thenReturn(invalidStoreRs);
		when(invalidStoreRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
							    .thenReturn(true).thenReturn(false);					// Check Store

		when(invalidStoreRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidStoreRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidStoreRs.getString(any(String.class))).thenReturn(" ");
		when(invalidStoreRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidOwner() throws SQLException {
		invalidOwnerConn = mock(Connection.class);
		invalidOwnerStmt = mock(PreparedStatement.class);
		invalidOwnerRs = mock(ResultSet.class);
		
		when(invalidOwnerConn.prepareStatement(any(String.class))).thenReturn(invalidOwnerStmt);
		when(invalidOwnerStmt.executeUpdate()).thenReturn(1);
		
		when(invalidOwnerStmt.executeQuery()).thenReturn(invalidOwnerRs);
		when(invalidOwnerRs.next()).thenReturn(true).thenReturn(false);	// Check Owner

		when(invalidOwnerRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidOwnerRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidOwnerRs.getString(any(String.class))).thenReturn(" ");
		when(invalidOwnerRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	@Test
	public void insertNewRecord() {
		initializeInsertNewRecord();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"timeValidFrom\":{\"hour\":11,\"minute\":22},\"timeValidTo\":{\"hour\":11,\"minute\":22},\"codTimezone\":\" \",\"description\":\" \"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	private void initializeInsertNewRecord() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new StoreLDateModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	private String incomingDataOrdinaryUsage() {
		return "[  {  \"codOwner\": 8,  \"codStore\": 15,  \"dateValidFrom\": {\"day\": 1, \"month\": 6, \"year\": 2018},   \"timeValidFrom\": {\"hour\": 19, \"minute\": 0},  \"dateValidTo\": {\"day\": 1, \"month\": 6, \"year\": 2019},   \"timeValidTo\": {\"hour\": 19, \"minute\": 0},  \"description\": \"Férias\"  }]";
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
	
		
	
	private void initializeMissingFieldCodOwner() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);		
		model = new StoreLDateModelInsert(incomingDataMissingFieldCodOwner());
	}	
	
	
	
	private String incomingDataMissingFieldCodOwner() {
		return "[  {\"codStore\": 15,  \"dateValidFrom\": {\"day\": 1, \"month\": 6, \"year\": 2018},   \"timeValidFrom\": {\"hour\": 19, \"minute\": 0},  \"dateValidTo\": {\"day\": 1, \"month\": 6, \"year\": 2019},   \"timeValidTo\": {\"hour\": 19, \"minute\": 0},  \"description\": \"Férias\"  }]";
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
	
		
	
	private void initializeMissingFieldCodStore() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);		
		model = new StoreLDateModelInsert(incomingDataMissingFieldCodStore());
	}	
	
	
	
	private String incomingDataMissingFieldCodStore() {
		return "[  {  \"codOwner\": 8,  \"dateValidFrom\": {\"day\": 1, \"month\": 6, \"year\": 2018},   \"timeValidFrom\": {\"hour\": 19, \"minute\": 0},  \"dateValidTo\": {\"day\": 1, \"month\": 6, \"year\": 2019},   \"timeValidTo\": {\"hour\": 19, \"minute\": 0},  \"description\": \"Férias\"  }]";
	}
	
	
	
	@Test
	public void missingFieldDateValidFrom() {
		initializeMissingFieldDateValidFrom();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	private void initializeMissingFieldDateValidFrom() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);		
		model = new StoreLDateModelInsert(incomingDataMissingFieldDateValidFrom());
	}	
	
	
	
	private String incomingDataMissingFieldDateValidFrom() {
		return "[  {  \"codOwner\": 8,  \"codStore\": 15,  \"timeValidFrom\": {\"hour\": 19, \"minute\": 0},  \"dateValidTo\": {\"day\": 1, \"month\": 6, \"year\": 2019},   \"timeValidTo\": {\"hour\": 19, \"minute\": 0},  \"description\": \"Férias\"  }]";
	}
	
	
	
	@Test
	public void missingFieldDateValidTo() {
		initializeMissingFieldDateValidTo();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	private void initializeMissingFieldDateValidTo() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);		
		model = new StoreLDateModelInsert(incomingDataMissingFieldDateValidTo());
	}	
	
	
	
	private String incomingDataMissingFieldDateValidTo() {
		return "[  {  \"codOwner\": 8,  \"codStore\": 15,  \"dateValidFrom\": {\"day\": 1, \"month\": 6, \"year\": 2018},   \"timeValidFrom\": {\"hour\": 19, \"minute\": 0},  \"timeValidTo\": {\"hour\": 19, \"minute\": 0},  \"description\": \"Férias\"  }]";
	}
	
	
	
	@Test
	public void missingFieldTimeValidFrom() {
		initializeMissingFieldTimeValidFrom();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	private void initializeMissingFieldTimeValidFrom() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);		
		model = new StoreLDateModelInsert(incomingDataMissingFieldTimeValidFrom());
	}	
	
	
	
	private String incomingDataMissingFieldTimeValidFrom() {
		return "[  {  \"codOwner\": 8,  \"codStore\": 15,  \"dateValidFrom\": {\"day\": 1, \"month\": 6, \"year\": 2018},  \"dateValidTo\": {\"day\": 1, \"month\": 6, \"year\": 2019},   \"timeValidTo\": {\"hour\": 19, \"minute\": 0},  \"description\": \"Férias\"  }]";
	}
	
	
	
	@Test
	public void missingFieldTimeValidTo() {
		initializeMissingFieldTimeValidTo();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	private void initializeMissingFieldTimeValidTo() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);		
		model = new StoreLDateModelInsert(incomingDataMissingFieldTimeValidTo());
	}	
	
	
	
	private String incomingDataMissingFieldTimeValidTo() {
		return "[  {  \"codOwner\": 8,  \"codStore\": 15,  \"dateValidFrom\": {\"day\": 1, \"month\": 6, \"year\": 2018},   \"timeValidFrom\": {\"hour\": 19, \"minute\": 0},  \"dateValidTo\": {\"day\": 1, \"month\": 6, \"year\": 2019},   \"description\": \"Férias\"  }]";
	}
	
	
	
	@Test
	public void equalTimeRange() {
		initializeEqualTimeRange();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":7,\"selectMessage\":\"Invalid time range\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	private void initializeEqualTimeRange() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);		
		model = new StoreLDateModelInsert(incomingDataEqualTimeRange());
	}	
	
	
	
	private String incomingDataEqualTimeRange() {
		return "[  {  \"codOwner\": 8,  \"codStore\": 15,  \"dateValidFrom\": {\"day\": 1, \"month\": 6, \"year\": 2018},   \"timeValidFrom\": {\"hour\": 19, \"minute\": 0},  \"dateValidTo\": {\"day\": 1, \"month\": 6, \"year\": 2018},   \"timeValidTo\": {\"hour\": 19, \"minute\": 0},  \"description\": \"Férias\"  }]";
	}
	
	
	
	@Test
	public void invalidDateRange() {
		initializeInvalidDateRange();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":7,\"selectMessage\":\"Invalid time range\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	private void initializeInvalidDateRange() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);		
		model = new StoreLDateModelInsert(incomingDataInvalidDateRange());
	}	
	
	
	
	private String incomingDataInvalidDateRange() {
		return "[  {  \"codOwner\": 8,  \"codStore\": 15,  \"dateValidFrom\": {\"day\": 1, \"month\": 6, \"year\": 2018},   \"timeValidFrom\": {\"hour\": 19, \"minute\": 0},  \"dateValidTo\": {\"day\": 1, \"month\": 6, \"year\": 2017},   \"timeValidTo\": {\"hour\": 19, \"minute\": 0},  \"description\": \"Férias\"  }]";
	}
	
	
	
	@Test
	public void invalidTimeRange() {
		initializeInvalidTimeRange();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":7,\"selectMessage\":\"Invalid time range\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	private void initializeInvalidTimeRange() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);		
		model = new StoreLDateModelInsert(incomingDataInvalidTimeRange());
	}	
	
	
	
	private String incomingDataInvalidTimeRange() {
		return "[  {  \"codOwner\": 8,  \"codStore\": 15,  \"dateValidFrom\": {\"day\": 1, \"month\": 6, \"year\": 2018},   \"timeValidFrom\": {\"hour\": 19, \"minute\": 0},  \"dateValidTo\": {\"day\": 1, \"month\": 6, \"year\": 2018},   \"timeValidTo\": {\"hour\": 18, \"minute\": 0},  \"description\": \"Férias\"  }]";
	}
	
	
	
	@Test
	public void invalidConnection() {
		initializeinvalidConnection();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
		
		String responseBody = "{\"selectCode\":500,\"selectMessage\":\"Ops... something went wrong\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	private void initializeinvalidConnection() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidConn);
		model = new StoreLDateModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void invalidFieldOwner() {
		initializeInvalidFieldOwner();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1251,\"selectMessage\":\"Owner data not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	private void initializeInvalidFieldOwner() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidOwnerConn);
		model = new StoreLDateModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void invalidFieldStore() {
		initializeInvalidFieldStore();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1103,\"selectMessage\":\"Store's data not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	private void initializeInvalidFieldStore() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidStoreConn);
		model = new StoreLDateModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void nullArgument() {
		initializeNullArgument();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":400,\"selectMessage\":\"IllegalArgument: mandatory argument might be missing or invalid value was passed\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	private void initializeNullArgument() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new StoreLDateModelInsert(null);
	}
	
	
	
	@Test
	public void recordAlreadyExist() {
		initializeRecordAlreadyExist();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1114,\"selectMessage\":\"Store's leave date already exist on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	private void initializeRecordAlreadyExist() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(alreadyExistConn);
		model = new StoreLDateModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void insertSoftDeletedRecord() {
		initializeInsertSoftDeletedRecord();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"timeValidFrom\":{\"hour\":11,\"minute\":22},\"timeValidTo\":{\"hour\":11,\"minute\":22},\"codTimezone\":\" \",\"description\":\" \"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	private void initializeInsertSoftDeletedRecord() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertSoftDeletedConn);
		model = new StoreLDateModelInsert(incomingDataOrdinaryUsage());
	}
}
