package br.com.gda.business.phone.model.decisionTree;

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
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.gda.business.phone.info.PhoneInfo;
import br.com.gda.common.DbConnection;
import br.com.gda.common.DbSchema;
import br.com.gda.model.decisionTree.DeciResult;
import br.com.gda.model.decisionTree.DeciTree;
import br.com.gda.model.decisionTree.DeciTreeOption;

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class RootPhoneDeleteTest {
	@Mock private Connection deleteConn;
	@Mock private PreparedStatement deleteStmt;
	@Mock private ResultSet deleteRs;
	
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;
	
	@Mock private Connection dontExistConn;
	@Mock private PreparedStatement dontExistStmt;
	@Mock private ResultSet dontExistRs;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		initializeScenarioInvalidConnection();
		initializeScenarioDelete();
		initializeScenarioDontExist();
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
	
	
	
	private void initializeScenarioDelete() throws SQLException {
		deleteConn = mock(Connection.class);
		deleteStmt = mock(PreparedStatement.class);
		deleteRs = mock(ResultSet.class);
		
		when(deleteConn.prepareStatement(any(String.class))).thenReturn(deleteStmt);
		when(deleteStmt.executeUpdate()).thenReturn(1);
		
		when(deleteStmt.executeQuery()).thenReturn(deleteRs);
		when(deleteRs.next()).thenReturn(true).thenReturn(true).thenReturn(false);	// Check Exist
						  	    													// Delete
		when(deleteRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(deleteRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(deleteRs.getString(any(String.class))).thenReturn(" ");
		when(deleteRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioDontExist() throws SQLException {
		dontExistConn = mock(Connection.class);
		dontExistStmt = mock(PreparedStatement.class);
		dontExistRs = mock(ResultSet.class);
		
		when(dontExistConn.prepareStatement(any(String.class))).thenReturn(dontExistStmt);
		when(dontExistStmt.executeUpdate()).thenReturn(1);
		
		when(dontExistStmt.executeQuery()).thenReturn(dontExistRs);
		when(dontExistRs.next()).thenReturn(false);										// Check Exist
		when(dontExistRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(dontExistRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(dontExistRs.getString(any(String.class))).thenReturn(" ");
		when(dontExistRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	@Test
	public void dontExist() {
		DeciTree<PhoneInfo> tree = initializeDontExist();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1564);
		assertTrue(result.getFailMessage().equals("Phone not found on DB"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeDontExist() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesDelete();
		option.conn = dontExistConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneDelete(option);
	}
	
	
	
	@Test
	public void missingFieldCodOwner() {
		DeciTree<PhoneInfo> tree = initializeMissingFieldCodOwner();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMissingFieldCodOwner() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesMissingFieldCodOwner();
		option.conn = deleteConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneDelete(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesMissingFieldCodOwner() {
		PhoneInfo phone = new PhoneInfo();
		
		phone.codOwner = -1;
		phone.codPhone = 1;
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void missingFieldCodPhone() {
		DeciTree<PhoneInfo> tree = initializeMissingFieldCodPhone();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMissingFieldCodPhone() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesMissingFieldCodPhone();
		option.conn = deleteConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneDelete(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesMissingFieldCodPhone() {
		PhoneInfo phone = new PhoneInfo();
		
		phone.codOwner = 1;
		phone.codPhone = -1;
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test(expected = IllegalArgumentException.class)
	public void emptyRecords() {
		DeciTree<PhoneInfo> tree = initializeEmptyRecords();
		tree.makeDecision();	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeEmptyRecords() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesEmptyRecords();
		option.conn = deleteConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneDelete(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesEmptyRecords() {
		return new ArrayList<>();
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullOption() {
		DeciTree<PhoneInfo> tree = initializeNullOption();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PhoneInfo> initializeNullOption() {		
		return new RootPhoneDelete(null);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullSchema() {
		DeciTree<PhoneInfo> tree = initializeNullSchema();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PhoneInfo> initializeNullSchema() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesDelete();
		option.conn = deleteConn;
		option.schemaName = null;
		
		return new RootPhoneDelete(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullRecord() {
		DeciTree<PhoneInfo> tree = initializeNullRecord();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PhoneInfo> initializeNullRecord() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = null;
		option.conn = deleteConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneDelete(option);
	}
	
	
	
	@Test(expected = IllegalStateException.class)
	public void invalidConnection() {
		DeciTree<PhoneInfo> tree = initializeInvalidConnection();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PhoneInfo> initializeInvalidConnection() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesDelete();
		option.conn = invalidConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneDelete(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullConnection() {
		DeciTree<PhoneInfo> tree = initializeNullConnection();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PhoneInfo> initializeNullConnection() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesDelete();
		option.conn = null;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneDelete(option);
	}
	
	

	@Test
	public void delete() {
		DeciTree<PhoneInfo> tree = initializeDelete();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());		
		PhoneInfo phone = result.getResultset().get(0);
		assertTrue(phone.codPhone == -1);	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeDelete() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesDelete();
		option.conn = deleteConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneDelete(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesDelete() {
		PhoneInfo phone = new PhoneInfo();
		
		phone.codOwner = 1;
		phone.codPhone = 1;
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
}
