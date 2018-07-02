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
	@Mock private Connection updateCpfConn;
	@Mock private Connection updateCpfAlreadyExistConn;
	@Mock private Connection empDontExistConn;
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement updateStmt;
	@Mock private PreparedStatement updateCpfStmt;
	@Mock private PreparedStatement updateCpfAlreadyExistStmt;
	@Mock private PreparedStatement empDontExistStmt;
	@Mock private PreparedStatement invalidStmt;
	@Mock private ResultSet updateRs;
	@Mock private ResultSet updateCpfRs;
	@Mock private ResultSet updateCpfAlreadyExitRs;
	@Mock private ResultSet empDontExistRs;
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioUpdate();
		initializeScenarioUpdateCpf();
		initializeScenarioUpdateCpfAlreadyExist();
		initializeScenarioEmpDontExist();
		initializeScenarioInvalidConnection();
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
		when(updateRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)
							 .thenReturn(true).thenReturn(true).thenReturn(false)
							 .thenReturn(true).thenReturn(true).thenReturn(false);
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
		when(updateCpfRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)
								.thenReturn(true).thenReturn(false)
								.thenReturn(true).thenReturn(false)
								.thenReturn(true).thenReturn(true).thenReturn(false);
		when(updateCpfRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateCpfRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateCpfRs.getString(any(String.class))).thenReturn(" ");
		when(updateCpfRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
	}
	
	
	
	private void initializeScenarioUpdateCpfAlreadyExist() throws SQLException {
		updateCpfAlreadyExistStmt = mock(PreparedStatement.class);
		updateCpfAlreadyExitRs = mock(ResultSet.class);
		updateCpfAlreadyExistConn = mock(Connection.class);
		
		when(updateCpfAlreadyExistConn.prepareStatement(any(String.class))).thenReturn(updateCpfAlreadyExistStmt);	
		doNothing().when(updateCpfAlreadyExistStmt).setString(anyInt(), anyString());
		doNothing().when(updateCpfAlreadyExistStmt).setLong(anyInt(), anyLong());
		doNothing().when(updateCpfAlreadyExistStmt).setTime(anyInt(), any(Time.class));		
		
		when(updateCpfAlreadyExistStmt.executeUpdate()).thenReturn(1);		

		when(updateCpfAlreadyExistStmt.executeQuery()).thenReturn(updateCpfAlreadyExitRs);
		when(updateCpfAlreadyExitRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)
										   .thenReturn(true).thenReturn(false)
		                                   .thenReturn(true).thenReturn(true).thenReturn(false)
		                                   .thenReturn(true).thenReturn(true).thenReturn(false);
		when(updateCpfAlreadyExitRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateCpfAlreadyExitRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateCpfAlreadyExitRs.getString(any(String.class))).thenReturn(" ");
		when(updateCpfAlreadyExitRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
	}
	
	
	
	private void initializeScenarioEmpDontExist() throws SQLException {
		empDontExistStmt = mock(PreparedStatement.class);
		empDontExistRs = mock(ResultSet.class);		
		empDontExistConn = mock(Connection.class);
		
		when(empDontExistConn.prepareStatement(any(String.class))).thenReturn(empDontExistStmt);

		when(empDontExistStmt.executeQuery()).thenReturn(empDontExistRs);
		when(empDontExistRs.next()).thenReturn(false);
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
	public void updateEmp() {
		initializeUpdateEmp();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codEmployee\":1,\"stores\":[],\"cpf\":\" \",\"name\":\" \",\"codGender\":1,\"txtGender\":\" \",\"email\":\" \",\"address1\":\" \",\"address2\":\" \",\"postalCode\":1,\"city\":\" \",\"codCountry\":\" \",\"txtCountry\":\" \",\"stateProvince\":\" \",\"phone\":\" \",\"beginTime\":{\"hour\":11,\"minute\":22},\"endTime\":{\"hour\":11,\"minute\":22},\"codPosition\":1,\"txtPosition\":\" \",\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeUpdateEmp() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(updateConn);
		model = new EmpModelUpdate(incomingDataUpdateEmp());
	}
	
	
	
	@Test
	public void updateEmpCpf() {
		initializeEmpUpdateCpf();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codEmployee\":1,\"stores\":[],\"cpf\":\" \",\"name\":\" \",\"codGender\":1,\"txtGender\":\" \",\"email\":\" \",\"address1\":\" \",\"address2\":\" \",\"postalCode\":1,\"city\":\" \",\"codCountry\":\" \",\"txtCountry\":\" \",\"stateProvince\":\" \",\"phone\":\" \",\"beginTime\":{\"hour\":11,\"minute\":22},\"endTime\":{\"hour\":11,\"minute\":22},\"codPosition\":1,\"txtPosition\":\" \",\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeEmpUpdateCpf() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(updateCpfConn);
		model = new EmpModelUpdate(incomingDataUpdateEmp());
	}
	
	
	
	protected String incomingDataUpdateEmp() {
		return "[{\"codOwner\": 8,\"codEmployee\": 56,\"cpf\": \"15717068603\",\"password\": \"eer2\",\"name\": \"Ricardo Dummy #3\",\"codPosition\": 2,\"codGender\": 2,\"bornDate\": {\"year\": 1984, \"month\": 8, \"day\": 16},\"email\": \"dummy03@dummy.com\",\"address1\": \"Rua Dummy\",\"address2\": \"Ap 100\",\"postalcode\": 20735060,\"city\": \"Rio de Janeiro\",\"country\": \"BR\",\"state\": \"RJ\",\"phone\": \"2125922592\",\"beginTime\": {\"hour\": 9, \"minute\": 0},\"endTime\": {\"hour\": 18, \"minute\": 0},\"recordMode\": \" \"}]";
	}
	
	
	
	@Test
	public void updateCpfAlreadyExist() {
		initializeUpdateCpfAlreadyExist();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1055,\"selectMessage\":\"Employee's CPF already exist on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeUpdateCpfAlreadyExist() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(updateCpfAlreadyExistConn);
		model = new EmpModelUpdate(incomingDataUpdateEmp());
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(empDontExistConn);		
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(empDontExistConn);		
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(empDontExistConn);		
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(empDontExistConn);		
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(empDontExistConn);		
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
		model = new EmpModelUpdate(incomingDataUpdateEmp());
	}
}
