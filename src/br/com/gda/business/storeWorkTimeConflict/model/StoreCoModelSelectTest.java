package br.com.gda.business.storeWorkTimeConflict.model;

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
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.gda.business.storeWorkTimeConflict.info.StoreCoInfo;
import br.com.gda.common.DbConnection;
import br.com.gda.model.Model;

import javax.ws.rs.core.Response;


@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class StoreCoModelSelectTest {
	@Mock private Connection selectConn;
	@Mock private PreparedStatement selectStmt;
	@Mock private ResultSet selectRs;
	
	@Mock private Connection notFoundConn;
	@Mock private PreparedStatement notFoundtmt;
	@Mock private ResultSet notFoundRs;
	
	@Mock private Connection swtNotFoundConn;
	@Mock private PreparedStatement swtNotFoundtmt;
	@Mock private ResultSet swtNotFoundRs;
	
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;
	
	private Model model;
	private StoreCoInfo infoRecord;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioSelect();
		initializeScenarioRecordNotFound();
		initializeScenarioSwtNotFound();
		initializeScenarioInvalidConnection();
	}
	
	
	
	private void initializeScenarioSelect() throws SQLException {
		selectStmt = mock(PreparedStatement.class);
		selectRs = mock(ResultSet.class);
		selectConn = mock(Connection.class);
		
		when(selectConn.prepareStatement(any(String.class))).thenReturn(selectStmt);
		doNothing().when(selectStmt).setString(anyInt(), anyString());
		doNothing().when(selectStmt).setLong(anyInt(), anyLong());
		doNothing().when(selectStmt).setTime(anyInt(), any(Time.class));
		
		when(selectStmt.executeQuery()).thenReturn(selectRs);
		when(selectRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)			//Check SWT
		                     .thenReturn(true).thenReturn(true).thenReturn(false)			//Select SWT
		                     .thenReturn(true).thenReturn(true).thenReturn(false);			//Select Conflict
		when(selectRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(selectRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(selectRs.getString(any(String.class))).thenReturn(" ");
		when(selectRs.getTime(any(String.class))).thenReturn(Time.valueOf("09:00:00"))		//Check SWT
		                                         .thenReturn(Time.valueOf("18:00:00"))  	//Check SWT
												 .thenReturn(Time.valueOf("09:00:00"))		//Select SWT
										         .thenReturn(Time.valueOf("18:00:00")); 	//Select SWT
	}
	
	
	
	private void initializeScenarioRecordNotFound() throws SQLException {
		notFoundtmt = mock(PreparedStatement.class);						
		notFoundRs = mock(ResultSet.class);		
		notFoundConn = mock(Connection.class);
		
		when(notFoundConn.prepareStatement(any(String.class))).thenReturn(notFoundtmt);	
		
		when(notFoundtmt.executeQuery()).thenReturn(notFoundRs);		
		when(notFoundRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)			//Check SWT
						       .thenReturn(true).thenReturn(true).thenReturn(false)			//Select SWT
			                   .thenReturn(true).thenReturn(true).thenReturn(false);		//Select Conflict
		
		when(notFoundRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(notFoundRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(notFoundRs.getString(any(String.class))).thenReturn(" ");
		
		when(notFoundRs.getTime(any(String.class))).thenReturn(Time.valueOf("09:00:00"))	//Check SWT
											       .thenReturn(Time.valueOf("18:00:00"))  	//Check SWT
											       .thenReturn(Time.valueOf("09:00:00"))	//Select SWT
											       .thenReturn(Time.valueOf("18:00:00")); 	//Select SWT		
	}
	
	
	
	private void initializeScenarioSwtNotFound() throws SQLException {
		swtNotFoundtmt = mock(PreparedStatement.class);						
		swtNotFoundRs = mock(ResultSet.class);		
		swtNotFoundConn = mock(Connection.class);
		
		when(swtNotFoundConn.prepareStatement(any(String.class))).thenReturn(swtNotFoundtmt);	
		
		when(swtNotFoundtmt.executeQuery()).thenReturn(swtNotFoundRs);		
		when(swtNotFoundRs.next()).thenReturn(false);			//Check SWT
						          
		when(swtNotFoundRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(swtNotFoundRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(swtNotFoundRs.getString(any(String.class))).thenReturn(" ");
		
		when(swtNotFoundRs.getTime(any(String.class))).thenReturn(Time.valueOf("09:00:00"))		//Check SWT
											       	  .thenReturn(Time.valueOf("18:00:00"))  	//Check SWT
											       	  .thenReturn(Time.valueOf("09:00:00"))		//Select SWT
											       	  .thenReturn(Time.valueOf("18:00:00")); 	//Select SWT		
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
	public void conflictFound() {
		initializeConflictFound();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"codWeekday\":1,\"txtWeekday\":\" \",\"beginTime\":{\"hour\":18,\"minute\":0},\"endTime\":{\"hour\":18,\"minute\":0},\"codTimezone\":\" \",\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
	
	
	
	protected void initializeConflictFound() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectConn);
		
		infoRecord = new StoreCoInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		infoRecord.codEmployee = 1;
		infoRecord.codWeekday = 1;
		infoRecord.beginTime = LocalTime.of(10, 00);
		infoRecord.endTime = LocalTime.of(17, 0);		
		
		model = new StoreCoModelSelect(infoRecord);
	}
	
	
	
	@Test
	public void conflictNotFound() {
		initializeConflictNotFound();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":10,\"selectMessage\":\"Data not found\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
		}
	
	
	
	protected void initializeConflictNotFound() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(notFoundConn);
		
		infoRecord = new StoreCoInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		infoRecord.codEmployee = 1;
		infoRecord.codWeekday = 1;
		infoRecord.beginTime = LocalTime.of(9, 00);
		infoRecord.endTime = LocalTime.of(18, 0);		
		
		model = new StoreCoModelSelect(infoRecord);
	}
	
	
	
	@Test
	public void storeWorkTimeNotFound() {
		initializeSwtNotFound();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1110,\"selectMessage\":\"Store's working time not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
		}
	
	
	
	protected void initializeSwtNotFound() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(swtNotFoundConn);
		
		infoRecord = new StoreCoInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		infoRecord.codEmployee = 1;
		infoRecord.codWeekday = 1;
		infoRecord.beginTime = LocalTime.of(9, 00);
		infoRecord.endTime = LocalTime.of(18, 0);		
		
		model = new StoreCoModelSelect(infoRecord);
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
		
		infoRecord = new StoreCoInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		infoRecord.codEmployee = 1;
		infoRecord.codWeekday = 1;
		infoRecord.beginTime = LocalTime.of(9, 00);
		infoRecord.endTime = LocalTime.of(18, 0);		
		
		model = new StoreCoModelSelect(infoRecord);
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
	
	
	
	protected void initializeNullArgument() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectConn);
		model = new StoreCoModelSelect(null);
	}
	
	
	
	@Test
	public void missingFieldCodWeekday() {
		initializeMissingFieldCodWeekday();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
		}
	
	
	
	protected void initializeMissingFieldCodWeekday() {
		initializeConflictFound();
		infoRecord.codWeekday = -1;
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
		initializeConflictFound();
		infoRecord.codStore = -1;
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
		initializeConflictFound();
		infoRecord.codOwner = -1;
	}
	
	
	
	@Test
	public void missingFieldBeginTime() {
		initializeMissingFieldBeginTime();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));
	}
	
	
	
	
	protected void initializeMissingFieldBeginTime() {
		initializeConflictFound();
		infoRecord.beginTime = null;
	}
	
	
	
	
	@Test
	public void missingFieldEndTime() {
		initializeMissingFieldEndTime();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));
	}
	
	
	
	
	protected void initializeMissingFieldEndTime() {
		initializeConflictFound();
		infoRecord.endTime = null;
	}
}
