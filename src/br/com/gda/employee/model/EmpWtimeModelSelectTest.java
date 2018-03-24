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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.gda.common.DbConnection;
import br.com.gda.employee.info.EmpWTimeInfo;

import javax.ws.rs.core.Response;


@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class EmpWtimeModelSelectTest {
	@Mock private Connection validConn;
	@Mock private Connection emptyConn;
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement validStmt;
	@Mock private PreparedStatement emptyStmt;
	@Mock private PreparedStatement invalidStmt;
	@Mock private ResultSet validRs;
	@Mock private ResultSet emptyRs;
	
	private EmpWtimeModelSelect model;
	private EmpWTimeInfo infoRecord;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		validStmt = mock(PreparedStatement.class);
		emptyStmt = mock(PreparedStatement.class);
		invalidStmt = mock(PreparedStatement.class);
		validRs = mock(ResultSet.class);		
		emptyRs = mock(ResultSet.class);
		validConn = mock(Connection.class);
		emptyConn = mock(Connection.class);
		invalidConn = mock(Connection.class);
		
		when(invalidConn.prepareStatement(anyString())).thenThrow(new SQLException());
		
		when(validConn.prepareStatement(any(String.class))).thenReturn(validStmt);	
		when(emptyConn.prepareStatement(any(String.class))).thenReturn(emptyStmt);
		when(validStmt.executeQuery()).thenReturn(validRs);
		when(emptyStmt.executeQuery()).thenReturn(emptyRs);
		doNothing().when(validStmt).setString(anyInt(), anyString());
		doNothing().when(validStmt).setLong(anyInt(), anyLong());
		doNothing().when(validStmt).setTime(anyInt(), any(Time.class));
		
		when(invalidStmt.executeQuery()).thenThrow(new SQLException());
		doThrow(new SQLException()).when(invalidStmt).setString(anyInt(), anyString());
		doThrow(new SQLException()).when(invalidStmt).setLong(anyInt(), anyLong());
		doThrow(new SQLException()).when(invalidStmt).setTime(anyInt(), any(Time.class)); 
		
		
		when(emptyRs.next()).thenReturn(true).thenReturn(false);
		when(validRs.next()).thenReturn(true).thenReturn(false);
		when(validRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(validRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(validRs.getString(any(String.class))).thenReturn(" ");
		when(validRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
	}
	
	
	
	
	@Test
	public void OrdinaryUsageFullKey() {
		initializeOrdinaryUsageFullKey();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"weekday\":1,\"beginTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"endTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"recordMode\":\" \"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
	
	
	
	protected void initializeOrdinaryUsageFullKey() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(validConn);
		
		infoRecord = new EmpWTimeInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		infoRecord.codEmployee = 1;
		infoRecord.weekday = 1;
		
		model = new EmpWtimeModelSelect(infoRecord);
	}
	
	
	
	@Test
	public void OrdinaryUsagePartialKey() {
		initializeOrdinaryUsagePartialKey();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"weekday\":1,\"beginTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"endTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"recordMode\":\" \"}]}";
		assertTrue(response.getEntity().equals(responseBody));
		
		}
	
	
	
	protected void initializeOrdinaryUsagePartialKey() {
		initializeOrdinaryUsageFullKey();
		infoRecord.weekday = -1;
	}
	
	
	
	@Test
	public void OrdinaryUsageEmpNotFound() {
		initializeOrdinaryUsageEmpNotFound();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":400,\"selectMessage\":\"Employee's data not found\",\"results\":{\"codOwner\":-1,\"codStore\":-1,\"codEmployee\":-1,\"weekday\":-1}}";
		assertTrue(response.getEntity().equals(responseBody));
		
		}
	
	
	
	protected void initializeOrdinaryUsageEmpNotFound() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(emptyConn);
		
		infoRecord = new EmpWTimeInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		infoRecord.codEmployee = 1;
		infoRecord.weekday = 1;
		
		model = new EmpWtimeModelSelect(infoRecord);
	}
	
	
	
	
	@Test
	public void MandatoryField1() {
		initializeMandatoryField1();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{\"codOwner\":-1,\"codStore\":-1,\"codEmployee\":-1,\"weekday\":-1}}";
		assertTrue(response.getEntity().equals(responseBody));
	}
	
	
	
	
	protected void initializeMandatoryField1() {
		initializeOrdinaryUsageFullKey();
		infoRecord.codEmployee = -1;
	}
	
	
	
	@Test
	public void MandatoryField2() {
		initializeMandatoryField2();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{\"codOwner\":-1,\"codStore\":-1,\"codEmployee\":-1,\"weekday\":-1}}";
		assertTrue(response.getEntity().equals(responseBody));
	}
	
	
	
	
	protected void initializeMandatoryField2() {
		initializeOrdinaryUsageFullKey();
		infoRecord.codStore = -1;
	}
	
	
	
	@Test
	public void MandatoryField3() {
		initializeMandatoryField2();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{\"codOwner\":-1,\"codStore\":-1,\"codEmployee\":-1,\"weekday\":-1}}";
		assertTrue(response.getEntity().equals(responseBody));
	}
	
	
	
	
	protected void initializeMandatoryField3() {
		initializeOrdinaryUsageFullKey();
		infoRecord.codOwner = -1;
	}
	
	
	
	@Test
	public void invalidConnection() {
		initializeInvalidConnection();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
		
		String responseBody = "{\"selectCode\":500,\"selectMessage\":\"Ops... something went wrong\",\"results\":{\"codOwner\":-1,\"codStore\":-1,\"codEmployee\":-1,\"weekday\":-1}}";
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
