package br.com.gda.business.customer.model;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

import br.com.gda.common.DbConnection;
import br.com.gda.model.Model;

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class CusModelInsertTest {
	@Mock private Connection insertConn;
	@Mock private PreparedStatement insertStmt;
	@Mock private ResultSet insertRs;
	
	@Mock private Connection cpfAlreadyExistConn;
	@Mock private PreparedStatement cpfAlreadyExistStmt;
	@Mock private ResultSet cpfAlreadyExistRs;
	
	@Mock private Connection emailAlreadyExistConn;
	@Mock private PreparedStatement emailAlreadyExistStmt;
	@Mock private ResultSet emailAlreadyExistRs;
	
	@Mock private Connection invalidOwnerConn;
	@Mock private PreparedStatement invalidOwnerStmt;
	@Mock private ResultSet invalidOwnerRs;
	
	@Mock private Connection invalidConn;	
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioInsert();
		initializeScenarioInvalidOwner();
		initializeScenarioCpf();
		initializeScenarioEmail();
		initializeScenarioInvalidConnection();
	}
	
	
	
	private void initializeScenarioInsert() throws SQLException {
		insertConn = mock(Connection.class);
		insertStmt = mock(PreparedStatement.class);
		insertRs = mock(ResultSet.class);
		
		when(insertConn.prepareStatement(any(String.class))).thenReturn(insertStmt);
		when(insertStmt.executeUpdate()).thenReturn(1);
		
		when(insertStmt.executeQuery()).thenReturn(insertRs);
		when(insertRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
							 .thenReturn(true).thenReturn(false)					// Check CPF exist
		                     .thenReturn(true).thenReturn(false)					// Check Email Exist
		                     .thenReturn(true)										// Insert
		                     .thenReturn(true).thenReturn(true).thenReturn(false);  // Select
		when(insertRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(insertRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(insertRs.getString(any(String.class))).thenReturn(" ");
		when(insertRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidOwner() throws SQLException {
		invalidOwnerConn = mock(Connection.class);
		invalidOwnerStmt = mock(PreparedStatement.class);
		invalidOwnerRs = mock(ResultSet.class);
		
		when(invalidOwnerConn.prepareStatement(any(String.class))).thenReturn(invalidOwnerStmt);
		when(invalidOwnerStmt.executeUpdate()).thenReturn(1);
		
		when(invalidOwnerStmt.executeQuery()).thenReturn(invalidOwnerRs);
		when(invalidOwnerRs.next()).thenReturn(true).thenReturn(false);	// Check Owner

		when(invalidOwnerRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidOwnerRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidOwnerRs.getString(any(String.class))).thenReturn(" ");
		when(invalidOwnerRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioCpf() throws SQLException {
		cpfAlreadyExistConn = mock(Connection.class);
		cpfAlreadyExistStmt = mock(PreparedStatement.class);
		cpfAlreadyExistRs = mock(ResultSet.class);
		
		when(cpfAlreadyExistConn.prepareStatement(any(String.class))).thenReturn(cpfAlreadyExistStmt);
		when(cpfAlreadyExistStmt.executeUpdate()).thenReturn(1);
		
		when(cpfAlreadyExistStmt.executeQuery()).thenReturn(cpfAlreadyExistRs);
		when(cpfAlreadyExistRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
							  .thenReturn(true).thenReturn(true).thenReturn(false);			// Check CPF exist
		when(cpfAlreadyExistRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(cpfAlreadyExistRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(cpfAlreadyExistRs.getString(any(String.class))).thenReturn(" ");
		when(cpfAlreadyExistRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioEmail() throws SQLException {
		emailAlreadyExistConn = mock(Connection.class);
		emailAlreadyExistStmt = mock(PreparedStatement.class);
		emailAlreadyExistRs = mock(ResultSet.class);
		
		when(emailAlreadyExistConn.prepareStatement(any(String.class))).thenReturn(emailAlreadyExistStmt);
		when(emailAlreadyExistStmt.executeUpdate()).thenReturn(1);
		
		when(emailAlreadyExistStmt.executeQuery()).thenReturn(emailAlreadyExistRs);
		when(emailAlreadyExistRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
							            .thenReturn(true).thenReturn(false)						// Check email exist
		                                .thenReturn(true).thenReturn(true).thenReturn(false);	// Check Email Exist
		when(emailAlreadyExistRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(emailAlreadyExistRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(emailAlreadyExistRs.getString(any(String.class))).thenReturn(" ");
		when(emailAlreadyExistRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));			
	}
	
	
	
	private void initializeScenarioInvalidConnection() throws SQLException {
		invalidConn = mock(Connection.class);
		when(invalidConn.prepareStatement(anyString())).thenThrow(new SQLException());
	}
	
	
	
	@Test
	public void insertNewRecord() {
		initializeInsertNewRecord();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codCustomer\":1,\"cpf\":\" \",\"name\":\" \",\"codGender\":1,\"txtGender\":\" \",\"email\":\" \",\"address1\":\" \",\"address2\":\" \",\"postalCode\":1,\"city\":\" \",\"codCountry\":\" \",\"txtCountry\":\" \",\"stateProvince\":\" \",\"phone\":\" \",\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInsertNewRecord() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);
		model = new CusModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	protected String incomingDataOrdinaryUsage() {
		return "[{\"codOwner\": 8,\"cpf\": \"34284231430\", \"name\": \"Grazi\", \"codGender\": 2, \"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16}, \"email\": \"dummy13@dummy.com\", \"address1\": \"Rua Dummy\", \"address2\": \"Ap 100\", \"postalCode\": 20735060, \"city\": \"Rio de Janeiro\", \"codCountry\": \"BR\", \"stateProvince\": \"RJ\", \"phone\": \"2125922592\"}]";
	}
	
	
	
	@Test
	public void missingFieldOwner() {
		initializeMissingFieldOwner();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMissingFieldOwner() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);
		model = new CusModelInsert(incomingDataMissingFieldOwner());
	}
	
	
	
	protected String incomingDataMissingFieldOwner() {
		return "[{\"cpf\": \"34284231430\", \"name\": \"Grazi\", \"codGender\": 2, \"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16}, \"email\": \"dummy13@dummy.com\", \"address1\": \"Rua Dummy\", \"address2\": \"Ap 100\", \"postalCode\": 20735060, \"city\": \"Rio de Janeiro\", \"codCountry\": \"BR\", \"stateProvince\": \"RJ\", \"phone\": \"2125922592\"}]";
	}
	
	
	
	@Test
	public void missingFieldCpf() {
		initializeMissingFieldCpf();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMissingFieldCpf() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);
		model = new CusModelInsert(incomingDataMissingFieldCpf());
	}
	
	
	
	protected String incomingDataMissingFieldCpf() {
		return "[{\"codOwner\": 8, \"name\": \"Grazi\", \"codGender\": 2, \"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16}, \"email\": \"dummy13@dummy.com\", \"address1\": \"Rua Dummy\", \"address2\": \"Ap 100\", \"postalCode\": 20735060, \"city\": \"Rio de Janeiro\", \"codCountry\": \"BR\", \"stateProvince\": \"RJ\", \"phone\": \"2125922592\"}]";
	}
	
	
	
	@Test
	public void missingFieldName() {
		initializeMissingFieldName();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMissingFieldName() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);
		model = new CusModelInsert(incomingDataMissingFieldName());
	}
	
	
	
	protected String incomingDataMissingFieldName() {
		return "[{\"codOwner\": 8,\"cpf\": \"34284231430\", \"codGender\": 2, \"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16}, \"email\": \"dummy13@dummy.com\", \"address1\": \"Rua Dummy\", \"address2\": \"Ap 100\", \"postalCode\": 20735060, \"city\": \"Rio de Janeiro\", \"codCountry\": \"BR\", \"stateProvince\": \"RJ\", \"phone\": \"2125922592\"}]";
	}
	
	
	
	@Test
	public void missingFieldEmail() {
		initializeMissingFieldEmail();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMissingFieldEmail() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);
		model = new CusModelInsert(incomingDataMissingFieldEmail());
	}
	
	
	
	protected String incomingDataMissingFieldEmail() {
		return "[{\"codOwner\": 8,\"cpf\": \"34284231430\", \"name\": \"Grazi\", \"codGender\": 2, \"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16}, \"address1\": \"Rua Dummy\", \"address2\": \"Ap 100\", \"postalCode\": 20735060, \"city\": \"Rio de Janeiro\", \"codCountry\": \"BR\", \"stateProvince\": \"RJ\", \"phone\": \"2125922592\"}]";
	}
	
	
	
	@Test
	public void fieldCodCustomer() {
		initializeFieldCodCustomer();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1057,\"selectMessage\":\"Auto generated fields should not be passed\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeFieldCodCustomer() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);
		model = new CusModelInsert(incomingDataFieldCodCustomer());
	}
	
	
	
	protected String incomingDataFieldCodCustomer() {
		return "[{\"codCustomer\": 8, \"codOwner\": 8,\"cpf\": \"34284231430\", \"name\": \"Grazi\", \"codGender\": 2, \"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16}, \"email\": \"dummy13@dummy.com\", \"address1\": \"Rua Dummy\", \"address2\": \"Ap 100\", \"postalCode\": 20735060, \"city\": \"Rio de Janeiro\", \"codCountry\": \"BR\", \"stateProvince\": \"RJ\", \"phone\": \"2125922592\"}]";
	}
	
	
	
	@Test
	public void invalidFieldCodOwner() {
		initializeInvalidFieldCodOwner();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1251,\"selectMessage\":\"Owner data not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidFieldCodOwner() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidOwnerConn);
		model = new CusModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void invalidCnpj() {
		initializeInvalidCnpj();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":3,\"selectMessage\":\"CPF is invalid\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	protected void initializeInvalidCnpj() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);		
		model = new CusModelInsert(incomingDataInvalidCnpj());
	}	
	
	
	
	protected String incomingDataInvalidCnpj() {
		return "[{\"codOwner\": 8,\"cpf\": \"34284231431\", \"name\": \"Grazi\", \"codGender\": 2, \"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16}, \"email\": \"dummy13@dummy.com\", \"address1\": \"Rua Dummy\", \"address2\": \"Ap 100\", \"postalCode\": 20735060, \"city\": \"Rio de Janeiro\", \"codCountry\": \"BR\", \"stateProvince\": \"RJ\", \"phone\": \"2125922592\"}]";
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
		model = new CusModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void cpfAlreadyExist() {
		initializeCpfAlreadyExist();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1350,\"selectMessage\":\"Customer's CPF already exist on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeCpfAlreadyExist() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(cpfAlreadyExistConn);
		model = new CusModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void emailAlreadyExist() {
		initializeEmailAlreadyExist();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1352,\"selectMessage\":\"Customer's e-email already exist on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeEmailAlreadyExist() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(emailAlreadyExistConn);
		model = new CusModelInsert(incomingDataOrdinaryUsage());
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);
		model = new CusModelInsert(null);
	}	
}
