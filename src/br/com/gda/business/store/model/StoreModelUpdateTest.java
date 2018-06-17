package br.com.gda.business.store.model;

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

import br.com.gda.common.DbConnection;
import br.com.gda.model.Model;

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class StoreModelUpdateTest {
	@Mock private Connection updateConn;
	@Mock private PreparedStatement updateStmt;
	@Mock private ResultSet updateRs;
	
	@Mock private Connection updateCnpjConn;
	@Mock private PreparedStatement updateCnpjStmt;
	@Mock private ResultSet updateCnpjRs;
	
	@Mock private Connection updateCnpjAlreadyTakenConn;
	@Mock private PreparedStatement updateCnpjAlreadyTakenStmt;
	@Mock private ResultSet updateCnpjAlreadyTakenRs;
	
	@Mock private Connection storeDontExistConn;
	@Mock private PreparedStatement storeDontExistStmt;
	@Mock private ResultSet storeDontExistRs;
	
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;
	
	@Mock private Connection invalidTimezoneConn;
	@Mock private PreparedStatement invalidTimezoneStmt;
	@Mock private ResultSet invalidTimezoneRs;
	
	@Mock private Connection invalidOwnerConn;
	@Mock private PreparedStatement invalidOwnerStmt;
	@Mock private ResultSet invalidOwnerRs;
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioUpdate();
		initializeScenarioUpdateConstraint();
		initializeScenarioInvalidConstraint();
		initializeScenarioStoreDontExist();
		initializeScenarioInvalidConnection();
		initializeScenarioInvalidOwner();
		initializeScenarioInvalidTimezone();
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
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Timezone
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Exist
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// Check CNPJ is the same
		                     														// Update
		                     .thenReturn(true).thenReturn(true).thenReturn(false);	// Select
		when(updateRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateRs.getString(any(String.class))).thenReturn(" ");
		when(updateRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
	}
	
	
	
	private void initializeScenarioUpdateConstraint() throws SQLException {
		updateCnpjStmt = mock(PreparedStatement.class);
		updateCnpjRs = mock(ResultSet.class);
		updateCnpjConn = mock(Connection.class);
		
		when(updateCnpjConn.prepareStatement(any(String.class))).thenReturn(updateCnpjStmt);	
		doNothing().when(updateCnpjStmt).setString(anyInt(), anyString());
		doNothing().when(updateCnpjStmt).setLong(anyInt(), anyLong());
		doNothing().when(updateCnpjStmt).setTime(anyInt(), any(Time.class));		
		
		when(updateCnpjStmt.executeUpdate()).thenReturn(1);		

		when(updateCnpjStmt.executeQuery()).thenReturn(updateCnpjRs);
		when(updateCnpjRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
						         .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Timezone
						         .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Exist
						         .thenReturn(true).thenReturn(false)					// Check CNPJ is the same
						         .thenReturn(true).thenReturn(false)					// Check CNPJ is already taken
						        														// Update
						         .thenReturn(true).thenReturn(true).thenReturn(false);	// Select
		when(updateCnpjRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateCnpjRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateCnpjRs.getString(any(String.class))).thenReturn(" ");
		when(updateCnpjRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
	}
	
	
	
	private void initializeScenarioInvalidConstraint() throws SQLException {
		updateCnpjAlreadyTakenStmt = mock(PreparedStatement.class);
		updateCnpjAlreadyTakenRs = mock(ResultSet.class);
		updateCnpjAlreadyTakenConn = mock(Connection.class);
		
		when(updateCnpjAlreadyTakenConn.prepareStatement(any(String.class))).thenReturn(updateCnpjAlreadyTakenStmt);	
		doNothing().when(updateCnpjAlreadyTakenStmt).setString(anyInt(), anyString());
		doNothing().when(updateCnpjAlreadyTakenStmt).setLong(anyInt(), anyLong());
		doNothing().when(updateCnpjAlreadyTakenStmt).setTime(anyInt(), any(Time.class));		
		
		when(updateCnpjAlreadyTakenStmt.executeUpdate()).thenReturn(1);		

		when(updateCnpjAlreadyTakenStmt.executeQuery()).thenReturn(updateCnpjAlreadyTakenRs);
		when(updateCnpjAlreadyTakenRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
									         .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Timezone
									         .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Exist
									         .thenReturn(true).thenReturn(false)					// Check CNPJ is the same
									         .thenReturn(true).thenReturn(true).thenReturn(false);	// Check CNPJ is already taken
		when(updateCnpjAlreadyTakenRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateCnpjAlreadyTakenRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateCnpjAlreadyTakenRs.getString(any(String.class))).thenReturn(" ");
		when(updateCnpjAlreadyTakenRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));
	}
	
	
	
	private void initializeScenarioStoreDontExist() throws SQLException {
		storeDontExistStmt = mock(PreparedStatement.class);
		storeDontExistRs = mock(ResultSet.class);		
		storeDontExistConn = mock(Connection.class);
		
		when(storeDontExistConn.prepareStatement(any(String.class))).thenReturn(storeDontExistStmt);

		when(storeDontExistStmt.executeQuery()).thenReturn(storeDontExistRs);
		when(storeDontExistRs.next()).thenReturn(false);
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
	
	
	
	private void initializeScenarioInvalidTimezone() throws SQLException {
		invalidTimezoneConn = mock(Connection.class);
		invalidTimezoneStmt = mock(PreparedStatement.class);
		invalidTimezoneRs = mock(ResultSet.class);
		
		when(invalidTimezoneConn.prepareStatement(any(String.class))).thenReturn(invalidTimezoneStmt);
		when(invalidTimezoneStmt.executeUpdate()).thenReturn(1);
		
		when(invalidTimezoneStmt.executeQuery()).thenReturn(invalidTimezoneRs);
		when(invalidTimezoneRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
		                      		  .thenReturn(true).thenReturn(false);					// Check Timezone

		when(invalidTimezoneRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidTimezoneRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidTimezoneRs.getString(any(String.class))).thenReturn(" ");
		when(invalidTimezoneRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	

	@Test
	public void updateRecord() {
		initializeUpdateRecord();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"cnpj\":\" \",\"inscrMun\":\" \",\"inscrEst\":\" \",\"razaoSocial\":\" \",\"name\":\" \",\"address1\":\" \",\"address2\":\" \",\"postalCode\":1,\"city\":\" \",\"codCountry\":\" \",\"txtCountry\":\" \",\"stateProvince\":\" \",\"phone\":\" \",\"codCurr\":\" \",\"codPayment\":\" \",\"latitude\":0.0,\"longitude\":0.0,\"codTimezone\":\" \",\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeUpdateRecord() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(updateConn);
		model = new StoreModelUpdate(incomingDataOrdinaryUsage());
	}
	
	
	
	
	@Test
	public void updateCnpj() {
		initializeUpdateCnpj();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"cnpj\":\" \",\"inscrMun\":\" \",\"inscrEst\":\" \",\"razaoSocial\":\" \",\"name\":\" \",\"address1\":\" \",\"address2\":\" \",\"postalCode\":1,\"city\":\" \",\"codCountry\":\" \",\"txtCountry\":\" \",\"stateProvince\":\" \",\"phone\":\" \",\"codCurr\":\" \",\"codPayment\":\" \",\"latitude\":0.0,\"longitude\":0.0,\"codTimezone\":\" \",\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeUpdateCnpj() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(updateCnpjConn);
		model = new StoreModelUpdate(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void updateCnpjAlreadyTaken() {
		initializeUpdateCnpjAlreadyTaken();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1100,\"selectMessage\":\"Store's CNPJ already exist on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeUpdateCnpjAlreadyTaken() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(updateCnpjAlreadyTakenConn);
		model = new StoreModelUpdate(incomingDataOrdinaryUsage());
	}
	
	
	
	protected String incomingDataOrdinaryUsage() {
		return "[   {\"codOwner\": 8,\"codStore\": 15,\"cnpj\": \"58371636000120\",\"inscrMun\": \"873787620999\",\"inscrEst\": \"83739029\",\"razaoSocial\": \"Loja ABC LTDA\",\"name\": \"ABC Coiffeur - Filial B\",\"address1\": \"Rua 123 de Oliveira 4, Centro\",\"address2\": \"Loja A\",\"postalCode\": 20735060,\"city\": \"Rio de Janeiro\",\"codCountry\": \"BR\",\"txtCountry\": \"Brasil\",\"stateProvince\": \"RJ\",\"phone\": \"21-2592-2592\",\"codCurr\": \"BRL\",\"codPayment\": \"MPA-BF17622CF63C\",\"latitude\": 0,\"longitude\": 0,\"codLanguage\": \"PT\",\"codTimezone\": \"America/Sao_Paulo\"   }]";
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(storeDontExistConn);		
		model = new StoreModelUpdate(incomingDataMissingFieldCodOwner());
	}	
	
	
	
	protected String incomingDataMissingFieldCodOwner() {
		return "[   {\"codStore\": 15,\"cnpj\": \"58371636000120\",\"inscrMun\": \"873787620999\",\"inscrEst\": \"83739029\",\"razaoSocial\": \"Loja ABC LTDA\",\"name\": \"ABC Coiffeur - Filial B\",\"address1\": \"Rua 123 de Oliveira 4, Centro\",\"address2\": \"Loja A\",\"postalCode\": 20735060,\"city\": \"Rio de Janeiro\",\"codCountry\": \"BR\",\"txtCountry\": \"Brasil\",\"stateProvince\": \"RJ\",\"phone\": \"21-2592-2592\",\"codCurr\": \"BRL\",\"codPayment\": \"MPA-BF17622CF63C\",\"latitude\": 0,\"longitude\": 0,\"codLanguage\": \"PT\",\"codTimezone\": \"America/Sao_Paulo\"   }]";
	}
	
	
	
	@Test
	public void missingFieldCodStore() {
		initializeMissingFieldCodStore();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":6,\"selectMessage\":\"Key field should not be null\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	protected void initializeMissingFieldCodStore() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(storeDontExistConn);		
		model = new StoreModelUpdate(incomingDataMissingFieldCodStore());
	}	
	
	
	
	protected String incomingDataMissingFieldCodStore() {
		return "[   {\"codOwner\": 8,\"cnpj\": \"58371636000120\",\"inscrMun\": \"873787620999\",\"inscrEst\": \"83739029\",\"razaoSocial\": \"Loja ABC LTDA\",\"name\": \"ABC Coiffeur - Filial B\",\"address1\": \"Rua 123 de Oliveira 4, Centro\",\"address2\": \"Loja A\",\"postalCode\": 20735060,\"city\": \"Rio de Janeiro\",\"codCountry\": \"BR\",\"txtCountry\": \"Brasil\",\"stateProvince\": \"RJ\",\"phone\": \"21-2592-2592\",\"codCurr\": \"BRL\",\"codPayment\": \"MPA-BF17622CF63C\",\"latitude\": 0,\"longitude\": 0,\"codLanguage\": \"PT\",\"codTimezone\": \"America/Sao_Paulo\"   }]";
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(storeDontExistConn);		
		model = new StoreModelUpdate(incomingDataMissingFieldName());
	}	
	
	
	
	protected String incomingDataMissingFieldName() {
		return "[   {	\"codOwner\": \"8\",	\"codStore\": \"16\",	\"cnpj\": \"02357537000139\", 	\"inscEstadual\": \"83739029\", 	\"inscMunicipal\": \"873787620999\", 	\"razaoSocial\": \"Loja DEF LTDA\",	\"address1\": \"Rua 123 de Oliveira 4, Centro\",	\"address2\": \"Loja A\",	\"postalcode\": \"20735060\",	\"city\": \"Rio de Janeiro\",	\"state\": \"RJ\",	\"country\": \"BR\",	\"phone\": \"21-2592-2592\",	\"codCurr\": \"BRL\",	\"codPayment\": \"MPA-BF17622CF63C\"   }]";
	}
	
	
	
	@Test
	public void missingFieldCnpj() {
		initializeMissingFieldCnpj();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	protected void initializeMissingFieldCnpj() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(storeDontExistConn);		
		model = new StoreModelUpdate(incomingDataMissingFieldCnpj());
	}	
	
	
	protected String incomingDataMissingFieldCnpj() {
		return "[   {	\"codOwner\": \"8\",	\"codStore\": \"16\", 	\"inscEstadual\": \"83739029\", 	\"inscMunicipal\": \"873787620999\", 	\"razaoSocial\": \"Loja DEF LTDA\",	\"name\": \"ABC Coiffeur - Filial B\",	\"address1\": \"Rua 123 de Oliveira 4, Centro\",	\"address2\": \"Loja A\",	\"postalcode\": \"20735060\",	\"city\": \"Rio de Janeiro\",	\"state\": \"RJ\",	\"country\": \"BR\",	\"phone\": \"21-2592-2592\",	\"codCurr\": \"BRL\",	\"codPayment\": \"MPA-BF17622CF63C\"   }]";
	}
	
	
	
	@Test
	public void invalidCnpj() {
		initializeInvalidCnpj();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":4,\"selectMessage\":\"CNPJ is invalid\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	protected void initializeInvalidCnpj() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(storeDontExistConn);		
		model = new StoreModelUpdate(incomingDataInvalidCnpj());
	}	
	
	
	protected String incomingDataInvalidCnpj() {
		return "[   {\"codOwner\": 8,\"codStore\": 15,\"cnpj\": \"58371636000129\",\"inscrMun\": \"873787620999\",\"inscrEst\": \"83739029\",\"razaoSocial\": \"Loja ABC LTDA\",\"name\": \"ABC Coiffeur - Filial B\",\"address1\": \"Rua 123 de Oliveira 4, Centro\",\"address2\": \"Loja A\",\"postalCode\": 20735060,\"city\": \"Rio de Janeiro\",\"codCountry\": \"BR\",\"txtCountry\": \"Brasil\",\"stateProvince\": \"RJ\",\"phone\": \"21-2592-2592\",\"codCurr\": \"BRL\",\"codPayment\": \"MPA-BF17622CF63C\",\"latitude\": 0,\"longitude\": 0,\"codLanguage\": \"PT\",\"codTimezone\": \"America/Sao_Paulo\"   }]";
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
		model = new StoreModelUpdate(incomingDataOrdinaryUsage());
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
		model = new StoreModelUpdate(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void invalidFieldCodTimezone() {
		initializeInvalidFieldCodTimezone();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1167,\"selectMessage\":\"Timezone not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidFieldCodTimezone() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidTimezoneConn);
		model = new StoreModelUpdate(incomingDataOrdinaryUsage());
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
		model = new StoreModelUpdate(null);
	}	
}
