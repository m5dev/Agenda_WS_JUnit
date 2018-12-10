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
import br.com.gda.common.DbConnection;
import br.com.gda.model.Model;


@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class CusModelDeleteTest {
	@Mock private Connection deleteConn;
	@Mock private PreparedStatement deleteStmt;
	@Mock private ResultSet deleteRs;
	
	@Mock private Connection deleteNoAddressConn;
	@Mock private PreparedStatement deleteNoAddressStmt;
	@Mock private ResultSet deleteNoAddressRs;
	
	@Mock private Connection deleteNoPhoneConn;
	@Mock private PreparedStatement deleteNoPhoneStmt;
	@Mock private ResultSet deleteNoPhoneRs;
	
	@Mock private Connection notFoundConn;
	@Mock private PreparedStatement notFoundStmt;
	@Mock private ResultSet notFoundRs;
	
	@Mock private Connection deleteNoAddressPhoneConn;
	@Mock private PreparedStatement deleteNoAddressPhoneStmt;
	@Mock private ResultSet deleteNoAddressPhoneRs;
	
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;	
	
	private Model model;	
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioDelete();
		initializeScenarioDeleteNoAddress();
		initializeScenarioDeleteNoPhone();
		initializeScenarioDeleteNoAddressPhone();
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
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Check Person has changed
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Check Address Exist
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Merge Address - Select
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Merge Address - Address Form - Check Country
		                     .thenReturn(false).thenReturn(false)					// Customer - Merge Address - Address Form - Check Exist
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// Address - Delete - Check Exist
		                     .thenReturn(false)										// Address - Delete - Delete Address	
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Check Phone Exist
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Merge Phone - Select Phone
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Merge Phone - Select CountryPhone
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Merge Phone - Form - Check Country
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Merge Phone - Form - Check Exist
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Merge Phone - Form - Select
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// Phone - Delete - Check Exist
		                     .thenReturn(false)										// Phone - Delete - Delete Phone		                     
		                     .thenReturn(false)										// Customer - Delete
		                     .thenReturn(true).thenReturn(true).thenReturn(false);	// Person - Check Exist
																					// Person - Delete
		
		when(deleteRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(deleteRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(deleteRs.getString(any(String.class))).thenReturn(" ");
		when(deleteRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
		when(deleteRs.getString(FormAddressDbTableColumn.COL_COD_FORM)).thenReturn("A01");
		when(deleteRs.getString(FormPhoneDbTableColumn.COL_COD_FORM)).thenReturn("T01");
	}
	
	
	
	private void initializeScenarioDeleteNoPhone() throws SQLException {
		deleteNoPhoneConn = mock(Connection.class);
		deleteNoPhoneStmt = mock(PreparedStatement.class);
		deleteNoPhoneRs = mock(ResultSet.class);
		
		when(deleteNoPhoneConn.prepareStatement(any(String.class))).thenReturn(deleteNoPhoneStmt);			
		doNothing().when(deleteNoPhoneStmt).setString(anyInt(), anyString());
		doNothing().when(deleteNoPhoneStmt).setLong(anyInt(), anyLong());
		doNothing().when(deleteNoPhoneStmt).setTime(anyInt(), any(Time.class));		
		
		when(deleteNoPhoneStmt.executeUpdate()).thenReturn(1);
		
		when(deleteNoPhoneStmt.executeQuery()).thenReturn(deleteNoPhoneRs);		
		when(deleteNoPhoneRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Check Exist
			                        .thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Check Person has changed
			                        .thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Check Address Exist
			                        .thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Merge Address - Select
			                        .thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Merge Address - Address Form - Check Country
			                        .thenReturn(false).thenReturn(false)					// Customer - Merge Address - Address Form - Check Exist
			                        .thenReturn(true).thenReturn(true).thenReturn(false)	// Address - Delete - Check Exist
			                        .thenReturn(false)										// Address - Delete - deleteNoPhone Address	
			                        .thenReturn(false).thenReturn(false)					// Customer - Check Phone Exist		                     
			                        .thenReturn(false)										// Customer - Delete
			                        .thenReturn(true).thenReturn(true).thenReturn(false);	// Person - Check Exist
																							// Person - Delete
		
		when(deleteNoPhoneRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(deleteNoPhoneRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(deleteNoPhoneRs.getString(any(String.class))).thenReturn(" ");
		when(deleteNoPhoneRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
		when(deleteNoPhoneRs.getString(FormAddressDbTableColumn.COL_COD_FORM)).thenReturn("A01");
	}
	
	
	
	private void initializeScenarioDeleteNoAddress() throws SQLException {
		deleteNoAddressConn = mock(Connection.class);
		deleteNoAddressStmt = mock(PreparedStatement.class);
		deleteNoAddressRs = mock(ResultSet.class);
		
		when(deleteNoAddressConn.prepareStatement(any(String.class))).thenReturn(deleteNoAddressStmt);			
		doNothing().when(deleteNoAddressStmt).setString(anyInt(), anyString());
		doNothing().when(deleteNoAddressStmt).setLong(anyInt(), anyLong());
		doNothing().when(deleteNoAddressStmt).setTime(anyInt(), any(Time.class));		
		
		when(deleteNoAddressStmt.executeUpdate()).thenReturn(1);
		
		when(deleteNoAddressStmt.executeQuery()).thenReturn(deleteNoAddressRs);		
		when(deleteNoAddressRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Check Exist
				                      .thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Check Person has changed
				                      .thenReturn(false).thenReturn(false)					// Customer - Check Address Exist
				                      .thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Check Phone Exist
				                      .thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Merge Phone - Select Phone
				                      .thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Merge Phone - Select CountryPhone
				                      .thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Merge Phone - Form - Check Country
				                      .thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Merge Phone - Form - Check Exist
				                      .thenReturn(true).thenReturn(true).thenReturn(false)	// Customer - Merge Phone - Form - Select
				                      .thenReturn(true).thenReturn(true).thenReturn(false)	// Phone - Delete - Check Exist
				                      .thenReturn(false)									// Phone - Delete - Delete Phone		                     
				                      .thenReturn(false)									// Customer - Delete
				                      .thenReturn(true).thenReturn(true).thenReturn(false);	// Person - Check Exist
																							// Person - Delete
		
		when(deleteNoAddressRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(deleteNoAddressRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(deleteNoAddressRs.getString(any(String.class))).thenReturn(" ");
		when(deleteNoAddressRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
		when(deleteNoAddressRs.getString(FormPhoneDbTableColumn.COL_COD_FORM)).thenReturn("T01");
	}
	
	
	
	private void initializeScenarioDeleteNoAddressPhone() throws SQLException {
		deleteNoAddressPhoneConn = mock(Connection.class);
		deleteNoAddressPhoneStmt = mock(PreparedStatement.class);
		deleteNoAddressPhoneRs = mock(ResultSet.class);
		
		when(deleteNoAddressPhoneConn.prepareStatement(any(String.class))).thenReturn(deleteNoAddressPhoneStmt);			
		doNothing().when(deleteNoAddressPhoneStmt).setString(anyInt(), anyString());
		doNothing().when(deleteNoAddressPhoneStmt).setLong(anyInt(), anyLong());
		doNothing().when(deleteNoAddressPhoneStmt).setTime(anyInt(), any(Time.class));		
		
		when(deleteNoAddressPhoneStmt.executeUpdate()).thenReturn(1);
		
		when(deleteNoAddressPhoneStmt.executeQuery()).thenReturn(deleteNoAddressPhoneRs);		
		when(deleteNoAddressPhoneRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		// Customer - Check Exist
					                       .thenReturn(true).thenReturn(true).thenReturn(false)		// Customer - Check Person has changed
					                       .thenReturn(false).thenReturn(false)						// Customer - Check Address Exist
					                       .thenReturn(false).thenReturn(false)						// Customer - Check Phone Exist                    
					                       .thenReturn(false)										// Customer - Delete
					                       .thenReturn(true).thenReturn(true).thenReturn(false);	// Person - Check Exist
																									// Person - Delete
		
		when(deleteNoAddressPhoneRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(deleteNoAddressPhoneRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(deleteNoAddressPhoneRs.getString(any(String.class))).thenReturn(" ");
		when(deleteNoAddressPhoneRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
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
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":-1,\"codCustomer\":-1,\"codPerson\":-1,\"codGender\":1,\"addresses\":[],\"phones\":[],\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
	
	
	protected void initializeDeleteRecord() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(deleteConn);
		
		CusInfo infoRecord = new CusInfo();
		infoRecord.codOwner = 1;
		infoRecord.codCustomer = 1;
		infoRecord.codPerson = 1;
		
		model = new CusModelDelete(infoRecord);
	}
	
	
	
	@Test
	public void deleteNoPhone() {
		initializeDeleteNoPhone();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":-1,\"codCustomer\":-1,\"codPerson\":-1,\"codGender\":1,\"addresses\":[],\"phones\":[],\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
	
	
	protected void initializeDeleteNoPhone() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(deleteNoPhoneConn);
		
		CusInfo infoRecord = new CusInfo();
		infoRecord.codOwner = 1;
		infoRecord.codCustomer = 1;
		infoRecord.codPerson = 1;
		
		model = new CusModelDelete(infoRecord);
	}
	
	
	
	@Test
	public void deleteNoAddress() {
		initializeDeleteNoAddress();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":-1,\"codCustomer\":-1,\"codPerson\":-1,\"codGender\":1,\"addresses\":[],\"phones\":[],\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
	
	
	protected void initializeDeleteNoAddress() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(deleteNoAddressConn);
		
		CusInfo infoRecord = new CusInfo();
		infoRecord.codOwner = 1;
		infoRecord.codCustomer = 1;
		infoRecord.codPerson = 1;
		
		model = new CusModelDelete(infoRecord);
	}
	
	
	
	@Test
	public void missingFieldCodPerson() {
		initializeMissingFieldCodPerson();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
	
	
	protected void initializeMissingFieldCodPerson() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(deleteConn);
		
		CusInfo infoRecord = new CusInfo();
		infoRecord.codOwner = 1;
		infoRecord.codCustomer = 1;
		infoRecord.codPerson = -1;
		
		model = new CusModelDelete(infoRecord);
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
		
		CusInfo infoRecord = new CusInfo();
		infoRecord.codOwner = -1;
		infoRecord.codCustomer = 1;
		infoRecord.codPerson = 1;
		
		model = new CusModelDelete(infoRecord);
	}
	
	
	
	@Test
	public void missingFieldCodCustomer() {
		initializeMissingFieldCodCustomer();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
	
	
	protected void initializeMissingFieldCodCustomer() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(deleteConn);
		
		CusInfo infoRecord = new CusInfo();
		infoRecord.codOwner = 1;
		infoRecord.codCustomer = -1;
		infoRecord.codPerson = -1;
		
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
		infoRecord.codPerson = 1;
		
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
		infoRecord.codPerson = 1;
		
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
	
	
	
	@Test
	public void deleteNoAddressPhone() {
		initializeDeleteNoAddressPhone();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":-1,\"codCustomer\":-1,\"codPerson\":-1,\"codGender\":1,\"addresses\":[],\"phones\":[],\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
	
	
	protected void initializeDeleteNoAddressPhone() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(deleteNoAddressPhoneConn);
		
		CusInfo infoRecord = new CusInfo();
		infoRecord.codOwner = 1;
		infoRecord.codCustomer = 1;
		infoRecord.codPerson = 1;
		
		model = new CusModelDelete(infoRecord);
	}
}
