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

import br.com.gda.business.form.formPhone.dao.FormPhoneDbTableColumn;
import br.com.gda.business.phone.info.PhoneInfo;
import br.com.gda.common.DbConnection;
import br.com.gda.common.DbSchema;
import br.com.gda.model.decisionTree.DeciResult;
import br.com.gda.model.decisionTree.DeciTree;
import br.com.gda.model.decisionTree.DeciTreeOption;

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class RootPhoneInsertTest {
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;
	
	@Mock private Connection insertT00Conn;
	@Mock private PreparedStatement insertT00Stmt;
	@Mock private ResultSet insertT00Rs;
	
	@Mock private Connection insertT01Conn;
	@Mock private PreparedStatement insertT01Stmt;
	@Mock private ResultSet insertT01Rs;
	
	@Mock private Connection invalidCountryPhoneConn;
	@Mock private PreparedStatement invalidCountryPhoneStmt;
	@Mock private ResultSet invalidCountryPhoneRs;
	
	@Mock private Connection limitExceededConn;
	@Mock private PreparedStatement limitExceededStmt;
	@Mock private ResultSet limitExceededRs;
	
	@Mock private Connection invalidAreaPhoneConn;
	@Mock private PreparedStatement invalidAreaPhoneStmt;
	@Mock private ResultSet invalidAreaPhoneRs;
	
	@Mock private Connection invalidOwnerConn;
	@Mock private PreparedStatement invalidOwnerStmt;
	@Mock private ResultSet invalidOwnerRs;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		initializeScenarioInvalidConnection();
		initializeScenarioInsertT00();
		initializeScenarioInsertT01();
		initializeScenarioInvalidCountryPhone();
		initializeScenarioLimitExceeded();
		initializeScenarioInvalidAreaPhone();
		initializeScenarioInvalidOwner();
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
	
	
	
	private void initializeScenarioInsertT00() throws SQLException {
		insertT00Conn = mock(Connection.class);
		insertT00Stmt = mock(PreparedStatement.class);
		insertT00Rs = mock(ResultSet.class);
		
		when(insertT00Conn.prepareStatement(any(String.class))).thenReturn(insertT00Stmt);
		when(insertT00Stmt.executeUpdate()).thenReturn(1);
		
		when(insertT00Stmt.executeQuery()).thenReturn(insertT00Rs);
		when(insertT00Rs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
		                        .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Country Phone
								.thenReturn(true).thenReturn(true).thenReturn(false)	// Check Limit
								.thenReturn(true).thenReturn(true).thenReturn(false)	// Select Country Phone	
								.thenReturn(true).thenReturn(true).thenReturn(false)	// Phone Form - Check Country
						  	    .thenReturn(false).thenReturn(false)					// Phone Form - Check Exist
						  	    .thenReturn(true);										// Insert
		when(insertT00Rs.getLong(any(String.class))).thenReturn(new Long(1));
		when(insertT00Rs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(insertT00Rs.getString(any(String.class))).thenReturn(" ");
		when(insertT00Rs.getString(FormPhoneDbTableColumn.COL_COD_FORM)).thenReturn("T00");
		when(insertT00Rs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInsertT01() throws SQLException {
		insertT01Conn = mock(Connection.class);
		insertT01Stmt = mock(PreparedStatement.class);
		insertT01Rs = mock(ResultSet.class);
		
		when(insertT01Conn.prepareStatement(any(String.class))).thenReturn(insertT01Stmt);
		when(insertT01Stmt.executeUpdate()).thenReturn(1);
		
		when(insertT01Stmt.executeQuery()).thenReturn(insertT01Rs);
		when(insertT01Rs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
		                        .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Country Phone
								.thenReturn(true).thenReturn(true).thenReturn(false)	// Check Limit
								.thenReturn(true).thenReturn(true).thenReturn(false)	// Select Country Phone							
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Phone Form - Check Country
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Phone Form - Check Exist
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Phone Form - Select						  	    
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Phone Area
						  	    .thenReturn(true);										// Insert
		when(insertT01Rs.getLong(any(String.class))).thenReturn(new Long(1));
		when(insertT01Rs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(insertT01Rs.getString(any(String.class))).thenReturn(" ");
		when(insertT01Rs.getString(FormPhoneDbTableColumn.COL_COD_FORM)).thenReturn("T01");
		when(insertT01Rs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioLimitExceeded() throws SQLException {
		limitExceededConn = mock(Connection.class);
		limitExceededStmt = mock(PreparedStatement.class);
		limitExceededRs = mock(ResultSet.class);
		
		when(limitExceededConn.prepareStatement(any(String.class))).thenReturn(limitExceededStmt);
		when(limitExceededStmt.executeUpdate()).thenReturn(1);
		
		when(limitExceededStmt.executeQuery()).thenReturn(limitExceededRs);
		when(limitExceededRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		// Check Owner
		                            .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Country Phone
							        .thenReturn(true).thenReturn(true).thenReturn(true)			// Check Limit (2 Records)
							        .thenReturn(true).thenReturn(true).thenReturn(true)			// Check Limit (3 Records)
							        .thenReturn(true).thenReturn(true).thenReturn(true)			// Check Limit (3 Records)
							        .thenReturn(true).thenReturn(true).thenReturn(false);		// Check Limit (2 Records)
		when(limitExceededRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(limitExceededRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(limitExceededRs.getString(any(String.class))).thenReturn(" ");
		when(limitExceededRs.getString(FormPhoneDbTableColumn.COL_COD_FORM)).thenReturn("T01");
		when(limitExceededRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidCountryPhone() throws SQLException {
		invalidCountryPhoneConn = mock(Connection.class);
		invalidCountryPhoneStmt = mock(PreparedStatement.class);
		invalidCountryPhoneRs = mock(ResultSet.class);
		
		when(invalidCountryPhoneConn.prepareStatement(any(String.class))).thenReturn(invalidCountryPhoneStmt);
		when(invalidCountryPhoneStmt.executeUpdate()).thenReturn(1);
		
		when(invalidCountryPhoneStmt.executeQuery()).thenReturn(invalidCountryPhoneRs);
		when(invalidCountryPhoneRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		// Check Owner
		                                  .thenReturn(false);										// Check Country Phone
		when(invalidCountryPhoneRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidCountryPhoneRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidCountryPhoneRs.getString(any(String.class))).thenReturn(" ");
		when(invalidCountryPhoneRs.getString(FormPhoneDbTableColumn.COL_COD_FORM)).thenReturn("T01");
		when(invalidCountryPhoneRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidAreaPhone() throws SQLException {
		invalidAreaPhoneConn = mock(Connection.class);
		invalidAreaPhoneStmt = mock(PreparedStatement.class);
		invalidAreaPhoneRs = mock(ResultSet.class);
		
		when(invalidAreaPhoneConn.prepareStatement(any(String.class))).thenReturn(invalidAreaPhoneStmt);
		when(invalidAreaPhoneStmt.executeUpdate()).thenReturn(1);
		
		when(invalidAreaPhoneStmt.executeQuery()).thenReturn(invalidAreaPhoneRs);
		when(invalidAreaPhoneRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
		                               .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Country Phone
									   .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Limit
									   .thenReturn(true).thenReturn(true).thenReturn(false)	// Select Country Phone							
							  	       .thenReturn(true).thenReturn(true).thenReturn(false)	// Phone Form - Check Country
							  	       .thenReturn(true).thenReturn(true).thenReturn(false)	// Phone Form - Check Exist
							  	       .thenReturn(true).thenReturn(true).thenReturn(false)	// Phone Form - Select						  	    
							  	       .thenReturn(false);									// Check Phone Area
		when(invalidAreaPhoneRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidAreaPhoneRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidAreaPhoneRs.getString(any(String.class))).thenReturn(" ");
		when(invalidAreaPhoneRs.getString(FormPhoneDbTableColumn.COL_COD_FORM)).thenReturn("T01");
		when(invalidAreaPhoneRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	private void initializeScenarioInvalidOwner() throws SQLException {
		invalidOwnerConn = mock(Connection.class);
		invalidOwnerStmt = mock(PreparedStatement.class);
		invalidOwnerRs = mock(ResultSet.class);
		
		when(invalidOwnerConn.prepareStatement(any(String.class))).thenReturn(invalidOwnerStmt);
		when(invalidOwnerStmt.executeUpdate()).thenReturn(1);
		
		when(invalidOwnerStmt.executeQuery()).thenReturn(invalidOwnerRs);
		when(invalidOwnerRs.next()).thenReturn(false);								// Check Owner
		when(invalidOwnerRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(invalidOwnerRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(invalidOwnerRs.getString(any(String.class))).thenReturn(" ");
		when(invalidOwnerRs.getString(FormPhoneDbTableColumn.COL_COD_FORM)).thenReturn("T01");
		when(invalidOwnerRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	@Test
	public void multiReference1() {
		DeciTree<PhoneInfo> tree = initializeMultiReference1();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1661);
		assertTrue(result.getFailMessage().equals("Phone has multiple references"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMultiReference1() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesMultiReference1();
		option.conn = insertT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	private List<PhoneInfo> buildInsertPhonesMultiReference1() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codCustomer = 1;
		phone.codStore = 1;
		phone.codEmployee = -1;
		phone.codUser = -1;
		phone.fullNumber = "2125929319";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void multiReference2() {
		DeciTree<PhoneInfo> tree = initializeMultiReference2();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1661);
		assertTrue(result.getFailMessage().equals("Phone has multiple references"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMultiReference2() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesMultiReference2();
		option.conn = insertT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	private List<PhoneInfo> buildInsertPhonesMultiReference2() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codCustomer = 1;
		phone.codStore = -1;
		phone.codEmployee = 1;
		phone.codUser = -1;
		phone.fullNumber = "2125929319";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void multiReference3() {
		DeciTree<PhoneInfo> tree = initializeMultiReference3();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1661);
		assertTrue(result.getFailMessage().equals("Phone has multiple references"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMultiReference3() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesMultiReference3();
		option.conn = insertT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	private List<PhoneInfo> buildInsertPhonesMultiReference3() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codCustomer = -1;
		phone.codStore = 1;
		phone.codEmployee = 1;
		phone.codUser = -1;
		phone.fullNumber = "2125929319";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void multiReference4() {
		DeciTree<PhoneInfo> tree = initializeMultiReference4();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1661);
		assertTrue(result.getFailMessage().equals("Phone has multiple references"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMultiReference4() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesMultiReference4();
		option.conn = insertT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	private List<PhoneInfo> buildInsertPhonesMultiReference4() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codCustomer = 1;
		phone.codStore = 1;
		phone.codEmployee = 1;
		phone.codUser = 1;
		phone.fullNumber = "2125929319";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void multiReference5() {
		DeciTree<PhoneInfo> tree = initializeMultiReference5();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1661);
		assertTrue(result.getFailMessage().equals("Phone has multiple references"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMultiReference5() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesMultiReference5();
		option.conn = insertT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	private List<PhoneInfo> buildInsertPhonesMultiReference5() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codCustomer = 1;
		phone.codStore = -1;
		phone.codEmployee = -1;
		phone.codUser = 1;
		phone.fullNumber = "2125929319";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void multiReference6() {
		DeciTree<PhoneInfo> tree = initializeMultiReference6();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1661);
		assertTrue(result.getFailMessage().equals("Phone has multiple references"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMultiReference6() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesMultiReference6();
		option.conn = insertT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	private List<PhoneInfo> buildInsertPhonesMultiReference6() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codCustomer = -1;
		phone.codStore = 1;
		phone.codEmployee = -1;
		phone.codUser = 1;
		phone.fullNumber = "2125929319";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void multiReference7() {
		DeciTree<PhoneInfo> tree = initializeMultiReference7();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1661);
		assertTrue(result.getFailMessage().equals("Phone has multiple references"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMultiReference7() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesMultiReference7();
		option.conn = insertT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	private List<PhoneInfo> buildInsertPhonesMultiReference7() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codCustomer = -1;
		phone.codStore = -1;
		phone.codEmployee = 1;
		phone.codUser = 1;
		phone.fullNumber = "2125929319";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void missingReference() {
		DeciTree<PhoneInfo> tree = initializeMissingReference();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1660);
		assertTrue(result.getFailMessage().equals("No reference added to Phone"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMissingReference() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPhonesMissingReference();
		option.conn = insertT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	private List<PhoneInfo> buildPhonesMissingReference() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codCustomer = -1;
		phone.codStore = -1;
		phone.codEmployee = -1;
		phone.fullNumber = "2125929319";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void invalidLength() {
		DeciTree<PhoneInfo> tree = initializeInvalidLength();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1563);
		assertTrue(result.getFailMessage().equals("Invalid phone length"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeInvalidLength() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesInvalidLength();
		option.conn = insertT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	private List<PhoneInfo> buildInsertPhonesInvalidLength() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codCustomer = 1;
		phone.fullNumber = "1";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void missingFieldFullNumber() {
		DeciTree<PhoneInfo> tree = initializeMissingFieldFullNumber();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMissingFieldFullNumber() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesMissingFieldFullNumber();
		option.conn = insertT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	private List<PhoneInfo> buildInsertPhonesMissingFieldFullNumber() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codCustomer = 1;
		phone.fullNumber = null;
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void missingFieldCodCountryPhone() {
		DeciTree<PhoneInfo> tree = initializeMissingFieldCodCountryPhone();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMissingFieldCodCountryPhone() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesMissingFieldCodCountryPhone();
		option.conn = insertT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	private List<PhoneInfo> buildInsertPhonesMissingFieldCodCountryPhone() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codCustomer = 1;
		phone.fullNumber = "2125696969";
		phone.codCountryPhone = -1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void missingFieldOwner() {
		DeciTree<PhoneInfo> tree = initializeMissingFieldOwner();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeMissingFieldOwner() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesMissingFieldOwner();
		option.conn = insertT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	private List<PhoneInfo> buildInsertPhonesMissingFieldOwner() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = -1;
		phone.codCustomer = 1;
		phone.fullNumber = "2125696969";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void invalidCountryPhone() {
		DeciTree<PhoneInfo> tree = initializeInvalidCountryPhone();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1171);
		assertTrue(result.getFailMessage().equals("Phone Country code not found on DB"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeInvalidCountryPhone() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesT01();
		option.conn = invalidCountryPhoneConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	@Test
	public void limitExceeded() {
		DeciTree<PhoneInfo> tree = initializeLimitExceeded();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1662);
		assertTrue(result.getFailMessage().equals("Phone limit exceeded. Delete old phones before adding new ones"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeLimitExceeded() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesT01();
		option.conn = limitExceededConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	@Test
	public void t01InvalidLength1() {
		DeciTree<PhoneInfo> tree = initializeT01InvalidLength1();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1556);
		assertTrue(result.getFailMessage().equals("Invalid phone length. Area code is expected"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeT01InvalidLength1() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesT01InvalidLength1();
		option.conn = insertT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	private List<PhoneInfo> buildInsertPhonesT01InvalidLength1() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codCustomer = 1;
		phone.fullNumber = "2125696969999";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void t01InvalidLength2() {
		DeciTree<PhoneInfo> tree = initializeT01InvalidLength2();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1556);
		assertTrue(result.getFailMessage().equals("Invalid phone length. Area code is expected"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeT01InvalidLength2() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesT01InvalidLength2();
		option.conn = insertT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	private List<PhoneInfo> buildInsertPhonesT01InvalidLength2() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codCustomer = 1;
		phone.fullNumber = "212569696";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void t01InvalidString() {
		DeciTree<PhoneInfo> tree = initializeT01InvalidString();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1557);
		assertTrue(result.getFailMessage().equals("Only numbers are allowed for phone"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeT01InvalidString() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesT01InvalidString();
		option.conn = insertT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	private List<PhoneInfo> buildInsertPhonesT01InvalidString() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codCustomer = 1;
		phone.fullNumber = "212569696A";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void t01InvalidSequence() {
		DeciTree<PhoneInfo> tree = initializeT01InvalidSequence();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1559);
		assertTrue(result.getFailMessage().equals("Invalid sequence for phone number"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeT01InvalidSequence() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesT01InvalidSequence();
		option.conn = insertT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	private List<PhoneInfo> buildInsertPhonesT01InvalidSequence() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codCustomer = 1;
		phone.fullNumber = "2111111111";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void t01InvalidMobile() {
		DeciTree<PhoneInfo> tree = initializeT01InvalidMobile();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1554);
		assertTrue(result.getFailMessage().equals("Phone number is invalid"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeT01InvalidMobile() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesT01InvalidMobile();
		option.conn = insertT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	private List<PhoneInfo> buildInsertPhonesT01InvalidMobile() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codCustomer = 1;
		phone.fullNumber = "2198522852";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}
	
	
	
	@Test
	public void invalidAreaPhone() {
		DeciTree<PhoneInfo> tree = initializeInvalidAreaPhone();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1179);
		assertTrue(result.getFailMessage().equals("Phone Area not found on DB"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeInvalidAreaPhone() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesT01();
		option.conn = invalidAreaPhoneConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	@Test
	public void invalidOwner() {
		DeciTree<PhoneInfo> tree = initializeInvalidOwner();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1251);
		assertTrue(result.getFailMessage().equals("Owner data not found on DB"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeInvalidOwner() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesT01();
		option.conn = invalidOwnerConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	@Test(expected = IllegalStateException.class)
	public void invalidConnection() {
		DeciTree<PhoneInfo> tree = initializeInvalidConnection();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PhoneInfo> initializeInvalidConnection() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesT01();
		option.conn = invalidConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullOption() {
		DeciTree<PhoneInfo> tree = initializeNullOption();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PhoneInfo> initializeNullOption() {		
		return new RootPhoneInsert(null);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullSchema() {
		DeciTree<PhoneInfo> tree = initializeNullSchema();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PhoneInfo> initializeNullSchema() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesT01();
		option.conn = insertT01Conn;
		option.schemaName = null;
		
		return new RootPhoneInsert(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullConnection() {
		DeciTree<PhoneInfo> tree = initializeNullConnection();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PhoneInfo> initializeNullConnection() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesT01();
		option.conn = null;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullRecord() {
		DeciTree<PhoneInfo> tree = initializeNullRecord();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PhoneInfo> initializeNullRecord() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = null;
		option.conn = insertT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	@Test(expected = IllegalArgumentException.class)
	public void emptyRecords() {
		DeciTree<PhoneInfo> tree = initializeEmptyRecords();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PhoneInfo> initializeEmptyRecords() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildEmptyRecords();
		option.conn = insertT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	private List<PhoneInfo> buildEmptyRecords() {
		return new ArrayList<>();
	}	
	
	
	
	@Test
	public void insertT01() {
		DeciTree<PhoneInfo> tree = initializeInsertT01();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());		
		PhoneInfo phone = result.getResultset().get(0);
		assertTrue(phone.codForm.equals("T01"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeInsertT01() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesT01();
		option.conn = insertT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	private List<PhoneInfo> buildInsertPhonesT01() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codCustomer = 1;
		phone.fullNumber = "2125696969";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}	
	
	
	
	@Test
	public void t00InvalidLenght1() {
		DeciTree<PhoneInfo> tree = initializeT00InvalidLenght1();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1563);
		assertTrue(result.getFailMessage().equals("Invalid phone length"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeT00InvalidLenght1() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildT00InvalidLenght1();
		option.conn = insertT00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	private List<PhoneInfo> buildT00InvalidLenght1() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codCustomer = 1;
		phone.fullNumber = "1234";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}	
	
	
	
	@Test
	public void t00InvalidLenght2() {
		DeciTree<PhoneInfo> tree = initializeT00InvalidLenght2();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1563);
		assertTrue(result.getFailMessage().equals("Invalid phone length"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeT00InvalidLenght2() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildT00InvalidLenght2();
		option.conn = insertT00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	private List<PhoneInfo> buildT00InvalidLenght2() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codCustomer = 1;
		phone.fullNumber = "1111111111222222222233333333334";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}	
	
	
	
	@Test
	public void insertT00() {
		DeciTree<PhoneInfo> tree = initializeInsertT00();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());		
		PhoneInfo phone = result.getResultset().get(0);
		assertTrue(phone.codForm.equals("T00"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeInsertT00() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesT00();
		option.conn = insertT00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	private List<PhoneInfo> buildInsertPhonesT00() {
		PhoneInfo phone = new PhoneInfo();		
		
		phone.codOwner = 1;
		phone.codCustomer = 1;
		phone.fullNumber = "2125696969";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}	
	
	
	
	@Test
	public void techField() {
		DeciTree<PhoneInfo> tree = initializeTechField();
		tree.makeDecision();
		DeciResult<PhoneInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1573);
		assertTrue(result.getFailMessage().equals("Phone: technical fields shouldn't be filled"));	
	}
		
	
	
	private DeciTree<PhoneInfo> initializeTechField() {
		DeciTreeOption<PhoneInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertPhonesTechField();
		option.conn = insertT01Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPhoneInsert(option);
	}
	
	
	
	private List<PhoneInfo> buildInsertPhonesTechField() {
		PhoneInfo phone = new PhoneInfo();
		
		
		phone.codOwner = 1;
		phone.codPhone = 1;
		phone.codCustomer = 1;
		phone.fullNumber = "2125696969";
		phone.codCountryPhone = 1;
		phone.complement = "teste";
		
		List<PhoneInfo> phones = new ArrayList<>();
		phones.add(phone);
		return phones;
	}	
}
