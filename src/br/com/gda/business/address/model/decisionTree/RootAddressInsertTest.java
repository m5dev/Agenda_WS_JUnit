package br.com.gda.business.address.model.decisionTree;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
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

import br.com.gda.business.address.info.AddressInfo;
import br.com.gda.common.DbConnection;
import br.com.gda.common.DbSchema;
import br.com.gda.model.decisionTree.DeciResult;
import br.com.gda.model.decisionTree.DeciTree;
import br.com.gda.model.decisionTree.DeciTreeOption;

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class RootAddressInsertTest {
	@Mock private Connection insertA00Conn;
	@Mock private PreparedStatement insertA00Stmt;
	@Mock private ResultSet inserA00tRs;
	
	@Mock private Connection insertA01Conn;
	@Mock private PreparedStatement insertA01Stmt;
	@Mock private ResultSet inserA01tRs;
	
	@Mock private Connection invalidConn;	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		PowerMockito.mockStatic(DbConnection.class);
		initializeScenarioInsertA00();
	}
	
	
	
	private void initializeScenarioInsertA00() throws SQLException {
		insertA00Conn = mock(Connection.class);
		insertA00Stmt = mock(PreparedStatement.class);
		inserA00tRs = mock(ResultSet.class);
		
		when(insertA00Conn.prepareStatement(any(String.class))).thenReturn(insertA00Stmt);
		when(insertA00Stmt.executeUpdate()).thenReturn(1);
		
		when(insertA00Stmt.executeQuery()).thenReturn(inserA00tRs);
		when(inserA00tRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Country
						  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Address Form - Check Country
						  	    .thenReturn(false).thenReturn(false)					// Address Form - Check Exist
						  	    .thenReturn(true);										// Insert
		when(inserA00tRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(inserA00tRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(inserA00tRs.getString(any(String.class))).thenReturn(" ");
		when(inserA00tRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
	}
	
	
	
	@Test
	public void a00MissingFieldLine1() {
		DeciTree<AddressInfo> tree = initializeA00MissingFieldLine1();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
	
	
	
	protected DeciTree<AddressInfo> initializeA00MissingFieldLine1() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesA00MissingFieldLine1();
		option.conn = insertA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressInsert(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesA00MissingFieldLine1() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codCustomer = 1;
		address.codCountry = "X";
		address.line1 = null;
		address.line2 = "X";
		address.line3 = "X";
		address.line4 = "X";
		address.line5 = "X";
		address.line6 = "X";
		address.line7 = "X";		
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}
	
	
	
	
	@Test
	public void a00MissingFieldCountry() {
		DeciTree<AddressInfo> tree = initializeA00MissingFieldCountry();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1);
		assertTrue(result.getFailMessage().equals("Mandatory field is empty"));	
	}
	
	
	
	protected DeciTree<AddressInfo> initializeA00MissingFieldCountry() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildAddressesA00MissingFieldCountry();
		option.conn = insertA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressInsert(option);
	}
	
	
	
	private List<AddressInfo> buildAddressesA00MissingFieldCountry() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codCustomer = 1;
		address.codCountry = null;
		address.line1 = "X";
		address.line2 = "X";
		address.line3 = "X";
		address.line4 = "X";
		address.line5 = "X";
		address.line6 = "X";
		address.line7 = "X";		
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}

	
	
	@Test
	public void insertA00() {
		DeciTree<AddressInfo> tree = initializeInsertA00();
		tree.makeDecision();
		DeciResult<AddressInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());
		
		AddressInfo address = result.getResultset().get(0);
		assertTrue(address.codForm.equals("A00"));	
	}
		
	
	
	protected DeciTree<AddressInfo> initializeInsertA00() {
		DeciTreeOption<AddressInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildInsertAddresses();
		option.conn = insertA00Conn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootAddressInsert(option);
	}
	
	
	
	private List<AddressInfo> buildInsertAddresses() {
		AddressInfo address = new AddressInfo();
		
		address.codOwner = 1;
		address.codCustomer = 1;
		address.codCountry = "X";
		address.line1 = "X";
		address.line2 = "X";
		address.line3 = "X";
		address.line4 = "X";
		address.line5 = "X";
		address.line6 = "X";
		address.line7 = "X";		
		
		List<AddressInfo> addresses = new ArrayList<>();
		addresses.add(address);
		return addresses;
	}
}
