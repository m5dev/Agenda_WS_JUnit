package br.com.gda.business.employeeWorkTime.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import br.com.gda.business.employeeWorkTime.dao.EmpWTimeInsertSingle;
import br.com.gda.business.employeeWorkTime.info.EmpWTimeInfo;
import br.com.gda.common.Common;
import br.com.gda.dao.DaoStmt;
import br.com.gda.dao.DaoStmtExecOption;

import static org.mockito.Mockito.*;

public class EmpWTimeStmtInsertSingleTest {
	@Mock private Connection validConn;
	@Mock private Connection invalidConnWithoutStmt;
	@Mock private Connection invalidConnWithStmt;
	@Mock private PreparedStatement validStmt;
	@Mock private PreparedStatement invalidStmt;
	@Mock private ResultSet rs;
	private DaoStmtExecOption<EmpWTimeInfo> option;
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
		DaoStmt<EmpWTimeInfo> insertStatement = new EmpWTimeInsertSingle(option.conn, option.recordInfo, option.schemaName);
		
		assertTrue(insertStatement.checkStmtGeneration());
		insertStatement.generateStmt();
	}
	
	
	
	private void initializeToPass() {
		workingTime = new EmpWTimeInfo();
		workingTime.codOwner = 10;
		workingTime.codStore = 9;
		workingTime.codEmployee = 8;
		
		
		this.option = new DaoStmtExecOption<>();
		option.conn = this.validConn;
		option.schemaName = Common.SCHEMA_NAME;
		option.recordInfo = this.workingTime;
	}
	
	
	
	@Test (expected = NullPointerException.class)
	public void testNullArgumentOption() throws SQLException {
		initializeToFailNullArgumentOption();		
		DaoStmt<EmpWTimeInfo> insertStatement = new EmpWTimeInsertSingle(option.conn, option.recordInfo, option.schemaName);
		
		assertFalse(insertStatement.checkStmtGeneration());
		insertStatement.generateStmt();
	}	
	
	
	
	private void initializeToFailNullArgumentOption() {
		workingTime = new EmpWTimeInfo();
		workingTime.codOwner = 10;
		workingTime.codStore = 9;
		workingTime.codEmployee = 8;
		
		
		this.option = new DaoStmtExecOption<>();
	}
	
	
	
	@Test (expected = NullPointerException.class)
	public void testNullArgumentOptionConn() throws SQLException {
		initializeToFailNullArgumentOptionConn();		
		DaoStmt<EmpWTimeInfo> insertStatement = new EmpWTimeInsertSingle(option.conn, option.recordInfo, option.schemaName);
		
		assertFalse(insertStatement.checkStmtGeneration());
		insertStatement.generateStmt();
	}	
	
	
	
	private void initializeToFailNullArgumentOptionConn() {
		workingTime = new EmpWTimeInfo();
		workingTime.codOwner = 10;
		workingTime.codStore = 9;
		workingTime.codEmployee = 8;
		
		
		this.option = new DaoStmtExecOption<>();
		option.schemaName = Common.SCHEMA_NAME;
		option.recordInfo = this.workingTime;
	}
	
	
	
	@Test (expected = SQLException.class)
	public void testInvalidArgumentOptionConn() throws SQLException {
		initializeToFailInvalidArgumentOptionConn();		
		DaoStmt<EmpWTimeInfo> insertStatement = new EmpWTimeInsertSingle(option.conn, option.recordInfo, option.schemaName);
		
		assertFalse(insertStatement.checkStmtGeneration());
		insertStatement.generateStmt();
	}	
	
	
	
	private void initializeToFailInvalidArgumentOptionConn() {
		workingTime = new EmpWTimeInfo();
		workingTime.codOwner = 10;
		workingTime.codStore = 9;
		workingTime.codEmployee = 8;
		
		
		this.option = new DaoStmtExecOption<>();
		option.schemaName = Common.SCHEMA_NAME;
		option.recordInfo = this.workingTime;
		option.conn = invalidConnWithoutStmt;
	}
	
	
	
	@Test (expected = SQLException.class)
	public void testInvalidArgumentOptionConnStmt() throws SQLException {
		initializeToFailInvalidArgumentOptionConnStmt();		
		EmpWTimeInsertSingle insertStatement = new EmpWTimeInsertSingle(option.conn, option.recordInfo, option.schemaName);
		
		assertTrue(insertStatement.checkStmtGeneration());
		insertStatement.generateStmt();
	}	
	
	
	
	private void initializeToFailInvalidArgumentOptionConnStmt() {
		workingTime = new EmpWTimeInfo();
		workingTime.codOwner = 10;
		workingTime.codStore = 9;
		workingTime.codEmployee = 8;
		
		
		this.option = new DaoStmtExecOption<>();
		option.schemaName = Common.SCHEMA_NAME;
		option.recordInfo = this.workingTime;
		option.conn = invalidConnWithStmt;
	}
	
	
	
	@Test (expected = NullPointerException.class)
	public void testNullArgumentOptionSchemaName() throws SQLException {
		initializeToFailNullArgumentOptionSchemaName();		
		EmpWTimeInsertSingle insertStatement = new EmpWTimeInsertSingle(option.conn, option.recordInfo, option.schemaName);
		
		assertFalse(insertStatement.checkStmtGeneration());
		insertStatement.generateStmt();
	}	
	
	
	
	private void initializeToFailNullArgumentOptionSchemaName() {
		workingTime = new EmpWTimeInfo();
		workingTime.codOwner = 10;
		workingTime.codStore = 9;
		workingTime.codEmployee = 8;
		
		
		this.option = new DaoStmtExecOption<>();
		option.conn = this.validConn;
		option.schemaName = null;
		option.recordInfo = this.workingTime;
	}
}
