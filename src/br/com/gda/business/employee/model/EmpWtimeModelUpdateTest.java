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

import br.com.gda.business.employee.model.EmpWtimeModelUpdate;
import br.com.gda.common.DbConnection;
import br.com.gda.model.Model;
import javax.ws.rs.core.Response;



@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class EmpWtimeModelUpdateTest {
	@Mock private Connection updateConn;
	@Mock private Connection empDontExistConn;
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement updateStmt;
	@Mock private PreparedStatement empDontExistStmt;
	@Mock private PreparedStatement invalidStmt;
	@Mock private ResultSet updateRs;
	@Mock private ResultSet empDontExistRs;
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioUpdate();
		initializeScenarioEmpDontExist();
		initializeScenarioInvalidConnection();
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
		when(updateRs.next()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
		when(updateRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateRs.getString(any(String.class))).thenReturn(" ");
		when(updateRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
	}
	
	
	
	private void initializeScenarioEmpDontExist() throws SQLException {
		empDontExistStmt = mock(PreparedStatement.class);
		empDontExistRs = mock(ResultSet.class);		
		empDontExistConn = mock(Connection.class);
		
		when(empDontExistConn.prepareStatement(any(String.class))).thenReturn(empDontExistStmt);

		when(empDontExistStmt.executeQuery()).thenReturn(empDontExistRs);
		when(empDontExistRs.next()).thenReturn(false);
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
	public void updateEmpWtime() {
		initializeUpdateEmpWtime();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"weekday\":1,\"beginTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"endTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"recordMode\":\" \"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeUpdateEmpWtime() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(updateConn);
		model = new EmpWtimeModelUpdate(incomingDataUpdateEmpWtimeUsage());
	}
	
	
	
	@Test
	public void empDontExist() {
		initializeEmpDontExist();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1001,\"selectMessage\":\"Employee's working time data don't exist on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeEmpDontExist() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(empDontExistConn);
		model = new EmpWtimeModelUpdate(incomingDataUpdateEmpWtimeUsage());
	}
	
	
	
	protected String incomingDataUpdateEmpWtimeUsage() {
		return "[	{	\"codOwner\": 8,	\"codStore\": 15,	\"codEmployee\": 54,	\"weekday\": 1, 	\"beginTime\": {\"hour\": 9, \"minute\": 0}, 	\"endTime\": {\"hour\": 18, \"minute\": 0}	}]";
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(empDontExistConn);		
		model = new EmpWtimeModelUpdate(incomingDataMandatoryField1());
	}	
	
	
	protected String incomingDataMandatoryField1() {
		return "[	{	\"codStore\": 15,	\"codEmployee\": 54,	\"weekday\": 1, 	\"beginTime\": {\"hour\": 9, \"minute\": 0}, 	\"endTime\": {\"hour\": 18, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15,	\"codEmployee\": 54,	\"weekday\": 2, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15,	\"codEmployee\": 54,	\"weekday\": 3, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	}]";
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(empDontExistConn);		
		model = new EmpWtimeModelUpdate(incomingDataMandatoryField2());
	}	
	
	
	
	protected String incomingDataMandatoryField2() {
		return "[	{	\"codOwner\": 8,	\"codEmployee\": 54,	\"weekday\": 1, 	\"beginTime\": {\"hour\": 9, \"minute\": 0}, 	\"endTime\": {\"hour\": 18, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15,	\"codEmployee\": 54,	\"weekday\": 2, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15,	\"codEmployee\": 54,	\"weekday\": 3, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	}]";
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(empDontExistConn);		
		model = new EmpWtimeModelUpdate(incomingDataMandatoryField3());
	}	
	
	
	
	protected String incomingDataMandatoryField3() {
		return "[	{	\"codOwner\": 8,	\"codStore\": 15,	\"weekday\": 1, 	\"beginTime\": {\"hour\": 9, \"minute\": 0}, 	\"endTime\": {\"hour\": 18, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15,	\"codEmployee\": 54,	\"weekday\": 2, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15,	\"codEmployee\": 54,	\"weekday\": 3, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	}]";
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(empDontExistConn);		
		model = new EmpWtimeModelUpdate(incomingDataMandatoryField4());
	}	
	
	
	
	protected String incomingDataMandatoryField4() {
		return "[	{	\"codOwner\": 8,	\"codStore\": 15,	\"codEmployee\": 54, 	\"beginTime\": {\"hour\": 9, \"minute\": 0}, 	\"endTime\": {\"hour\": 18, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15,	\"codEmployee\": 54,	\"weekday\": 2, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15,	\"codEmployee\": 54, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	}]";
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
		
	
	
	protected void initializeinvalidConnection() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidConn);
		model = new EmpWtimeModelUpdate(incomingDataUpdateEmpWtimeUsage());
	}
}
