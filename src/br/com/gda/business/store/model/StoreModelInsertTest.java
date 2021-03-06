package br.com.gda.business.store.model;

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
public class StoreModelInsertTest {
	@Mock private Connection insertConn;
	@Mock private PreparedStatement insertStmt;
	@Mock private ResultSet insertRs;
	
	@Mock private Connection cnpjAlreadyExistConn;
	@Mock private PreparedStatement cnpjAlreadyExistStmt;
	@Mock private ResultSet cnpjAlreadyExistRs;
	
	@Mock private Connection invalidTimezoneConn;
	@Mock private PreparedStatement invalidTimezoneStmt;
	@Mock private ResultSet invalidTimezoneRs;
	
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
		initializeScenarioInvalidTimezone();
		initializeScenarioCnpj();
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
		                     .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Timezone
		                     .thenReturn(true).thenReturn(false)					// Check CNPJ Exist
		                     .thenReturn(true)										// Insert
		                     .thenReturn(true).thenReturn(true).thenReturn(false);
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
	
	
	
	private void initializeScenarioCnpj() throws SQLException {
		cnpjAlreadyExistConn = mock(Connection.class);
		cnpjAlreadyExistStmt = mock(PreparedStatement.class);
		cnpjAlreadyExistRs = mock(ResultSet.class);		
		
		when(cnpjAlreadyExistConn.prepareStatement(any(String.class))).thenReturn(cnpjAlreadyExistStmt);
		when(cnpjAlreadyExistStmt.executeUpdate()).thenReturn(1);
		
		
		when(cnpjAlreadyExistStmt.executeQuery()).thenReturn(cnpjAlreadyExistRs);
		when(cnpjAlreadyExistRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		// Check Owner
								       .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Timezone
								       .thenReturn(true).thenReturn(true).thenReturn(false);	// Check CNPJ Exist
		when(cnpjAlreadyExistRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(cnpjAlreadyExistRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(cnpjAlreadyExistRs.getString(any(String.class))).thenReturn(" ");
		when(cnpjAlreadyExistRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));	
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
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"cnpj\":\" \",\"inscrMun\":\" \",\"inscrEst\":\" \",\"razaoSocial\":\" \",\"name\":\" \",\"address1\":\" \",\"address2\":\" \",\"postalCode\":1,\"city\":\" \",\"codCountry\":\" \",\"txtCountry\":\" \",\"stateProvince\":\" \",\"phone\":\" \",\"codCurr\":\" \",\"codPayment\":\" \",\"latitude\":0.0,\"longitude\":0.0,\"codTimezone\":\" \",\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInsertNewRecord() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);
		model = new StoreModelInsert(incomingDataOrdinaryUsage());
	}
	

		
	@Test
	public void cnpjAlreadyExist() {
		initializeCnpjAlreadyExist();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1100,\"selectMessage\":\"Store's CNPJ already exist on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeCnpjAlreadyExist() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(cnpjAlreadyExistConn);
		model = new StoreModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	protected String incomingDataOrdinaryUsage() {
		return "[{\"codOwner\": \"8\",\"cnpj\": \"36258584000147\",\"inscEstadual\": \"83739029\",\"inscMunicipal\": \"873787620999\",\"razaoSocial\": \"Loja ABC LTDA\",\"name\": \"ABC Coiffeur - Filial B\",\"address1\": \"Rua 123 de Oliveira 4, Centro\",\"address2\": \"Loja A\",\"postalcode\": \"20735060\",\"city\": \"Rio de Janeiro\",\"state\": \"RJ\",\"country\": \"BR\",\"phone\": \"21-2592-2592\",\"codCurr\": \"BRL\",\"codPayment\": \"MPA-BF17622CF63C\",\"codTimezone\": \"America/Sao_Paulo\"}]";
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
		model = new StoreModelInsert(incomingDataMissingFieldName());
	}	
	
	
	
	protected String incomingDataMissingFieldName() {
		return "[   {	\"codOwner\": \"8\",	\"cnpj\": \"02357537000139\", 	\"inscEstadual\": \"83739029\", 	\"inscMunicipal\": \"873787620999\", 	\"razaoSocial\": \"Loja DEF LTDA\",	\"address1\": \"Rua 123 de Oliveira 4, Centro\",	\"address2\": \"Loja A\",	\"postalcode\": \"20735060\",	\"city\": \"Rio de Janeiro\",	\"state\": \"RJ\",	\"country\": \"BR\",	\"phone\": \"21-2592-2592\",	\"codCurr\": \"BRL\",	\"codPayment\": \"MPA-BF17622CF63C\"   }]";
	}
	
	
	
	@Test
	public void missingFieldCodTimezone() {
		initializeMissingFieldCodTimezone();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	protected void initializeMissingFieldCodTimezone() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);		
		model = new StoreModelInsert(incomingDataMissingFieldCodTimezone());
	}	
	
	
	
	protected String incomingDataMissingFieldCodTimezone() {
		return "[{\"codOwner\": \"8\",\"cnpj\": \"36258584000147\",\"inscEstadual\": \"83739029\",\"inscMunicipal\": \"873787620999\",\"razaoSocial\": \"Loja ABC LTDA\",\"name\": \"ABC Coiffeur - Filial B\",\"address1\": \"Rua 123 de Oliveira 4, Centro\",\"address2\": \"Loja A\",\"postalcode\": \"20735060\",\"city\": \"Rio de Janeiro\",\"state\": \"RJ\",\"country\": \"BR\",\"phone\": \"21-2592-2592\",\"codCurr\": \"BRL\",\"codPayment\": \"MPA-BF17622CF63C\"}]";
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);		
		model = new StoreModelInsert(incomingDataMissingFieldName());
	}	
	
	
	
	protected String incomingDataMissingFieldCnpj() {
		return "[   {	\"codOwner\": \"8\", 	\"inscEstadual\": \"83739029\", 	\"inscMunicipal\": \"873787620999\", 	\"razaoSocial\": \"Loja DEF LTDA\",	\"name\": \"ABC Coiffeur - Filial B\",	\"address1\": \"Rua 123 de Oliveira 4, Centro\",	\"address2\": \"Loja A\",	\"postalcode\": \"20735060\",	\"city\": \"Rio de Janeiro\",	\"state\": \"RJ\",	\"country\": \"BR\",	\"phone\": \"21-2592-2592\",	\"codCurr\": \"BRL\",	\"codPayment\": \"MPA-BF17622CF63C\"   }]";
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
		model = new StoreModelInsert(incomingDataMissingFieldName());
	}	
	
	
	
	protected String incomingDataMissingFieldcodOwner() {
		return "[   {	\"cnpj\": \"02357537000139\", 	\"inscEstadual\": \"83739029\", 	\"inscMunicipal\": \"873787620999\", 	\"razaoSocial\": \"Loja DEF LTDA\",	\"name\": \"ABC Coiffeur - Filial B\",	\"address1\": \"Rua 123 de Oliveira 4, Centro\",	\"address2\": \"Loja A\",	\"postalcode\": \"20735060\",	\"city\": \"Rio de Janeiro\",	\"state\": \"RJ\",	\"country\": \"BR\",	\"phone\": \"21-2592-2592\",	\"codCurr\": \"BRL\",	\"codPayment\": \"MPA-BF17622CF63C\"   }]";
	}
	
	
	
	@Test
	public void keyFieldCodStore() {
		initializeKeyFieldCodStore();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1057,\"selectMessage\":\"Auto generated fields should not be passed\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	protected void initializeKeyFieldCodStore() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);		
		model = new StoreModelInsert(incomingDataKeyFieldCodEmployee());
	}	
	
	
	
	protected String incomingDataKeyFieldCodEmployee() {
		return "[{\"codOwner\": \"8\",	\"codStore\": \"1\",\"cnpj\": \"36258584000147\",\"inscEstadual\": \"83739029\",\"inscMunicipal\": \"873787620999\",\"razaoSocial\": \"Loja ABC LTDA\",\"name\": \"ABC Coiffeur - Filial B\",\"address1\": \"Rua 123 de Oliveira 4, Centro\",\"address2\": \"Loja A\",\"postalcode\": \"20735060\",\"city\": \"Rio de Janeiro\",\"state\": \"RJ\",\"country\": \"BR\",\"phone\": \"21-2592-2592\",\"codCurr\": \"BRL\",\"codPayment\": \"MPA-BF17622CF63C\",\"codTimezone\": \"America/Sao_Paulo\"}]";
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);		
		model = new StoreModelInsert(incomingDataInvalidCnpj());
	}	
	
	
	
	protected String incomingDataInvalidCnpj() {
		return "[{\"codOwner\": \"8\",\"cnpj\": \"36258584000140\",\"inscEstadual\": \"83739029\",\"inscMunicipal\": \"873787620999\",\"razaoSocial\": \"Loja ABC LTDA\",\"name\": \"ABC Coiffeur - Filial B\",\"address1\": \"Rua 123 de Oliveira 4, Centro\",\"address2\": \"Loja A\",\"postalcode\": \"20735060\",\"city\": \"Rio de Janeiro\",\"state\": \"RJ\",\"country\": \"BR\",\"phone\": \"21-2592-2592\",\"codCurr\": \"BRL\",\"codPayment\": \"MPA-BF17622CF63C\",\"codTimezone\": \"America/Sao_Paulo\"}]";
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
		model = new StoreModelInsert(incomingDataOrdinaryUsage());
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
		model = new StoreModelInsert(incomingDataOrdinaryUsage());
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
		model = new StoreModelInsert(incomingDataOrdinaryUsage());
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
		model = new StoreModelInsert(null);
	}	
}
