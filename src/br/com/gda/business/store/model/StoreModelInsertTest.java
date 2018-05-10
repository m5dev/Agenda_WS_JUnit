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
	@Mock private Connection cnpjAlreadyExistConn;
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement insertStmt;
	@Mock private PreparedStatement cnpjAlreadyExistStmt;
	@Mock private ResultSet insertRs;
	@Mock private ResultSet cnpjAlreadyExistRs;
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioInsert();
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
		when(insertRs.next()).thenReturn(false).thenReturn(true).thenReturn(false);
		when(insertRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(insertRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(insertRs.getString(any(String.class))).thenReturn(" ");
		when(insertRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioCnpj() throws SQLException {
		cnpjAlreadyExistConn = mock(Connection.class);
		cnpjAlreadyExistStmt = mock(PreparedStatement.class);
		cnpjAlreadyExistRs = mock(ResultSet.class);		
		
		when(cnpjAlreadyExistConn.prepareStatement(any(String.class))).thenReturn(cnpjAlreadyExistStmt);
		when(cnpjAlreadyExistStmt.executeUpdate()).thenReturn(1);
		
		
		when(cnpjAlreadyExistStmt.executeQuery()).thenReturn(cnpjAlreadyExistRs);
		when(cnpjAlreadyExistRs.next()).thenReturn(true).thenReturn(false);
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
	public void insertNewEmpStore() {
		initializeInsertNewStore();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"cnpj\":\" \",\"inscrMun\":\" \",\"inscrEst\":\" \",\"razaoSocial\":\" \",\"name\":\" \",\"address1\":\" \",\"address2\":\" \",\"postalCode\":1,\"city\":\" \",\"codCountry\":\" \",\"txtCountry\":\" \",\"stateProvince\":\" \",\"phone\":\" \",\"codCurr\":\" \",\"codPayment\":\" \",\"latitude\":0.0,\"longitude\":0.0,\"codLanguage\":\"PT\"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInsertNewStore() {
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
		return "[   {	\"codOwner\": \"8\",	\"cnpj\": \"02357537000139\", 	\"inscEstadual\": \"83739029\", 	\"inscMunicipal\": \"873787620999\", 	\"razaoSocial\": \"Loja DEF LTDA\",	\"name\": \"ABC Coiffeur - Filial B\",	\"address1\": \"Rua 123 de Oliveira 4, Centro\",	\"address2\": \"Loja A\",	\"postalcode\": \"20735060\",	\"city\": \"Rio de Janeiro\",	\"state\": \"RJ\",	\"country\": \"BR\",	\"phone\": \"21-2592-2592\",	\"codCurr\": \"BRL\",	\"codPayment\": \"MPA-BF17622CF63C\"   }]";
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
	public void missingFieldcodOwner() {
		initializeMissingFieldcodOwner();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	protected void initializeMissingFieldcodOwner() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);		
		model = new StoreModelInsert(incomingDataMissingFieldName());
	}	
	
	
	
	protected String incomingDataMissingFieldcodOwner() {
		return "[   {	\"cnpj\": \"02357537000139\", 	\"inscEstadual\": \"83739029\", 	\"inscMunicipal\": \"873787620999\", 	\"razaoSocial\": \"Loja DEF LTDA\",	\"name\": \"ABC Coiffeur - Filial B\",	\"address1\": \"Rua 123 de Oliveira 4, Centro\",	\"address2\": \"Loja A\",	\"postalcode\": \"20735060\",	\"city\": \"Rio de Janeiro\",	\"state\": \"RJ\",	\"country\": \"BR\",	\"phone\": \"21-2592-2592\",	\"codCurr\": \"BRL\",	\"codPayment\": \"MPA-BF17622CF63C\"   }]";
	}
	
	
	
	@Test
	public void passingFieldCodStore() {
		initializePassingFieldCodStore();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1057,\"selectMessage\":\"Auto generated fields should not be passed\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
	
		
	
	protected void initializePassingFieldCodStore() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertConn);		
		model = new StoreModelInsert(incomingDataPassingFieldCodEmployee());
	}	
	
	
	
	protected String incomingDataPassingFieldCodEmployee() {
		return "[   {	\"codOwner\": \"8\",	\"codStore\": \"1\",	\"cnpj\": \"02357537000139\", 	\"inscEstadual\": \"83739029\", 	\"inscMunicipal\": \"873787620999\", 	\"razaoSocial\": \"Loja DEF LTDA\",	\"name\": \"ABC Coiffeur - Filial B\",	\"address1\": \"Rua 123 de Oliveira 4, Centro\",	\"address2\": \"Loja A\",	\"postalcode\": \"20735060\",	\"city\": \"Rio de Janeiro\",	\"state\": \"RJ\",	\"country\": \"BR\",	\"phone\": \"21-2592-2592\",	\"codCurr\": \"BRL\",	\"codPayment\": \"MPA-BF17622CF63C\"   }]";
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
		return "[   {	\"codOwner\": \"8\",	\"cnpj\": \"02357537000130\", 	\"inscEstadual\": \"83739029\", 	\"inscMunicipal\": \"873787620999\", 	\"razaoSocial\": \"Loja DEF LTDA\",	\"name\": \"ABC Coiffeur - Filial B\",	\"address1\": \"Rua 123 de Oliveira 4, Centro\",	\"address2\": \"Loja A\",	\"postalcode\": \"20735060\",	\"city\": \"Rio de Janeiro\",	\"state\": \"RJ\",	\"country\": \"BR\",	\"phone\": \"21-2592-2592\",	\"codCurr\": \"BRL\",	\"codPayment\": \"MPA-BF17622CF63C\"   }]";
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
}
