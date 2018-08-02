package br.com.gda.business.storeWorkTime.model;

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
public class StoreWTimeModelUpdateTest {
	@Mock private Connection updateConn;
	@Mock private PreparedStatement updateStmt;
	@Mock private ResultSet updateRs;
	
	@Mock private Connection conflictConn;
	@Mock private PreparedStatement conflictStmt;
	@Mock private ResultSet conflictRs;
	
	@Mock private Connection recordDontExistConn;
	@Mock private PreparedStatement recordDontExistStmt;
	@Mock private ResultSet recordDontExistRs;
	
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;
	
	@Mock private Connection insertSoftDeletedConn;
	@Mock private PreparedStatement insertSoftDeletedStmt;
	@Mock private ResultSet insertSoftDeletedRs;
	
	@Mock private Connection invalidStoreConn;
	@Mock private PreparedStatement invalidStoreStmt;
	@Mock private ResultSet invalidStoreRs;
	
	@Mock private Connection invalidWeekdayConn;
	@Mock private PreparedStatement invalidWeekdayStmt;
	@Mock private ResultSet invalidWeekdayRs;
	
	@Mock private Connection invalidOwnerConn;
	@Mock private PreparedStatement invalidOwnerStmt;
	@Mock private ResultSet invalidOwnerRs;
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioUpdate();
		initializeScenarioConflict();
		initializeScenarioRecordDontExist();
		initializeScenarioInvalidConnection();
		initializeScenarioInvalidWeekday();
		initializeScenarioInvalidStore();
		initializeScenarioInvalidOwner();
	}
	
	
	
	private void initializeScenarioUpdate() throws SQLException {
		updateStmt = mock(PreparedStatement.class);
		updateRs = mock(ResultSet.class);
		updateConn = mock(Connection.class);
		
		when(updateConn.prepareStatement(any(String.class))).thenReturn(updateStmt);	
		doNothing().when(updateStmt).setString(anyInt(), anyString());
		doNothing().when(updateStmt).setLong(anyInt(), anyLong());
		doNothing().when(updateStmt).setTime(anyInt(), any(Time.class));		
		
		when(updateStmt.executeUpdate()).thenReturn(1);		

		when(updateStmt.executeQuery()).thenReturn(updateRs);
		when(updateRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
							 .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Store
							 .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Weekday
							 .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Exist
							 .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Conflict - Store WorkTime
							 .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Conflict - Time Range - Get SWT
							 .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Conflict - Time Range - Check SWT
							 .thenReturn(true).thenReturn(false)					// Check Conflict - Return time overlapping
							 														// Update
							 .thenReturn(true).thenReturn(true).thenReturn(false);	// Select
		when(updateRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateRs.getString(any(String.class))).thenReturn(" ");
		when(updateRs.getTime(any(String.class))).thenReturn(Time.valueOf("08:00:00")).thenReturn(Time.valueOf("18:00:00"))
										         .thenReturn(Time.valueOf("08:00:00")).thenReturn(Time.valueOf("18:00:00"))
										         .thenReturn(Time.valueOf("11:22:33"));
	}
	
	
	
	private void initializeScenarioConflict() throws SQLException {
		conflictStmt = mock(PreparedStatement.class);
		conflictRs = mock(ResultSet.class);
		conflictConn = mock(Connection.class);
		
		when(conflictConn.prepareStatement(any(String.class))).thenReturn(conflictStmt);	
		doNothing().when(conflictStmt).setString(anyInt(), anyString());
		doNothing().when(conflictStmt).setLong(anyInt(), anyLong());
		doNothing().when(conflictStmt).setTime(anyInt(), any(Time.class));		
		
		when(conflictStmt.executeUpdate()).thenReturn(1);		

		when(conflictStmt.executeQuery()).thenReturn(conflictRs);
		when(conflictRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		// Check Owner
							   .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Store
							   .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Weekday
							   .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Exist
							   .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Conflict - Store WorkTime
							   .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Conflict - Time Range - Get SWT
							   .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Conflict - Time Range - Check SWT
							   .thenReturn(true).thenReturn(true).thenReturn(false);					// Check Conflict - Return time overlapping
		when(conflictRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(conflictRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(conflictRs.getString(any(String.class))).thenReturn(" ");
		when(conflictRs.getTime(any(String.class))).thenReturn(Time.valueOf("08:00:00")).thenReturn(Time.valueOf("18:00:00"))
		                                           .thenReturn(Time.valueOf("08:00:00")).thenReturn(Time.valueOf("18:00:00"))
		                                           .thenReturn(Time.valueOf("08:00:00")).thenReturn(Time.valueOf("18:00:00"))
		                                           .thenReturn(Time.valueOf("08:00:00")).thenReturn(Time.valueOf("18:00:00"));
	}
	
	
	
	private void initializeScenarioRecordDontExist() throws SQLException {
		recordDontExistStmt = mock(PreparedStatement.class);
		recordDontExistRs = mock(ResultSet.class);		
		recordDontExistConn = mock(Connection.class);
		
		when(recordDontExistConn.prepareStatement(any(String.class))).thenReturn(recordDontExistStmt);

		when(recordDontExistStmt.executeQuery()).thenReturn(recordDontExistRs);
		when(recordDontExistRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
						 			  .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Store
									  .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Weekday
									  .thenReturn(true).thenReturn(false);					// Check Exist;
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
	
	
	
	private void initializeScenarioInvalidWeekday() throws SQLException {
		invalidWeekdayConn = mock(Connection.class);
		invalidWeekdayStmt = mock(PreparedStatement.class);
		invalidWeekdayRs = mock(ResultSet.class);
		
		when(invalidWeekdayConn.prepareStatement(any(String.class))).thenReturn(invalidWeekdayStmt);
		when(invalidWeekdayStmt.executeUpdate()).thenReturn(1);
		
		when(invalidWeekdayStmt.executeQuery()).thenReturn(invalidWeekdayRs);
		when(invalidWeekdayRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
							    .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Store
							    .thenReturn(true).thenReturn(false);						// Check Weekday

		when(invalidWeekdayRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidWeekdayRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidWeekdayRs.getString(any(String.class))).thenReturn(" ");
		when(invalidWeekdayRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
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
	public void updateRecord() {
		initializeUpdateRecord();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"codWeekday\":1,\"txtWeekday\":\" \",\"beginTime\":{\"hour\":11,\"minute\":22},\"endTime\":{\"hour\":11,\"minute\":22},\"codTimezone\":\" \",\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	private void initializeUpdateRecord() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(updateConn);
		model = new StoreWTimeModelUpdate(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void conflictEmpWorkTime() {
		initializeConflictEmpWorkTime();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":9,\"selectMessage\":\"Conflict detected\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	private void initializeConflictEmpWorkTime() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(conflictConn);
		model = new StoreWTimeModelUpdate(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void recordDontExist() {
		initializeRecordDontExist();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1110,\"selectMessage\":\"Store's working time not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	private void initializeRecordDontExist() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(recordDontExistConn);
		model = new StoreWTimeModelUpdate(incomingDataOrdinaryUsage());
	}
	
	
	
	private String incomingDataOrdinaryUsage() {
		return "[	{	\"codOwner\": 8,	\"codStore\": 15,	\"codWeekday\": 1, 	\"beginTime\": {\"hour\": 9, \"minute\": 0}, 	\"endTime\": {\"hour\": 18, \"minute\": 0}	}]";
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(recordDontExistConn);		
		model = new StoreWTimeModelUpdate(incomingDataMissingFieldCodOwner());
	}	
	
	
	private String incomingDataMissingFieldCodOwner() {
		return "[	{	\"codStore\": 15,	\"weekday\": 1, 	\"beginTime\": {\"hour\": 9, \"minute\": 0}, 	\"endTime\": {\"hour\": 18, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15,	\"weekday\": 2, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15,	\"weekday\": 3, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	}]";
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(recordDontExistConn);		
		model = new StoreWTimeModelUpdate(incomingDataMissingFieldCodStore());
	}	
	
	
	
	private String incomingDataMissingFieldCodStore() {
		return "[	{	\"codOwner\": 8,	\"weekday\": 1, 	\"beginTime\": {\"hour\": 9, \"minute\": 0}, 	\"endTime\": {\"hour\": 18, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15,	\"weekday\": 2, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15,	\"weekday\": 3, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	}]";
	}
	
	
	
	@Test
	public void missingFieldWeekday() {
		initializeMissingFieldWeekday();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	private void initializeMissingFieldWeekday() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(recordDontExistConn);		
		model = new StoreWTimeModelUpdate(incomingDataMissingFieldWeekday());
	}	
	
	
	
	private String incomingDataMissingFieldWeekday() {
		return "[	{	\"codOwner\": 8,	\"codStore\": 15, 	\"beginTime\": {\"hour\": 9, \"minute\": 0}, 	\"endTime\": {\"hour\": 18, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15,	\"weekday\": 2, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	}]";
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
		model = new StoreWTimeModelUpdate(incomingDataOrdinaryUsage());
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(updateConn);
		model = new StoreWTimeModelUpdate(null);
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
		model = new StoreWTimeModelUpdate(incomingDataOrdinaryUsage());
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
		model = new StoreWTimeModelUpdate(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void invalidFieldWeekday() {
		initializeInvalidFieldWeekday();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1165,\"selectMessage\":\"Weekday not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	private void initializeInvalidFieldWeekday() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidWeekdayConn);
		model = new StoreWTimeModelUpdate(incomingDataOrdinaryUsage());
	}
}
