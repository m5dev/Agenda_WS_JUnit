package br.com.gda.business.employee.model;

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

import br.com.gda.business.employee.model.EmpModelInsert;
import br.com.gda.common.DbConnection;
import br.com.gda.model.Model;

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class EmpModelInsertTest {
	@Mock private Connection insertConn;
	@Mock private PreparedStatement insertStmt;
	@Mock private ResultSet insertRs;
	
	@Mock private Connection cpfAlreadyExistConn;
	@Mock private PreparedStatement cpfAlreadyExistStmt;
	@Mock private ResultSet cpfAlreadyExistRs;
	
	@Mock private Connection invalidGenderConn;
	@Mock private PreparedStatement invalidGenderStmt;
	@Mock private ResultSet invalidGenderRs;
	
	@Mock private Connection invalidOwnerConn;
	@Mock private PreparedStatement invalidOwnerStmt;
	@Mock private ResultSet invalidOwnerRs;
	
	@Mock private Connection invalidConn;
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioInsert();
		initializeScenarioCpf();
		initializeScenarioInvalidConnection();
		initializeScenarioInvalidGender();
		initializeScenarioInvalidOwner();
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
		                     .thenReturn(true).thenReturn(false)					// Check CPF already exist
		                     .thenReturn(true)										// Insert
		                     .thenReturn(true).thenReturn(true).thenReturn(false);	// Select
		when(insertRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(insertRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(insertRs.getString(any(String.class))).thenReturn(" ");
		when(insertRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
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
								      .thenReturn(true).thenReturn(true).thenReturn(false);	// Check CPF already exist
		when(cpfAlreadyExistRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(cpfAlreadyExistRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(cpfAlreadyExistRs.getString(any(String.class))).thenReturn(" ");
		when(cpfAlreadyExistRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));	
	}
	
	
	
	private void initializeScenarioInvalidConnection() throws SQLException {
		invalidConn = mock(Connection.class);
		when(invalidConn.prepareStatement(anyString())).thenThrow(new SQLException());
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
	
	
		
	@Test
	public void insertNewRecord() {
		initializeInsertNewRecord();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codEmployee\":1,\"stores\":[],\"cpf\":\" \",\"name\":\" \",\"codGender\":1,\"txtGender\":\" \",\"email\":\" \",\"address1\":\" \",\"address2\":\" \",\"postalCode\":1,\"city\":\" \",\"codCountry\":\" \",\"txtCountry\":\" \",\"stateProvince\":\" \",\"phone\":\" \",\"beginTime\":{\"hour\":11,\"minute\":22},\"endTime\":{\"hour\":11,\"minute\":22},\"codPosition\":1,\"txtPosition\":\" \",\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInsertNewRecord() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);
		model = new EmpModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void cpfAlreadyExist() {
		initializeCpfAlreadyExist();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1055,\"selectMessage\":\"Employee's CPF already exist on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeCpfAlreadyExist() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(cpfAlreadyExistConn);
		model = new EmpModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	protected String incomingDataOrdinaryUsage() {
		return "[{\"codOwner\": 8,\"cpf\": \"47106581968\",\"name\": \"Grazi\",\"codPosition\": 4,\"codGender\": 2,\"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16},\"email\": \"dummy02@dummy.com\",\"address1\": \"Rua Dummy\",\"address2\": \"Ap 100\",\"postalcode\": 20735060,\"city\": \"Rio de Janeiro\",\"country\": \"BR\",\"state\": \"RJ\",\"phone\": \"2125922592\",\"beginTime\": {\"hour\": 9, \"minute\": 0},\"endTime\": {\"hour\": 18, \"minute\": 0},\"recordMode\": \" \"}]";
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
		model = new EmpModelInsert(incomingDataMissingFieldName());
	}	
	
	
	
	protected String incomingDataMissingFieldName() {
		return "[{\"codOwner\": 8,\"cpf\": \"47106581968\",\"codPosition\": 4,\"codGender\": 2,\"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16},\"email\": \"dummy02@dummy.com\",\"address1\": \"Rua Dummy\",\"address2\": \"Ap 100\",\"postalcode\": 20735060,\"city\": \"Rio de Janeiro\",\"country\": \"BR\",\"state\": \"RJ\",\"phone\": \"2125922592\",\"beginTime\": {\"hour\": 9, \"minute\": 0},\"endTime\": {\"hour\": 18, \"minute\": 0},\"recordMode\": \" \"}]";
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
		model = new EmpModelInsert(incomingDataMissingFieldName());
	}	
	
	
	
	protected String incomingDataMissingFieldCpf() {
		return "[{\"codOwner\": 8,\"name\": \"Grazi\",\"codPosition\": 4,\"codGender\": 2,\"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16},\"email\": \"dummy02@dummy.com\",\"address1\": \"Rua Dummy\",\"address2\": \"Ap 100\",\"postalcode\": 20735060,\"city\": \"Rio de Janeiro\",\"country\": \"BR\",\"state\": \"RJ\",\"phone\": \"2125922592\",\"beginTime\": {\"hour\": 9, \"minute\": 0},\"endTime\": {\"hour\": 18, \"minute\": 0},\"recordMode\": \" \"}]";
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);		
		model = new EmpModelInsert(incomingDataMissingFieldName());
	}	
	
	
	
	protected String incomingDataMissingFieldcodOwner() {
		return "[{\"cpf\": \"47106581968\",\"name\": \"Grazi\",\"codPosition\": 4,\"codGender\": 2,\"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16},\"email\": \"dummy02@dummy.com\",\"address1\": \"Rua Dummy\",\"address2\": \"Ap 100\",\"postalcode\": 20735060,\"city\": \"Rio de Janeiro\",\"country\": \"BR\",\"state\": \"RJ\",\"phone\": \"2125922592\",\"beginTime\": {\"hour\": 9, \"minute\": 0},\"endTime\": {\"hour\": 18, \"minute\": 0},\"recordMode\": \" \"}]";
	}
	
	
	
	@Test
	public void fieldCodEmployee() {
		initializeFieldCodEmployee();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1057,\"selectMessage\":\"Auto generated fields should not be passed\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	protected void initializeFieldCodEmployee() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);		
		model = new EmpModelInsert(incomingDataPassingFieldCodEmployee());
	}	
	
	
	
	protected String incomingDataPassingFieldCodEmployee() {
		return "[{\"codEmployee\": 80,\"codOwner\": 8,\"cpf\": \"47106581968\",\"name\": \"Grazi\",\"codPosition\": 4,\"codGender\": 2,\"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16},\"email\": \"dummy02@dummy.com\",\"address1\": \"Rua Dummy\",\"address2\": \"Ap 100\",\"postalcode\": 20735060,\"city\": \"Rio de Janeiro\",\"country\": \"BR\",\"state\": \"RJ\",\"phone\": \"2125922592\",\"beginTime\": {\"hour\": 9, \"minute\": 0},\"endTime\": {\"hour\": 18, \"minute\": 0},\"recordMode\": \" \"}]";
	}
	
	
	
	@Test
	public void invalidCpf() {
		initializeInvalidCpf();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":3,\"selectMessage\":\"CPF is invalid\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	protected void initializeInvalidCpf() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);		
		model = new EmpModelInsert(incomingDataInvalidCpf());
	}	
	
	
	
	protected String incomingDataInvalidCpf() {
		return "[{\"codOwner\": 8,\"cpf\": \"47106581960\",\"name\": \"Grazi\",\"codPosition\": 4,\"codGender\": 2,\"birthDate\": {\"year\": 1984, \"month\": 8, \"day\": 16},\"email\": \"dummy02@dummy.com\",\"address1\": \"Rua Dummy\",\"address2\": \"Ap 100\",\"postalcode\": 20735060,\"city\": \"Rio de Janeiro\",\"country\": \"BR\",\"state\": \"RJ\",\"phone\": \"2125922592\",\"beginTime\": {\"hour\": 9, \"minute\": 0},\"endTime\": {\"hour\": 18, \"minute\": 0},\"recordMode\": \" \"}]";
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
		model = new EmpModelInsert(incomingDataOrdinaryUsage());
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
		model = new EmpModelInsert(incomingDataOrdinaryUsage());
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
		model = new EmpModelInsert(incomingDataOrdinaryUsage());
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
		model = new EmpModelInsert(null);
	}	
}
