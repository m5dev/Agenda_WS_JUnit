package br.com.gda.business.employee.model;

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

import br.com.gda.business.employee.info.EmpWTimeInfo;
import br.com.gda.business.employee.model.EmpWtimeModelSelect;
import br.com.gda.common.DbConnection;

import javax.ws.rs.core.Response;


@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class EmpWtimeModelSelectTest {
	@Mock private Connection selectConn;
	@Mock private Connection empNotFoundConn;
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement selectStmt;
	@Mock private PreparedStatement empNotFoundStmt;
	@Mock private PreparedStatement invalidStmt;
	@Mock private ResultSet selectRs;
	@Mock private ResultSet empNotFoundRs;
	
	private EmpWtimeModelSelect model;
	private EmpWTimeInfo infoRecord;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioSelect();
		initializeScenarioEmpNotFound();
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
		when(selectRs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(selectRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(selectRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(selectRs.getString(any(String.class))).thenReturn(" ");
		when(selectRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
	}
	
	
	
	private void initializeScenarioEmpNotFound() throws SQLException {
		empNotFoundStmt = mock(PreparedStatement.class);						
		empNotFoundRs = mock(ResultSet.class);		
		empNotFoundConn = mock(Connection.class);
		
		when(empNotFoundConn.prepareStatement(any(String.class))).thenReturn(empNotFoundStmt);	
		
		when(empNotFoundStmt.executeQuery()).thenReturn(empNotFoundRs);		
		when(empNotFoundRs.next()).thenReturn(false);
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
	public void selectWithFullKey() {
		initializeSelectWithFullKey();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"weekday\":1,\"beginTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"endTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0}}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
	
	
	
	protected void initializeSelectWithFullKey() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectConn);
		
		infoRecord = new EmpWTimeInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		infoRecord.codEmployee = 1;
		infoRecord.weekday = 1;
		
		model = new EmpWtimeModelSelect(infoRecord);
	}
	
	
	
	@Test
	public void selectWithPartialKey() {
		initializeSelectWithPartialKey();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"weekday\":1,\"beginTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"endTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0}}]}";
		assertTrue(response.getEntity().equals(responseBody));
		
		}
	
	
	
	protected void initializeSelectWithPartialKey() {
		initializeSelectWithFullKey();
		infoRecord.weekday = -1;
	}
	
	
	
	@Test
	public void empNotFound() {
		initializeEmpNotFound();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":400,\"selectMessage\":\"Data not found\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));
		
		}
	
	
	
	protected void initializeEmpNotFound() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(empNotFoundConn);
		
		infoRecord = new EmpWTimeInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		infoRecord.codEmployee = 1;
		infoRecord.weekday = 1;
		
		model = new EmpWtimeModelSelect(infoRecord);
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
		initializeSelectWithFullKey();
		infoRecord.codEmployee = -1;
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
		initializeSelectWithFullKey();
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
		initializeSelectWithFullKey();
		infoRecord.codOwner = -1;
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
		
		infoRecord = new EmpWTimeInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		infoRecord.codEmployee = 1;
		infoRecord.weekday = 1;
		
		model = new EmpWtimeModelSelect(infoRecord);
	}
}
