package br.com.gda.business.employeeLeaveDate.model;

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
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.gda.business.employeeLeaveDate.info.EmpLDateInfo;
import br.com.gda.common.DbConnection;
import br.com.gda.model.Model;
import javax.ws.rs.core.Response;



@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class EmpLDateModelSelectTest {
	@Mock private Connection dontExistConn;
	@Mock private PreparedStatement dontExistStmt;
	@Mock private ResultSet dontExistRs;
	
	@Mock private Connection selectConn;
	@Mock private PreparedStatement selectStmt;
	@Mock private ResultSet selectRs;
	
	@Mock private Connection invalidConn;	
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioSelect();
		initializeScenarioDataAlreadyExist();
		initializeScenarioInvalidConnection();
	}
	
	
	
	private void initializeScenarioSelect() throws SQLException {
		selectConn = mock(Connection.class);
		selectStmt = mock(PreparedStatement.class);
		selectRs = mock(ResultSet.class);
		
		when(selectConn.prepareStatement(any(String.class))).thenReturn(selectStmt);
		when(selectStmt.executeUpdate()).thenReturn(1);
		
		when(selectStmt.executeQuery()).thenReturn(selectRs);
		when(selectRs.next()).thenReturn(true).thenReturn(true).thenReturn(false);	// Select
		when(selectRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(selectRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(selectRs.getString(any(String.class))).thenReturn(" ");
		when(selectRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioDataAlreadyExist() throws SQLException {
		dontExistConn = mock(Connection.class);
		dontExistStmt = mock(PreparedStatement.class);
		dontExistRs = mock(ResultSet.class);	
		
		when(dontExistConn.prepareStatement(any(String.class))).thenReturn(dontExistStmt);	
		
		when(dontExistStmt.executeUpdate()).thenReturn(1);
		doNothing().when(dontExistStmt).setString(anyInt(), anyString());
		doNothing().when(dontExistStmt).setLong(anyInt(), anyLong());
		doNothing().when(dontExistStmt).setTime(anyInt(), any(Time.class));
		
		when(dontExistStmt.executeQuery()).thenReturn(dontExistRs);				
		when(dontExistRs.next()).thenReturn(true).thenReturn(false);						// Select
		when(dontExistRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(dontExistRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(dontExistRs.getString(any(String.class))).thenReturn(" ");
		when(dontExistRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));	
	}
	
	
	
	private void initializeScenarioInvalidConnection() throws SQLException {
		invalidConn = mock(Connection.class);
		when(invalidConn.prepareStatement(anyString())).thenThrow(new SQLException());
	}
	
	
	
	@Test
	public void selectRecord() {
		initializeSelectRecord();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"timeValidFrom\":{\"hour\":11,\"minute\":22},\"timeValidTo\":{\"hour\":11,\"minute\":22},\"codTimezone\":\" \",\"description\":\" \",\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeSelectRecord() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectConn);
		
		EmpLDateInfo infoRecord = new EmpLDateInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		infoRecord.codEmployee = 1;
		infoRecord.dateValidFrom = LocalDate.now();
		infoRecord.dateValidTo = LocalDate.now();
		infoRecord.timeValidFrom = LocalTime.now();
		infoRecord.timeValidTo = LocalTime.now();
		
		model = new EmpLDateModelSelect(infoRecord);
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectConn);
		
		EmpLDateInfo infoRecord = new EmpLDateInfo();
		infoRecord.codStore = 1;
		infoRecord.codEmployee = 1;
		infoRecord.dateValidFrom = LocalDate.now();
		infoRecord.dateValidTo = LocalDate.now();
		infoRecord.timeValidFrom = LocalTime.now();
		infoRecord.timeValidTo = LocalTime.now();
		
		model = new EmpLDateModelSelect(infoRecord);
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectConn);
		
		EmpLDateInfo infoRecord = new EmpLDateInfo();
		infoRecord.codOwner = 1;
		infoRecord.codEmployee = 1;
		infoRecord.dateValidFrom = LocalDate.now();
		infoRecord.dateValidTo = LocalDate.now();
		infoRecord.timeValidFrom = LocalTime.now();
		infoRecord.timeValidTo = LocalTime.now();
		
		model = new EmpLDateModelSelect(infoRecord);
	}
	
	
	
	@Test
	public void missingFieldDateValidFrom() {
		initializeMissingFieldDateValidFrom();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"timeValidFrom\":{\"hour\":11,\"minute\":22},\"timeValidTo\":{\"hour\":11,\"minute\":22},\"codTimezone\":\" \",\"description\":\" \",\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));				
	}
	
		
	
	private void initializeMissingFieldDateValidFrom() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectConn);
		
		EmpLDateInfo infoRecord = new EmpLDateInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		infoRecord.codEmployee = 1;
		infoRecord.dateValidTo = LocalDate.now();
		infoRecord.timeValidFrom = LocalTime.now();
		infoRecord.timeValidTo = LocalTime.now();
		
		model = new EmpLDateModelSelect(infoRecord);
	}
	
	
	
	@Test
	public void missingFieldDateValidTo() {
		initializeMissingFieldDateValidTo();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"timeValidFrom\":{\"hour\":11,\"minute\":22},\"timeValidTo\":{\"hour\":11,\"minute\":22},\"codTimezone\":\" \",\"description\":\" \",\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));				
	}
	
		
	
	private void initializeMissingFieldDateValidTo() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectConn);
		
		EmpLDateInfo infoRecord = new EmpLDateInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		infoRecord.codEmployee = 1;
		infoRecord.dateValidFrom = LocalDate.now();
		infoRecord.timeValidFrom = LocalTime.now();
		infoRecord.timeValidTo = LocalTime.now();
		
		model = new EmpLDateModelSelect(infoRecord);
	}
	
	
	
	@Test
	public void missingFieldTimeValidFrom() {
		initializeMissingFieldTimeValidFrom();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"timeValidFrom\":{\"hour\":11,\"minute\":22},\"timeValidTo\":{\"hour\":11,\"minute\":22},\"codTimezone\":\" \",\"description\":\" \",\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));				
	}
	
		
	
	private void initializeMissingFieldTimeValidFrom() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectConn);
		
		EmpLDateInfo infoRecord = new EmpLDateInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		infoRecord.codEmployee = 1;
		infoRecord.dateValidFrom = LocalDate.now();
		infoRecord.dateValidTo = LocalDate.now();
		infoRecord.timeValidTo = LocalTime.now();
		
		model = new EmpLDateModelSelect(infoRecord);
	}
	
	
	
	@Test
	public void missingFieldTimeValidTo() {
		initializeMissingFieldTimeValidTo();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"timeValidFrom\":{\"hour\":11,\"minute\":22},\"timeValidTo\":{\"hour\":11,\"minute\":22},\"codTimezone\":\" \",\"description\":\" \",\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));				
	}
	
		
	
	private void initializeMissingFieldTimeValidTo() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectConn);
		
		EmpLDateInfo infoRecord = new EmpLDateInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		infoRecord.codEmployee = 1;
		infoRecord.dateValidFrom = LocalDate.now();
		infoRecord.timeValidFrom = LocalTime.now();
		infoRecord.timeValidTo = LocalTime.now();
		
		model = new EmpLDateModelSelect(infoRecord);
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
	
		
	
	private void initializeMissingFieldCodEmployee() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectConn);
		
		EmpLDateInfo infoRecord = new EmpLDateInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		infoRecord.dateValidFrom = LocalDate.now();
		infoRecord.dateValidTo = LocalDate.now();
		infoRecord.timeValidFrom = LocalTime.now();
		infoRecord.timeValidTo = LocalTime.now();
		
		model = new EmpLDateModelSelect(infoRecord);
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
		
		EmpLDateInfo infoRecord = new EmpLDateInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		infoRecord.codEmployee = 1;
		infoRecord.dateValidFrom = LocalDate.now();
		infoRecord.dateValidTo = LocalDate.now();
		infoRecord.timeValidFrom = LocalTime.now();
		infoRecord.timeValidTo = LocalTime.now();
		
		model = new EmpLDateModelSelect(infoRecord);
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectConn);
		model = new EmpLDateModelSelect(null);
	}
	
	
	
	@Test
	public void recordDontExist() {
		initializeRecordDontExist();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":400,\"selectMessage\":\"Data not found\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	private void initializeRecordDontExist() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(dontExistConn);
		
		EmpLDateInfo infoRecord = new EmpLDateInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		infoRecord.codEmployee = 1;
		infoRecord.dateValidFrom = LocalDate.now();
		infoRecord.dateValidTo = LocalDate.now();
		infoRecord.timeValidFrom = LocalTime.now();
		infoRecord.timeValidTo = LocalTime.now();
		
		model = new EmpLDateModelSelect(infoRecord);
	}
}
