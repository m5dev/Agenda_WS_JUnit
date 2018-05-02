package br.com.gda.employee.model;

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

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.gda.common.DbConnection;
import br.com.gda.employee.info.EmpInfo;
import br.com.gda.employee.model.EmpModelDelete;
import br.com.gda.model.Model;

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class EmpModelDeleteTest {
	@Mock private Connection deleteConn;
	@Mock private Connection empNotFoundConn;
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement deleteStmt;
	@Mock private PreparedStatement empNotFoundStmt;
	@Mock private PreparedStatement invalidStmt;
	@Mock private ResultSet deleteRs;
	@Mock private ResultSet empNotFoundRs;
	
	private Model model;
	private EmpInfo infoRecord;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioDelete();
		initializeScenarioEmpNotFound();
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
		when(deleteRs.next()).thenReturn(true).thenReturn(false);
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
		when(invalidStmt.executeQuery()).thenThrow(new SQLException());
		
		doThrow(new SQLException()).when(invalidStmt).setString(anyInt(), anyString());
		doThrow(new SQLException()).when(invalidStmt).setLong(anyInt(), anyLong());
		doThrow(new SQLException()).when(invalidStmt).setTime(anyInt(), any(Time.class)); 
	}
	
	
	
	@Test
	public void deleteEmployee() {
		initializeDeleteEmployee();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":-1,\"codEmployee\":-1,\"stores\":[],\"codGender\":-1,\"postalCode\":-1,\"codPosition\":-1,\"codLanguage\":\"PT\",\"recordMode\":\" \"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeDeleteEmployee() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(deleteConn);
		
		infoRecord = new EmpInfo();
		infoRecord.codOwner = 1;
		infoRecord.codEmployee = 1;
		
		model = new EmpModelDelete(infoRecord);
	}
	
	
	
	@Test
	public void empNotFound() {
		initializeEmpNotFound();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1051,\"selectMessage\":\"Employee's data don't exist on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
	
	
	protected void initializeEmpNotFound() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(empNotFoundConn);
		
		infoRecord = new EmpInfo();
		infoRecord.codOwner = 1;
		infoRecord.codEmployee = 1;
		
		model = new EmpModelDelete(infoRecord);
	}
	
	
	
	@Test
	public void missingMandatoryField1() {
		initializeMissingMandatoryField1();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));
	}
	
	
	
	
	protected void initializeMissingMandatoryField1() {
		initializeMantadoryField();
		infoRecord.codEmployee = -1;
	}
	
	
	
	@Test
	public void missingMandatoryField2() {
		initializeMissingMandatoryField2();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));
	}
	
	
	
	
	protected void initializeMissingMandatoryField2() {
		initializeMantadoryField();
		infoRecord.codOwner = -1;
	}
	
	
	
	protected void initializeMantadoryField() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(deleteConn);
		
		infoRecord = new EmpInfo();
		infoRecord.codOwner = 1;
		infoRecord.codEmployee = 1;
		
		model = new EmpModelDelete(infoRecord);
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
		
		infoRecord = new EmpInfo();
		infoRecord.codOwner = 1;
		infoRecord.codEmployee = 1;
		
		model = new EmpModelDelete(infoRecord);
	}
}
