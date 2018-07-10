package br.com.gda.business.materialEmployee.model;

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

import br.com.gda.business.materialEmployee.info.MatEmpInfo;
import br.com.gda.common.DbConnection;
import br.com.gda.model.Model;

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public final class MatEmpModelSelectTest {
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;
	@Mock private ResultSet invalidRs;
	
	@Mock private Connection notFoundConn;
	@Mock private PreparedStatement notFoundStmt;
	@Mock private ResultSet notFoundRs;
	
	@Mock private Connection empNotFoundConn;
	@Mock private PreparedStatement empNotFoundStmt;
	@Mock private ResultSet empNotFoundRs;
	
	@Mock private Connection matNotFoundConn;
	@Mock private PreparedStatement matNotFoundStmt;
	@Mock private ResultSet matNotFoundRs;
	
	@Mock private Connection selectConn;
	@Mock private PreparedStatement selectStmt;
	@Mock private ResultSet selectRs;
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioSelect();
		initializeScenarioNotFound();
		initializeScenarioEmpNotFound();
		initializeScenarioMatNotFound();
		initializeScenarioInvalidConnection();
	}
	
	
	
	private void initializeScenarioSelect() throws SQLException {
		selectConn = mock(Connection.class);
		selectStmt = mock(PreparedStatement.class);
		selectRs = mock(ResultSet.class);
		
		when(selectConn.prepareStatement(any(String.class))).thenReturn(selectStmt);
		when(selectStmt.executeUpdate()).thenReturn(1);
		
		when(selectStmt.executeQuery()).thenReturn(selectRs);
		
		when(selectRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		//Select MapEmp
		                     .thenReturn(true).thenReturn(true).thenReturn(false)		//Select Map
		                     .thenReturn(true).thenReturn(true).thenReturn(false);		//Select Emp
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
	
	
	
	private void initializeScenarioEmpNotFound() throws SQLException {
		empNotFoundConn = mock(Connection.class);
		empNotFoundStmt = mock(PreparedStatement.class);
		empNotFoundRs = mock(ResultSet.class);
		
		when(empNotFoundConn.prepareStatement(any(String.class))).thenReturn(empNotFoundStmt);
		when(empNotFoundStmt.executeUpdate()).thenReturn(1);
		
		when(empNotFoundStmt.executeQuery()).thenReturn(empNotFoundRs);
		
		when(empNotFoundRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		//Select MapEmp
		                     	  .thenReturn(true).thenReturn(true).thenReturn(false)		//Select Map
		                     	  .thenReturn(true).thenReturn(false);						//Select Emp
		when(empNotFoundRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(empNotFoundRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(empNotFoundRs.getString(any(String.class))).thenReturn(" ");
		when(empNotFoundRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));	
	}
	
	
	
	private void initializeScenarioMatNotFound() throws SQLException {
		matNotFoundConn = mock(Connection.class);
		matNotFoundStmt = mock(PreparedStatement.class);
		matNotFoundRs = mock(ResultSet.class);
		
		when(matNotFoundConn.prepareStatement(any(String.class))).thenReturn(matNotFoundStmt);
		when(matNotFoundStmt.executeUpdate()).thenReturn(1);
		
		when(matNotFoundStmt.executeQuery()).thenReturn(matNotFoundRs);
		
		when(matNotFoundRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		//Select MapEmp
		                     	  .thenReturn(true).thenReturn(false);						//Select Map
		when(matNotFoundRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(matNotFoundRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(matNotFoundRs.getString(any(String.class))).thenReturn(" ");
		when(matNotFoundRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
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
	public void allFields() {
		initializeAllFields();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"nameEmployee\":\" \",\"codMat\":1,\"txtMat\":\" \",\"codType\":1,\"txtType\":\" \",\"codCategory\":1,\"txtCategory\":\" \",\"priceUnit\":1,\"codUnit\":\" \",\"txtUnit\":\" \",\"codLanguage\":\" \"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeAllFields() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectConn);	
		
		MatEmpInfo recordInfo = new MatEmpInfo();
		recordInfo.codOwner = 1;
		recordInfo.codEmployee = 1;
		recordInfo.codStore = 1;
		recordInfo.codMat = 1;
		
		model = new MatEmpModelSelect(recordInfo);
	}
	
	
	
	@Test
	public void recordNotFound() {
		initializeRecordNotFound();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
		//TODO: reflexo de um Bug. Deverá ser impactado ao corrigir 
		String responseBody = "{\"selectCode\":500,\"selectMessage\":\"Ops... something went wrong\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeRecordNotFound() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(notFoundConn);
		
		MatEmpInfo recordInfo = new MatEmpInfo();
		recordInfo.codOwner = 1;
		recordInfo.codEmployee = 1;
		recordInfo.codStore = 1;
		recordInfo.codMat = 1;
		
		model = new MatEmpModelSelect(recordInfo);
	}
	
	
	
	@Test
	public void empNotFound() {
		initializeEmpNotFound();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		String responseBody = "{\"selectCode\":10,\"selectMessage\":\"Data not found\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeEmpNotFound() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(empNotFoundConn);
		
		MatEmpInfo recordInfo = new MatEmpInfo();
		recordInfo.codOwner = 1;
		recordInfo.codEmployee = 1;
		recordInfo.codStore = 1;
		recordInfo.codMat = 1;
		
		model = new MatEmpModelSelect(recordInfo);
	}
	
	
	
	@Test
	public void matNotFound() {
		initializeMatNotFound();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		String responseBody = "{\"selectCode\":10,\"selectMessage\":\"Data not found\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMatNotFound() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(matNotFoundConn);
		
		MatEmpInfo recordInfo = new MatEmpInfo();
		recordInfo.codOwner = 1;
		recordInfo.codEmployee = 1;
		recordInfo.codStore = 1;
		recordInfo.codMat = 1;
		
		model = new MatEmpModelSelect(recordInfo);
	}
	
	
	
	@Test
	public void missingFieldCodMat() {
		initializeMissingFieldCodMat();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"nameEmployee\":\" \",\"codMat\":1,\"txtMat\":\" \",\"codType\":1,\"txtType\":\" \",\"codCategory\":1,\"txtCategory\":\" \",\"priceUnit\":1,\"codUnit\":\" \",\"txtUnit\":\" \",\"codLanguage\":\" \"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMissingFieldCodMat() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectConn);
		
		MatEmpInfo recordInfo = new MatEmpInfo();
		recordInfo.codOwner = 1;
		recordInfo.codEmployee = 1;
		recordInfo.codStore = 1;
		
		model = new MatEmpModelSelect(recordInfo);
	}
	
	
	
	@Test
	public void missingFieldCodEmloyee() {
		initializeMissingFieldCodEmloyee();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"nameEmployee\":\" \",\"codMat\":1,\"txtMat\":\" \",\"codType\":1,\"txtType\":\" \",\"codCategory\":1,\"txtCategory\":\" \",\"priceUnit\":1,\"codUnit\":\" \",\"txtUnit\":\" \",\"codLanguage\":\" \"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMissingFieldCodEmloyee() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectConn);
		
		MatEmpInfo recordInfo = new MatEmpInfo();
		recordInfo.codOwner = 1;
		recordInfo.codStore = 1;
		recordInfo.codMat = 1;
		
		model = new MatEmpModelSelect(recordInfo);
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
		
		MatEmpInfo recordInfo = new MatEmpInfo();
		recordInfo.codOwner = 1;
		recordInfo.codEmployee = 1;
		recordInfo.codMat = 1;
		
		model = new MatEmpModelSelect(recordInfo);
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
		
		MatEmpInfo recordInfo = new MatEmpInfo();
		recordInfo.codEmployee = 1;
		recordInfo.codStore = 1;
		recordInfo.codMat = 1;
		
		model = new MatEmpModelSelect(recordInfo);
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
		
		MatEmpInfo recordInfo = new MatEmpInfo();
		recordInfo.codOwner = 1;
		recordInfo.codEmployee = 1;
		recordInfo.codStore = 1;
		recordInfo.codMat = 1;
		recordInfo.codLanguage = null;
		
		model = new MatEmpModelSelect(recordInfo);
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
		model = new MatEmpModelSelect(null);
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
		
		MatEmpInfo recordInfo = new MatEmpInfo();
		recordInfo.codOwner = 1;
		recordInfo.codEmployee = 1;
		recordInfo.codStore = 1;
		recordInfo.codMat = 1;
		
		model = new MatEmpModelSelect(recordInfo);
	}
}
