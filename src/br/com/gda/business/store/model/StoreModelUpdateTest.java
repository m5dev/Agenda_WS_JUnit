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
	@Mock private Connection updateConstraintConn;
	@Mock private Connection invalidConstraintConn;
	@Mock private Connection storeDontExistConn;
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement updateStmt;
	@Mock private PreparedStatement updateConstraintStmt;
	@Mock private PreparedStatement invalidConstraintStmt;
	@Mock private PreparedStatement storeDontExistStmt;
	@Mock private PreparedStatement invalidStmt;
	@Mock private ResultSet updateRs;
	@Mock private ResultSet updateConstraintRs;
	@Mock private ResultSet invalidConstraintRs;
	@Mock private ResultSet storeDontExistRs;
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioUpdate();
		initializeScenarioUpdateConstraint();
		initializeScenarioInvalidConstraint();
		initializeScenarioStoreDontExist();
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
	
	

	@Test
	public void updateStore() {
		initializeUpdateStore();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"cnpj\":\" \",\"inscrMun\":\" \",\"inscrEst\":\" \",\"razaoSocial\":\" \",\"name\":\" \",\"address1\":\" \",\"address2\":\" \",\"postalCode\":1,\"city\":\" \",\"codCountry\":\" \",\"txtCountry\":\" \",\"stateProvince\":\" \",\"phone\":\" \",\"codCurr\":\" \",\"codPayment\":\" \",\"latitude\":0.0,\"longitude\":0.0,\"codLanguage\":\"PT\",\"recordMode\":\" \"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeUpdateStore() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(updateConn);
		model = new StoreModelUpdate(incomingDataUpdateStore());
	}
	
	
	
	
	@Test
	public void updateStoreConstraintCheck() {
		initializeUpdateStoreConstraintCheck();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"cnpj\":\" \",\"inscrMun\":\" \",\"inscrEst\":\" \",\"razaoSocial\":\" \",\"name\":\" \",\"address1\":\" \",\"address2\":\" \",\"postalCode\":1,\"city\":\" \",\"codCountry\":\" \",\"txtCountry\":\" \",\"stateProvince\":\" \",\"phone\":\" \",\"codCurr\":\" \",\"codPayment\":\" \",\"latitude\":0.0,\"longitude\":0.0,\"codLanguage\":\"PT\",\"recordMode\":\" \"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeUpdateStoreConstraintCheck() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(updateConstraintConn);
		model = new StoreModelUpdate(incomingDataUpdateStore());
	}
	
	
	
	@Test
	public void invalidConstraintCheck() {
		initializeInvalidConstraintCheck();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1100,\"selectMessage\":\"Store's CNPJ already exist on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidConstraintCheck() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidConstraintConn);
		model = new StoreModelUpdate(incomingDataUpdateStore());
	}
	
	
	
	protected String incomingDataUpdateStore() {
		return "[   {	\"codOwner\": \"8\",	\"codStore\": \"16\",	\"cnpj\": \"02357537000139\", 	\"inscEstadual\": \"83739029\", 	\"inscMunicipal\": \"873787620999\", 	\"razaoSocial\": \"Loja DEF LTDA\",	\"name\": \"ABC Coiffeur - Filial B\",	\"address1\": \"Rua 123 de Oliveira 4, Centro\",	\"address2\": \"Loja A\",	\"postalcode\": \"20735060\",	\"city\": \"Rio de Janeiro\",	\"state\": \"RJ\",	\"country\": \"BR\",	\"phone\": \"21-2592-2592\",	\"codCurr\": \"BRL\",	\"codPayment\": \"MPA-BF17622CF63C\"   }]";
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
		return "[   {\"codStore\": \"16\",	\"cnpj\": \"02357537000139\", 	\"inscEstadual\": \"83739029\", 	\"inscMunicipal\": \"873787620999\", 	\"razaoSocial\": \"Loja DEF LTDA\",	\"name\": \"ABC Coiffeur - Filial B\",	\"address1\": \"Rua 123 de Oliveira 4, Centro\",	\"address2\": \"Loja A\",	\"postalcode\": \"20735060\",	\"city\": \"Rio de Janeiro\",	\"state\": \"RJ\",	\"country\": \"BR\",	\"phone\": \"21-2592-2592\",	\"codCurr\": \"BRL\",	\"codPayment\": \"MPA-BF17622CF63C\"   }]";
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
		return "[   {	\"codOwner\": \"8\",\"cnpj\": \"02357537000139\", 	\"inscEstadual\": \"83739029\", 	\"inscMunicipal\": \"873787620999\", 	\"razaoSocial\": \"Loja DEF LTDA\",	\"name\": \"ABC Coiffeur - Filial B\",	\"address1\": \"Rua 123 de Oliveira 4, Centro\",	\"address2\": \"Loja A\",	\"postalcode\": \"20735060\",	\"city\": \"Rio de Janeiro\",	\"state\": \"RJ\",	\"country\": \"BR\",	\"phone\": \"21-2592-2592\",	\"codCurr\": \"BRL\",	\"codPayment\": \"MPA-BF17622CF63C\"   }]";
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
		return "[   {	\"codOwner\": \"8\",	\"codStore\": \"16\",	\"cnpj\": \"02357537000130\", 	\"inscEstadual\": \"83739029\", 	\"inscMunicipal\": \"873787620999\", 	\"razaoSocial\": \"Loja DEF LTDA\",	\"name\": \"ABC Coiffeur - Filial B\",	\"address1\": \"Rua 123 de Oliveira 4, Centro\",	\"address2\": \"Loja A\",	\"postalcode\": \"20735060\",	\"city\": \"Rio de Janeiro\",	\"state\": \"RJ\",	\"country\": \"BR\",	\"phone\": \"21-2592-2592\",	\"codCurr\": \"BRL\",	\"codPayment\": \"MPA-BF17622CF63C\"   }]";
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
		model = new StoreModelUpdate(incomingDataUpdateStore());
	}

}
