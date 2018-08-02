package br.com.gda.business.materialEmployee.model;

import static org.junit.Assert.*;
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
public class MatEmpModelInsertTest {
	@Mock private Connection insertSoftDeletedConn;
	@Mock private PreparedStatement insertSoftDeletedStmt;
	@Mock private ResultSet insertSoftDeletedRs;
	
	@Mock private Connection insertNewConn;
	@Mock private PreparedStatement insertNewStmt;
	@Mock private ResultSet insertNewRs;
	
	@Mock private Connection invalidStoreEmpConn;
	@Mock private PreparedStatement invalidStoreEmpStmt;
	@Mock private ResultSet invalidStoreEmpRs;
	
	@Mock private Connection invalidMatStoreConn;
	@Mock private PreparedStatement invalidMatStoreStmt;
	@Mock private ResultSet invalidMatStoreRs;
	
	@Mock private Connection invalidStoreConn;
	@Mock private PreparedStatement invalidStoreStmt;
	@Mock private ResultSet invalidStoreRs;
	
	@Mock private Connection invalidMatConn;
	@Mock private PreparedStatement invalidMatStmt;
	@Mock private ResultSet invalidMatRs;
	
	@Mock private Connection invalidEmpConn;
	@Mock private PreparedStatement invalidEmpStmt;
	@Mock private ResultSet invalidEmpRs;
	
	@Mock private Connection invalidOwnerConn;
	@Mock private PreparedStatement invalidOwnerStmt;
	@Mock private ResultSet invalidOwnerRs;
	
	@Mock private Connection alreadyExistConn;
	@Mock private PreparedStatement alreadyExistStmt;
	@Mock private ResultSet alreadyExistRs;
	
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;	
	@Mock private ResultSet invalidRs;
	
	private Model model;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		
		initializeScenarioInsertNew();
		initializeScenarioInsertSoftDeleted();
		initializeScenarioInvalidStoreEmp();
		initializeScenarioInvalidMatStore();
		initializeScenarioInvalidMat();
		initializeScenarioInvalidEmp();
		initializeScenarioInvalidStore();
		initializeScenarioInvalidOwner();
		initializeScenarioAlreadyExist();
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
		                        .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckStore
		                        .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckEmp
		                        .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckMat
		                        .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckStoreEmp
		                        .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckMatStore
		                        .thenReturn(true).thenReturn(false)						//CheckExist
		                        .thenReturn(true).thenReturn(false)						//CheckSoftDeleted
		                        														//Insert
		                        .thenReturn(true).thenReturn(true).thenReturn(false)	//Select MapEmp
						        .thenReturn(true).thenReturn(true).thenReturn(false)	//Select Map
						        .thenReturn(true).thenReturn(true).thenReturn(false);	//Select Emp
		when(insertNewRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(insertNewRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(insertNewRs.getString(any(String.class))).thenReturn(" ");
		when(insertNewRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInsertSoftDeleted() throws SQLException {
		insertSoftDeletedConn = mock(Connection.class);
		insertSoftDeletedStmt = mock(PreparedStatement.class);
		insertSoftDeletedRs = mock(ResultSet.class);
		
		when(insertSoftDeletedConn.prepareStatement(any(String.class))).thenReturn(insertSoftDeletedStmt);
		when(insertSoftDeletedStmt.executeUpdate()).thenReturn(1);
		
		when(insertSoftDeletedStmt.executeQuery()).thenReturn(insertSoftDeletedRs);
		when(insertSoftDeletedRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	//CheckOwner
		                        		.thenReturn(true).thenReturn(true).thenReturn(false)	//CheckStore
		                        		.thenReturn(true).thenReturn(true).thenReturn(false)	//CheckEmp
		                        		.thenReturn(true).thenReturn(true).thenReturn(false)	//CheckMat
		                        		.thenReturn(true).thenReturn(true).thenReturn(false)	//CheckStoreEmp
		                        		.thenReturn(true).thenReturn(true).thenReturn(false)	//CheckMatStore
		                        		.thenReturn(true).thenReturn(false)						//CheckExist
		                        		.thenReturn(true).thenReturn(true).thenReturn(false)	//CheckSoftDeleted
		                        																//Update
				                        .thenReturn(true).thenReturn(true).thenReturn(false)	//Select MapEmp
								        .thenReturn(true).thenReturn(true).thenReturn(false)	//Select Map
								        .thenReturn(true).thenReturn(true).thenReturn(false);	//Select Emp
		when(insertSoftDeletedRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(insertSoftDeletedRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(insertSoftDeletedRs.getString(any(String.class))).thenReturn(" ");
		when(insertSoftDeletedRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidStoreEmp() throws SQLException {
		invalidStoreEmpConn = mock(Connection.class);
		invalidStoreEmpStmt = mock(PreparedStatement.class);
		invalidStoreEmpRs = mock(ResultSet.class);
		
		when(invalidStoreEmpConn.prepareStatement(any(String.class))).thenReturn(invalidStoreEmpStmt);
		when(invalidStoreEmpStmt.executeUpdate()).thenReturn(1);
		
		when(invalidStoreEmpStmt.executeQuery()).thenReturn(invalidStoreEmpRs);
		when(invalidStoreEmpRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	//CheckOwner
		                        	  .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckStore
		                        	  .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckEmp
		                        	  .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckMat
		                        	  .thenReturn(true).thenReturn(false);					//CheckStoreEmp

		when(invalidStoreEmpRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidStoreEmpRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidStoreEmpRs.getString(any(String.class))).thenReturn(" ");
		when(invalidStoreEmpRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidMatStore() throws SQLException {
		invalidMatStoreConn = mock(Connection.class);
		invalidMatStoreStmt = mock(PreparedStatement.class);
		invalidMatStoreRs = mock(ResultSet.class);
		
		when(invalidMatStoreConn.prepareStatement(any(String.class))).thenReturn(invalidMatStoreStmt);
		when(invalidMatStoreStmt.executeUpdate()).thenReturn(1);
		
		when(invalidMatStoreStmt.executeQuery()).thenReturn(invalidMatStoreRs);
		when(invalidMatStoreRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	//CheckOwner
		                        	  .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckStore
		                        	  .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckEmp
		                        	  .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckMat
		                        	  .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckStoreEmp
		                        	  .thenReturn(true).thenReturn(false);					//CheckMatStore
		when(invalidMatStoreRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidMatStoreRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidMatStoreRs.getString(any(String.class))).thenReturn(" ");
		when(invalidMatStoreRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidMat() throws SQLException {
		invalidMatConn = mock(Connection.class);
		invalidMatStmt = mock(PreparedStatement.class);
		invalidMatRs = mock(ResultSet.class);
		
		when(invalidMatConn.prepareStatement(any(String.class))).thenReturn(invalidMatStmt);
		when(invalidMatStmt.executeUpdate()).thenReturn(1);
		
		when(invalidMatStmt.executeQuery()).thenReturn(invalidMatRs);
		when(invalidMatRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	//CheckOwner
		                         .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckStore
		                         .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckEmp
		                         .thenReturn(true).thenReturn(false);					//CheckMat

		when(invalidMatRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidMatRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidMatRs.getString(any(String.class))).thenReturn(" ");
		when(invalidMatRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidEmp() throws SQLException {
		invalidEmpConn = mock(Connection.class);
		invalidEmpStmt = mock(PreparedStatement.class);
		invalidEmpRs = mock(ResultSet.class);
		
		when(invalidEmpConn.prepareStatement(any(String.class))).thenReturn(invalidEmpStmt);
		when(invalidEmpStmt.executeUpdate()).thenReturn(1);
		
		when(invalidEmpStmt.executeQuery()).thenReturn(invalidEmpRs);
		when(invalidEmpRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	//CheckOwner
		                         .thenReturn(true).thenReturn(true).thenReturn(false)	//CheckStore
		                         .thenReturn(true).thenReturn(false);					//CheckEmp

		when(invalidEmpRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidEmpRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidEmpRs.getString(any(String.class))).thenReturn(" ");
		when(invalidEmpRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidStore() throws SQLException {
		invalidStoreConn = mock(Connection.class);
		invalidStoreStmt = mock(PreparedStatement.class);
		invalidStoreRs = mock(ResultSet.class);
		
		when(invalidStoreConn.prepareStatement(any(String.class))).thenReturn(invalidStoreStmt);
		when(invalidStoreStmt.executeUpdate()).thenReturn(1);
		
		when(invalidStoreStmt.executeQuery()).thenReturn(invalidStoreRs);
		when(invalidStoreRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	//CheckOwner
		                           .thenReturn(true).thenReturn(false);					//CheckStore

		when(invalidStoreRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidStoreRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidStoreRs.getString(any(String.class))).thenReturn(" ");
		when(invalidStoreRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidOwner() throws SQLException {
		invalidOwnerConn = mock(Connection.class);
		invalidOwnerStmt = mock(PreparedStatement.class);
		invalidOwnerRs = mock(ResultSet.class);
		
		when(invalidOwnerConn.prepareStatement(any(String.class))).thenReturn(invalidOwnerStmt);
		when(invalidOwnerStmt.executeUpdate()).thenReturn(1);
		
		when(invalidOwnerStmt.executeQuery()).thenReturn(invalidOwnerRs);
		when(invalidOwnerRs.next()).thenReturn(true).thenReturn(false);	//CheckOwner

		when(invalidOwnerRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidOwnerRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidOwnerRs.getString(any(String.class))).thenReturn(" ");
		when(invalidOwnerRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioAlreadyExist() throws SQLException {
		alreadyExistConn = mock(Connection.class);
		alreadyExistStmt = mock(PreparedStatement.class);
		alreadyExistRs = mock(ResultSet.class);
		
		when(alreadyExistConn.prepareStatement(any(String.class))).thenReturn(alreadyExistStmt);
		when(alreadyExistStmt.executeUpdate()).thenReturn(1);
		
		when(alreadyExistStmt.executeQuery()).thenReturn(alreadyExistRs);
		when(alreadyExistRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		//CheckOwner
		                           .thenReturn(true).thenReturn(true).thenReturn(false)		//CheckStore
		                           .thenReturn(true).thenReturn(true).thenReturn(false)		//CheckEmp
		                           .thenReturn(true).thenReturn(true).thenReturn(false)		//CheckMat
		                           .thenReturn(true).thenReturn(true).thenReturn(false)		//CheckStoreEmp
		                           .thenReturn(true).thenReturn(true).thenReturn(false)		//CheckMatStore
		                           .thenReturn(true).thenReturn(true).thenReturn(false);	//CheckExist
		when(alreadyExistRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(alreadyExistRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(alreadyExistRs.getString(any(String.class))).thenReturn(" ");
		when(alreadyExistRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
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
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"nameEmployee\":\" \",\"codMat\":1,\"txtMat\":\" \",\"codType\":1,\"txtType\":\" \",\"codCategory\":1,\"txtCategory\":\" \",\"priceUnit\":1,\"codUnit\":\" \",\"txtUnit\":\" \",\"codLanguage\":\" \"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInsertNewRecord() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new MatEmpModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	protected String incomingDataOrdinaryUsage() {
		return "{\"codOwner\": 8,    \"codStore\": 15,    \"codEmployee\": 66,    \"codMat\": 72}";
	}
	
	
	
	@Test
	public void recordAlreadyExist() {
		initializeAlreadyExist();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1107,\"selectMessage\":\"Employee's material data already exist on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeAlreadyExist() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(alreadyExistConn);
		model = new MatEmpModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void invalidStoreEmp() {
		initializeInvalidStoreEmp();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1105,\"selectMessage\":\"Store-Employee's data not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidStoreEmp() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidStoreEmpConn);
		model = new MatEmpModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void invalidFieldCodMaterial() {
		initializeInvalidFieldCodMaterial();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1201,\"selectMessage\":\"Material data not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidFieldCodMaterial() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidMatConn);
		model = new MatEmpModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void invalidFieldCodEmployee() {
		initializeInvalidFieldCodEmployee();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1051,\"selectMessage\":\"Employee's data not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidFieldCodEmployee() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidEmpConn);
		model = new MatEmpModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void invalidFieldCodStore() {
		initializeInvalidFieldCodStore();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1103,\"selectMessage\":\"Store's data not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidFieldCodStore() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidStoreConn);
		model = new MatEmpModelInsert(incomingDataOrdinaryUsage());
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
		model = new MatEmpModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void insertSoftDeletedRecord() {
		initializeInsertSoftDeletedRecord();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
		
		String responseBody = "{\"selectCode\":200,\"selectMessage\":\"The list was returned successfully\",\"results\":[{\"codOwner\":1,\"codStore\":1,\"codEmployee\":1,\"nameEmployee\":\" \",\"codMat\":1,\"txtMat\":\" \",\"codType\":1,\"txtType\":\" \",\"codCategory\":1,\"txtCategory\":\" \",\"priceUnit\":1,\"codUnit\":\" \",\"txtUnit\":\" \",\"codLanguage\":\" \"}]}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInsertSoftDeletedRecord() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertSoftDeletedConn);
		model = new MatEmpModelInsert(incomingDataOrdinaryUsage());
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
		model = new MatEmpModelInsert(incomingDataMissingFieldCodOwner());
	}
	
	
	
	protected String incomingDataMissingFieldCodOwner() {
		return "{\"codStore\": 15,    \"codEmployee\": 66,    \"codMat\": 72}";
	}
	
	
	
	@Test
	public void missingFieldCodStore() {
		initializeMissingFieldCodStore();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMissingFieldCodStore() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new MatEmpModelInsert(incomingDataMissingFieldCodStore());
	}
	
	
	
	protected String incomingDataMissingFieldCodStore() {
		return "{\"codOwner\": 8,    \"codEmployee\": 66,    \"codMat\": 72}";
	}
	
	
	
	@Test
	public void missingFieldCodEmployee() {
		initializeMissingFieldCodEmployee();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMissingFieldCodEmployee() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new MatEmpModelInsert(incomingDataMissingFieldCodEmployee());
	}
	
	
	
	protected String incomingDataMissingFieldCodEmployee() {
		return "{\"codOwner\": 8,    \"codStore\": 15,    \"codMat\": 72}";
	}
	
	
	
	@Test
	public void missingFieldCodMat() {
		initializeMissingFieldCodMat();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1,\"selectMessage\":\"Mandatory field is empty\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeMissingFieldCodMat() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(insertNewConn);
		model = new MatEmpModelInsert(incomingDataMissingFieldCodEmployee());
	}
	
	
	
	protected String incomingDataMissingFieldCodMat() {
		return "{\"codOwner\": 8,    \"codStore\": 15,    \"codEmployee\": 66}";
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
		model = new MatEmpModelInsert(null);
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
		model = new MatEmpModelInsert(incomingDataOrdinaryUsage());
	}
	
	
	
	@Test
	public void invalidMatStore() {
		initializeInvalidMatStore();
		model.executeRequest();
		Response response = model.getResponse();
		assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
		
		String responseBody = "{\"selectCode\":1119,\"selectMessage\":\"Store's material data not found on DB\",\"results\":{}}";
		assertTrue(response.getEntity().equals(responseBody));		
	}
		
	
	
	protected void initializeInvalidMatStore() {
		PowerMockito.when(DbConnection.getConnection()).thenReturn(invalidMatStoreConn);
		model = new MatEmpModelInsert(incomingDataOrdinaryUsage());
	}
}
