package br.com.gda.business.material.model;

import static org.junit.Assert.assertTrue;
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

import br.com.gda.business.material.info.MatInfo;
import br.com.gda.common.DbConnection;
import br.com.gda.model.Model;

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class MatModelDeleteTest {
	@Mock private Connection deleteConn;
	@Mock private Connection recordNotFoundConn;
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement deleteStmt;
	@Mock private PreparedStatement recordNotFoundStmt;
	@Mock private PreparedStatement invalidStmt;
	@Mock private ResultSet deleteRs;
	@Mock private ResultSet recordNotFoundRs;
	
	private Model model;
	private MatInfo infoRecord;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioDelete();
		initializeScenarioInvalidConnection();
		initializeScenarioRecordNotFound();		
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
		when(deleteRs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
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
	
	
	
	private void initializeScenarioRecordNotFound() throws SQLException {
		recordNotFoundStmt = mock(PreparedStatement.class);
		recordNotFoundRs = mock(ResultSet.class);		
		recordNotFoundConn = mock(Connection.class);
		
		when(recordNotFoundConn.prepareStatement(any(String.class))).thenReturn(recordNotFoundStmt);
		when(recordNotFoundStmt.executeQuery()).thenReturn(recordNotFoundRs);		
		when(recordNotFoundRs.next()).thenReturn(true).thenReturn(false);
	}
	
	
	
	@Test
	public void delete() {
		initializeDelete();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":-1,\"codMat\":-1,\"codType\":-1,\"codCategory\":-1,\"price\":-1.0,\"priceUnit\":1,\"codGroup\":-1,\"codBusiness\":-1,\"codLanguage\":\"PT\",\"isLocked\":false}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
	
	
	protected void initializeDelete() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(deleteConn);
		
		infoRecord = new MatInfo();
		infoRecord.codOwner = 1;
		infoRecord.codMat = 1;
		
		model = new MatModelDelete(infoRecord);
	}
	
	
	
	@Test
	public void recordNotFound() {
		initializeRecordNotFound();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1201,\"selectMessage\":\"Material data not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
	
	
	protected void initializeRecordNotFound() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(recordNotFoundConn);
		
		infoRecord = new MatInfo();
		infoRecord.codOwner = 1;
		infoRecord.codMat = 1;
		
		model = new MatModelDelete(infoRecord);
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(deleteConn);
		
		infoRecord = new MatInfo();
		infoRecord.codMat = 1;
		
		model = new MatModelDelete(infoRecord);
	}
	
	
	
	@Test
	public void missingFieldCodMat() {
		initializeMissingFieldCodMat();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
	
	
	protected void initializeMissingFieldCodMat() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(deleteConn);
		
		infoRecord = new MatInfo();
		infoRecord.codOwner = 1;
		
		model = new MatModelDelete(infoRecord);
	}
	
	
	
	@Test
	public void argumentNull() {
		initializeArgumentNull();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":400,\"selectMessage\":\"IllegalArgument: mandatory argument might be missing or invalid value was passed\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
	
	
	protected void initializeArgumentNull() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(deleteConn);
		
		infoRecord = null;		
		model = new MatModelDelete(infoRecord);
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
		
		infoRecord = new MatInfo();
		infoRecord.codOwner = 1;
		infoRecord.codMat = 1;
		
		model = new MatModelDelete(infoRecord);
	}
}
