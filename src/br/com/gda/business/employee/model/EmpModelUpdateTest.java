package br.com.gda.business.employee.model;

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

import br.com.gda.business.employee.model.EmpModelUpdate;
import br.com.gda.common.DbConnection;
import br.com.gda.model.Model;

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class EmpModelUpdateTest {
	@Mock private Connection updateConn;
	@Mock private PreparedStatement updateStmt;
	@Mock private ResultSet updateRs;
	
	@Mock private Connection updateCpfConn;
	@Mock private PreparedStatement updateCpfStmt;
	@Mock private ResultSet updateCpfRs;
	
	@Mock private Connection cpfAlreadyTakenConn;
	@Mock private PreparedStatement cpfAlreadyTakenStmt;
	@Mock private ResultSet cpfAlreadyTakenRs;
	
	@Mock private Connection dontExistConn;
	@Mock private PreparedStatement dontExistStmt;
	@Mock private ResultSet dontExistRs;
	
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;
	
	@Mock private Connection invalidOwnerConn;
	@Mock private PreparedStatement invalidOwnerStmt;
	@Mock private ResultSet invalidOwnerRs;
	
	@Mock private Connection invalidGenderConn;
	@Mock private PreparedStatement invalidGenderStmt;
	@Mock private ResultSet invalidGenderRs;
	
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioUpdate();
		initializeScenarioUpdateCpf();
		initializeScenarioUpdateCpfAlreadyExist();
		initializeScenarioEmpDontExist();
		initializeScenarioInvalidConnection();
		initializeScenarioInvalidOwner();
		initializeScenarioInvalidGender();
	}
	
	
	
	private void initializeScenarioUpdate() throws SQLException {
		updateStmt = mock(PreparedStatement.class);
		updateRs = mock(ResultSet.class);
		updateConn = mock(Connection.class);
		
		when(updateConn.prepareStatement(any(String.class))).thenReturn(updateStmt);	
		doNothing().when(updateStmt).setString(anyInt(), anyString());
		doNothing().when(updateStmt).setLong(anyInt(), anyLong());
		doNothing().when(updateStmt).setTime(anyInt(), any(Time.class));		
		
		when(updateStmt.executeUpdate()).thenReturn(1);		

		when(updateStmt.executeQuery()).thenReturn(updateRs);
		when(updateRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
							 .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Gender
							 .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Exist
							 .thenReturn(true).thenReturn(true).thenReturn(false)	// CPF not changed
							 														// Update
							 .thenReturn(true).thenReturn(true).thenReturn(false);	// Select
		when(updateRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateRs.getString(any(String.class))).thenReturn(" ");
		when(updateRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
	}
	
	
	
	private void initializeScenarioUpdateCpf() throws SQLException {
		updateCpfStmt = mock(PreparedStatement.class);
		updateCpfRs = mock(ResultSet.class);
		updateCpfConn = mock(Connection.class);
		
		when(updateCpfConn.prepareStatement(any(String.class))).thenReturn(updateCpfStmt);	
		doNothing().when(updateCpfStmt).setString(anyInt(), anyString());
		doNothing().when(updateCpfStmt).setLong(anyInt(), anyLong());
		doNothing().when(updateCpfStmt).setTime(anyInt(), any(Time.class));		
		
		when(updateCpfStmt.executeUpdate()).thenReturn(1);		

		when(updateCpfStmt.executeQuery()).thenReturn(updateCpfRs);
		when(updateCpfRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
								.thenReturn(true).thenReturn(true).thenReturn(false)	// Check Gender
								.thenReturn(true).thenReturn(true).thenReturn(false)	// Check Exist
								.thenReturn(true).thenReturn(false)						// CPF changed
								.thenReturn(true).thenReturn(false)						// New CPF don't exist
								 														// Update
								.thenReturn(true).thenReturn(true).thenReturn(false);	// Select
		when(updateCpfRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateCpfRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateCpfRs.getString(any(String.class))).thenReturn(" ");
		when(updateCpfRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
	}
	
	
	
	private void initializeScenarioUpdateCpfAlreadyExist() throws SQLException {
		cpfAlreadyTakenStmt = mock(PreparedStatement.class);
		cpfAlreadyTakenRs = mock(ResultSet.class);
		cpfAlreadyTakenConn = mock(Connection.class);
		
		when(cpfAlreadyTakenConn.prepareStatement(any(String.class))).thenReturn(cpfAlreadyTakenStmt);	
		doNothing().when(cpfAlreadyTakenStmt).setString(anyInt(), anyString());
		doNothing().when(cpfAlreadyTakenStmt).setLong(anyInt(), anyLong());
		doNothing().when(cpfAlreadyTakenStmt).setTime(anyInt(), any(Time.class));		
		
		when(cpfAlreadyTakenStmt.executeUpdate()).thenReturn(1);		

		when(cpfAlreadyTakenStmt.executeQuery()).thenReturn(cpfAlreadyTakenRs);
		when(cpfAlreadyTakenRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		// Check Owner
									  .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Gender
									  .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Exist
									  .thenReturn(true).thenReturn(false)						// CPF changed
									  .thenReturn(true).thenReturn(true).thenReturn(false);		// New CPF already exist
		when(cpfAlreadyTakenRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(cpfAlreadyTakenRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(cpfAlreadyTakenRs.getString(any(String.class))).thenReturn(" ");
		when(cpfAlreadyTakenRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
	}
	
	
	
	private void initializeScenarioEmpDontExist() throws SQLException {
		dontExistStmt = mock(PreparedStatement.class);
		dontExistRs = mock(ResultSet.class);		
		dontExistConn = mock(Connection.class);
		
		when(dontExistConn.prepareStatement(any(String.class))).thenReturn(dontExistStmt);

		when(dontExistStmt.executeQuery()).thenReturn(dontExistRs);
		when(dontExistRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		// Check Owner
							    .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Gender
							    .thenReturn(true).thenReturn(false);						// Check Exist
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
	
	
	
	
	@Test
	public void updateRecord() {
		initializeUpdateRecord();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codEmployee\":1,\"stores\":[],\"cpf\":\" \",\"name\":\" \",\"codGender\":1,\"txtGender\":\" \",\"email\":\" \",\"address1\":\" \",\"address2\":\" \",\"postalCode\":1,\"city\":\" \",\"codCountry\":\" \",\"txtCountry\":\" \",\"stateProvince\":\" \",\"phone\":\" \",\"beginTime\":{\"hour\":11,\"minute\":22},\"endTime\":{\"hour\":11,\"minute\":22},\"codPosition\":1,\"txtPosition\":\" \",\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeUpdateRecord() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(updateConn);
		model = new EmpModelUpdate(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void cpfUpdate() {
		initializeCpfChange();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codEmployee\":1,\"stores\":[],\"cpf\":\" \",\"name\":\" \",\"codGender\":1,\"txtGender\":\" \",\"email\":\" \",\"address1\":\" \",\"address2\":\" \",\"postalCode\":1,\"city\":\" \",\"codCountry\":\" \",\"txtCountry\":\" \",\"stateProvince\":\" \",\"phone\":\" \",\"beginTime\":{\"hour\":11,\"minute\":22},\"endTime\":{\"hour\":11,\"minute\":22},\"codPosition\":1,\"txtPosition\":\" \",\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeCpfChange() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(updateCpfConn);
		model = new EmpModelUpdate(incomingDataOrdinaryUsage());
	}
	
	
	
	protected String incomingDataOrdinaryUsage() {
		return "[{\"codOwner\": 8,\"codEmployee\": 56,\"cpf\": \"15717068603\",\"password\": \"eer2\",\"name\": \"Ricardo Dummy #3\",\"codPosition\": 2,\"codGender\": 2,\"bornDate\": {\"year\": 1984, \"month\": 8, \"day\": 16},\"email\": \"dummy03@dummy.com\",\"address1\": \"Rua Dummy\",\"address2\": \"Ap 100\",\"postalcode\": 20735060,\"city\": \"Rio de Janeiro\",\"country\": \"BR\",\"state\": \"RJ\",\"phone\": \"2125922592\",\"beginTime\": {\"hour\": 9, \"minute\": 0},\"endTime\": {\"hour\": 18, \"minute\": 0},\"recordMode\": \" \"}]";
	}
	
	
	
	@Test
	public void cpfAlreadyTaken() {
		initializeCpfAlreadyTaken();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1055,\"selectMessage\":\"Employee's CPF already exist on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeCpfAlreadyTaken() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(cpfAlreadyTakenConn);
		model = new EmpModelUpdate(incomingDataOrdinaryUsage());
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(dontExistConn);		
		model = new EmpModelUpdate(incomingDataMissingFieldCodOwner());
	}	
	
	
	
	protected String incomingDataMissingFieldCodOwner() {
		return "[{\"codEmployee\": 56,\"cpf\": \"15717068603\",\"password\": \"eer2\",\"name\": \"Ricardo Dummy #3\",\"codPosition\": 2,\"codGender\": 2,\"bornDate\": {\"year\": 1984, \"month\": 8, \"day\": 16},\"email\": \"dummy03@dummy.com\",\"address1\": \"Rua Dummy\",\"address2\": \"Ap 100\",\"postalcode\": 20735060,\"city\": \"Rio de Janeiro\",\"country\": \"BR\",\"state\": \"RJ\",\"phone\": \"2125922592\",\"beginTime\": {\"hour\": 9, \"minute\": 0},\"endTime\": {\"hour\": 18, \"minute\": 0},\"recordMode\": \" \"}]";
	}
	
	
	
	@Test
	public void missingFieldCodEmployee() {
		initializeMissingFieldCodEmployee();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":6,\"selectMessage\":\"Key field should not be null\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	protected void initializeMissingFieldCodEmployee() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(dontExistConn);		
		model = new EmpModelUpdate(incomingDataMissingFieldCodEmployee());
	}	
	
	
	
	protected String incomingDataMissingFieldCodEmployee() {
		return "[{\"codOwner\": 8,\"cpf\": \"15717068603\",\"password\": \"eer2\",\"name\": \"Ricardo Dummy #3\",\"codPosition\": 2,\"codGender\": 2,\"bornDate\": {\"year\": 1984, \"month\": 8, \"day\": 16},\"email\": \"dummy03@dummy.com\",\"address1\": \"Rua Dummy\",\"address2\": \"Ap 100\",\"postalcode\": 20735060,\"city\": \"Rio de Janeiro\",\"country\": \"BR\",\"state\": \"RJ\",\"phone\": \"2125922592\",\"beginTime\": {\"hour\": 9, \"minute\": 0},\"endTime\": {\"hour\": 18, \"minute\": 0},\"recordMode\": \" \"}]";
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(dontExistConn);		
		model = new EmpModelUpdate(incomingDataMissingFieldName());
	}	
	
	
	
	protected String incomingDataMissingFieldName() {
		return "[{\"codOwner\": 8,\"codEmployee\": 56,\"cpf\": \"15717068603\",\"password\": \"eer2\",\"codPosition\": 2,\"codGender\": 2,\"bornDate\": {\"year\": 1984, \"month\": 8, \"day\": 16},\"email\": \"dummy03@dummy.com\",\"address1\": \"Rua Dummy\",\"address2\": \"Ap 100\",\"postalcode\": 20735060,\"city\": \"Rio de Janeiro\",\"country\": \"BR\",\"state\": \"RJ\",\"phone\": \"2125922592\",\"beginTime\": {\"hour\": 9, \"minute\": 0},\"endTime\": {\"hour\": 18, \"minute\": 0},\"recordMode\": \" \"}]";
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(dontExistConn);		
		model = new EmpModelUpdate(incomingDataMissingFieldCpf());
	}	
	
	
	protected String incomingDataMissingFieldCpf() {
		return "[{\"codOwner\": 8,\"codEmployee\": 56,\"password\": \"eer2\",\"name\": \"Ricardo Dummy #3\",\"codPosition\": 2,\"codGender\": 2,\"bornDate\": {\"year\": 1984, \"month\": 8, \"day\": 16},\"email\": \"dummy03@dummy.com\",\"address1\": \"Rua Dummy\",\"address2\": \"Ap 100\",\"postalcode\": 20735060,\"city\": \"Rio de Janeiro\",\"country\": \"BR\",\"state\": \"RJ\",\"phone\": \"2125922592\",\"beginTime\": {\"hour\": 9, \"minute\": 0},\"endTime\": {\"hour\": 18, \"minute\": 0},\"recordMode\": \" \"}]";
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(dontExistConn);		
		model = new EmpModelUpdate(incomingDataInvalidCpf());
	}	
	
	
	protected String incomingDataInvalidCpf() {
		return "[{\"codOwner\": 8,\"codEmployee\": 56,\"cpf\": \"15717068600\",\"password\": \"eer2\",\"name\": \"Ricardo Dummy #3\",\"codPosition\": 2,\"codGender\": 2,\"bornDate\": {\"year\": 1984, \"month\": 8, \"day\": 16},\"email\": \"dummy03@dummy.com\",\"address1\": \"Rua Dummy\",\"address2\": \"Ap 100\",\"postalcode\": 20735060,\"city\": \"Rio de Janeiro\",\"country\": \"BR\",\"state\": \"RJ\",\"phone\": \"2125922592\",\"beginTime\": {\"hour\": 9, \"minute\": 0},\"endTime\": {\"hour\": 18, \"minute\": 0},\"recordMode\": \" \"}]";
	}
	
	
	
	@Test
	public void invalidConnection() {
		initializeinvalidConnection();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
		
		String responseBody = "{\"selectCode\":500,\"selectMessage\":\"Ops... something went wrong\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeinvalidConnection() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidConn);
		model = new EmpModelUpdate(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void invalidCodOwner() {
		initializeInvalidCodOwner();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1251,\"selectMessage\":\"Owner data not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidCodOwner() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidOwnerConn);
		model = new EmpModelUpdate(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void invalidCodGender() {
		initializeInvalidCodGender();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1169,\"selectMessage\":\"Gender not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidCodGender() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidGenderConn);
		model = new EmpModelUpdate(incomingDataOrdinaryUsage());
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(updateConn);
		model = new EmpModelUpdate(null);
	}
	
	
	
	@Test
	public void recordDontExist() {
		initializeRecordDontExist();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1051,\"selectMessage\":\"Employee's data not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeRecordDontExist() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(dontExistConn);
		model = new EmpModelUpdate(incomingDataOrdinaryUsage());
	}
}
