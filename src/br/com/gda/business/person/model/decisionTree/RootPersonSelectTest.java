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
public class RootPersonSelectTest {
	@Mock private Connection selectConn;
	@Mock private PreparedStatement selectStmt;
	@Mock private ResultSet selectRs;
	
	@Mock private Connection notFoundConn;
	@Mock private PreparedStatement notFoundStmt;
	@Mock private ResultSet notFoundRs;
	
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;



	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		initializeScenarioInvalidConnection();
		initializeScenarioSelect();
		initializeScenarioNotFound();
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
	
	
	
	private void initializeScenarioSelect() throws SQLException {
		selectConn = mock(Connection.class);
		selectStmt = mock(PreparedStatement.class);
		selectRs = mock(ResultSet.class);
		
		when(selectConn.prepareStatement(any(String.class))).thenReturn(selectStmt);
		when(selectStmt.executeUpdate()).thenReturn(1);
		
		when(selectStmt.executeQuery()).thenReturn(selectRs);
		when(selectRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Select Person
		                     .thenReturn(true).thenReturn(true).thenReturn(false);	// Select Gender
		when(selectRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(selectRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(selectRs.getString(any(String.class))).thenReturn(" ");
		when(selectRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioNotFound() throws SQLException {
		notFoundConn = mock(Connection.class);
		notFoundStmt = mock(PreparedStatement.class);
		notFoundRs = mock(ResultSet.class);
		
		when(notFoundConn.prepareStatement(any(String.class))).thenReturn(notFoundStmt);
		when(notFoundStmt.executeUpdate()).thenReturn(1);
		
		when(notFoundStmt.executeQuery()).thenReturn(notFoundRs);
		when(notFoundRs.next()).thenReturn(false).thenReturn(false);	// notFound
		when(notFoundRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(notFoundRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(notFoundRs.getString(any(String.class))).thenReturn(" ");
		when(notFoundRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	@Test
	public void invalidConnection() {
		DeciTree<PersonInfo> tree = initializeInvalidConnection();
		tree.makeDecision();
		
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());	
		assertTrue(result.getFailCode() == 500);
		assertTrue(result.getFailMessage().equals("Ops... something went wrong"));
	}
		
	
	
	private DeciTree<PersonInfo> initializeInvalidConnection() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsSelect();
		option.conn = invalidConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonSelect(option);
	}
	
	
	
	@Test(expected = IllegalArgumentException.class)
	public void emptyRecords() {
		DeciTree<PersonInfo> tree = initializeEmptyRecords();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PersonInfo> initializeEmptyRecords() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsEmptyRecords();
		option.conn = selectConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonSelect(option);
	}
	
	
	
	private List<PersonInfo> buildPersonsEmptyRecords() {
		return new ArrayList<>();
	}	
	
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullRecords() {
		DeciTree<PersonInfo> tree = initializeNullRecords();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PersonInfo> initializeNullRecords() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = null;
		option.conn = selectConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonSelect(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullConnection() {
		DeciTree<PersonInfo> tree = initializeNullConnection();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PersonInfo> initializeNullConnection() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsSelect();
		option.conn = null;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonSelect(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullSchema() {
		DeciTree<PersonInfo> tree = initializeNullSchema();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PersonInfo> initializeNullSchema() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsSelect();
		option.conn = selectConn;
		option.schemaName = null;
		
		return new RootPersonSelect(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullOption() {
		DeciTree<PersonInfo> tree = initializeNullOption();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PersonInfo> initializeNullOption() {	
		return new RootPersonSelect(null);
	}
	
	
	
	@Test
	public void select() {
		DeciTree<PersonInfo> tree = initializeSelect();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());
	}
		
	
	
	private DeciTree<PersonInfo> initializeSelect() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsSelect();
		option.conn = selectConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonSelect(option);
	}
	
	
	
	private List<PersonInfo> buildPersonsSelect() {
		PersonInfo person = new PersonInfo();		
		
		person.codOwner = 1;
		person.codPerson = 1;
		
		List<PersonInfo> persons = new ArrayList<>();
		persons.add(person);
		return persons;
	}	
	
	
	
	@Test
	public void missingFieldCodOwner() {
		DeciTree<PersonInfo> tree = initializeMissingFieldCodOwner();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
		
	
	
	private DeciTree<PersonInfo> initializeMissingFieldCodOwner() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsMissingFieldCodOwner();
		option.conn = selectConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonSelect(option);
	}
	
	
	
	private List<PersonInfo> buildPersonsMissingFieldCodOwner() {
		PersonInfo person = new PersonInfo();		
		
		person.codOwner = -1;
		person.codPerson = 1;
		
		List<PersonInfo> persons = new ArrayList<>();
		persons.add(person);
		return persons;
	}	
	
	
	
	@Test
	public void missingFieldCodPerson() {
		DeciTree<PersonInfo> tree = initializeMissingFieldCodPerson();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
		
	
	
	private DeciTree<PersonInfo> initializeMissingFieldCodPerson() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsMissingFieldCodPerson();
		option.conn = selectConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonSelect(option);
	}
	
	
	
	private List<PersonInfo> buildPersonsMissingFieldCodPerson() {
		PersonInfo person = new PersonInfo();		
		
		person.codOwner = 1;
		person.codPerson = -1;
		
		List<PersonInfo> persons = new ArrayList<>();
		persons.add(person);
		return persons;
	}

	
	
	@Test
	public void notFound() {
		DeciTree<PersonInfo> tree = initializeNotFound();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());	
		assertTrue(result.getFailCode() == 10);
		assertTrue(result.getFailMessage().equals("Data not found"));
	}
		
	
	
	private DeciTree<PersonInfo> initializeNotFound() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsNotFound();
		option.conn = notFoundConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonSelect(option);
	}
	
	
	
	private List<PersonInfo> buildPersonsNotFound() {
		PersonInfo person = new PersonInfo();		
		
		person.codOwner = 1;
		person.codPerson = 1;
		
		List<PersonInfo> persons = new ArrayList<>();
		persons.add(person);
		return persons;
	}
}
