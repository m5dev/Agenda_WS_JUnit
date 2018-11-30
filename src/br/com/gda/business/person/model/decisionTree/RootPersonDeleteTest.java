package br.com.gda.business.person.model.decisionTree;

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

import br.com.gda.business.person.info.PersonInfo;
import br.com.gda.common.DbConnection;
import br.com.gda.common.DbSchema;
import br.com.gda.model.decisionTree.DeciResult;
import br.com.gda.model.decisionTree.DeciTree;
import br.com.gda.model.decisionTree.DeciTreeOption;

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class RootPersonDeleteTest {
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
	
	
	
	@Test(expected = IllegalStateException.class)
	public void invalidConnection() {
		DeciTree<PersonInfo> tree = initializeInvalidConnection();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PersonInfo> initializeInvalidConnection() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsDelete();
		option.conn = invalidConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonDelete(option);
	}
	
	
	
	@Test
	public void dontExist() {
		DeciTree<PersonInfo> tree = initializeDontExist();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1761);
		assertTrue(result.getFailMessage().equals("Person not found on DB"));	
	}
		
	
	
	private DeciTree<PersonInfo> initializeDontExist() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsDelete();
		option.conn = dontExistConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonDelete(option);
	}	
	
	
	
	@Test
	public void missingFieldCodOwner() {
		DeciTree<PersonInfo> tree = initializeMissingFieldCodOwner();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 6);
		assertTrue(result.getFailMessage().equals("Key field should not be null"));	
	}
		
	
	
	private DeciTree<PersonInfo> initializeMissingFieldCodOwner() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsMissingFieldCodOwner();
		option.conn = deleteConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonDelete(option);
	}
	
	
	
	private List<PersonInfo> buildPersonsMissingFieldCodOwner() {
		PersonInfo phone = new PersonInfo();
		
		phone.codOwner = -1;
		phone.codPerson = 1;
		
		List<PersonInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void missingFieldCodPerson() {
		DeciTree<PersonInfo> tree = initializeMissingFieldCodPerson();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 6);
		assertTrue(result.getFailMessage().equals("Key field should not be null"));	
	}
		
	
	
	private DeciTree<PersonInfo> initializeMissingFieldCodPerson() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsMissingFieldCodPerson();
		option.conn = deleteConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonDelete(option);
	}
	
	
	
	private List<PersonInfo> buildPersonsMissingFieldCodPerson() {
		PersonInfo phone = new PersonInfo();
		
		phone.codOwner = 1;
		phone.codPerson = -1;
		
		List<PersonInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test(expected = IllegalArgumentException.class)
	public void emptyRecords() {
		DeciTree<PersonInfo> tree = initializeEmptyRecords();
		tree.makeDecision();	
	}
		
	
	
	private DeciTree<PersonInfo> initializeEmptyRecords() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsEmptyRecords();
		option.conn = deleteConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonDelete(option);
	}
	
	
	
	private List<PersonInfo> buildPersonsEmptyRecords() {
		return new ArrayList<>();
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullOption() {
		DeciTree<PersonInfo> tree = initializeNullOption();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PersonInfo> initializeNullOption() {		
		return new RootPersonDelete(null);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullSchema() {
		DeciTree<PersonInfo> tree = initializeNullSchema();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PersonInfo> initializeNullSchema() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsDelete();
		option.conn = deleteConn;
		option.schemaName = null;
		
		return new RootPersonDelete(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullRecord() {
		DeciTree<PersonInfo> tree = initializeNullRecord();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PersonInfo> initializeNullRecord() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = null;
		option.conn = deleteConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonDelete(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullConnection() {
		DeciTree<PersonInfo> tree = initializeNullConnection();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PersonInfo> initializeNullConnection() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsDelete();
		option.conn = null;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonDelete(option);
	}

	
	
	@Test
	public void delete() {
		DeciTree<PersonInfo> tree = initializeDelete();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());		
		PersonInfo phone = result.getResultset().get(0);
		assertTrue(phone.codPerson == -1);	
	}
		
	
	
	private DeciTree<PersonInfo> initializeDelete() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsDelete();
		option.conn = deleteConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonDelete(option);
	}
	
	
	
	private List<PersonInfo> buildPersonsDelete() {
		PersonInfo person = new PersonInfo();
		
		person.codOwner = 1;
		person.codPerson = 1;
		
		List<PersonInfo> phones = new ArrayList<>();
		phones.add(person);
		return phones;
	}

}
