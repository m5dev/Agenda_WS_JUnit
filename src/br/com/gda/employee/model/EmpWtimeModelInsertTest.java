package br.com.gda.employee.model;

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
public class EmpWtimeModelInsertTest {
	@Mock private Connection alreadyExistConn;
	@Mock private Connection insertConn;
	@Mock private Connection updateConn;
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement alreadyExistStmt;
	@Mock private PreparedStatement insertStmt;
	@Mock private PreparedStatement updateStmt;
	@Mock private ResultSet alreadyExistRs;
	@Mock private ResultSet insertRs;
	@Mock private ResultSet updateRs;
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioInsert();
		initializeScenarioUpdate();
		initializeScenarioDataAlreadyExist();
		initializeScenarioInvalidConnection();
	}
	
	
	
	private void initializeScenarioInsert() throws SQLException {
		insertConn = mock(Connection.class);
		insertStmt = mock(PreparedStatement.class);
		insertRs = mock(ResultSet.class);
		
		when(insertConn.prepareStatement(any(String.class))).thenReturn(insertStmt);
		when(insertStmt.executeUpdate()).thenReturn(1);
		
		when(insertStmt.executeQuery()).thenReturn(insertRs);
		when(insertRs.next()).thenReturn(false).thenReturn(false).thenReturn(true).thenReturn(false)
		                     .thenReturn(false).thenReturn(false).thenReturn(true).thenReturn(false)
		                     .thenReturn(false).thenReturn(false).thenReturn(true).thenReturn(false)
		                     .thenReturn(false);
		when(insertRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(insertRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(insertRs.getString(any(String.class))).thenReturn(" ");
		when(insertRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioUpdate() throws SQLException {
		updateConn = mock(Connection.class);
		updateStmt = mock(PreparedStatement.class);
		updateRs = mock(ResultSet.class);		
		
		when(updateConn.prepareStatement(any(String.class))).thenReturn(updateStmt);
		when(updateStmt.executeUpdate()).thenReturn(1);
		
		
		when(updateStmt.executeQuery()).thenReturn(updateRs);
		when(updateRs.next()).thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false)
		                     .thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false)
		                     .thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false)
		                     .thenReturn(false);
		when(updateRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateRs.getString(any(String.class))).thenReturn(" ");
		when(updateRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));	
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
		when(alreadyExistRs.next()).thenReturn(true).thenReturn(false);
		when(alreadyExistRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(alreadyExistRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(alreadyExistRs.getString(any(String.class))).thenReturn(" ");
		when(alreadyExistRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));	
	}
	
	
	
	private void initializeScenarioInvalidConnection() throws SQLException {
		invalidConn = mock(Connection.class);
		when(invalidConn.prepareStatement(anyString())).thenThrow(new SQLException());
	}
	
	
	
	@Test
	public void insertNewEmpWtime() {
		initializeInsertNewEmpWtime();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"weekday\":1,\"beginTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"endTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"recordMode\":\" \"},{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"weekday\":1,\"beginTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"endTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"recordMode\":\" \"},{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"weekday\":1,\"beginTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"endTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"recordMode\":\" \"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInsertNewEmpWtime() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);
		model = new EmpWtimeModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void updateSoftDeletedEmpWtime() {
		initializeUpdateSoftDeleted();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"weekday\":1,\"beginTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"endTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"recordMode\":\" \"},{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"weekday\":1,\"beginTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"endTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"recordMode\":\" \"},{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"weekday\":1,\"beginTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"endTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"recordMode\":\" \"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeUpdateSoftDeleted() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(updateConn);
		model = new EmpWtimeModelInsert(incomingDataOrdinaryUsage());
	}
		
	
	
	@Test
	public void empWtimeAlreadyExist() {
		initializeEmpWtimeAlreadyExist();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1000,\"selectMessage\":\"Employee's working time data already exist on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	protected void initializeEmpWtimeAlreadyExist() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(alreadyExistConn);
		model = new EmpWtimeModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	protected String incomingDataOrdinaryUsage() {
		return "[	{	\"codOwner\": 8,	\"codStore\": 15,	\"codEmployee\": 54,	\"weekday\": 1, 	\"beginTime\": {\"hour\": 9, \"minute\": 0}, 	\"endTime\": {\"hour\": 18, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15,	\"codEmployee\": 54,	\"weekday\": 2, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15,	\"codEmployee\": 54,	\"weekday\": 3, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	}]";
	}
	
		
	
	@Test
	public void mandatoryField1() {
		initializeMandatoryField1();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	protected void initializeMandatoryField1() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);		
		model = new EmpWtimeModelInsert(incomingDataMandatoryField1());
	}	
	
	
	
	protected String incomingDataMandatoryField1() {
		return "[	{	\"codStore\": 15,	\"codEmployee\": 54,	\"weekday\": 1, 	\"beginTime\": {\"hour\": 9, \"minute\": 0}, 	\"endTime\": {\"hour\": 18, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15,	\"codEmployee\": 54,	\"weekday\": 2, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15,	\"codEmployee\": 54,	\"weekday\": 3, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	}]";
	}
	
	
	
	@Test
	public void mandatoryField2() {
		initializeMandatoryField2();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	protected void initializeMandatoryField2() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);		
		model = new EmpWtimeModelInsert(incomingDataMandatoryField2());
	}	
	
	
	
	protected String incomingDataMandatoryField2() {
		return "[	{	\"codOwner\": 8,	\"codEmployee\": 54,	\"weekday\": 1, 	\"beginTime\": {\"hour\": 9, \"minute\": 0}, 	\"endTime\": {\"hour\": 18, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15,	\"codEmployee\": 54,	\"weekday\": 2, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15,	\"codEmployee\": 54,	\"weekday\": 3, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	}]";
	}
	
	
	
	@Test
	public void mandatoryField3() {
		initializeMandatoryField3();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	protected void initializeMandatoryField3() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);		
		model = new EmpWtimeModelInsert(incomingDataMandatoryField3());
	}	
	
	
	
	protected String incomingDataMandatoryField3() {
		return "[	{	\"codOwner\": 8,	\"codStore\": 15,	\"weekday\": 1, 	\"beginTime\": {\"hour\": 9, \"minute\": 0}, 	\"endTime\": {\"hour\": 18, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15,	\"codEmployee\": 54,	\"weekday\": 2, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15,	\"codEmployee\": 54,	\"weekday\": 3, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	}]";
	}
	
	
	
	@Test
	public void mandatoryField4() {
		initializeMandatoryField4();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	protected void initializeMandatoryField4() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);		
		model = new EmpWtimeModelInsert(incomingDataMandatoryField4());
	}	
	
	
	
	protected String incomingDataMandatoryField4() {
		return "[	{	\"codOwner\": 8,	\"codStore\": 15,	\"codEmployee\": 54,	\"weekday\": 1, 	\"beginTime\": {\"hour\": 9, \"minute\": 0}, 	\"endTime\": {\"hour\": 18, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15,	\"codEmployee\": 54,	\"weekday\": 2, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	},	{	\"codOwner\": 8,	\"codStore\": 15,	\"codEmployee\": 54, 	\"beginTime\": {\"hour\": 8, \"minute\": 0}, 	\"endTime\": {\"hour\": 17, \"minute\": 0}	}]";
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
		model = new EmpWtimeModelInsert(incomingDataOrdinaryUsage());
	}
}
