package br.com.gda.business.material.model;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
import br.com.gda.business.material.model.MatModelSelect;
import br.com.gda.common.DbConnection;
import br.com.gda.model.Model;

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public final class MatModelSelectTest {
	@Mock private Connection invalidConn;
	@Mock private Connection notFoundConn;
	@Mock private Connection selectConn;
	@Mock private PreparedStatement invalidStmt;
	@Mock private PreparedStatement notFoundStmt;
	@Mock private PreparedStatement selectStmt;
	@Mock private ResultSet invalidRs;
	@Mock private ResultSet notFoundRs;
	@Mock private ResultSet selectRs;
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioSelect();
		initializeScenarioNotFound();
		initializeScenarioInvalidConnection();
	}
	
	
	
	private void initializeScenarioSelect() throws SQLException {
		selectConn = mock(Connection.class);
		selectStmt = mock(PreparedStatement.class);
		selectRs = mock(ResultSet.class);
		
		when(selectConn.prepareStatement(any(String.class))).thenReturn(selectStmt);
		when(selectStmt.executeUpdate()).thenReturn(1);
		
		when(selectStmt.executeQuery()).thenReturn(selectRs);
		
		when(selectRs.next()).thenReturn(true).thenReturn(true).thenReturn(false);		
		when(selectRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(selectRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(selectRs.getString(any(String.class))).thenReturn(" ");
		when(selectRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioNotFound() throws SQLException {
		notFoundConn = mock(Connection.class);
		notFoundStmt = mock(PreparedStatement.class);
		notFoundRs = mock(ResultSet.class);
		
		when(notFoundConn.prepareStatement(any(String.class))).thenReturn(notFoundStmt);
		when(notFoundStmt.executeUpdate()).thenReturn(1);
		
		when(notFoundStmt.executeQuery()).thenReturn(notFoundRs);
		
		when(notFoundRs.next()).thenReturn(true).thenReturn(false);		
		when(notFoundRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(notFoundRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(notFoundRs.getString(any(String.class))).thenReturn(" ");
		when(notFoundRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
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
	public void keyFields() {
		initializeKeyFields();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codMat\":1,\"txtMat\":\" \",\"description\":\" \",\"codType\":1,\"txtType\":\" \",\"codCategory\":1,\"txtCategory\":\" \",\"price\":0.0,\"priceUnit\":1,\"codCurr\":\" \",\"txtCurr\":\" \",\"codUnit\":\" \",\"txtUnit\":\" \",\"codGroup\":1,\"txtGroup\":\" \",\"codBusiness\":1,\"txtBusiness\":\" \",\"codLanguage\":\" \",\"isLocked\":false}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeKeyFields() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectConn);
		
		MatInfo recordInfo = new MatInfo();
		recordInfo.codOwner = 1;
		recordInfo.codMat = 1;
		
		model = new MatModelSelect(recordInfo);
	}
	
	
	
	@Test
	public void recordNotFound() {
		initializeRecordNotFound();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":10,\"selectMessage\":\"Data not found\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeRecordNotFound() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(notFoundConn);
		
		MatInfo recordInfo = new MatInfo();
		recordInfo.codOwner = 1;
		recordInfo.codMat = 1;
		
		model = new MatModelSelect(recordInfo);
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectConn);
		
		MatInfo recordInfo = new MatInfo();
		recordInfo.codMat = 1;
		
		model = new MatModelSelect(recordInfo);
	}
	
	
	
	@Test
	public void missingFieldCodLanguage() {
		initializeMissingFieldCodLanguage();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMissingFieldCodLanguage() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectConn);
		
		MatInfo recordInfo = new MatInfo();
		recordInfo.codOwner = 1;
		recordInfo.codLanguage = null;
		
		model = new MatModelSelect(recordInfo);
	}
	
	
	
	@Test
	public void missingFieldCodMat() {
		initializeMissingFieldCodMat();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codMat\":1,\"txtMat\":\" \",\"description\":\" \",\"codType\":1,\"txtType\":\" \",\"codCategory\":1,\"txtCategory\":\" \",\"price\":0.0,\"priceUnit\":1,\"codCurr\":\" \",\"txtCurr\":\" \",\"codUnit\":\" \",\"txtUnit\":\" \",\"codGroup\":1,\"txtGroup\":\" \",\"codBusiness\":1,\"txtBusiness\":\" \",\"codLanguage\":\" \",\"isLocked\":false}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMissingFieldCodMat() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectConn);
		
		MatInfo recordInfo = new MatInfo();
		recordInfo.codOwner = 1;
		
		model = new MatModelSelect(recordInfo);
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
		model = new MatModelSelect(null);
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
		
		MatInfo recordInfo = new MatInfo();
		recordInfo.codOwner = 1;
		recordInfo.codMat = 1;
		
		model = new MatModelSelect(recordInfo);
	}
}
