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
import br.com.gda.business.form.formAddress.dao.FormAddressDbTableColumn;
import br.com.gda.business.form.formPhone.dao.FormPhoneDbTableColumn;
import br.com.gda.business.phone.dao.PhoneDbTableColumn;
import br.com.gda.common.DbConnection;
import br.com.gda.model.Model;

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class CusModelSelectTest {
	@Mock private Connection selectPhoneT00Conn;
	@Mock private PreparedStatement selectPhoneT00Stmt;
	@Mock private ResultSet selectPhoneT00Rs;
	
	@Mock private Connection selectPhoneT01Conn;
	@Mock private PreparedStatement selectPhoneT01Stmt;
	@Mock private ResultSet selectPhoneT01Rs;
	
	@Mock private Connection selectAddressA00Conn;
	@Mock private PreparedStatement selectAddressA00Stmt;
	@Mock private ResultSet selectAddressA00Rs;
	
	@Mock private Connection selectAddressA01Conn;
	@Mock private PreparedStatement selectAddressA01Stmt;
	@Mock private ResultSet selectAddressA01Rs;
	
	@Mock private Connection notFoundConn;
	@Mock private PreparedStatement notFoundStmt;
	@Mock private ResultSet notFoundRs;
	
	@Mock private Connection addressNotFoundConn;
	@Mock private PreparedStatement addressNotFoundStmt;
	@Mock private ResultSet addressNotFoundRs;
	
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioSelectPhoneT00();
		initializeScenarioSelectPhoneT01();
		initializeScenarioSelectAddressA00();
		initializeScenarioSelectAddressA01();
		initializeScenarioNotFound();
		initializeScenarioAddressNotFound();
		initializeScenarioInvalidConnection();
	}
	
	
	
	private void initializeScenarioSelectPhoneT00() throws SQLException {
		selectPhoneT00Stmt = mock(PreparedStatement.class);
		selectPhoneT00Rs = mock(ResultSet.class);
		selectPhoneT00Conn = mock(Connection.class);
		
		when(selectPhoneT00Conn.prepareStatement(any(String.class))).thenReturn(selectPhoneT00Stmt);
		doNothing().when(selectPhoneT00Stmt).setString(anyInt(), anyString());
		doNothing().when(selectPhoneT00Stmt).setLong(anyInt(), anyLong());
		doNothing().when(selectPhoneT00Stmt).setTime(anyInt(), any(Time.class));
		
		when(selectPhoneT00Stmt.executeQuery()).thenReturn(selectPhoneT00Rs);
		when(selectPhoneT00Rs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Select
				                     .thenReturn(true).thenReturn(true).thenReturn(false)	// Person - Select
				                     .thenReturn(true).thenReturn(true).thenReturn(false)	// Person - Select Gender
				                     .thenReturn(true).thenReturn(true).thenReturn(false)	// Address - Select
								  	 .thenReturn(true).thenReturn(true).thenReturn(false)	// Address - Address Form - Check Country
								  	 .thenReturn(true).thenReturn(true).thenReturn(false)	// Address - Address Form - Check Exist
								  	 .thenReturn(true).thenReturn(true).thenReturn(false)	// Address - Address Form - Select									  	 
								  	 .thenReturn(true).thenReturn(true).thenReturn(false)	// Select Phone
									 .thenReturn(true).thenReturn(true).thenReturn(false)	// Select CountryPhone
									 .thenReturn(true).thenReturn(true).thenReturn(false)	// Form - Check Country
									 .thenReturn(false);
		when(selectPhoneT00Rs.getLong(any(String.class))).thenReturn(new Long(1));
		when(selectPhoneT00Rs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(selectPhoneT00Rs.getString(any(String.class))).thenReturn(" ");
		when(selectPhoneT00Rs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
		when(selectPhoneT00Rs.getString(FormAddressDbTableColumn.COL_COD_FORM)).thenReturn("A01");
		when(selectPhoneT00Rs.getString(PhoneDbTableColumn.COL_FULL_NUMBER)).thenReturn("21925922592");
	}
	
	
	
	private void initializeScenarioSelectPhoneT01() throws SQLException {
		selectPhoneT01Stmt = mock(PreparedStatement.class);
		selectPhoneT01Rs = mock(ResultSet.class);
		selectPhoneT01Conn = mock(Connection.class);
		
		when(selectPhoneT01Conn.prepareStatement(any(String.class))).thenReturn(selectPhoneT01Stmt);
		doNothing().when(selectPhoneT01Stmt).setString(anyInt(), anyString());
		doNothing().when(selectPhoneT01Stmt).setLong(anyInt(), anyLong());
		doNothing().when(selectPhoneT01Stmt).setTime(anyInt(), any(Time.class));
		
		when(selectPhoneT01Stmt.executeQuery()).thenReturn(selectPhoneT01Rs);
		when(selectPhoneT01Rs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Select
				                    .thenReturn(true).thenReturn(true).thenReturn(false)	// Person - Select
				                    .thenReturn(true).thenReturn(true).thenReturn(false)	// Person - Select Gender
				                    .thenReturn(true).thenReturn(true).thenReturn(false)	// Address - Select
								  	.thenReturn(true).thenReturn(true).thenReturn(false)	// Address - Address Form - Check Country
								  	.thenReturn(true).thenReturn(true).thenReturn(false)	// Address - Address Form - Check Exist
								  	.thenReturn(true).thenReturn(true).thenReturn(false)	// Address - Address Form - Select						  	 
								  	.thenReturn(true).thenReturn(true).thenReturn(false)	// Phone - Select
									.thenReturn(true).thenReturn(true).thenReturn(false)	// Phone - Select CountryPhone
									.thenReturn(true).thenReturn(true).thenReturn(false)	// Phone - Form - Check Country
									.thenReturn(true).thenReturn(true).thenReturn(false)	// Phone - Form - Check Exist
									.thenReturn(true).thenReturn(true).thenReturn(false);	// Phone - Form - Select
		when(selectPhoneT01Rs.getLong(any(String.class))).thenReturn(new Long(1));
		when(selectPhoneT01Rs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(selectPhoneT01Rs.getString(any(String.class))).thenReturn(" ");
		when(selectPhoneT01Rs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
		when(selectPhoneT01Rs.getString(FormAddressDbTableColumn.COL_COD_FORM)).thenReturn("A01");
		when(selectPhoneT01Rs.getString(FormPhoneDbTableColumn.COL_COD_FORM)).thenReturn("T01");
		when(selectPhoneT01Rs.getString(PhoneDbTableColumn.COL_FULL_NUMBER)).thenReturn("21925922592");
	}
	
	
	
	private void initializeScenarioSelectAddressA00() throws SQLException {
		selectAddressA00Stmt = mock(PreparedStatement.class);
		selectAddressA00Rs = mock(ResultSet.class);
		selectAddressA00Conn = mock(Connection.class);
		
		when(selectAddressA00Conn.prepareStatement(any(String.class))).thenReturn(selectAddressA00Stmt);
		doNothing().when(selectAddressA00Stmt).setString(anyInt(), anyString());
		doNothing().when(selectAddressA00Stmt).setLong(anyInt(), anyLong());
		doNothing().when(selectAddressA00Stmt).setTime(anyInt(), any(Time.class));
		
		when(selectAddressA00Stmt.executeQuery()).thenReturn(selectAddressA00Rs);
		when(selectAddressA00Rs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		// Customer - Select
				                       .thenReturn(true).thenReturn(true).thenReturn(false)		// Person - Select
				                       .thenReturn(true).thenReturn(true).thenReturn(false)		// Person - Select Gender
				                       .thenReturn(true).thenReturn(true).thenReturn(false)		// Address - Select
				                       .thenReturn(true).thenReturn(true).thenReturn(false)		// Address - Address Form - Check Country
				                       .thenReturn(false);										// Address - Address Form - Check Exist
		when(selectAddressA00Rs.getLong(any(String.class))).thenReturn(new Long(1));
		when(selectAddressA00Rs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(selectAddressA00Rs.getString(any(String.class))).thenReturn(" ");
		when(selectAddressA00Rs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
	}
	
	
	
	private void initializeScenarioSelectAddressA01() throws SQLException {
		selectAddressA01Stmt = mock(PreparedStatement.class);
		selectAddressA01Rs = mock(ResultSet.class);
		selectAddressA01Conn = mock(Connection.class);
		
		when(selectAddressA01Conn.prepareStatement(any(String.class))).thenReturn(selectAddressA01Stmt);
		doNothing().when(selectAddressA01Stmt).setString(anyInt(), anyString());
		doNothing().when(selectAddressA01Stmt).setLong(anyInt(), anyLong());
		doNothing().when(selectAddressA01Stmt).setTime(anyInt(), any(Time.class));
		
		when(selectAddressA01Stmt.executeQuery()).thenReturn(selectAddressA01Rs);
		when(selectAddressA01Rs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		// Customer - Select
				                       .thenReturn(true).thenReturn(true).thenReturn(false)		// Person - Select
				                       .thenReturn(true).thenReturn(true).thenReturn(false)		// Person - Select Gender
				                       .thenReturn(true).thenReturn(true).thenReturn(false)		// Address - Select
				                       .thenReturn(true).thenReturn(true).thenReturn(false)		// Address - Address Form - Check Country
				                       .thenReturn(true).thenReturn(true).thenReturn(false)		// Address - Address Form - Check Exist
				                       .thenReturn(true).thenReturn(true).thenReturn(false)		// Address - Address Form - Select						  	 
				                       .thenReturn(false);										// Phone - Select
		when(selectAddressA01Rs.getLong(any(String.class))).thenReturn(new Long(1));
		when(selectAddressA01Rs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(selectAddressA01Rs.getString(any(String.class))).thenReturn(" ");
		when(selectAddressA01Rs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
		when(selectAddressA01Rs.getString(FormAddressDbTableColumn.COL_COD_FORM)).thenReturn("A01");
	}
	
	
	
	private void initializeScenarioNotFound() throws SQLException {
		notFoundStmt = mock(PreparedStatement.class);						
		notFoundRs = mock(ResultSet.class);		
		notFoundConn = mock(Connection.class);
		
		when(notFoundConn.prepareStatement(any(String.class))).thenReturn(notFoundStmt);	
		
		when(notFoundStmt.executeQuery()).thenReturn(notFoundRs);		
		when(notFoundRs.next()).thenReturn(false);
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
		when(addressNotFoundRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		// Customer - Select
				                      .thenReturn(true).thenReturn(true).thenReturn(false)		// Person - Select
				                      .thenReturn(true).thenReturn(true).thenReturn(false)		// Person - Select Gender
				                      .thenReturn(false);										// Address - Select
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
	public void selectPhoneT00() {
		initializeSelectPhoneT00();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codCustomer\":1,\"codPerson\":1,\"cpf\":\" \",\"name\":\" \",\"codEntityCateg\":\" \",\"codGender\":1,\"txtGender\":\" \",\"email\":\" \",\"addresses\":[{\"codOwner\":1,\"codAddress\":1,\"codCustomer\":1,\"codStore\":1,\"codEmployee\":-1,\"codUser\":1,\"codCountry\":\" \",\"codState\":\" \",\"city\":\" \",\"district\":\" \",\"street\":\" \",\"streetNumber\":\" \",\"complement\":\" \",\"postalCode\":\" \",\"longitude\":0.0,\"latitude\":0.0,\"line1\":\" \",\"line2\":\" \",\"line3\":\" \",\"line4\":\" \",\"line5\":\" \",\"line6\":\" \",\"line7\":\" \",\"codForm\":\"A01\",\"isDeleted\":false}],\"phones\":[{\"codOwner\":1,\"codPhone\":1,\"codCustomer\":1,\"codStore\":1,\"codEmployee\":1,\"codUser\":1,\"codCountry\":\" \",\"fullNumber\":\"21925922592\",\"codCountryPhone\":1,\"complement\":\" \",\"codForm\":\"T00\",\"isDeleted\":false}],\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));	
		assertTrue(response.getEntity().toString().indexOf("T00") > -1);
	}
	
	
	
	protected void initializeSelectPhoneT00() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectPhoneT00Conn);
		
		CusInfo infoRecord = new CusInfo();
		infoRecord.codOwner = 1;
		infoRecord.codCustomer = 1;
		
		model = new CusModelSelect(infoRecord);
	}
		


	@Test
	public void selectPhoneT01() {
		initializeSelectPhoneT01();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codCustomer\":1,\"codPerson\":1,\"cpf\":\" \",\"name\":\" \",\"codEntityCateg\":\" \",\"codGender\":1,\"txtGender\":\" \",\"email\":\" \",\"addresses\":[{\"codOwner\":1,\"codAddress\":1,\"codCustomer\":1,\"codStore\":1,\"codEmployee\":-1,\"codUser\":1,\"codCountry\":\" \",\"codState\":\" \",\"city\":\" \",\"district\":\" \",\"street\":\" \",\"streetNumber\":\" \",\"complement\":\" \",\"postalCode\":\" \",\"longitude\":0.0,\"latitude\":0.0,\"line1\":\" \",\"line2\":\" \",\"line3\":\" \",\"line4\":\" \",\"line5\":\" \",\"line6\":\" \",\"line7\":\" \",\"codForm\":\"T01\",\"isDeleted\":false}],\"phones\":[{\"codOwner\":1,\"codPhone\":1,\"codCustomer\":1,\"codStore\":1,\"codEmployee\":1,\"codUser\":1,\"codCountry\":\" \",\"fullNumber\":\"21925922592\",\"codCountryPhone\":1,\"complement\":\" \",\"codForm\":\"T01\",\"isDeleted\":false}],\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
		assertTrue(response.getEntity().toString().indexOf("T01") > -1);
	}
	
	
	
	
	protected void initializeSelectPhoneT01() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectPhoneT01Conn);
		
		CusInfo infoRecord = new CusInfo();
		infoRecord.codOwner = 1;
		infoRecord.codCustomer = 1;
		
		model = new CusModelSelect(infoRecord);
	}
	
	
	
	@Test
	public void selectAddressA00() {
		initializeSelectAddressA00();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codCustomer\":1,\"codPerson\":1,\"cpf\":\" \",\"name\":\" \",\"codEntityCateg\":\" \",\"codGender\":1,\"txtGender\":\" \",\"email\":\" \",\"addresses\":[{\"codOwner\":1,\"codAddress\":1,\"codCustomer\":1,\"codStore\":1,\"codEmployee\":-1,\"codUser\":1,\"codCountry\":\" \",\"codState\":\" \",\"city\":\" \",\"district\":\" \",\"street\":\" \",\"streetNumber\":\" \",\"complement\":\" \",\"postalCode\":\" \",\"longitude\":0.0,\"latitude\":0.0,\"line1\":\" \",\"line2\":\" \",\"line3\":\" \",\"line4\":\" \",\"line5\":\" \",\"line6\":\" \",\"line7\":\" \",\"codForm\":\"A00\",\"isDeleted\":false}],\"phones\":[],\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
		assertTrue(response.getEntity().toString().indexOf("A00") > -1);
	}
	
	
	
	
	protected void initializeSelectAddressA00() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectAddressA00Conn);
		
		CusInfo infoRecord = new CusInfo();
		infoRecord.codOwner = 1;
		infoRecord.codCustomer = 1;
		
		model = new CusModelSelect(infoRecord);
	}	
	
	
	
	@Test
	public void selectAddressA01() {
		initializeSelectAddressA01();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codCustomer\":1,\"codPerson\":1,\"cpf\":\" \",\"name\":\" \",\"codEntityCateg\":\" \",\"codGender\":1,\"txtGender\":\" \",\"email\":\" \",\"addresses\":[{\"codOwner\":1,\"codAddress\":1,\"codCustomer\":1,\"codStore\":1,\"codEmployee\":-1,\"codUser\":1,\"codCountry\":\" \",\"codState\":\" \",\"city\":\" \",\"district\":\" \",\"street\":\" \",\"streetNumber\":\" \",\"complement\":\" \",\"postalCode\":\" \",\"longitude\":0.0,\"latitude\":0.0,\"line1\":\" \",\"line2\":\" \",\"line3\":\" \",\"line4\":\" \",\"line5\":\" \",\"line6\":\" \",\"line7\":\" \",\"codForm\":\"A01\",\"isDeleted\":false}],\"phones\":[],\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
		assertTrue(response.getEntity().toString().indexOf("A01") > -1);
	}
	
	
	
	
	protected void initializeSelectAddressA01() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectAddressA01Conn);
		
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
	public void missingFieldCodOwner() {
		initializeMissingFieldCodOwner();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));
	}
	
	
	
	
	protected void initializeMissingFieldCodOwner() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectPhoneT01Conn);
		
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(selectPhoneT01Conn);
		model = new CusModelSelect(null);
	}
	
	
	
	@Test
	public void addressNotFound() {
		initializeAddressNotFound();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codCustomer\":1,\"codPerson\":1,\"cpf\":\" \",\"name\":\" \",\"codEntityCateg\":\" \",\"codGender\":1,\"txtGender\":\" \",\"email\":\" \",\"addresses\":[],\"phones\":[],\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));
		
		}
	
	
	
	protected void initializeAddressNotFound() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(addressNotFoundConn);
		
		CusInfo infoRecord = new CusInfo();
		infoRecord.codOwner = 1;
		infoRecord.codCustomer = 1;
		
		model = new CusModelSelect(infoRecord);
	}
	
	//TODO: SELECT NO_ADDRESS AND NO_PHONE
}
