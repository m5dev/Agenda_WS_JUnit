package br.com.gda.business.material.model;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
public class MatModelInsertTest {
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;
	@Mock private ResultSet invalidRs;
	
	@Mock private Connection insertNewConn;	
	@Mock private PreparedStatement insertNewStmt;	
	@Mock private ResultSet insertNewRs;
	
	@Mock private Connection invalidTypeConn;	
	@Mock private PreparedStatement invalidTypeStmt;	
	@Mock private ResultSet invalidTypeRs;
	
	@Mock private Connection invalidGroupConn;	
	@Mock private PreparedStatement invalidGroupStmt;	
	@Mock private ResultSet invalidGroupRs;
	
	@Mock private Connection invalidCategoryConn;	
	@Mock private PreparedStatement invalidCategoryStmt;	
	@Mock private ResultSet invalidCategoryRs;
	
	@Mock private Connection invalidUnitConn;	
	@Mock private PreparedStatement invalidUnitStmt;	
	@Mock private ResultSet invalidUnitRs;
	
	@Mock private Connection invalidCurrencyConn;	
	@Mock private PreparedStatement invalidCurrencyStmt;	
	@Mock private ResultSet invalidCurrencyRs;
	
	@Mock private Connection invalidLanguageConn;	
	@Mock private PreparedStatement invalidLanguageStmt;	
	@Mock private ResultSet invalidLanguageRs;
	
	@Mock private Connection invalidOwnerConn;	
	@Mock private PreparedStatement invalidOwnerStmt;	
	@Mock private ResultSet invalidOwnerRs;
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
			PowerMockito.mockStatic(DbConnection.class);
			
			initializeScenarioInsertNew();
			initializeScenarioInvalidType();
			initializeScenarioInvalidGroup();
			initializeScenarioInvalidCategory();
			initializeScenarioInvalidUnit();
			initializeScenarioInvalidCurrency();
			initializeScenarioInvalidLanguage();
			initializeScenarioInvalidOwner();
			initializeScenarioInvalidConnection();
		}



	private void initializeScenarioInsertNew() throws SQLException {
		insertNewConn = mock(Connection.class);
		insertNewStmt = mock(PreparedStatement.class);
		insertNewRs = mock(ResultSet.class);
		
		when(insertNewConn.prepareStatement(any(String.class))).thenReturn(insertNewStmt);
		when(insertNewStmt.executeUpdate()).thenReturn(1);
		
		when(insertNewStmt.executeQuery()).thenReturn(insertNewRs);
		when(insertNewRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	//CheckOwner
		                        .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckLanguage
		                        .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckCurrency
		                        .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckUnit
		                        .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckCategory
		                        .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckGroup
		                        .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckType
		                        .thenReturn(true)										//Insert Attr
		                        .thenReturn(true)										//Insert Text
		                        .thenReturn(true).thenReturn(true).thenReturn(false);	//Select
		when(insertNewRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(insertNewRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(insertNewRs.getString(any(String.class))).thenReturn(" ");
		when(insertNewRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidType() throws SQLException {
		invalidTypeConn = mock(Connection.class);
		invalidTypeStmt = mock(PreparedStatement.class);
		invalidTypeRs = mock(ResultSet.class);
		
		when(invalidTypeConn.prepareStatement(any(String.class))).thenReturn(invalidTypeStmt);
		when(invalidTypeStmt.executeUpdate()).thenReturn(1);
		
		when(invalidTypeStmt.executeQuery()).thenReturn(invalidTypeRs);
		when(invalidTypeRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	//CheckOwner
		                          .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckLanguage
		                          .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckCurrency
		                          .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckUnit
		                          .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckCategory
		                          .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckGroup
		                          .thenReturn(true).thenReturn(false);					//CheckType
		when(invalidTypeRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidTypeRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidTypeRs.getString(any(String.class))).thenReturn(" ");
		when(invalidTypeRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidGroup() throws SQLException {
		invalidGroupConn = mock(Connection.class);
		invalidGroupStmt = mock(PreparedStatement.class);
		invalidGroupRs = mock(ResultSet.class);
		
		when(invalidGroupConn.prepareStatement(any(String.class))).thenReturn(invalidGroupStmt);
		when(invalidGroupStmt.executeUpdate()).thenReturn(1);
		
		when(invalidGroupStmt.executeQuery()).thenReturn(invalidGroupRs);
		when(invalidGroupRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	//CheckOwner
		                           .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckLanguage
		                           .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckCurrency
		                           .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckUnit
		                           .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckCategory
		                           .thenReturn(true).thenReturn(false);					//CheckGroup
		when(invalidGroupRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidGroupRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidGroupRs.getString(any(String.class))).thenReturn(" ");
		when(invalidGroupRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidCategory() throws SQLException {
		invalidCategoryConn = mock(Connection.class);
		invalidCategoryStmt = mock(PreparedStatement.class);
		invalidCategoryRs = mock(ResultSet.class);
		
		when(invalidCategoryConn.prepareStatement(any(String.class))).thenReturn(invalidCategoryStmt);
		when(invalidCategoryStmt.executeUpdate()).thenReturn(1);
		
		when(invalidCategoryStmt.executeQuery()).thenReturn(invalidCategoryRs);
		when(invalidCategoryRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	//CheckOwner
		                          	  .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckLanguage
		                          	  .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckCurrency
		                          	  .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckUnit
		                          	  .thenReturn(true).thenReturn(false);					//CheckCategory
		when(invalidCategoryRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidCategoryRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidCategoryRs.getString(any(String.class))).thenReturn(" ");
		when(invalidCategoryRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidUnit() throws SQLException {
		invalidUnitConn = mock(Connection.class);
		invalidUnitStmt = mock(PreparedStatement.class);
		invalidUnitRs = mock(ResultSet.class);
		
		when(invalidUnitConn.prepareStatement(any(String.class))).thenReturn(invalidUnitStmt);
		when(invalidUnitStmt.executeUpdate()).thenReturn(1);
		
		when(invalidUnitStmt.executeQuery()).thenReturn(invalidUnitRs);
		when(invalidUnitRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	//CheckOwner
		                          .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckLanguage
		                          .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckCurrency
		                          .thenReturn(true).thenReturn(false);					//CheckUnit
		when(invalidUnitRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidUnitRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidUnitRs.getString(any(String.class))).thenReturn(" ");
		when(invalidUnitRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidCurrency() throws SQLException {
		invalidCurrencyConn = mock(Connection.class);
		invalidCurrencyStmt = mock(PreparedStatement.class);
		invalidCurrencyRs = mock(ResultSet.class);
		
		when(invalidCurrencyConn.prepareStatement(any(String.class))).thenReturn(invalidCurrencyStmt);
		when(invalidCurrencyStmt.executeUpdate()).thenReturn(1);
		
		when(invalidCurrencyStmt.executeQuery()).thenReturn(invalidCurrencyRs);
		when(invalidCurrencyRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	//CheckOwner
		                          	  .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckLanguage
		                          	  .thenReturn(true).thenReturn(false);					//CheckCurrency
		when(invalidCurrencyRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidCurrencyRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidCurrencyRs.getString(any(String.class))).thenReturn(" ");
		when(invalidCurrencyRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidLanguage() throws SQLException {
		invalidLanguageConn = mock(Connection.class);
		invalidLanguageStmt = mock(PreparedStatement.class);
		invalidLanguageRs = mock(ResultSet.class);
		
		when(invalidLanguageConn.prepareStatement(any(String.class))).thenReturn(invalidLanguageStmt);
		when(invalidLanguageStmt.executeUpdate()).thenReturn(1);
		
		when(invalidLanguageStmt.executeQuery()).thenReturn(invalidLanguageRs);
		when(invalidLanguageRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	//CheckOwner
		                          	  .thenReturn(true).thenReturn(false);					//CheckLanguage
		when(invalidLanguageRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidLanguageRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidLanguageRs.getString(any(String.class))).thenReturn(" ");
		when(invalidLanguageRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidOwner() throws SQLException {
		invalidOwnerConn = mock(Connection.class);
		invalidOwnerStmt = mock(PreparedStatement.class);
		invalidOwnerRs = mock(ResultSet.class);
		
		when(invalidOwnerConn.prepareStatement(any(String.class))).thenReturn(invalidOwnerStmt);
		when(invalidOwnerStmt.executeUpdate()).thenReturn(1);
		
		when(invalidOwnerStmt.executeQuery()).thenReturn(invalidOwnerRs);
		when(invalidOwnerRs.next()).thenReturn(true).thenReturn(false);					//CheckOwner
		when(invalidOwnerRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidOwnerRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidOwnerRs.getString(any(String.class))).thenReturn(" ");
		when(invalidOwnerRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidConnection() throws SQLException {
		invalidStmt = mock(PreparedStatement.class);
		invalidConn = mock(Connection.class);
		
		when(invalidConn.prepareStatement(anyString())).thenThrow(new SQLException());
		when(invalidStmt.executeQuery()).thenThrow(new SQLException());
		
		doThrow(new SQLException()).when(invalidStmt).setString(anyInt(), anyString());
		doThrow(new SQLException()).when(invalidStmt).setLong(anyInt(), anyLong());
		doThrow(new SQLException()).when(invalidStmt).setTime(anyInt(), any(Time.class)); 
	}
	
	
	
	@Test
	public void insertNewRecord() {
		initializeInsertNewRecord();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codMat\":1,\"txtMat\":\" \",\"description\":\" \",\"codType\":1,\"txtType\":\" \",\"codCategory\":1,\"txtCategory\":\" \",\"price\":0.0,\"priceUnit\":1,\"codCurr\":\" \",\"txtCurr\":\" \",\"codUnit\":\" \",\"txtUnit\":\" \",\"codGroup\":1,\"txtGroup\":\" \",\"codBusiness\":1,\"txtBusiness\":\" \",\"codLanguage\":\" \",\"isLocked\":false},{\"codOwner\":1,\"codMat\":1,\"txtMat\":\" \",\"description\":\" \",\"codType\":1,\"txtType\":\" \",\"codCategory\":1,\"txtCategory\":\" \",\"price\":0.0,\"priceUnit\":1,\"codCurr\":\" \",\"txtCurr\":\" \",\"codUnit\":\" \",\"txtUnit\":\" \",\"codGroup\":1,\"txtGroup\":\" \",\"codBusiness\":1,\"txtBusiness\":\" \",\"codLanguage\":\" \",\"isLocked\":false}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInsertNewRecord() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new MatModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	protected String incomingDataOrdinaryUsage() {
		return "[{\"codOwner\": 8, \"txtMat\": \"txt de teste\", \"description\": \"Bom para testar\", \"codType\": 2, \"codCategory\": 2, \"price\": 38.99, \"priceUnit\": 1, \"codCurr\": \"BRL\", \"codUnit\": \"EAC\", \"codGroup\": 1, \"codLanguage\": \"PT\", \"isLocked\": false}]";
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new MatModelInsert(incomingDataMissingFieldCodOwner());
	}
	
	
	
	protected String incomingDataMissingFieldCodOwner() {
		return "[{\"txtMat\": \"txt de teste\", \"description\": \"Bom para testar\", \"codType\": 2, \"codCategory\": 2, \"price\": 38.99, \"priceUnit\": 1, \"codCurr\": \"BRL\", \"codUnit\": \"EAC\", \"codGroup\": 1, \"codLanguage\": \"PT\", \"isLocked\": false}]";
	}
	
	
	
	@Test
	public void missingFieldMatText() {
		initializeMissingFieldMatText();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMissingFieldMatText() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new MatModelInsert(incomingDataMissingFieldMatText());
	}
	
	
	
	protected String incomingDataMissingFieldMatText() {
		return "[{\"codOwner\": 8, \"description\": \"Bom para testar\", \"codType\": 2, \"codCategory\": 2, \"price\": 38.99, \"priceUnit\": 1, \"codCurr\": \"BRL\", \"codUnit\": \"EAC\", \"codGroup\": 1, \"codLanguage\": \"PT\", \"isLocked\": false}]";
	}
	
	
	
	@Test
	public void missingFieldDescription() {
		initializeMissingFieldDescription();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMissingFieldDescription() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new MatModelInsert(incomingDataMissingFieldDescription());
	}
	
	
	
	protected String incomingDataMissingFieldDescription() {
		return "[{\"codOwner\": 8, \"txtMat\": \"txt de teste\", \"codType\": 2, \"codCategory\": 2, \"price\": 38.99, \"priceUnit\": 1, \"codCurr\": \"BRL\", \"codUnit\": \"EAC\", \"codGroup\": 1, \"codLanguage\": \"PT\", \"isLocked\": false}]";
	}
	
	
	
	@Test
	public void missingFieldCodType() {
		initializeMissingFieldCodType();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMissingFieldCodType() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new MatModelInsert(incomingDataMissingFieldCodType());
	}
	
	
	
	protected String incomingDataMissingFieldCodType() {
		return "[{\"codOwner\": 8, \"txtMat\": \"txt de teste\", \"description\": \"Bom para testar\", \"codCategory\": 2, \"price\": 38.99, \"priceUnit\": 1, \"codCurr\": \"BRL\", \"codUnit\": \"EAC\", \"codGroup\": 1, \"codLanguage\": \"PT\", \"isLocked\": false}]";
	}
	
	
	
	@Test
	public void missingFieldCodCategory() {
		initializeMissingFieldCodCategory();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMissingFieldCodCategory() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new MatModelInsert(incomingDataMissingFieldCodCategory());
	}
	
	
	
	protected String incomingDataMissingFieldCodCategory() {
		return "[{\"codOwner\": 8, \"txtMat\": \"txt de teste\", \"description\": \"Bom para testar\", \"codType\": 2, \"price\": 38.99, \"priceUnit\": 1, \"codCurr\": \"BRL\", \"codUnit\": \"EAC\", \"codGroup\": 1, \"codLanguage\": \"PT\", \"isLocked\": false}]";
	}
	
	
	
	@Test
	public void missingFieldPrice() {
		initializeMissingFieldPrice();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMissingFieldPrice() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new MatModelInsert(incomingDataMissingFieldPrice());
	}
	
	
	
	protected String incomingDataMissingFieldPrice() {
		return "[{\"codOwner\": 8, \"txtMat\": \"txt de teste\", \"description\": \"Bom para testar\", \"codType\": 2, \"codCategory\": 2, \"priceUnit\": 1, \"codCurr\": \"BRL\", \"codUnit\": \"EAC\", \"codGroup\": 1, \"codLanguage\": \"PT\", \"isLocked\": false}]";
	}
	
	
	
	@Test
	public void missingFieldPriceUnit() {
		initializeMissingFieldPriceUnit();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMissingFieldPriceUnit() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new MatModelInsert(incomingDataMissingFieldPriceUnit());
	}
	
	
	
	protected String incomingDataMissingFieldPriceUnit() {
		return "[{\"codOwner\": 8, \"txtMat\": \"txt de teste\", \"description\": \"Bom para testar\", \"codType\": 2, \"codCategory\": 2, \"price\": 38.99, \"priceUnit\": -1, \"codCurr\": \"BRL\", \"codUnit\": \"EAC\", \"codGroup\": 1, \"codLanguage\": \"PT\", \"isLocked\": false}]";
	}
	
	
	
	@Test
	public void missingFieldCodCur() {
		initializeMissingFieldCodCur();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMissingFieldCodCur() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new MatModelInsert(incomingDataMissingFieldCodCur());
	}
	
	
	
	protected String incomingDataMissingFieldCodCur() {
		return "[{\"codOwner\": 8, \"txtMat\": \"txt de teste\", \"description\": \"Bom para testar\", \"codType\": 2, \"codCategory\": 2, \"price\": 38.99, \"priceUnit\": 1, \"codUnit\": \"EAC\", \"codGroup\": 1, \"codLanguage\": \"PT\", \"isLocked\": false}]";
	}
	
	
	
	@Test
	public void missingFieldCodUnit() {
		initializeMissingFieldCodUnit();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMissingFieldCodUnit() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new MatModelInsert(incomingDataMissingFieldCodUnit());
	}
	
	
	
	protected String incomingDataMissingFieldCodUnit() {
		return "[{\"codOwner\": 8, \"txtMat\": \"txt de teste\", \"description\": \"Bom para testar\", \"codType\": 2, \"codCategory\": 2, \"price\": 38.99, \"priceUnit\": 1, \"codCurr\": \"BRL\", \"codGroup\": 1, \"codLanguage\": \"PT\", \"isLocked\": false}]";
	}
	
	
	
	@Test
	public void missingFieldCodGroup() {
		initializeMissingFieldCodGroup();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMissingFieldCodGroup() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new MatModelInsert(incomingDataMissingFieldCodGroup());
	}
	
	
	
	protected String incomingDataMissingFieldCodGroup() {
		return "[{\"codOwner\": 8, \"txtMat\": \"txt de teste\", \"description\": \"Bom para testar\", \"codType\": 2, \"codCategory\": 2, \"price\": 38.99, \"priceUnit\": 1, \"codCurr\": \"BRL\", \"codUnit\": \"EAC\", \"codLanguage\": \"PT\", \"isLocked\": false}]";
	}
	
	
	
	@Test
	public void missingFieldCodLanguage() {
		initializeMissingFieldCodLanguage();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMissingFieldCodLanguage() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new MatModelInsert(incomingDataMissingFieldCodLanguage());
	}
	
	
	
	protected String incomingDataMissingFieldCodLanguage() {
		return "[{\"codOwner\": 8, \"txtMat\": \"txt de teste\", \"description\": \"Bom para testar\", \"codType\": 2, \"codCategory\": 2, \"price\": 38.99, \"priceUnit\": 1, \"codCurr\": \"BRL\", \"codUnit\": \"EAC\", \"codGroup\": 1, \"codLanguage\": null, \"isLocked\": false}]";
	}
	
	
	
	@Test
	public void autoGeneratedFieldCodMat() {
		initializeAutoGeneratedFieldCodMat();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1057,\"selectMessage\":\"Auto generated fields should not be passed\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeAutoGeneratedFieldCodMat() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new MatModelInsert(incomingDataAutoGeneratedFieldCodMat());
	}
	
	
	
	protected String incomingDataAutoGeneratedFieldCodMat() {
		return "[{\"codMat\": 8,\"codOwner\": 8, \"txtMat\": \"txt de teste\", \"description\": \"Bom para testar\", \"codType\": 2, \"codCategory\": 2, \"price\": 38.99, \"priceUnit\": 1, \"codCurr\": \"BRL\", \"codUnit\": \"EAC\", \"codGroup\": 1, \"codLanguage\": \"PT\", \"isLocked\": false}]";
	}
	
	
	
	@Test
	public void invalidType() {
		initializeInvalidType();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1163,\"selectMessage\":\"Type not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidType() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidTypeConn);
		model = new MatModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void invalidGroup() {
		initializeInvalidGroup();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1161,\"selectMessage\":\"Group not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidGroup() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidGroupConn);
		model = new MatModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void invalidCategory() {
		initializeInvalidCategory();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1159,\"selectMessage\":\"Category not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidCategory() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidCategoryConn);
		model = new MatModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void invalidUnit() {
		initializeInvalidUnit();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1157,\"selectMessage\":\"Unit not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidUnit() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidUnitConn);
		model = new MatModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void invalidCurrency() {
		initializeInvalidCurrency();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1155,\"selectMessage\":\"Currency not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidCurrency() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidCurrencyConn);
		model = new MatModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void invalidLanguage() {
		initializeInvalidLanguage();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1153,\"selectMessage\":\"Language not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidLanguage() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidLanguageConn);
		model = new MatModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void invalidOwner() {
		initializeInvalidOwner();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1251,\"selectMessage\":\"Owner data not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidOwner() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidOwnerConn);
		model = new MatModelInsert(incomingDataOrdinaryUsage());
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
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new MatModelInsert(null);
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
		model = new MatModelInsert(incomingDataOrdinaryUsage());
	}
}
