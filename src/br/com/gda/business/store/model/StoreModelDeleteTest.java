package br.com.gda.business.store.model;

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

import br.com.gda.business.store.info.StoreInfo;
import br.com.gda.common.DbConnection;
import br.com.gda.model.Model;


@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class StoreModelDeleteTest {
	@Mock private Connection deleteConn;
	@Mock private Connection empNotFoundConn;
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement deleteStmt;
	@Mock private PreparedStatement empNotFoundStmt;
	@Mock private PreparedStatement invalidStmt;
	@Mock private ResultSet deleteRs;
	@Mock private ResultSet empNotFoundRs;
	
	private Model model;
	private StoreInfo infoRecord;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioDelete();
		initializeScenarioStoreNotFound();
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
	
	
	
	private void initializeScenarioStoreNotFound() throws SQLException {
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
	public void deleteStore() {
		initializeDeleteStore();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":-1,\"codStore\":-1,\"postalCode\":-1,\"latitude\":0.0,\"longitude\":0.0,\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
	
	
	protected void initializeDeleteStore() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(deleteConn);
		
		infoRecord = new StoreInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		
		model = new StoreModelDelete(infoRecord);
	}
	
	
	
	@Test
	public void storeNotFound() {
		initializeStoreNotFound();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1103,\"selectMessage\":\"Store's data don't exist on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
	
	
	protected void initializeStoreNotFound() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(empNotFoundConn);
		
		infoRecord = new StoreInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		
		model = new StoreModelDelete(infoRecord);
	}
	
	
	
	@Test
	public void missingFieldCodStore() {
		initializeMissingFieldCodStore();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":6,\"selectMessage\":\"Key field should not be null\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));
	}
	
	
	
	
	protected void initializeMissingFieldCodStore() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(deleteConn);
		
		infoRecord = new StoreInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = -1;
		
		model = new StoreModelDelete(infoRecord);
	}
	
	
	
	@Test
	public void missingFieldCodOwner() {
		initializeMissingFieldCodOwner();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":6,\"selectMessage\":\"Key field should not be null\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));
	}
	
	
	
	
	protected void initializeMissingFieldCodOwner() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(deleteConn);
		
		infoRecord = new StoreInfo();
		infoRecord.codOwner = -1;
		infoRecord.codStore = 1;
		
		model = new StoreModelDelete(infoRecord);		
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
		
		infoRecord = new StoreInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		
		model = new StoreModelDelete(infoRecord);
	}

}
