package br.com.gda.business.employeeWorkTime.dao;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import br.com.gda.business.employeeWorkTime.dao.EmpWTimeUpdate;
import br.com.gda.business.employeeWorkTime.info.EmpWTimeInfo;
import br.com.gda.common.Common;
import br.com.gda.dao.DaoStmtExec;
import br.com.gda.dao.DaoStmtExecOption;

public class EmpWTimeStmtUpdateTest {
	@Mock private Connection validConn;
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement validStmt;
	@Mock private ResultSet rs;
	
	private List<EmpWTimeInfo> workingTimes = new ArrayList<>();
	private List<DaoStmtExecOption<EmpWTimeInfo>> sqlStatemetOptions = new ArrayList<>();
	private DaoStmtExec<EmpWTimeInfo> sqlStatemetExecutor;
	
	
	
	@Before
	public void initializeMockObjects() throws SQLException {
		validConn = mock(Connection.class);
		validStmt = mock(PreparedStatement.class);
		invalidConn = mock(Connection.class);
		rs = mock(ResultSet.class);
		when(rs.next()).thenReturn(false);
		when(validConn.prepareStatement(any(String.class))).thenReturn(validStmt);
		when(validStmt.executeQuery()).thenReturn(rs);
		when(validStmt.executeUpdate()).thenReturn(1);
		when(invalidConn.prepareStatement(anyString())).thenThrow(new SQLException());
		doNothing().when(validStmt).setString(anyInt(), anyString());
		doNothing().when(validStmt).setLong(anyInt(), anyLong());
		doNothing().when(validStmt).setTime(anyInt(), any(Time.class));
	}
	
	
	@Test
	public void ordinaryUsage() throws SQLException {
		initializeOrdinaryUsage();
		this.sqlStatemetExecutor.executeStmt();
	}
	
	
	
	private void initializeOrdinaryUsage() {
		initializeWorkingTime();			
			
		for (EmpWTimeInfo eachInfo : this.workingTimes) {
			DaoStmtExecOption<EmpWTimeInfo> oneOption = new DaoStmtExecOption<>();			
			oneOption.conn = this.validConn;
			oneOption.schemaName = Common.SCHEMA_NAME;
			oneOption.recordInfo = eachInfo;
			this.sqlStatemetOptions.add(oneOption);
		}		
		
		this.sqlStatemetExecutor = new EmpWTimeUpdate(this.sqlStatemetOptions);
	}
	
	
	
	@Test (expected = NullPointerException.class)
	public void nullSchema() throws SQLException {
		initializeNullSchema();
		this.sqlStatemetExecutor.executeStmt();
	}
	
	
	
	private void initializeNullSchema() {
		initializeWorkingTime();			
			
		for (EmpWTimeInfo eachInfo : this.workingTimes) {
			DaoStmtExecOption<EmpWTimeInfo> oneOption = new DaoStmtExecOption<>();			
			oneOption.conn = this.validConn;
			oneOption.schemaName = null;
			oneOption.recordInfo = eachInfo;
			this.sqlStatemetOptions.add(oneOption);
		}		
		
		this.sqlStatemetExecutor = new EmpWTimeUpdate(this.sqlStatemetOptions);
	}
	
	
	
	@Test (expected = NullPointerException.class)
	public void nullConnection() throws SQLException {
		initializeNullConnection();
		this.sqlStatemetExecutor.executeStmt();
	}
	
	
	
	private void initializeNullConnection() {
		initializeWorkingTime();			
			
		for (EmpWTimeInfo eachInfo : this.workingTimes) {
			DaoStmtExecOption<EmpWTimeInfo> oneOption = new DaoStmtExecOption<>();			
			oneOption.conn = null;
			oneOption.schemaName = Common.SCHEMA_NAME;
			oneOption.recordInfo = eachInfo;
			this.sqlStatemetOptions.add(oneOption);
		}		
		
		this.sqlStatemetExecutor = new EmpWTimeUpdate(this.sqlStatemetOptions);
	}
	
	
	
	@Test (expected = NullPointerException.class)
	public void nullWorkingTime() throws SQLException {
		initializeNullWorkingTime();
		this.sqlStatemetExecutor.executeStmt();
	}
	
	
	
	@SuppressWarnings("unused")
	private void initializeNullWorkingTime() {
		initializeWorkingTime();			
			
		for (EmpWTimeInfo eachInfo : this.workingTimes) {
			DaoStmtExecOption<EmpWTimeInfo> oneOption = new DaoStmtExecOption<>();			
			oneOption.conn = this.validConn;
			oneOption.schemaName = Common.SCHEMA_NAME;
			oneOption.recordInfo = null;
			this.sqlStatemetOptions.add(oneOption);
		}		
		
		this.sqlStatemetExecutor = new EmpWTimeUpdate(this.sqlStatemetOptions);
	}
	
	
	
	@Test (expected = IllegalArgumentException.class)
	public void emptyStatementOption() throws SQLException {
		initializeEmptyStatementOption();
		this.sqlStatemetExecutor.executeStmt();
	}
	
	
	
	private void initializeEmptyStatementOption() {
		initializeWorkingTime();
		
		this.sqlStatemetExecutor = new EmpWTimeUpdate(this.sqlStatemetOptions);
	}
	
	
	
	@Test (expected = SQLException.class)
	public void invalidConnection() throws SQLException {
		initializeInvalidConnection();
		this.sqlStatemetExecutor.executeStmt();
	}
	
	
	
	private void initializeInvalidConnection() {
		initializeWorkingTime();			
			
		for (EmpWTimeInfo eachInfo : this.workingTimes) {
			DaoStmtExecOption<EmpWTimeInfo> oneOption = new DaoStmtExecOption<>();			
			oneOption.conn = this.invalidConn;
			oneOption.schemaName = Common.SCHEMA_NAME;
			oneOption.recordInfo = eachInfo;
			this.sqlStatemetOptions.add(oneOption);
		}		
		
		this.sqlStatemetExecutor = new EmpWTimeUpdate(this.sqlStatemetOptions);
	}
	
	
	
	private void initializeWorkingTime() {
		try {
			EmpWTimeInfo workingTime = new EmpWTimeInfo();
			workingTime.codOwner = 8;
			workingTime.codStore = 15;
			workingTime.codEmployee = 54;
			workingTime.codWeekday = 1;
			workingTime.beginTime = LocalTime.of(9, 00);
			workingTime.endTime = LocalTime.of(18, 00);
			
			EmpWTimeInfo clone = (EmpWTimeInfo) workingTime.clone();;
			this.workingTimes.add(clone);
			
			
			workingTime.codWeekday = 2;
			workingTime.beginTime = LocalTime.of(9, 00);
			workingTime.endTime = LocalTime.of(13, 00);
			clone = (EmpWTimeInfo) workingTime.clone();;
			this.workingTimes.add(clone);
			
			
			workingTime.codWeekday = 3;
			workingTime.beginTime = LocalTime.of(13, 00);
			workingTime.endTime = LocalTime.of(20, 00);
			clone = (EmpWTimeInfo) workingTime.clone();;
			this.workingTimes.add(clone);	

		} catch (CloneNotSupportedException e) {
			throw new IllegalStateException(e);
		}				
	}
}
