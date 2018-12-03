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
import java.time.LocalDate;
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
public class RootPersonInsertTest {
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;
	
	@Mock private Connection insertEmptyDocConn;
	@Mock private PreparedStatement insertEmptyDocStmt;
	@Mock private ResultSet insertEmptyDocRs;
	
	@Mock private Connection insertCpfConn;
	@Mock private PreparedStatement insertCpfStmt;
	@Mock private ResultSet insertCpfRs;
	
	@Mock private Connection insertEmailConn;
	@Mock private PreparedStatement insertEmailStmt;
	@Mock private ResultSet insertEmailRs;
	
	@Mock private Connection cpfAlreadyExistConn;
	@Mock private PreparedStatement cpfAlreadyExistStmt;
	@Mock private ResultSet cpfAlreadyExistRs;
	
	@Mock private Connection emailAlreadyExistConn;
	@Mock private PreparedStatement emailAlreadyExistStmt;
	@Mock private ResultSet emailAlreadyExistRs;
	
	@Mock private Connection invalidOwnerConn;
	@Mock private PreparedStatement invalidOwnerStmt;
	@Mock private ResultSet invalidOwnerRs;
	
	@Mock private Connection invalidGenderConn;
	@Mock private PreparedStatement invalidGenderStmt;
	@Mock private ResultSet invalidGenderRs;
	
	@Mock private Connection invalidEntityCategConn;
	@Mock private PreparedStatement invalidEntityCategStmt;
	@Mock private ResultSet invalidEntityCategRs;



	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		initializeScenarioInvalidConnection();
		initializeScenarioInsertEmptyDoc();
		initializeScenarioInsertCpf();
		initializeScenarioInsertEmail();
		initializeScenarioCpfAlreadyExist();
		initializeScenarioEmailAlreadyExist();
		initializeScenarioInvalidOwner();
		initializeScenarioInvalidGender();
		initializeScenarioInvalidEntityCateg();
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
	
	
	
	private void initializeScenarioInsertEmptyDoc() throws SQLException {
		insertEmptyDocConn = mock(Connection.class);
		insertEmptyDocStmt = mock(PreparedStatement.class);
		insertEmptyDocRs = mock(ResultSet.class);
		
		when(insertEmptyDocConn.prepareStatement(any(String.class))).thenReturn(insertEmptyDocStmt);
		when(insertEmptyDocStmt.executeUpdate()).thenReturn(1);
		
		when(insertEmptyDocStmt.executeQuery()).thenReturn(insertEmptyDocRs);
		when(insertEmptyDocRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
									 .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Gender
									 .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Entity Category
							  	     .thenReturn(true);										// Insert
		when(insertEmptyDocRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(insertEmptyDocRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(insertEmptyDocRs.getString(any(String.class))).thenReturn(" ");
		when(insertEmptyDocRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInsertCpf() throws SQLException {
		insertCpfConn = mock(Connection.class);
		insertCpfStmt = mock(PreparedStatement.class);
		insertCpfRs = mock(ResultSet.class);
		
		when(insertCpfConn.prepareStatement(any(String.class))).thenReturn(insertCpfStmt);
		when(insertCpfStmt.executeUpdate()).thenReturn(1);
		
		when(insertCpfStmt.executeQuery()).thenReturn(insertCpfRs);
		when(insertCpfRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
								.thenReturn(true).thenReturn(true).thenReturn(false)	// Check Gender
								.thenReturn(true).thenReturn(true).thenReturn(false)	// Check Entity Category
								.thenReturn(false).thenReturn(false)					// Check CPF already Exist
							  	.thenReturn(true);										// Insert
		when(insertCpfRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(insertCpfRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(insertCpfRs.getString(any(String.class))).thenReturn(" ");
		when(insertCpfRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInsertEmail() throws SQLException {
		insertEmailConn = mock(Connection.class);
		insertEmailStmt = mock(PreparedStatement.class);
		insertEmailRs = mock(ResultSet.class);
		
		when(insertEmailConn.prepareStatement(any(String.class))).thenReturn(insertEmailStmt);
		when(insertEmailStmt.executeUpdate()).thenReturn(1);
		
		when(insertEmailStmt.executeQuery()).thenReturn(insertEmailRs);
		when(insertEmailRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
								  .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Gender
								  .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Entity Category
								  .thenReturn(false).thenReturn(false)					// Check CPF already Exist
								  .thenReturn(false).thenReturn(false)					// Check Email already Exist
							  	  .thenReturn(true);									// Insert
		when(insertEmailRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(insertEmailRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(insertEmailRs.getString(any(String.class))).thenReturn(" ");
		when(insertEmailRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioCpfAlreadyExist() throws SQLException {
		cpfAlreadyExistConn = mock(Connection.class);
		cpfAlreadyExistStmt = mock(PreparedStatement.class);
		cpfAlreadyExistRs = mock(ResultSet.class);
		
		when(cpfAlreadyExistConn.prepareStatement(any(String.class))).thenReturn(cpfAlreadyExistStmt);
		when(cpfAlreadyExistStmt.executeUpdate()).thenReturn(1);
		
		when(cpfAlreadyExistStmt.executeQuery()).thenReturn(cpfAlreadyExistRs);
		when(cpfAlreadyExistRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		// Check Owner
									  .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Gender
									  .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Entity Category
									  .thenReturn(true).thenReturn(true).thenReturn(false);		// Check CPF already Exist
		when(cpfAlreadyExistRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(cpfAlreadyExistRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(cpfAlreadyExistRs.getString(any(String.class))).thenReturn(" ");
		when(cpfAlreadyExistRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioEmailAlreadyExist() throws SQLException {
		emailAlreadyExistConn = mock(Connection.class);
		emailAlreadyExistStmt = mock(PreparedStatement.class);
		emailAlreadyExistRs = mock(ResultSet.class);
		
		when(emailAlreadyExistConn.prepareStatement(any(String.class))).thenReturn(emailAlreadyExistStmt);
		when(emailAlreadyExistStmt.executeUpdate()).thenReturn(1);
		
		when(emailAlreadyExistStmt.executeQuery()).thenReturn(emailAlreadyExistRs);
		when(emailAlreadyExistRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
								  		.thenReturn(true).thenReturn(true).thenReturn(false)	// Check Gender
									    .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Entity Category
									    .thenReturn(false).thenReturn(false)					// Check CPF already Exist
									    .thenReturn(true).thenReturn(true).thenReturn(false);	// Check Email already Exist
		when(emailAlreadyExistRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(emailAlreadyExistRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(emailAlreadyExistRs.getString(any(String.class))).thenReturn(" ");
		when(emailAlreadyExistRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidOwner() throws SQLException {
		invalidOwnerConn = mock(Connection.class);
		invalidOwnerStmt = mock(PreparedStatement.class);
		invalidOwnerRs = mock(ResultSet.class);
		
		when(invalidOwnerConn.prepareStatement(any(String.class))).thenReturn(invalidOwnerStmt);
		when(invalidOwnerStmt.executeUpdate()).thenReturn(1);
		
		when(invalidOwnerStmt.executeQuery()).thenReturn(invalidOwnerRs);
		when(invalidOwnerRs.next()).thenReturn(false);							// Check Owner
		when(invalidOwnerRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidOwnerRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidOwnerRs.getString(any(String.class))).thenReturn(" ");
		when(invalidOwnerRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidGender() throws SQLException {
		invalidGenderConn = mock(Connection.class);
		invalidGenderStmt = mock(PreparedStatement.class);
		invalidGenderRs = mock(ResultSet.class);
		
		when(invalidGenderConn.prepareStatement(any(String.class))).thenReturn(invalidGenderStmt);
		when(invalidGenderStmt.executeUpdate()).thenReturn(1);
		
		when(invalidGenderStmt.executeQuery()).thenReturn(invalidGenderRs);
		when(invalidGenderRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
								    .thenReturn(false);										// Check Gender
		when(invalidGenderRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidGenderRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidGenderRs.getString(any(String.class))).thenReturn(" ");
		when(invalidGenderRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidEntityCateg() throws SQLException {
		invalidEntityCategConn = mock(Connection.class);
		invalidEntityCategStmt = mock(PreparedStatement.class);
		invalidEntityCategRs = mock(ResultSet.class);
		
		when(invalidEntityCategConn.prepareStatement(any(String.class))).thenReturn(invalidEntityCategStmt);
		when(invalidEntityCategStmt.executeUpdate()).thenReturn(1);
		
		when(invalidEntityCategStmt.executeQuery()).thenReturn(invalidEntityCategRs);
		when(invalidEntityCategRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
		                                 .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Gender
								         .thenReturn(false);									// Check EntityCateg
		when(invalidEntityCategRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidEntityCategRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidEntityCategRs.getString(any(String.class))).thenReturn(" ");
		when(invalidEntityCategRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	@Test(expected = IllegalStateException.class)
	public void invalidConnection() {
		DeciTree<PersonInfo> tree = initializeInvalidConnection();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PersonInfo> initializeInvalidConnection() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonEmptyDoc();
		option.conn = invalidConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonInsert(option);
	}
	
	
	
	@Test
	public void invalidOwner() {
		DeciTree<PersonInfo> tree = initializeInvalidOwner();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1251);
		assertTrue(result.getFailMessage().equals("Owner data not found on DB"));	
	}
		
	
	
	private DeciTree<PersonInfo> initializeInvalidOwner() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonEmptyDoc();
		option.conn = invalidOwnerConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonInsert(option);
	}
	
	
	
	@Test
	public void invalidGender() {
		DeciTree<PersonInfo> tree = initializeInvalidGender();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1169);
		assertTrue(result.getFailMessage().equals("Gender not found on DB"));	
	}
		
	
	
	private DeciTree<PersonInfo> initializeInvalidGender() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonEmptyDoc();
		option.conn = invalidGenderConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonInsert(option);
	}
	
	
	
	@Test
	public void invalidEntityCateg() {
		DeciTree<PersonInfo> tree = initializeInvalidEntityCateg();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1181);
		assertTrue(result.getFailMessage().equals("Entity Category not found on DB"));	
	}
		
	
	
	private DeciTree<PersonInfo> initializeInvalidEntityCateg() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonEmptyDoc();
		option.conn = invalidEntityCategConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonInsert(option);
	}
	
	
	
	@Test
	public void insertEmptyDoc() {
		DeciTree<PersonInfo> tree = initializeInsertEmptyDoc();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());	
	}
		
	
	
	private DeciTree<PersonInfo> initializeInsertEmptyDoc() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonEmptyDoc();
		option.conn = insertEmptyDocConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonInsert(option);
	}
	
	
	
	private List<PersonInfo> buildPersonEmptyDoc() {
		PersonInfo person = new PersonInfo();		
		
		person.codOwner = 1;
		person.cpf = null;
		person.name = "name";
		person.codGender = 1;
		person.birthDate = LocalDate.of(2018, 11, 30);
		person.codEntityCateg = "C";
		
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
		
		option.recordInfos = buildPersonMissingFieldCodOwner();
		option.conn = insertEmptyDocConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonInsert(option);
	}
	
	
	
	private List<PersonInfo> buildPersonMissingFieldCodOwner() {
		PersonInfo person = new PersonInfo();		
		
		person.codOwner = -1;
		person.cpf = null;
		person.name = "name";
		person.codGender = 1;
		person.birthDate = LocalDate.of(2018, 11, 30);
		person.codEntityCateg = "C";
		
		List<PersonInfo> persons = new ArrayList<>();
		persons.add(person);
		return persons;
	}	
	
	
	
	@Test
	public void missingFieldName() {
		DeciTree<PersonInfo> tree = initializeMissingFieldName();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
		
	
	
	private DeciTree<PersonInfo> initializeMissingFieldName() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonMissingFieldName();
		option.conn = insertEmptyDocConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonInsert(option);
	}
	
	
	
	private List<PersonInfo> buildPersonMissingFieldName() {
		PersonInfo person = new PersonInfo();		
		
		person.codOwner = 1;
		person.cpf = null;
		person.name = null;
		person.codGender = 1;
		person.birthDate = LocalDate.of(2018, 11, 30);
		person.codEntityCateg = "C";
		
		List<PersonInfo> persons = new ArrayList<>();
		persons.add(person);
		return persons;
	}
	
	
	
	@Test
	public void missingFieldCodGender() {
		DeciTree<PersonInfo> tree = initializeMissingFieldCodGender();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
		
	
	
	private DeciTree<PersonInfo> initializeMissingFieldCodGender() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonMissingFieldCodGender();
		option.conn = insertEmptyDocConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonInsert(option);
	}
	
	
	
	private List<PersonInfo> buildPersonMissingFieldCodGender() {
		PersonInfo person = new PersonInfo();		
		
		person.codOwner = 1;
		person.cpf = null;
		person.name = "name";
		person.codGender = -1;
		person.birthDate = LocalDate.of(2018, 11, 30);
		person.codEntityCateg = "C";
		
		List<PersonInfo> persons = new ArrayList<>();
		persons.add(person);
		return persons;
	}
	
	
	
	@Test
	public void missingFieldCodEntityCateg() {
		DeciTree<PersonInfo> tree = initializeMissingFieldCodEntityCateg();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
		
	
	
	private DeciTree<PersonInfo> initializeMissingFieldCodEntityCateg() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonMissingFieldCodEntityCateg();
		option.conn = insertEmptyDocConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonInsert(option);
	}
	
	
	
	private List<PersonInfo> buildPersonMissingFieldCodEntityCateg() {
		PersonInfo person = new PersonInfo();		
		
		person.codOwner = 1;
		person.cpf = null;
		person.name = "name";
		person.codGender = 1;
		person.birthDate = LocalDate.of(2018, 11, 30);
		person.codEntityCateg = null;
		
		List<PersonInfo> persons = new ArrayList<>();
		persons.add(person);
		return persons;
	}
	
	
	
	@Test
	public void cpfAlreadyExist() {
		DeciTree<PersonInfo> tree = initializeCpfAlreadyExist();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1755);
		assertTrue(result.getFailMessage().equals("CPF already exist on DB"));	
	}
		
	
	
	private DeciTree<PersonInfo> initializeCpfAlreadyExist() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonCpf();
		option.conn = cpfAlreadyExistConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonInsert(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullOption() {
		DeciTree<PersonInfo> tree = initializeNullOption();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PersonInfo> initializeNullOption() {		
		return new RootPersonInsert(null);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullSchema() {
		DeciTree<PersonInfo> tree = initializeNullSchema();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PersonInfo> initializeNullSchema() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonCpf();
		option.conn = insertCpfConn;
		option.schemaName = null;
		
		return new RootPersonInsert(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullConnection() {
		DeciTree<PersonInfo> tree = initializeNullConnection();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PersonInfo> initializeNullConnection() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonCpf();
		option.conn = null;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonInsert(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullRecord() {
		DeciTree<PersonInfo> tree = initializeNullRecord();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PersonInfo> initializeNullRecord() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = null;
		option.conn = insertCpfConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonInsert(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void emptyRecords() {
		DeciTree<PersonInfo> tree = initializeEmptyRecords();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PersonInfo> initializeEmptyRecords() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildEmptyRecords();
		option.conn = insertCpfConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonInsert(option);
	}
	
	
	
	private List<PersonInfo> buildEmptyRecords() {
		return new ArrayList<>();
	}	
	
	
	
	@Test
	public void insertCpf() {
		DeciTree<PersonInfo> tree = initializeInsertCpf();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());	
	}
		
	
	
	private DeciTree<PersonInfo> initializeInsertCpf() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonCpf();
		option.conn = insertCpfConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonInsert(option);
	}
	
	
	
	private List<PersonInfo> buildPersonCpf() {
		PersonInfo person = new PersonInfo();		
		
		person.codOwner = 1;
		person.cpf = "30115841849";
		person.name = "name";
		person.codGender = 1;
		person.birthDate = LocalDate.of(2018, 11, 30);
		person.codEntityCateg = "C";
		
		List<PersonInfo> persons = new ArrayList<>();
		persons.add(person);
		return persons;
	}	
	
	
	
	@Test
	public void invalidCpfString() {
		DeciTree<PersonInfo> tree = initializeInvalidCpfString();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1751);
		assertTrue(result.getFailMessage().equals("Only numbers are allowed for CPF"));	
	}
		
	
	
	private DeciTree<PersonInfo> initializeInvalidCpfString() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonInvalidCpfString();
		option.conn = insertCpfConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonInsert(option);
	}
	
	
	
	private List<PersonInfo> buildPersonInvalidCpfString() {
		PersonInfo person = new PersonInfo();		
		
		person.codOwner = 1;
		person.cpf = "3011584184A";
		person.name = "name";
		person.codGender = 1;
		person.birthDate = LocalDate.of(2018, 11, 30);
		person.codEntityCateg = "C";
		
		List<PersonInfo> persons = new ArrayList<>();
		persons.add(person);
		return persons;
	}		
	
	
	
	@Test
	public void invalidCpfLength() {
		DeciTree<PersonInfo> tree = initializeInvalidCpfLength();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1752);
		assertTrue(result.getFailMessage().equals("Invalid CPF length"));	
	}
		
	
	
	private DeciTree<PersonInfo> initializeInvalidCpfLength() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonInvalidCpfLength();
		option.conn = insertCpfConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonInsert(option);
	}
	
	
	
	private List<PersonInfo> buildPersonInvalidCpfLength() {
		PersonInfo person = new PersonInfo();		
		
		person.codOwner = 1;
		person.cpf = "301158418499";
		person.name = "name";
		person.codGender = 1;
		person.birthDate = LocalDate.of(2018, 11, 30);
		person.codEntityCateg = "C";
		
		List<PersonInfo> persons = new ArrayList<>();
		persons.add(person);
		return persons;
	}
	
	
	
	@Test
	public void invalidCpfSequence() {
		DeciTree<PersonInfo> tree = initializeInvalidCpfSequence();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1753);
		assertTrue(result.getFailMessage().equals("Invalid sequence for CPF"));	
	}
		
	
	
	private DeciTree<PersonInfo> initializeInvalidCpfSequence() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonInvalidCpfSequence();
		option.conn = insertCpfConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonInsert(option);
	}
	
	
	
	private List<PersonInfo> buildPersonInvalidCpfSequence() {
		PersonInfo person = new PersonInfo();		
		
		person.codOwner = 1;
		person.cpf = "11111111111";
		person.name = "name";
		person.codGender = 1;
		person.birthDate = LocalDate.of(2018, 11, 30);
		person.codEntityCateg = "C";
		
		List<PersonInfo> persons = new ArrayList<>();
		persons.add(person);
		return persons;
	}
	
	
	
	@Test
	public void invalidCpfNumber() {
		DeciTree<PersonInfo> tree = initializeInvalidCpfNumber();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1754);
		assertTrue(result.getFailMessage().equals("CPF is invalid"));	
	}
		
	
	
	private DeciTree<PersonInfo> initializeInvalidCpfNumber() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonInvalidCpfNumber();
		option.conn = insertCpfConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonInsert(option);
	}
	
	
	
	private List<PersonInfo> buildPersonInvalidCpfNumber() {
		PersonInfo person = new PersonInfo();		
		
		person.codOwner = 1;
		person.cpf = "30115841848";
		person.name = "name";
		person.codGender = 1;
		person.birthDate = LocalDate.of(2018, 11, 30);
		person.codEntityCateg = "C";
		
		List<PersonInfo> persons = new ArrayList<>();
		persons.add(person);
		return persons;
	}
	
	
	
	@Test
	public void emailAlreadyExist() {
		DeciTree<PersonInfo> tree = initializeEmailAlreadyExist();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1758);
		assertTrue(result.getFailMessage().equals("e-email already exist on DB"));	
	}
		
	
	
	private DeciTree<PersonInfo> initializeEmailAlreadyExist() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonEmail();
		option.conn = emailAlreadyExistConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonInsert(option);
	}
	
	
	
	@Test
	public void insertEmail() {
		DeciTree<PersonInfo> tree = initializeInsertEmail();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());	
	}
		
	
	
	private DeciTree<PersonInfo> initializeInsertEmail() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonEmail();
		option.conn = insertEmailConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonInsert(option);
	}
	
	
	
	private List<PersonInfo> buildPersonEmail() {
		PersonInfo person = new PersonInfo();		
		
		person.codOwner = 1;
		person.cpf = "30115841849";
		person.name = "name";
		person.codGender = 1;
		person.birthDate = LocalDate.of(2018, 11, 30);
		person.codEntityCateg = "C";
		person.email = "email@dummy.com";
		
		List<PersonInfo> persons = new ArrayList<>();
		persons.add(person);
		return persons;
	}	
	
	
	
	@Test
	public void techField() {
		DeciTree<PersonInfo> tree = initializeTechField();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1772);
		assertTrue(result.getFailMessage().equals("Person: technical fields shouldn't be filled"));	
	}
		
	
	
	private DeciTree<PersonInfo> initializeTechField() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonTechField();
		option.conn = insertEmptyDocConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonInsert(option);
	}
	
	
	
	private List<PersonInfo> buildPersonTechField() {
		PersonInfo person = new PersonInfo();		
		
		person.codOwner = 1;
		person.codPerson = 1;
		person.cpf = null;
		person.name = "name";
		person.codGender = 1;
		person.birthDate = LocalDate.of(2018, 11, 30);
		person.codEntityCateg = "C";
		
		List<PersonInfo> persons = new ArrayList<>();
		persons.add(person);
		return persons;
	}
}
