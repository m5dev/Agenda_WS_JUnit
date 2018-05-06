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
import br.com.gda.business.employee.model.EmpWtimeModelDelete;
import br.com.gda.common.DbConnection;
import br.com.gda.model.Model;
import javax.ws.rs.core.Response;



@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class EmpWtimeModelDeleteTest {	
	@Mock private Connection deleteConn;
	@Mock private Connection empNotFoundConn;
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement deleteStmt;
	@Mock private PreparedStatement empNotFoundStmt;
	@Mock private PreparedStatement invalidStmt;
	@Mock private ResultSet deleteRs;
	@Mock private ResultSet empNotFoundRs;
	
	private Model model;
	private EmpWTimeInfo infoRecord;
	
	
	
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
	public void deleteEmpWtime() {
		initializeDeleteEmpWtime();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":-1,\"codStore\":-1,\"codEmployee\":-1,\"weekday\":-1,\"recordMode\":\" \"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeDeleteEmpWtime() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(deleteConn);
		
		infoRecord = new EmpWTimeInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		infoRecord.codEmployee = 1;
		infoRecord.weekday = 1;
		
		model = new EmpWtimeModelDelete(infoRecord);
	}
	
	
	
	@Test
	public void empNotFound() {
		initializeEmpNotFound();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1001,\"selectMessage\":\"Employee's working time data don't exist on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));
		
		}
	
	
	
	protected void initializeEmpNotFound() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(empNotFoundConn);
		
		infoRecord = new EmpWTimeInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		infoRecord.codEmployee = 1;
		infoRecord.weekday = 1;
		
		model = new EmpWtimeModelDelete(infoRecord);
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
		infoRecord.codStore = -1;
	}
	
	
	
	@Test
	public void missingMandatoryField3() {
		initializeMissingMandatoryField3();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));
	}
	
	
	
	
	protected void initializeMissingMandatoryField3() {
		initializeMantadoryField();
		infoRecord.codOwner = -1;
	}
	
	
	
	@Test
	public void missingMandatoryField4() {
		initializeMissingMandatoryField4();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));
		
		}
	
	
	
	protected void initializeMissingMandatoryField4() {
		initializeMantadoryField();
		infoRecord.weekday = -1;
	}
	
	
		
	protected void initializeMantadoryField() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(deleteConn);
		
		infoRecord = new EmpWTimeInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		infoRecord.codEmployee = 1;
		infoRecord.weekday = 1;
		
		model = new EmpWtimeModelDelete(infoRecord);
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
		
		model = new EmpWtimeModelDelete(infoRecord);
	}
}
