package br.com.gda.business.storeEmployee.model;

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

import br.com.gda.business.storeEmployee.model.StoreEmpModelUpdate;
import br.com.gda.common.DbConnection;
import br.com.gda.model.Model;

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class StoreEmpModelUpdateTest {
	@Mock private Connection updateConn;
	@Mock private Connection empDontExistConn;
	@Mock private Connection entryNotFoundConn;
	@Mock private Connection positionDontExistConn;
	@Mock private Connection storeDontExistConn;
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement updateStmt;
	@Mock private PreparedStatement empDontExistStmt;
	@Mock private PreparedStatement entryNotFoundStmt;
	@Mock private PreparedStatement positionDontExistStmt;
	@Mock private PreparedStatement storeDontExistStmt;
	@Mock private PreparedStatement invalidStmt;
	@Mock private ResultSet updateRs;
	@Mock private ResultSet empDontExistRs;
	@Mock private ResultSet entryNotFoundRs;
	@Mock private ResultSet positionDontExistRs;
	@Mock private ResultSet storeDontExistRs;
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioUpdate();
		initializeScenarioEntryNotFound();
		initializeScenarioEmployeeDontExist();
		initializeScenarioStoreDontExist();
		initializeScenarioPositionDontExist();
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
		when(updateRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)
		                     .thenReturn(true).thenReturn(true).thenReturn(false)
		                     .thenReturn(true).thenReturn(true).thenReturn(false)
		                     .thenReturn(true).thenReturn(true).thenReturn(false)
		                     .thenReturn(true).thenReturn(true).thenReturn(false);
		when(updateRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateRs.getString(any(String.class))).thenReturn(" ");
		when(updateRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
	}
	
	
	
	private void initializeScenarioEntryNotFound() throws SQLException {
		entryNotFoundStmt = mock(PreparedStatement.class);
		entryNotFoundRs = mock(ResultSet.class);
		entryNotFoundConn = mock(Connection.class);
		
		when(entryNotFoundConn.prepareStatement(any(String.class))).thenReturn(entryNotFoundStmt);	
		doNothing().when(entryNotFoundStmt).setString(anyInt(), anyString());
		doNothing().when(entryNotFoundStmt).setLong(anyInt(), anyLong());
		doNothing().when(entryNotFoundStmt).setTime(anyInt(), any(Time.class));		
		
		when(entryNotFoundStmt.executeUpdate()).thenReturn(1);		

		when(entryNotFoundStmt.executeQuery()).thenReturn(entryNotFoundRs);
		when(entryNotFoundRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)
		                            .thenReturn(true).thenReturn(true).thenReturn(false)
		                            .thenReturn(true).thenReturn(true).thenReturn(false)
		                            .thenReturn(true).thenReturn(false);
		when(entryNotFoundRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(entryNotFoundRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(entryNotFoundRs.getString(any(String.class))).thenReturn(" ");
		when(entryNotFoundRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
	}
	
	
	
	private void initializeScenarioEmployeeDontExist() throws SQLException {
		empDontExistStmt = mock(PreparedStatement.class);
		empDontExistRs = mock(ResultSet.class);
		empDontExistConn = mock(Connection.class);
		
		when(empDontExistConn.prepareStatement(any(String.class))).thenReturn(empDontExistStmt);	
		doNothing().when(empDontExistStmt).setString(anyInt(), anyString());
		doNothing().when(empDontExistStmt).setLong(anyInt(), anyLong());
		doNothing().when(empDontExistStmt).setTime(anyInt(), any(Time.class));		
		
		when(empDontExistStmt.executeUpdate()).thenReturn(1);		

		when(empDontExistStmt.executeQuery()).thenReturn(empDontExistRs);
		when(empDontExistRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)
								   .thenReturn(true).thenReturn(true).thenReturn(false)
								   .thenReturn(true).thenReturn(false);		
		when(empDontExistRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(empDontExistRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(empDontExistRs.getString(any(String.class))).thenReturn(" ");
		when(empDontExistRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
	}
	
	
	
	private void initializeScenarioStoreDontExist() throws SQLException {
		storeDontExistStmt = mock(PreparedStatement.class);
		storeDontExistRs = mock(ResultSet.class);		
		storeDontExistConn = mock(Connection.class);
		
		when(storeDontExistConn.prepareStatement(any(String.class))).thenReturn(storeDontExistStmt);

		when(storeDontExistStmt.executeQuery()).thenReturn(storeDontExistRs);
		when(storeDontExistRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)
								     .thenReturn(true).thenReturn(false);	
	}
	
	
	
	private void initializeScenarioPositionDontExist() throws SQLException {
		positionDontExistStmt = mock(PreparedStatement.class);
		positionDontExistRs = mock(ResultSet.class);		
		positionDontExistConn = mock(Connection.class);
		
		when(positionDontExistConn.prepareStatement(any(String.class))).thenReturn(positionDontExistStmt);

		when(positionDontExistStmt.executeQuery()).thenReturn(positionDontExistRs);
		when(positionDontExistRs.next()).thenReturn(true).thenReturn(false);	
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
	public void update() {
		initializeUpdate();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"nameStore\":\" \",\"codEmployee\":1,\"nameEmployee\":\" \",\"codPositionStore\":1,\"txtPositionStore\":\" \",\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeUpdate() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(updateConn);
		model = new StoreEmpModelUpdate(incomingDataUpdateStore());
	}
	
	
	
	protected String incomingDataUpdateStore() {
		return "[{\"codOwner\": 8,\"codStore\": 15,\"codEmployee\": 63,\"codPositionStore\": 3}]";
	}
	
	
	
	@Test
	public void fieldEmployeeDontExist() {
		initializeFieldEmployeeDontExist();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1051,\"selectMessage\":\"Employee's data not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeFieldEmployeeDontExist() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(empDontExistConn);
		model = new StoreEmpModelUpdate(incomingDataUpdateStore());
	}
	
	
	
	@Test
	public void fieldStoreDontExist() {
		initializeFieldStoreDontExist();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1103,\"selectMessage\":\"Store's data not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeFieldStoreDontExist() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(storeDontExistConn);
		model = new StoreEmpModelUpdate(incomingDataUpdateStore());
	}
	
	
	
	@Test
	public void fieldPositionDontExist() {
		initializeFieldPositionDontExist();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1151,\"selectMessage\":\"Position not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeFieldPositionDontExist() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(positionDontExistConn);
		model = new StoreEmpModelUpdate(incomingDataUpdateStore());
	}
	
	
	
	@Test
	public void entryNotFound() {
		initializeEntryNotFound();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1105,\"selectMessage\":\"Store-Employee's data not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeEntryNotFound() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(entryNotFoundConn);
		model = new StoreEmpModelUpdate(incomingDataUpdateStore());
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
		model = new StoreEmpModelUpdate(incomingDataUpdateStore());
	}
}
