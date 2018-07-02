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
public class StoreModelSelectTest {
	@Mock private Connection selectConn;
	@Mock private PreparedStatement selectStmt;
	@Mock private ResultSet selectRs;
	
	@Mock private Connection notFoundConn;
	@Mock private PreparedStatement notFoundStmt;
	@Mock private ResultSet notFoundRs;
	
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioSelect();
		initializeScenarioNotFound();
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
	
	
	
	private void initializeScenarioNotFound() throws SQLException {
		notFoundStmt = mock(PreparedStatement.class);						
		notFoundRs = mock(ResultSet.class);		
		notFoundConn = mock(Connection.class);
		
		when(notFoundConn.prepareStatement(any(String.class))).thenReturn(notFoundStmt);	
		
		when(notFoundStmt.executeQuery()).thenReturn(notFoundRs);		
		when(notFoundRs.next()).thenReturn(false);
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
	public void selectRecord() {
		initializeSelectRecord();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"cnpj\":\" \",\"inscrMun\":\" \",\"inscrEst\":\" \",\"razaoSocial\":\" \",\"name\":\" \",\"address1\":\" \",\"address2\":\" \",\"postalCode\":1,\"city\":\" \",\"codCountry\":\" \",\"txtCountry\":\" \",\"stateProvince\":\" \",\"phone\":\" \",\"codCurr\":\" \",\"codPayment\":\" \",\"latitude\":0.0,\"longitude\":0.0,\"codTimezone\":\" \",\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
	
	
	
	protected void initializeSelectRecord() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectConn);
		
		StoreInfo infoRecord = new StoreInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		
		model = new StoreModelSelect(infoRecord);
	}
	
	
	
	@Test
	public void recordNotFound() {
		initializeRecordNotFound();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":400,\"selectMessage\":\"Data not found\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));
		
		}
	
	
	
	protected void initializeRecordNotFound() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(notFoundConn);
		
		StoreInfo infoRecord = new StoreInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		
		model = new StoreModelSelect(infoRecord);
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectConn);
		
		StoreInfo infoRecord = new StoreInfo();
		infoRecord.codOwner = -1;
		infoRecord.codStore = -1;
		
		model = new StoreModelSelect(infoRecord);
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
		
		StoreInfo infoRecord = new StoreInfo();
		infoRecord.codOwner = 1;
		infoRecord.codStore = 1;
		
		model = new StoreModelSelect(infoRecord);
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
		model = new StoreModelSelect(null);
	}
}
