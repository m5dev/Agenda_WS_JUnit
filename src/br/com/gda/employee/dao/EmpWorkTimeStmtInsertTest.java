package br.com.gda.employee.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import br.com.gda.employee.info.EmpWTimeInfo;
import br.com.gda.sql.SqlStmt;

import static org.mockito.Mockito.*;

public class EmpWorkTimeStmtInsertTest {
	@Mock private Connection validConn;
	@Mock private Connection invalidConnWithoutStmt;
	@Mock private Connection invalidConnWithStmt;
	@Mock private PreparedStatement validStmt;
	@Mock private PreparedStatement invalidStmt;
	@Mock private ResultSet rs;
	private EmpStmtOption option;
	private EmpWTimeInfo workingTime;
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		validStmt = mock(PreparedStatement.class);
		invalidStmt = mock(PreparedStatement.class);
		rs = mock(ResultSet.class);		
		validConn = mock(Connection.class);
		invalidConnWithoutStmt = mock(Connection.class);
		invalidConnWithStmt = mock(Connection.class);
		
		when(invalidConnWithoutStmt.prepareStatement(anyString())).thenThrow(new SQLException());
		
		when(validConn.prepareStatement(any(String.class))).thenReturn(validStmt);		
		when(validStmt.executeQuery()).thenReturn(rs);
		doNothing().when(validStmt).setString(anyInt(), anyString());
		doNothing().when(validStmt).setLong(anyInt(), anyLong());
		doNothing().when(validStmt).setTime(anyInt(), any(Time.class));
		
		when(invalidConnWithStmt.prepareStatement(anyString())).thenReturn(invalidStmt);
		when(invalidStmt.executeQuery()).thenReturn(rs);
		doThrow(new SQLException()).when(invalidStmt).setString(anyInt(), anyString());
		doThrow(new SQLException()).when(invalidStmt).setLong(anyInt(), anyLong());
		doThrow(new SQLException()).when(invalidStmt).setTime(anyInt(), any(Time.class));
	}

	
	
	@Test
	public void ordinaryUsage() throws SQLException {
		initializeToPass();
		SqlStmt<EmpWTimeInfo> insertStatement = new EmpWtimeStmtInsert(this.option);
		
		assertTrue(insertStatement.checkStmtGeneration());
		insertStatement.generateStmt();
	}
	
	
	
	private void initializeToPass() {
		workingTime = new EmpWTimeInfo();
		workingTime.codOwner = 10;
		workingTime.codStore = 9;
		workingTime.codEmployee = 8;
		
		
		this.option = new EmpStmtOption();
		option.conn = this.validConn;
		option.schemaName = Common.SCHEMA_NAME;
		option.recordInfo = this.workingTime;
	}
	
	
	
	@Test (expected = NullPointerException.class)
	public void testNullArgumentOption() throws SQLException {
		initializeToFailNullArgumentOption();		
		SqlStmt<EmpWTimeInfo> insertStatement = new EmpWtimeStmtInsert(this.option);
		
		assertFalse(insertStatement.checkStmtGeneration());
		insertStatement.generateStmt();
	}	
	
	
	
	private void initializeToFailNullArgumentOption() {
		workingTime = new EmpWTimeInfo();
		workingTime.codOwner = 10;
		workingTime.codStore = 9;
		workingTime.codEmployee = 8;
		
		
		this.option = new EmpStmtOption();
	}
	
	
	
	@Test (expected = NullPointerException.class)
	public void testNullArgumentOptionConn() throws SQLException {
		initializeToFailNullArgumentOptionConn();		
		SqlStmt<EmpWTimeInfo> insertStatement = new EmpWtimeStmtInsert(this.option);
		
		assertFalse(insertStatement.checkStmtGeneration());
		insertStatement.generateStmt();
	}	
	
	
	
	private void initializeToFailNullArgumentOptionConn() {
		workingTime = new EmpWTimeInfo();
		workingTime.codOwner = 10;
		workingTime.codStore = 9;
		workingTime.codEmployee = 8;
		
		
		this.option = new EmpStmtOption();
		option.schemaName = Common.SCHEMA_NAME;
		option.recordInfo = this.workingTime;
	}
	
	
	
	@Test (expected = SQLException.class)
	public void testInvalidArgumentOptionConn() throws SQLException {
		initializeToFailInvalidArgumentOptionConn();		
		SqlStmt<EmpWTimeInfo> insertStatement = new EmpWtimeStmtInsert(this.option);
		
		assertFalse(insertStatement.checkStmtGeneration());
		insertStatement.generateStmt();
	}	
	
	
	
	private void initializeToFailInvalidArgumentOptionConn() {
		workingTime = new EmpWTimeInfo();
		workingTime.codOwner = 10;
		workingTime.codStore = 9;
		workingTime.codEmployee = 8;
		
		
		this.option = new EmpStmtOption();
		option.schemaName = Common.SCHEMA_NAME;
		option.recordInfo = this.workingTime;
		option.conn = invalidConnWithoutStmt;
	}
	
	
	
	@Test (expected = SQLException.class)
	public void testInvalidArgumentOptionConnStmt() throws SQLException {
		initializeToFailInvalidArgumentOptionConnStmt();		
		EmpWtimeStmtInsert insertStatement = new EmpWtimeStmtInsert(this.option);
		
		assertTrue(insertStatement.checkStmtGeneration());
		insertStatement.generateStmt();
	}	
	
	
	
	private void initializeToFailInvalidArgumentOptionConnStmt() {
		workingTime = new EmpWTimeInfo();
		workingTime.codOwner = 10;
		workingTime.codStore = 9;
		workingTime.codEmployee = 8;
		
		
		this.option = new EmpStmtOption();
		option.schemaName = Common.SCHEMA_NAME;
		option.recordInfo = this.workingTime;
		option.conn = invalidConnWithStmt;
	}
	
	
	
	@Test (expected = NullPointerException.class)
	public void testNullArgumentOptionSchemaName() throws SQLException {
		initializeToFailNullArgumentOptionSchemaName();		
		EmpWtimeStmtInsert insertStatement = new EmpWtimeStmtInsert(this.option);
		
		assertFalse(insertStatement.checkStmtGeneration());
		insertStatement.generateStmt();
	}	
	
	
	
	private void initializeToFailNullArgumentOptionSchemaName() {
		workingTime = new EmpWTimeInfo();
		workingTime.codOwner = 10;
		workingTime.codStore = 9;
		workingTime.codEmployee = 8;
		
		
		this.option = new EmpStmtOption();
		option.conn = this.validConn;
		option.schemaName = null;
		option.recordInfo = this.workingTime;
	}

}
