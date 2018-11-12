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
public class CusModelSelectTest {
	@Mock private Connection selectConn;
	@Mock private PreparedStatement selectStmt;
	@Mock private ResultSet selectRs;
	
	@Mock private Connection notFoundConn;
	@Mock private PreparedStatement notFoundStmt;
	@Mock private ResultSet notFoundRs;
	
	@Mock private Connection genderNotFoundConn;
	@Mock private PreparedStatement genderNotFoundStmt;
	@Mock private ResultSet genderNotFoundRs;
	
	@Mock private Connection addressNotFoundConn;
	@Mock private PreparedStatement addressNotFoundStmt;
	@Mock private ResultSet addressNotFoundRs;
	
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioSelect();
		initializeScenarioNotFound();
		initializeScenarioGenderNotFound();
		initializeScenarioAddressNotFound();
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
		when(selectRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Customer
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// Gender
							 .thenReturn(true).thenReturn(true).thenReturn(false)	// Address - Select
							 .thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Check Country
							 .thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Check Exist
							 .thenReturn(true).thenReturn(true).thenReturn(false);	// Address Form - Select
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
	
	
	
	private void initializeScenarioGenderNotFound() throws SQLException {
		genderNotFoundStmt = mock(PreparedStatement.class);
		genderNotFoundRs = mock(ResultSet.class);
		genderNotFoundConn = mock(Connection.class);
		
		when(genderNotFoundConn.prepareStatement(any(String.class))).thenReturn(genderNotFoundStmt);
		doNothing().when(genderNotFoundStmt).setString(anyInt(), anyString());
		doNothing().when(genderNotFoundStmt).setLong(anyInt(), anyLong());
		doNothing().when(genderNotFoundStmt).setTime(anyInt(), any(Time.class));
		
		when(genderNotFoundStmt.executeQuery()).thenReturn(genderNotFoundRs);
		when(genderNotFoundRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		//Customer
				                     .thenReturn(true).thenReturn(false)						//Gender
				                     .thenReturn(true).thenReturn(true).thenReturn(false)		//Address
				                     .thenReturn(true).thenReturn(true).thenReturn(false);		//Address - Form
		when(genderNotFoundRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(genderNotFoundRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(genderNotFoundRs.getString(any(String.class))).thenReturn(" ");
		when(genderNotFoundRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
	}
	
	
	
	private void initializeScenarioAddressNotFound() throws SQLException {
		addressNotFoundStmt = mock(PreparedStatement.class);
		addressNotFoundRs = mock(ResultSet.class);
		addressNotFoundConn = mock(Connection.class);
		
		when(addressNotFoundConn.prepareStatement(any(String.class))).thenReturn(addressNotFoundStmt);
		doNothing().when(addressNotFoundStmt).setString(anyInt(), anyString());
		doNothing().when(addressNotFoundStmt).setLong(anyInt(), anyLong());
		doNothing().when(addressNotFoundStmt).setTime(anyInt(), any(Time.class));
		
		when(addressNotFoundStmt.executeQuery()).thenReturn(addressNotFoundRs);
		when(addressNotFoundRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		//Customer
				                      .thenReturn(true).thenReturn(true).thenReturn(false)		//Gender
				                      .thenReturn(true).thenReturn(false);						//Address
		when(addressNotFoundRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(addressNotFoundRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(addressNotFoundRs.getString(any(String.class))).thenReturn(" ");
		when(addressNotFoundRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
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
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codCustomer\":1,\"cpf\":\" \",\"name\":\" \",\"codGender\":1,\"txtGender\":\" \",\"email\":\" \",\"codCountryPhone1\":1,\"phoneNumber1\":\" \",\"addresses\":[{\"codOwner\":1,\"codAddress\":1,\"codCustomer\":1,\"codStore\":1,\"codEmployee\":-1,\"codCountry\":\" \",\"codState\":\" \",\"city\":\" \",\"district\":\" \",\"street\":\" \",\"streetNumber\":\" \",\"complement\":\" \",\"postalCode\":\" \",\"longitude\":0.0,\"latitude\":0.0,\"line1\":\" \",\"line2\":\" \",\"line3\":\" \",\"line4\":\" \",\"line5\":\" \",\"line6\":\" \",\"line7\":\" \",\"codForm\":\" \",\"isDeleted\":false}],\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
	
	
	
	protected void initializeSelectRecord() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectConn);
		
		CusInfo infoRecord = new CusInfo();
		infoRecord.codOwner = 1;
		infoRecord.codCustomer = 1;
		
		model = new CusModelSelect(infoRecord);
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
		
		CusInfo infoRecord = new CusInfo();
		infoRecord.codOwner = 1;
		infoRecord.codCustomer = 1;
		
		model = new CusModelSelect(infoRecord);
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
		
		CusInfo infoRecord = new CusInfo();
		infoRecord.codOwner = -1;
		infoRecord.codCustomer = -1;
		
		model = new CusModelSelect(infoRecord);
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
		
		model = new CusModelSelect(infoRecord);
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
		model = new CusModelSelect(null);
	}
	
	
	
	@Test
	public void genderNotFound() {
		initializeGenderNotFound();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codCustomer\":1,\"cpf\":\" \",\"name\":\" \",\"codGender\":1,\"email\":\" \",\"codCountryPhone1\":1,\"phoneNumber1\":\" \",\"addresses\":[{\"codOwner\":1,\"codAddress\":1,\"codCustomer\":1,\"codStore\":1,\"codEmployee\":-1,\"codCountry\":\" \",\"codState\":\" \",\"city\":\" \",\"district\":\" \",\"street\":\" \",\"streetNumber\":\" \",\"complement\":\" \",\"postalCode\":\" \",\"longitude\":0.0,\"latitude\":0.0,\"line1\":\" \",\"line2\":\" \",\"line3\":\" \",\"line4\":\" \",\"line5\":\" \",\"line6\":\" \",\"line7\":\" \",\"codForm\":\"A00\",\"isDeleted\":false}],\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
	
	
	protected void initializeGenderNotFound() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(genderNotFoundConn);
		
		CusInfo infoRecord = new CusInfo();
		infoRecord.codOwner = 1;
		infoRecord.codCustomer = 1;
		
		model = new CusModelSelect(infoRecord);
	}
	
	
	
	@Test
	public void addressNotFound() {
		initializeAddressNotFound();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codCustomer\":1,\"cpf\":\" \",\"name\":\" \",\"codGender\":1,\"txtGender\":\" \",\"email\":\" \",\"codCountryPhone1\":1,\"phoneNumber1\":\" \",\"addresses\":[],\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));
		
		}
	
	
	
	protected void initializeAddressNotFound() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(addressNotFoundConn);
		
		CusInfo infoRecord = new CusInfo();
		infoRecord.codOwner = 1;
		infoRecord.codCustomer = 1;
		
		model = new CusModelSelect(infoRecord);
	}
}
