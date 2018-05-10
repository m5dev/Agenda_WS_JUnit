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
	@Mock private Connection updateConstraintConn;
	@Mock private Connection invalidConstraintConn;
	@Mock private Connection empDontExistConn;
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement updateStmt;
	@Mock private PreparedStatement updateConstraintStmt;
	@Mock private PreparedStatement invalidConstraintStmt;
	@Mock private PreparedStatement empDontExistStmt;
	@Mock private PreparedStatement invalidStmt;
	@Mock private ResultSet updateRs;
	@Mock private ResultSet updateConstraintRs;
	@Mock private ResultSet invalidConstraintRs;
	@Mock private ResultSet empDontExistRs;
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioUpdate();
		initializeScenarioUpdateConstraint();
		initializeScenarioInvalidConstraint();
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
		when(updateRs.next()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
		when(updateRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateRs.getString(any(String.class))).thenReturn(" ");
		when(updateRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
	}
	
	
	
	private void initializeScenarioUpdateConstraint() throws SQLException {
		updateConstraintStmt = mock(PreparedStatement.class);
		updateConstraintRs = mock(ResultSet.class);
		updateConstraintConn = mock(Connection.class);
		
		when(updateConstraintConn.prepareStatement(any(String.class))).thenReturn(updateConstraintStmt);	
		doNothing().when(updateConstraintStmt).setString(anyInt(), anyString());
		doNothing().when(updateConstraintStmt).setLong(anyInt(), anyLong());
		doNothing().when(updateConstraintStmt).setTime(anyInt(), any(Time.class));		
		
		when(updateConstraintStmt.executeUpdate()).thenReturn(1);		

		when(updateConstraintStmt.executeQuery()).thenReturn(updateConstraintRs);
		when(updateConstraintRs.next()).thenReturn(true).thenReturn(false).thenReturn(false).thenReturn(false).thenReturn(true).thenReturn(false);
		when(updateConstraintRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateConstraintRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateConstraintRs.getString(any(String.class))).thenReturn(" ");
		when(updateConstraintRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
	}
	
	
	
	private void initializeScenarioInvalidConstraint() throws SQLException {
		invalidConstraintStmt = mock(PreparedStatement.class);
		invalidConstraintRs = mock(ResultSet.class);
		invalidConstraintConn = mock(Connection.class);
		
		when(invalidConstraintConn.prepareStatement(any(String.class))).thenReturn(invalidConstraintStmt);	
		doNothing().when(invalidConstraintStmt).setString(anyInt(), anyString());
		doNothing().when(invalidConstraintStmt).setLong(anyInt(), anyLong());
		doNothing().when(invalidConstraintStmt).setTime(anyInt(), any(Time.class));		
		
		when(invalidConstraintStmt.executeUpdate()).thenReturn(1);		

		when(invalidConstraintStmt.executeQuery()).thenReturn(invalidConstraintRs);
		when(invalidConstraintRs.next()).thenReturn(true).thenReturn(false).thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
		when(invalidConstraintRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidConstraintRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidConstraintRs.getString(any(String.class))).thenReturn(" ");
		when(invalidConstraintRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
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
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codEmployee\":1,\"stores\":[],\"cpf\":\" \",\"name\":\" \",\"codGender\":1,\"txtGender\":\" \",\"email\":\" \",\"address1\":\" \",\"address2\":\" \",\"postalCode\":1,\"city\":\" \",\"codCountry\":\" \",\"txtCountry\":\" \",\"stateProvince\":\" \",\"phone\":\" \",\"beginTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"endTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"codPosition\":1,\"txtPosition\":\" \",\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeUpdateEmp() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(updateConn);
		model = new EmpModelUpdate(incomingDataUpdateEmp());
	}
	
	
	
	@Test
	public void updateEmpConstraintCheck() {
		initializeUpdateEmpConstraintCheck();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codEmployee\":1,\"stores\":[],\"cpf\":\" \",\"name\":\" \",\"codGender\":1,\"txtGender\":\" \",\"email\":\" \",\"address1\":\" \",\"address2\":\" \",\"postalCode\":1,\"city\":\" \",\"codCountry\":\" \",\"txtCountry\":\" \",\"stateProvince\":\" \",\"phone\":\" \",\"beginTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"endTime\":{\"hour\":11,\"minute\":22,\"second\":33,\"nano\":0},\"codPosition\":1,\"txtPosition\":\" \",\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeUpdateEmpConstraintCheck() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(updateConstraintConn);
		model = new EmpModelUpdate(incomingDataUpdateEmp());
	}
	
	
	
	protected String incomingDataUpdateEmp() {
		return "[{\"codOwner\": 8,\"codEmployee\": 56,\"cpf\": \"15717068603\",\"password\": \"eer2\",\"name\": \"Ricardo Dummy #3\",\"codPosition\": 2,\"codGender\": 2,\"bornDate\": {\"year\": 1984, \"month\": 8, \"day\": 16},\"email\": \"dummy03@dummy.com\",\"address1\": \"Rua Dummy\",\"address2\": \"Ap 100\",\"postalcode\": 20735060,\"city\": \"Rio de Janeiro\",\"country\": \"BR\",\"state\": \"RJ\",\"phone\": \"2125922592\",\"beginTime\": {\"hour\": 9, \"minute\": 0},\"endTime\": {\"hour\": 18, \"minute\": 0},\"recordMode\": \" \"}]";
	}
	
	
	
	@Test
	public void invalidConstraintCheck() {
		initializeInvalidConstraintCheck();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1055,\"selectMessage\":\"Employee's CPF already exist on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidConstraintCheck() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidConstraintConn);
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
