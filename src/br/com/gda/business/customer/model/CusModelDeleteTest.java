package br.com.gda.business.customer.model;

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

import br.com.gda.business.customer.info.CusInfo;
import br.com.gda.common.DbConnection;
import br.com.gda.model.Model;


@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class CusModelDeleteTest {
	@Mock private Connection deleteConn;
	@Mock private PreparedStatement deleteStmt;
	@Mock private ResultSet deleteRs;
	
	@Mock private Connection deleteAddressConn;
	@Mock private PreparedStatement deleteAddressStmt;
	@Mock private ResultSet deleteAddressRs;
	
	@Mock private Connection notFoundConn;
	@Mock private PreparedStatement notFoundStmt;
	@Mock private ResultSet notFoundRs;
	
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;	
	
	private Model model;	
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioDelete();
		initializeScenarioDeleteAddress();
		initializeScenarioNotFound();
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
		when(deleteRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Check Exist
		                     .thenReturn(false);									// Address - Check Exist
	}
	
	
	
	private void initializeScenarioDeleteAddress() throws SQLException {
		deleteAddressConn = mock(Connection.class);
		deleteAddressStmt = mock(PreparedStatement.class);
		deleteAddressRs = mock(ResultSet.class);
		
		when(deleteAddressConn.prepareStatement(any(String.class))).thenReturn(deleteAddressStmt);			
		doNothing().when(deleteAddressStmt).setString(anyInt(), anyString());
		doNothing().when(deleteAddressStmt).setLong(anyInt(), anyLong());
		doNothing().when(deleteAddressStmt).setTime(anyInt(), any(Time.class));		
		
		when(deleteAddressStmt.executeUpdate()).thenReturn(1);
		
		when(deleteAddressStmt.executeQuery()).thenReturn(deleteAddressRs);		
		when(deleteAddressRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Check Exist 
									.thenReturn(true).thenReturn(true).thenReturn(false)	// Address - Check Exist
									.thenReturn(true).thenReturn(true).thenReturn(false)	// Address - Select
									.thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Check Country
									.thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Check Exist
									.thenReturn(true).thenReturn(true).thenReturn(false);	// Address Form - Select
		
		when(deleteAddressRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(deleteAddressRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(deleteAddressRs.getString(any(String.class))).thenReturn(" ");
		when(deleteAddressRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));	
	}
	
	
	
	private void initializeScenarioNotFound() throws SQLException {
		notFoundStmt = mock(PreparedStatement.class);
		notFoundRs = mock(ResultSet.class);		
		notFoundConn = mock(Connection.class);
		
		when(notFoundConn.prepareStatement(any(String.class))).thenReturn(notFoundStmt);
		when(notFoundStmt.executeQuery()).thenReturn(notFoundRs);		
		when(notFoundRs.next()).thenReturn(true).thenReturn(false);
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
	public void deleteRecord() {
		initializeDeleteRecord();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":-1,\"codCustomer\":-1,\"codGender\":1,\"codCountryPhone1\":-1,\"addresses\":[],\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
	
	
	protected void initializeDeleteRecord() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(deleteConn);
		
		CusInfo infoRecord = new CusInfo();
		infoRecord.codOwner = 1;
		infoRecord.codCustomer = 1;
		
		model = new CusModelDelete(infoRecord);
	}
	
	
	
	@Test
	public void deleteAddress() {
		initializeDeleteAddress();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":-1,\"codCustomer\":-1,\"codGender\":1,\"codCountryPhone1\":-1,\"addresses\":[],\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
	
	
	protected void initializeDeleteAddress() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(deleteAddressConn);
		
		CusInfo infoRecord = new CusInfo();
		infoRecord.codOwner = 1;
		infoRecord.codCustomer = 1;
		
		model = new CusModelDelete(infoRecord);
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
		
		CusInfo infoRecord = new CusInfo();
		infoRecord.codCustomer = 1;
		
		model = new CusModelDelete(infoRecord);
	}
	
	
	
	@Test
	public void missingFieldCodCustomer() {
		initializeMissingFieldCodCustomer();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":6,\"selectMessage\":\"Key field should not be null\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
	
	
	protected void initializeMissingFieldCodCustomer() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(deleteConn);
		
		CusInfo infoRecord = new CusInfo();
		infoRecord.codOwner = 1;
		
		model = new CusModelDelete(infoRecord);
	}
	
	
	
	@Test
	public void recordNoFound() {
		initializeRecordNoFound();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1355,\"selectMessage\":\"Customer's data not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
	
	
	protected void initializeRecordNoFound() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(notFoundConn);
		
		CusInfo infoRecord = new CusInfo();
		infoRecord.codOwner = 1;
		infoRecord.codCustomer = 1;
		
		model = new CusModelDelete(infoRecord);
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
		
		CusInfo infoRecord = new CusInfo();
		infoRecord.codOwner = 1;
		infoRecord.codCustomer = 1;
		
		model = new CusModelDelete(infoRecord);
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(deleteConn);
		model = new CusModelDelete(null);
	}
}
