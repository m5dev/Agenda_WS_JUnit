package br.com.gda.business.customer.model;

//TODO: Testar insecao AddressA01
//TODO: Incluir casos de testes negativos para Address

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
	
	@Mock private Connection insertAddressA00Conn;
	@Mock private PreparedStatement insertAddressA00Stmt;
	@Mock private ResultSet insertAddressA00Rs;
	
	@Mock private Connection cpfAlreadyExistConn;
	@Mock private PreparedStatement cpfAlreadyExistStmt;
	@Mock private ResultSet cpfAlreadyExistRs;
	
	@Mock private Connection emailAlreadyExistConn;
	@Mock private PreparedStatement emailAlreadyExistStmt;
	@Mock private ResultSet emailAlreadyExistRs;
	
	@Mock private Connection invalidOwnerConn;
	@Mock private PreparedStatement invalidOwnerStmt;
	@Mock private ResultSet invalidOwnerRs;
	
	@Mock private Connection invalidGenderConn;
	@Mock private PreparedStatement invalidGenderStmt;
	@Mock private ResultSet invalidGenderRs;
	
	@Mock private Connection invalidPhone1CountryConn;
	@Mock private PreparedStatement invalidPhone1CountryStmt;
	@Mock private ResultSet invalidPhone1CountryRs;
	
	@Mock private Connection nullPhone1Conn;
	@Mock private PreparedStatement nullPhone1Stmt;
	@Mock private ResultSet nullPhone1Rs;
	
	@Mock private Connection invalidConn;	
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioInsert();
		initializeScenarioInsertAddressA00();
		initializeScenarioInvalidOwner();
		initializeScenarioInvalidGender();
		initializeScenarioCpf();
		initializeScenarioEmail();
		initializeScenarioInvalidConnection();
		initializeScenarioInvalidPhone1Country();
		initializeScenarioNullPhone1();
	}
	
	
	
	private void initializeScenarioInsert() throws SQLException {
		insertConn = mock(Connection.class);
		insertStmt = mock(PreparedStatement.class);
		insertRs = mock(ResultSet.class);
		
		when(insertConn.prepareStatement(any(String.class))).thenReturn(insertStmt);
		when(insertStmt.executeUpdate()).thenReturn(1);
		
		when(insertStmt.executeQuery()).thenReturn(insertRs);
		when(insertRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
							 .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Gender
							 .thenReturn(true).thenReturn(false)					// Check CPF exist
		                     .thenReturn(true).thenReturn(false)					// Check Email Exist
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Country Phone 1
		                     .thenReturn(true)										// Insert
		                     .thenReturn(true).thenReturn(true).thenReturn(false)   // Select - Customer
					         .thenReturn(true).thenReturn(true).thenReturn(false)	// Select - Gender
							 .thenReturn(true).thenReturn(true).thenReturn(false)	// Address - Select
							 .thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Check Country
							 .thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Check Exist
							 .thenReturn(true).thenReturn(true).thenReturn(false);	// Address Form - Select
		when(insertRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(insertRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(insertRs.getString(any(String.class))).thenReturn(" ");
		when(insertRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInsertAddressA00() throws SQLException {
		insertAddressA00Conn = mock(Connection.class);
		insertAddressA00Stmt = mock(PreparedStatement.class);
		insertAddressA00Rs = mock(ResultSet.class);
		
		when(insertAddressA00Conn.prepareStatement(any(String.class))).thenReturn(insertAddressA00Stmt);
		when(insertAddressA00Stmt.executeUpdate()).thenReturn(1);
		
		when(insertAddressA00Stmt.executeQuery()).thenReturn(insertAddressA00Rs);
		when(insertAddressA00Rs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
									.thenReturn(true).thenReturn(true).thenReturn(false)	// Check Gender
									.thenReturn(true).thenReturn(false)						// Check CPF exist
				                    .thenReturn(true).thenReturn(false)						// Check Email Exist
				                    .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Country Phone 1
				                    .thenReturn(true)										// Insert Customer
				                    .thenReturn(true).thenReturn(true).thenReturn(false)	// Address - Check Country
				                    .thenReturn(true).thenReturn(true).thenReturn(false)	// Address - Check Limit
									.thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Check Country
									.thenReturn(false).thenReturn(false)					// Address Form - Check Exist
									.thenReturn(true)										// Address - Insert	
				                    .thenReturn(true).thenReturn(true).thenReturn(false)   	// Select - Customer
							        .thenReturn(true).thenReturn(true).thenReturn(false)	// Select - Gender
									.thenReturn(true).thenReturn(true).thenReturn(false)	// Address - Select
									.thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Check Country
									.thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Check Exist
									.thenReturn(true).thenReturn(true).thenReturn(false);	// Address Form - Select
		when(insertAddressA00Rs.getLong(any(String.class))).thenReturn(new Long(1));
		when(insertAddressA00Rs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(insertAddressA00Rs.getString(any(String.class))).thenReturn(" ");
		when(insertAddressA00Rs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
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
	
	
	
	private void initializeScenarioInvalidGender() throws SQLException {
		invalidGenderConn = mock(Connection.class);
		invalidGenderStmt = mock(PreparedStatement.class);
		invalidGenderRs = mock(ResultSet.class);
		
		when(invalidGenderConn.prepareStatement(any(String.class))).thenReturn(invalidGenderStmt);
		when(invalidGenderStmt.executeUpdate()).thenReturn(1);
		
		when(invalidGenderStmt.executeQuery()).thenReturn(invalidGenderRs);
		when(invalidGenderRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
							        .thenReturn(true).thenReturn(false);					// Check Gender
		
		when(invalidGenderRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidGenderRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidGenderRs.getString(any(String.class))).thenReturn(" ");
		when(invalidGenderRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));			
	}
	
	
	
	private void initializeScenarioCpf() throws SQLException {
		cpfAlreadyExistConn = mock(Connection.class);
		cpfAlreadyExistStmt = mock(PreparedStatement.class);
		cpfAlreadyExistRs = mock(ResultSet.class);
		
		when(cpfAlreadyExistConn.prepareStatement(any(String.class))).thenReturn(cpfAlreadyExistStmt);
		when(cpfAlreadyExistStmt.executeUpdate()).thenReturn(1);
		
		when(cpfAlreadyExistStmt.executeQuery()).thenReturn(cpfAlreadyExistRs);
		when(cpfAlreadyExistRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
									  .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Gender
							          .thenReturn(true).thenReturn(true).thenReturn(false);	// Check CPF exist
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
		                                .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Gender
							            .thenReturn(true).thenReturn(false)						// Check email change
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
	
	
	
	private void initializeScenarioInvalidPhone1Country() throws SQLException {
		invalidPhone1CountryConn = mock(Connection.class);
		invalidPhone1CountryStmt = mock(PreparedStatement.class);
		invalidPhone1CountryRs = mock(ResultSet.class);
		
		when(invalidPhone1CountryConn.prepareStatement(any(String.class))).thenReturn(invalidPhone1CountryStmt);
		when(invalidPhone1CountryStmt.executeUpdate()).thenReturn(1);
		
		when(invalidPhone1CountryStmt.executeQuery()).thenReturn(invalidPhone1CountryRs);
		when(invalidPhone1CountryRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)				// Check Owner
										   .thenReturn(true).thenReturn(true).thenReturn(false)				// Check Gender
										   .thenReturn(true).thenReturn(false)								// Check CPF exist
					                       .thenReturn(true).thenReturn(false)								// Check Email Exist
					                       .thenReturn(true).thenReturn(false);								// Check Country Phone 1
		when(invalidPhone1CountryRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidPhone1CountryRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidPhone1CountryRs.getString(any(String.class))).thenReturn(" ");
		when(invalidPhone1CountryRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioNullPhone1() throws SQLException {
		nullPhone1Conn = mock(Connection.class);
		nullPhone1Stmt = mock(PreparedStatement.class);
		nullPhone1Rs = mock(ResultSet.class);
		
		when(nullPhone1Conn.prepareStatement(any(String.class))).thenReturn(nullPhone1Stmt);
		when(nullPhone1Stmt.executeUpdate()).thenReturn(1);
		
		when(nullPhone1Stmt.executeQuery()).thenReturn(nullPhone1Rs);
		when(nullPhone1Rs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		// Check Owner
								 .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Gender
								 .thenReturn(true).thenReturn(false)						// Check CPF exist
			                     .thenReturn(true).thenReturn(false)						// Check Email Exist
			                     .thenReturn(true)											// Insert
			                     .thenReturn(true).thenReturn(true).thenReturn(false);  	// Select
		when(nullPhone1Rs.getLong(any(String.class))).thenReturn(new Long(1));
		when(nullPhone1Rs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(nullPhone1Rs.getString(any(String.class))).thenReturn(" ");
		when(nullPhone1Rs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	@Test
	public void insertNewRecord() {
		initializeInsertNewRecord();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codCustomer\":1,\"cpf\":\" \",\"name\":\" \",\"codGender\":1,\"txtGender\":\" \",\"email\":\" \",\"codCountryPhone1\":1,\"phoneNumber1\":\" \",\"addresses\":[{\"codOwner\":1,\"codAddress\":1,\"codCustomer\":1,\"codStore\":1,\"codEmployee\":-1,\"codCountry\":\" \",\"codState\":\" \",\"city\":\" \",\"district\":\" \",\"street\":\" \",\"streetNumber\":\" \",\"complement\":\" \",\"postalCode\":\" \",\"longitude\":0.0,\"latitude\":0.0,\"line1\":\" \",\"line2\":\" \",\"line3\":\" \",\"line4\":\" \",\"line5\":\" \",\"line6\":\" \",\"line7\":\" \",\"codForm\":\" \",\"isDeleted\":false}],\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInsertNewRecord() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);
		model = new CusModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	protected String incomingDataOrdinaryUsage() {
		return "[{\"codOwner\": 8,\"cpf\": \"34284231430\", \"name\": \"Grazi\", \"codGender\": 2, \"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16}, \"email\": \"dummy13@dummy.com\", \"address1\": \"Rua Dummy\", \"address2\": \"Ap 100\", \"postalCode\": 20735060, \"city1\": \"Rio de Janeiro\", \"codCountry1\": \"BR\", \"codState1\": \"RJ\",\"codCountryPhone1\":55, \"phoneNumber1\": \"2125922592\"}]";
	}
	
	
	
	@Test
	public void insertAddressA00() {
		initializeInsertAddressA00();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codCustomer\":1,\"cpf\":\" \",\"name\":\" \",\"codGender\":1,\"txtGender\":\" \",\"email\":\" \",\"codCountryPhone1\":1,\"phoneNumber1\":\" \",\"addresses\":[{\"codOwner\":1,\"codAddress\":1,\"codCustomer\":1,\"codStore\":1,\"codEmployee\":-1,\"codCountry\":\" \",\"codState\":\" \",\"city\":\" \",\"district\":\" \",\"street\":\" \",\"streetNumber\":\" \",\"complement\":\" \",\"postalCode\":\" \",\"longitude\":0.0,\"latitude\":0.0,\"line1\":\" \",\"line2\":\" \",\"line3\":\" \",\"line4\":\" \",\"line5\":\" \",\"line6\":\" \",\"line7\":\" \",\"codForm\":\" \",\"isDeleted\":false}],\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
		//TODO: esse teste esta ruim. Retornar address form
	}
		
	
	
	protected void initializeInsertAddressA00() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertAddressA00Conn);
		model = new CusModelInsert(incomingDataInsertAddress());
	}
	
	
	
	protected String incomingDataInsertAddress() {
		return "[{\"codOwner\": 8,\"cpf\": \"34162232504\",\"name\": \"João Décimo Sétimo\",\"codGender\": 2,\"birthDate\": {\"year\": 1984,\"month\": 8,\"day\": 16},\"email\": \"joao17@dummy.com\",\"codCountryPhone1\": 55,\"phoneNumber1\": \"2125922592\",\"addresses\": [{\"codCountry\": \" \",\"line1\": \"Mr A N Address\",\"line2\": \"Allies Computing Ltd\",\"line3\": \"Manor Farm Barns\",\"line4\": \"Fox Road\",\"line5\": \"Framingham Pigot\",\"line6\": \"NORWICH\",\"line7\": \"NR14 7PZ\"}]}]";
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
		return "[{\"cpf\": \"34284231430\", \"name\": \"Grazi\", \"codGender\": 2, \"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16}, \"email\": \"dummy13@dummy.com\", \"address1\": \"Rua Dummy\", \"address2\": \"Ap 100\", \"postalCode\": 20735060, \"city1\": \"Rio de Janeiro\", \"codCountry1\": \"BR\", \"codState1\": \"RJ\", \"phoneNumber1\": \"2125922592\"}]";
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
		return "[{\"codOwner\": 8, \"name\": \"Grazi\", \"codGender\": 2, \"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16}, \"email\": \"dummy13@dummy.com\", \"address1\": \"Rua Dummy\", \"address2\": \"Ap 100\", \"postalCode\": 20735060, \"city1\": \"Rio de Janeiro\", \"codCountry1\": \"BR\", \"codState1\": \"RJ\", \"phoneNumber1\": \"2125922592\"}]";
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
		return "[{\"codOwner\": 8,\"cpf\": \"34284231430\", \"codGender\": 2, \"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16}, \"email\": \"dummy13@dummy.com\", \"address1\": \"Rua Dummy\", \"address2\": \"Ap 100\", \"postalCode\": 20735060, \"city1\": \"Rio de Janeiro\", \"codCountry1\": \"BR\", \"codState1\": \"RJ\", \"phoneNumber1\": \"2125922592\"}]";
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
		return "[{\"codOwner\": 8,\"cpf\": \"34284231430\", \"name\": \"Grazi\", \"codGender\": 2, \"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16}, \"address1\": \"Rua Dummy\", \"address2\": \"Ap 100\", \"postalCode\": 20735060, \"city1\": \"Rio de Janeiro\", \"codCountry1\": \"BR\", \"codState1\": \"RJ\", \"phoneNumber1\": \"2125922592\"}]";
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
		return "[{\"codCustomer\": 8, \"codOwner\": 8,\"cpf\": \"34284231430\", \"name\": \"Grazi\", \"codGender\": 2, \"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16}, \"email\": \"dummy13@dummy.com\", \"address1\": \"Rua Dummy\", \"address2\": \"Ap 100\", \"postalCode\": 20735060, \"city1\": \"Rio de Janeiro\", \"codCountry1\": \"BR\", \"codState1\": \"RJ\", \"phoneNumber1\": \"2125922592\"}]";
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
		return "[{\"codOwner\": 8,\"cpf\": \"34284231431\", \"name\": \"Grazi\", \"codGender\": 2, \"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16}, \"email\": \"dummy13@dummy.com\", \"address1\": \"Rua Dummy\", \"address2\": \"Ap 100\", \"postalCode\": 20735060, \"city1\": \"Rio de Janeiro\", \"codCountry1\": \"BR\", \"codState1\": \"RJ\", \"phoneNumber1\": \"2125922592\"}]";
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
	
	
	
	@Test
	public void invalidFieldCodGender() {
		initializeInvalidFieldCodGender();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1169,\"selectMessage\":\"Gender not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidFieldCodGender() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidGenderConn);
		model = new CusModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void invalidPhone1Country() {
		initializeInvalidPhone1Country();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1171,\"selectMessage\":\"Phone Country code not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidPhone1Country() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidPhone1CountryConn);
		model = new CusModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void invalidPhone1Area() {
		initializeInvalidPhone1Area();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1558,\"selectMessage\":\"Invalid area code for phone number\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidPhone1Area() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);
		model = new CusModelInsert(incomingDataInvalidPhone1Area());
	}
	
	
	
	protected String incomingDataInvalidPhone1Area() {
		return "[{\"codOwner\": 8,\"cpf\": \"34284231430\", \"name\": \"Grazi\", \"codGender\": 2, \"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16}, \"email\": \"dummy13@dummy.com\", \"address1\": \"Rua Dummy\", \"address2\": \"Ap 100\", \"postalCode\": 20735060, \"city1\": \"Rio de Janeiro\", \"codCountry1\": \"BR\", \"codState1\": \"RJ\",\"codCountryPhone1\":55, \"phoneNumber1\": \"1025922592\"}]";
	}
	
	
	
	@Test
	public void invalidPhone1SmallerLength() {
		initializeInvalidPhone1SmallerLength();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1556,\"selectMessage\":\"Invalid phone length. Area code is expected\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidPhone1SmallerLength() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);
		model = new CusModelInsert(incomingDataInvalidPhone1SmallerLength());
	}
	
	
	
	protected String incomingDataInvalidPhone1SmallerLength() {
		return "[{\"codOwner\": 8,\"cpf\": \"34284231430\", \"name\": \"Grazi\", \"codGender\": 2, \"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16}, \"email\": \"dummy13@dummy.com\", \"address1\": \"Rua Dummy\", \"address2\": \"Ap 100\", \"postalCode\": 20735060, \"city1\": \"Rio de Janeiro\", \"codCountry1\": \"BR\", \"codState1\": \"RJ\",\"codCountryPhone1\":55, \"phoneNumber1\": \"215922592\"}]";
	}
	
	
	
	@Test
	public void invalidPhone1BiggerLength() {
		initializeInvalidPhone1BiggerLength();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1554,\"selectMessage\":\"Phone number is invalid\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidPhone1BiggerLength() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);
		model = new CusModelInsert(incomingDataInvalidPhone1BiggerLength());
	}
	
	
	
	protected String incomingDataInvalidPhone1BiggerLength() {
		return "[{\"codOwner\": 8,\"cpf\": \"34284231430\", \"name\": \"Grazi\", \"codGender\": 2, \"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16}, \"email\": \"dummy13@dummy.com\", \"address1\": \"Rua Dummy\", \"address2\": \"Ap 100\", \"postalCode\": 20735060, \"city1\": \"Rio de Janeiro\", \"codCountry1\": \"BR\", \"codState1\": \"RJ\",\"codCountryPhone1\":55, \"phoneNumber1\": \"21225922592\"}]";
	}
	
	
	
	@Test
	public void invalidPhone1Mobile() {
		initializeInvalidPhone1Mobile();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1554,\"selectMessage\":\"Phone number is invalid\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidPhone1Mobile() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);
		model = new CusModelInsert(incomingDataInvalidPhone1Mobile());
	}
	
	
	
	protected String incomingDataInvalidPhone1Mobile() {
		return "[{\"codOwner\": 8,\"cpf\": \"34284231430\", \"name\": \"Grazi\", \"codGender\": 2, \"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16}, \"email\": \"dummy13@dummy.com\", \"address1\": \"Rua Dummy\", \"address2\": \"Ap 100\", \"postalCode\": 20735060, \"city1\": \"Rio de Janeiro\", \"codCountry1\": \"BR\", \"codState1\": \"RJ\",\"codCountryPhone1\":55, \"phoneNumber1\": \"2195939318\"}]";
	}
	
	
	
	@Test
	public void invalidPhone1Sequence() {
		initializeInvalidPhone1Sequence();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1559,\"selectMessage\":\"Invalid sequence for phone number\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidPhone1Sequence() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);
		model = new CusModelInsert(incomingDataInvalidPhone1Sequence());
	}
	
	
	
	protected String incomingDataInvalidPhone1Sequence() {
		return "[{\"codOwner\": 8,\"cpf\": \"34284231430\", \"name\": \"Grazi\", \"codGender\": 2, \"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16}, \"email\": \"dummy13@dummy.com\", \"address1\": \"Rua Dummy\", \"address2\": \"Ap 100\", \"postalCode\": 20735060, \"city1\": \"Rio de Janeiro\", \"codCountry1\": \"BR\", \"codState1\": \"RJ\",\"codCountryPhone1\":55, \"phoneNumber1\": \"2111111111\"}]";
	}
	
	
	
	@Test
	public void invalidPhone1NullNumber() {
		initializeInvalidPhone1NullNumber();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidPhone1NullNumber() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);
		model = new CusModelInsert(incomingDataInvalidPhone1NullNumber());
	}
	
	
	
	protected String incomingDataInvalidPhone1NullNumber() {
		return "[{\"codOwner\": 8,\"cpf\": \"34284231430\", \"name\": \"Grazi\", \"codGender\": 2, \"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16}, \"email\": \"dummy13@dummy.com\", \"address1\": \"Rua Dummy\", \"address2\": \"Ap 100\", \"postalCode\": 20735060, \"city1\": \"Rio de Janeiro\", \"codCountry1\": \"BR\", \"codState1\": \"RJ\",\"codCountryPhone1\":55}]";
	}
	
	
	
	@Test
	public void insertNullPhone() {
		initializeInsertNullPhone();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codCustomer\":1,\"cpf\":\" \",\"name\":\" \",\"codGender\":1,\"email\":\" \",\"codCountryPhone1\":1,\"phoneNumber1\":\" \",\"addresses\":[],\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInsertNullPhone() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(nullPhone1Conn);
		model = new CusModelInsert(incomingDataNullPhone());
	}
	
	
	
	protected String incomingDataNullPhone() {
		return "[{\"codOwner\": 8,\"cpf\": \"34284231430\", \"name\": \"Grazi\", \"codGender\": 2, \"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16}, \"email\": \"dummy13@dummy.com\", \"address1\": \"Rua Dummy\", \"address2\": \"Ap 100\", \"postalCode\": 20735060, \"city1\": \"Rio de Janeiro\", \"codCountry1\": \"BR\", \"codState1\": \"RJ\"}]";
	}
}
