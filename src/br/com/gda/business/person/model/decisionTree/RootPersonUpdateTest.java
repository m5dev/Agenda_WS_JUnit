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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.gda.business.person.dao.PersonDbTableColumn;
import br.com.gda.business.person.info.PersonInfo;
import br.com.gda.common.DbConnection;
import br.com.gda.common.DbSchema;
import br.com.gda.model.decisionTree.DeciResult;
import br.com.gda.model.decisionTree.DeciTree;
import br.com.gda.model.decisionTree.DeciTreeOption;

@PrepareForTest({DbConnection.class})
@RunWith(PowerMockRunner.class)
public class RootPersonUpdateTest {
	@Mock private Connection invalidConn;
	@Mock private PreparedStatement invalidStmt;
	
	@Mock private Connection updateEmptyDocConn;
	@Mock private PreparedStatement updateEmptyDocStmt;
	@Mock private ResultSet updateEmptyDocRs;
	
	@Mock private Connection updateCpfBlankToFilledConn;
	@Mock private PreparedStatement updateCpfBlankToFilledStmt;
	@Mock private ResultSet updateCpfBlankToFilledRs;
	
	@Mock private Connection updateCpfChangeConn;
	@Mock private PreparedStatement updateCpfChangeStmt;
	@Mock private ResultSet updateCpfChangeRs;
	
	@Mock private Connection updateCpfAlreadyExistConn;
	@Mock private PreparedStatement updateCpfAlreadyExistStmt;
	@Mock private ResultSet updateCpfAlreadyExistRs;
	
	@Mock private Connection updateCpfErasureConn;
	@Mock private PreparedStatement updateCpfErasureStmt;
	@Mock private ResultSet updateCpfErasureRs;	
	
	@Mock private Connection updateEmailBlankToFilledConn;
	@Mock private PreparedStatement updateEmailBlankToFilledStmt;
	@Mock private ResultSet updateEmailBlankToFilledRs;
	
	@Mock private Connection updateEmailChangeConn;
	@Mock private PreparedStatement updateEmailChangeStmt;
	@Mock private ResultSet updateEmailChangeRs;	
	
	@Mock private Connection updateEmailAlreadyExistConn;
	@Mock private PreparedStatement updateEmailAlreadyExistStmt;
	@Mock private ResultSet updateEmailAlreadyExistRs;
	
	@Mock private Connection updateEmailErasureConn;
	@Mock private PreparedStatement updateEmailErasureStmt;
	@Mock private ResultSet updateEmailErasureRs;	
	
	@Mock private Connection dontExistConn;
	@Mock private PreparedStatement dontExistStmt;
	@Mock private ResultSet dontExistRs;
	
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
		initializeScenarioUpdateEmptyDoc();
		initializeScenarioUpdateCpfBlankToFilled();
		initializeScenarioUpdateCpfErasure();
		initializeScenarioUpdateCpfAlreadyExist();
		initializeScenarioUpdateCpfChange();
		initializeScenarioUpdateEmailBlankToFilled();
		initializeScenarioUpdateEmailChange();
		initializeScenarioUpdateEmailAlreadyExist();
		initializeScenarioUpdateEmailErasure();
		initializeScenarioDontExist();
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
	
	
	
	private void initializeScenarioUpdateEmptyDoc() throws SQLException {
		updateEmptyDocConn = mock(Connection.class);
		updateEmptyDocStmt = mock(PreparedStatement.class);
		updateEmptyDocRs = mock(ResultSet.class);
		
		when(updateEmptyDocConn.prepareStatement(any(String.class))).thenReturn(updateEmptyDocStmt);
		when(updateEmptyDocStmt.executeUpdate()).thenReturn(1);
		
		when(updateEmptyDocStmt.executeQuery()).thenReturn(updateEmptyDocRs);
		when(updateEmptyDocRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
			                         .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Gender
									 .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Entity Category
									 .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Exist						
							  	     .thenReturn(true).thenReturn(true).thenReturn(false)	// Select - CPF erasure
							  	     .thenReturn(true).thenReturn(true).thenReturn(false);	// Select - Email erasure
							  	    														// Update
		when(updateEmptyDocRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateEmptyDocRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateEmptyDocRs.getString(any(String.class))).thenReturn(" ");
		when(updateEmptyDocRs.getString(PersonDbTableColumn.COL_CPF)).thenReturn(null);
		when(updateEmptyDocRs.getString(PersonDbTableColumn.COL_EMAIL)).thenReturn(null);
		when(updateEmptyDocRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
		when(updateEmptyDocRs.getTimestamp(any(String.class))).thenReturn(Timestamp.valueOf(LocalDateTime.of(2018, 12, 1, 22, 29)));
	}
	
	
	
	private void initializeScenarioUpdateCpfBlankToFilled() throws SQLException {
		updateCpfBlankToFilledConn = mock(Connection.class);
		updateCpfBlankToFilledStmt = mock(PreparedStatement.class);
		updateCpfBlankToFilledRs = mock(ResultSet.class);
		
		when(updateCpfBlankToFilledConn.prepareStatement(any(String.class))).thenReturn(updateCpfBlankToFilledStmt);
		when(updateCpfBlankToFilledStmt.executeUpdate()).thenReturn(1);
		
		when(updateCpfBlankToFilledStmt.executeQuery()).thenReturn(updateCpfBlankToFilledRs);
		when(updateCpfBlankToFilledRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
					                         .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Gender
											 .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Entity Category
											 .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Exist						
									  	     .thenReturn(true).thenReturn(true).thenReturn(false)	// Check CPF New
									  	     .thenReturn(false).thenReturn(false)					// Check CPF Exist
		                                     .thenReturn(true).thenReturn(true).thenReturn(false);	// Select - Email erasure
							  	    																// Update
		when(updateCpfBlankToFilledRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateCpfBlankToFilledRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateCpfBlankToFilledRs.getString(any(String.class))).thenReturn(" ");
		when(updateCpfBlankToFilledRs.getString(PersonDbTableColumn.COL_CPF)).thenReturn(null);
		when(updateCpfBlankToFilledRs.getString(PersonDbTableColumn.COL_EMAIL)).thenReturn(null);
		when(updateCpfBlankToFilledRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
		when(updateCpfBlankToFilledRs.getTimestamp(any(String.class))).thenReturn(Timestamp.valueOf(LocalDateTime.of(2018, 12, 1, 22, 29)));
	}
	
	
	
	private void initializeScenarioUpdateCpfErasure() throws SQLException {
		updateCpfErasureConn = mock(Connection.class);
		updateCpfErasureStmt = mock(PreparedStatement.class);
		updateCpfErasureRs = mock(ResultSet.class);
		
		when(updateCpfErasureConn.prepareStatement(any(String.class))).thenReturn(updateCpfErasureStmt);
		when(updateCpfErasureStmt.executeUpdate()).thenReturn(1);
		
		when(updateCpfErasureStmt.executeQuery()).thenReturn(updateCpfErasureRs);
		when(updateCpfErasureRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		// Check Owner
					                   .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Gender
									   .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Entity Category
									   .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Exist						
							  	       .thenReturn(true).thenReturn(true).thenReturn(false);	// Check CPF Erasure
		when(updateCpfErasureRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateCpfErasureRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateCpfErasureRs.getString(any(String.class))).thenReturn(" ");
		when(updateCpfErasureRs.getString(PersonDbTableColumn.COL_CPF)).thenReturn("38218232400");
		when(updateCpfErasureRs.getString(PersonDbTableColumn.COL_EMAIL)).thenReturn(null);
		when(updateCpfErasureRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
		when(updateCpfErasureRs.getTimestamp(any(String.class))).thenReturn(Timestamp.valueOf(LocalDateTime.of(2018, 12, 1, 22, 29)));
	}	
	
	
	
	private void initializeScenarioUpdateCpfAlreadyExist() throws SQLException {
		updateCpfAlreadyExistConn = mock(Connection.class);
		updateCpfAlreadyExistStmt = mock(PreparedStatement.class);
		updateCpfAlreadyExistRs = mock(ResultSet.class);
		
		when(updateCpfAlreadyExistConn.prepareStatement(any(String.class))).thenReturn(updateCpfAlreadyExistStmt);
		when(updateCpfAlreadyExistStmt.executeUpdate()).thenReturn(1);
		
		when(updateCpfAlreadyExistStmt.executeQuery()).thenReturn(updateCpfAlreadyExistRs);
		when(updateCpfAlreadyExistRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
					                        .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Gender
											.thenReturn(true).thenReturn(true).thenReturn(false)	// Check Entity Category
											.thenReturn(true).thenReturn(true).thenReturn(false)	// Check Exist						
									  	    .thenReturn(true).thenReturn(true).thenReturn(false)	// Check CPF New
									  	    .thenReturn(true).thenReturn(true).thenReturn(false);	// Check CPF Exist
		when(updateCpfAlreadyExistRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateCpfAlreadyExistRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateCpfAlreadyExistRs.getString(any(String.class))).thenReturn(" ");
		when(updateCpfAlreadyExistRs.getString(PersonDbTableColumn.COL_CPF)).thenReturn(null);
		when(updateCpfAlreadyExistRs.getString(PersonDbTableColumn.COL_EMAIL)).thenReturn(null);
		when(updateCpfAlreadyExistRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
		when(updateCpfAlreadyExistRs.getTimestamp(any(String.class))).thenReturn(Timestamp.valueOf(LocalDateTime.of(2018, 12, 1, 22, 29)));
	}	
	
	
	
	private void initializeScenarioUpdateCpfChange() throws SQLException {
		updateCpfChangeConn = mock(Connection.class);
		updateCpfChangeStmt = mock(PreparedStatement.class);
		updateCpfChangeRs = mock(ResultSet.class);
		
		when(updateCpfChangeConn.prepareStatement(any(String.class))).thenReturn(updateCpfChangeStmt);
		when(updateCpfChangeStmt.executeUpdate()).thenReturn(1);
		
		when(updateCpfChangeStmt.executeQuery()).thenReturn(updateCpfChangeRs);
		when(updateCpfChangeRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
			                          .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Gender
									  .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Entity Category
									  .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Exist						
							  	      .thenReturn(true).thenReturn(true).thenReturn(false);	// Check CPF New
		when(updateCpfChangeRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateCpfChangeRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateCpfChangeRs.getString(any(String.class))).thenReturn(" ");
		when(updateCpfChangeRs.getString(PersonDbTableColumn.COL_CPF)).thenReturn("38218232400");
		when(updateCpfChangeRs.getString(PersonDbTableColumn.COL_EMAIL)).thenReturn(null);
		when(updateCpfChangeRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
		when(updateCpfChangeRs.getTimestamp(any(String.class))).thenReturn(Timestamp.valueOf(LocalDateTime.of(2018, 12, 1, 22, 29)));
	}
	
	
	
	private void initializeScenarioUpdateEmailBlankToFilled() throws SQLException {
		updateEmailBlankToFilledConn = mock(Connection.class);
		updateEmailBlankToFilledStmt = mock(PreparedStatement.class);
		updateEmailBlankToFilledRs = mock(ResultSet.class);
		
		when(updateEmailBlankToFilledConn.prepareStatement(any(String.class))).thenReturn(updateEmailBlankToFilledStmt);
		when(updateEmailBlankToFilledStmt.executeUpdate()).thenReturn(1);
		
		when(updateEmailBlankToFilledStmt.executeQuery()).thenReturn(updateEmailBlankToFilledRs);
		when(updateEmailBlankToFilledRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		// Check Owner
									           .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Gender
									           .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Entity Category
									           .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Exist						
									           .thenReturn(true).thenReturn(true).thenReturn(false)		// Check CPF New
									           .thenReturn(false).thenReturn(false)						// Check CPF Exist
									           .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Email New
									           .thenReturn(false).thenReturn(false);					// Check Email Exist
									 																	// Update
		when(updateEmailBlankToFilledRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateEmailBlankToFilledRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateEmailBlankToFilledRs.getString(any(String.class))).thenReturn(" ");
		when(updateEmailBlankToFilledRs.getString(PersonDbTableColumn.COL_CPF)).thenReturn(null);
		when(updateEmailBlankToFilledRs.getString(PersonDbTableColumn.COL_EMAIL)).thenReturn(null);
		when(updateEmailBlankToFilledRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
		when(updateEmailBlankToFilledRs.getTimestamp(any(String.class))).thenReturn(Timestamp.valueOf(LocalDateTime.of(2018, 12, 1, 22, 29)));
	}	
	
	
	
	private void initializeScenarioUpdateEmailChange() throws SQLException {
		updateEmailChangeConn = mock(Connection.class);
		updateEmailChangeStmt = mock(PreparedStatement.class);
		updateEmailChangeRs = mock(ResultSet.class);
		
		when(updateEmailChangeConn.prepareStatement(any(String.class))).thenReturn(updateEmailChangeStmt);
		when(updateEmailChangeStmt.executeUpdate()).thenReturn(1);
		
		when(updateEmailChangeStmt.executeQuery()).thenReturn(updateEmailChangeRs);
		when(updateEmailChangeRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		// Check Owner
							            .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Gender
							            .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Entity Category
							            .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Exist						
							            .thenReturn(true).thenReturn(true).thenReturn(false)		// Check CPF New
							            .thenReturn(false).thenReturn(false)						// Check CPF Exist
							            .thenReturn(true).thenReturn(true).thenReturn(false);		// Check Email New
		when(updateEmailChangeRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateEmailChangeRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateEmailChangeRs.getString(any(String.class))).thenReturn(" ");
		when(updateEmailChangeRs.getString(PersonDbTableColumn.COL_CPF)).thenReturn(null);
		when(updateEmailChangeRs.getString(PersonDbTableColumn.COL_EMAIL)).thenReturn("email@test.com");
		when(updateEmailChangeRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
		when(updateEmailChangeRs.getTimestamp(any(String.class))).thenReturn(Timestamp.valueOf(LocalDateTime.of(2018, 12, 1, 22, 29)));
	}		
	
	
	
	private void initializeScenarioUpdateEmailAlreadyExist() throws SQLException {
		updateEmailAlreadyExistConn = mock(Connection.class);
		updateEmailAlreadyExistStmt = mock(PreparedStatement.class);
		updateEmailAlreadyExistRs = mock(ResultSet.class);
		
		when(updateEmailAlreadyExistConn.prepareStatement(any(String.class))).thenReturn(updateEmailAlreadyExistStmt);
		when(updateEmailAlreadyExistStmt.executeUpdate()).thenReturn(1);
		
		when(updateEmailAlreadyExistStmt.executeQuery()).thenReturn(updateEmailAlreadyExistRs);
		when(updateEmailAlreadyExistRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		// Check Owner
									          .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Gender
									          .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Entity Category
									          .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Exist						
									          .thenReturn(true).thenReturn(true).thenReturn(false)		// Check CPF New
									          .thenReturn(false).thenReturn(false)						// Check CPF Exist
									          .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Email New
									          .thenReturn(true).thenReturn(true).thenReturn(false);		// Check Email Exist
		when(updateEmailAlreadyExistRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateEmailAlreadyExistRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateEmailAlreadyExistRs.getString(any(String.class))).thenReturn(" ");
		when(updateEmailAlreadyExistRs.getString(PersonDbTableColumn.COL_CPF)).thenReturn(null);
		when(updateEmailAlreadyExistRs.getString(PersonDbTableColumn.COL_EMAIL)).thenReturn(null);
		when(updateEmailAlreadyExistRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
		when(updateEmailAlreadyExistRs.getTimestamp(any(String.class))).thenReturn(Timestamp.valueOf(LocalDateTime.of(2018, 12, 1, 22, 29)));
	}
	
	
	
	private void initializeScenarioUpdateEmailErasure() throws SQLException {
		updateEmailErasureConn = mock(Connection.class);
		updateEmailErasureStmt = mock(PreparedStatement.class);
		updateEmailErasureRs = mock(ResultSet.class);
		
		when(updateEmailErasureConn.prepareStatement(any(String.class))).thenReturn(updateEmailErasureStmt);
		when(updateEmailErasureStmt.executeUpdate()).thenReturn(1);
		
		when(updateEmailErasureStmt.executeQuery()).thenReturn(updateEmailErasureRs);
		when(updateEmailErasureRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)		// Check Owner
							             .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Gender
							             .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Entity Category
							             .thenReturn(true).thenReturn(true).thenReturn(false)		// Check Exist						
							             .thenReturn(true).thenReturn(true).thenReturn(false)		// Check CPF New
							             .thenReturn(false).thenReturn(false)						// Check CPF Exist
							             .thenReturn(true).thenReturn(true).thenReturn(false);		// Check Email Erasure
							            // .thenReturn(false).thenReturn(false);						// Check Email Exist
							 																		// Update
		when(updateEmailErasureRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(updateEmailErasureRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(updateEmailErasureRs.getString(any(String.class))).thenReturn(" ");
		when(updateEmailErasureRs.getString(PersonDbTableColumn.COL_CPF)).thenReturn(null);
		when(updateEmailErasureRs.getString(PersonDbTableColumn.COL_EMAIL)).thenReturn("email@test.com");
		when(updateEmailErasureRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
		when(updateEmailErasureRs.getTimestamp(any(String.class))).thenReturn(Timestamp.valueOf(LocalDateTime.of(2018, 12, 1, 22, 29)));
	}
	
	
	
	private void initializeScenarioDontExist() throws SQLException {
		dontExistConn = mock(Connection.class);
		dontExistStmt = mock(PreparedStatement.class);
		dontExistRs = mock(ResultSet.class);
		
		when(dontExistConn.prepareStatement(any(String.class))).thenReturn(dontExistStmt);
		when(dontExistStmt.executeUpdate()).thenReturn(1);
		
		when(dontExistStmt.executeQuery()).thenReturn(dontExistRs);
		when(dontExistRs.next()).thenReturn(true).thenReturn(true).thenReturn(false)	// Check Owner
		                        .thenReturn(true).thenReturn(true).thenReturn(false)	// Check Gender
								.thenReturn(true).thenReturn(true).thenReturn(false)	// Check Entity Category
								.thenReturn(false).thenReturn(false);					// Check Exist	
		when(dontExistRs.getLong(any(String.class))).thenReturn(new Long(1));
		when(dontExistRs.getInt(any(String.class))).thenReturn(new Integer(1));
		when(dontExistRs.getString(any(String.class))).thenReturn(" ");
		when(dontExistRs.getString(PersonDbTableColumn.COL_CPF)).thenReturn(null);
		when(dontExistRs.getString(PersonDbTableColumn.COL_EMAIL)).thenReturn(null);
		when(dontExistRs.getTime(any(String.class))).thenReturn(Time.valueOf("11:22:33"));		
		when(dontExistRs.getTimestamp(any(String.class))).thenReturn(Timestamp.valueOf(LocalDateTime.of(2018, 12, 1, 22, 29)));
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
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullOption() {
		DeciTree<PersonInfo> tree = initializeNullOption();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PersonInfo> initializeNullOption() {		
		return new RootPersonUpdate(null);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullSchema() {
		DeciTree<PersonInfo> tree = initializeNullSchema();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PersonInfo> initializeNullSchema() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsEmptyDoc();
		option.conn = updateEmptyDocConn;
		option.schemaName = null;
		
		return new RootPersonUpdate(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullConnection() {
		DeciTree<PersonInfo> tree = initializeNullConnection();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PersonInfo> initializeNullConnection() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsEmptyDoc();
		option.conn = null;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void nullRecord() {
		DeciTree<PersonInfo> tree = initializeNullRecord();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PersonInfo> initializeNullRecord() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = null;
		option.conn = updateEmptyDocConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void emptyRecords() {
		DeciTree<PersonInfo> tree = initializeEmptyRecords();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PersonInfo> initializeEmptyRecords() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildEmptyRecords();
		option.conn = updateEmptyDocConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}
	
	
	
	private List<PersonInfo> buildEmptyRecords() {
		return new ArrayList<>();
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
		
		option.recordInfos = buildPersonsEmptyDoc();
		option.conn = invalidOwnerConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
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
		
		option.recordInfos = buildPersonsEmptyDoc();
		option.conn = invalidGenderConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
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
		
		option.recordInfos = buildPersonsEmptyDoc();
		option.conn = invalidEntityCategConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}	
	
	
	
	@Test
	public void emptyDoc() {
		DeciTree<PersonInfo> tree = initializeEmptyDoc();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());		
		PersonInfo person = result.getResultset().get(0);
		assertTrue(person.cpf == null);	
		assertTrue(person.email == null);
	}
		
	
	
	private DeciTree<PersonInfo> initializeEmptyDoc() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsEmptyDoc();
		option.conn = updateEmptyDocConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}
	
	
	
	private List<PersonInfo> buildPersonsEmptyDoc() {
		PersonInfo person = new PersonInfo();
		
		person.codOwner = 1;
		person.codPerson = 1;
		person.cpf = null;
		person.name = "name";
		person.codGender = 1;
		person.codEntityCateg = "C";
		person.birthDate = LocalDate.of(2018, 12, 1);
		person.email = null;	
		
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
		
		option.recordInfos = buildPersonsInvalidCpfString();
		option.conn = updateCpfBlankToFilledConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}
	
	
	
	private List<PersonInfo> buildPersonsInvalidCpfString() {
		PersonInfo person = new PersonInfo();
		
		person.codOwner = 1;
		person.codPerson = 1;
		person.cpf = "3821823240A";
		person.name = "name";
		person.codGender = 1;
		person.codEntityCateg = "C";
		person.birthDate = LocalDate.of(2018, 12, 1);
		person.email = null;	
		
		List<PersonInfo> persons = new ArrayList<>();
		persons.add(person);
		return persons;
	}	
	
	
	
	@Test
	public void invalidCpfLength1() {
		DeciTree<PersonInfo> tree = initializeInvalidCpfLength1();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1752);
		assertTrue(result.getFailMessage().equals("Invalid CPF length"));	
	}
		
	
	
	private DeciTree<PersonInfo> initializeInvalidCpfLength1() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsInvalidCpfLength1();
		option.conn = updateCpfBlankToFilledConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}
	
	
	
	private List<PersonInfo> buildPersonsInvalidCpfLength1() {
		PersonInfo person = new PersonInfo();
		
		person.codOwner = 1;
		person.codPerson = 1;
		person.cpf = "382182324000";
		person.name = "name";
		person.codGender = 1;
		person.codEntityCateg = "C";
		person.birthDate = LocalDate.of(2018, 12, 1);
		person.email = null;	
		
		List<PersonInfo> persons = new ArrayList<>();
		persons.add(person);
		return persons;
	}	
	
	
	@Test
	public void invalidCpfLength2() {
		DeciTree<PersonInfo> tree = initializeInvalidCpfLength2();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1752);
		assertTrue(result.getFailMessage().equals("Invalid CPF length"));	
	}
		
	
	
	private DeciTree<PersonInfo> initializeInvalidCpfLength2() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsInvalidCpfLength2();
		option.conn = updateCpfBlankToFilledConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}
	
	
	
	private List<PersonInfo> buildPersonsInvalidCpfLength2() {
		PersonInfo person = new PersonInfo();
		
		person.codOwner = 1;
		person.codPerson = 1;
		person.cpf = "3821823240";
		person.name = "name";
		person.codGender = 1;
		person.codEntityCateg = "C";
		person.birthDate = LocalDate.of(2018, 12, 1);
		person.email = null;	
		
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
		
		option.recordInfos = buildPersonsInvalidCpfSequence();
		option.conn = updateCpfBlankToFilledConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}
	
	
	
	private List<PersonInfo> buildPersonsInvalidCpfSequence() {
		PersonInfo person = new PersonInfo();
		
		person.codOwner = 1;
		person.codPerson = 1;
		person.cpf = "11111111111";
		person.name = "name";
		person.codGender = 1;
		person.codEntityCateg = "C";
		person.birthDate = LocalDate.of(2018, 12, 1);
		person.email = null;	
		
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
		
		option.recordInfos = buildPersonsInvalidCpfNumber();
		option.conn = updateCpfBlankToFilledConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}
	
	
	
	private List<PersonInfo> buildPersonsInvalidCpfNumber() {
		PersonInfo person = new PersonInfo();
		
		person.codOwner = 1;
		person.codPerson = 1;
		person.cpf = "38218232401";
		person.name = "name";
		person.codGender = 1;
		person.codEntityCateg = "C";
		person.birthDate = LocalDate.of(2018, 12, 1);
		person.email = null;	
		
		List<PersonInfo> persons = new ArrayList<>();
		persons.add(person);
		return persons;
	}
	
	
	
	@Test
	public void cpfBlankToFilled() {
		DeciTree<PersonInfo> tree = initializeCpfBlankToFilled();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());		
	}
		
	
	
	private DeciTree<PersonInfo> initializeCpfBlankToFilled() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsCpfBlankToFilled();
		option.conn = updateCpfBlankToFilledConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}
	
	
	
	private List<PersonInfo> buildPersonsCpfBlankToFilled() {
		PersonInfo person = new PersonInfo();
		
		person.codOwner = 1;
		person.codPerson = 1;
		person.cpf = "38218232400";
		person.name = "name";
		person.codGender = 1;
		person.codEntityCateg = "C";
		person.birthDate = LocalDate.of(2018, 12, 1);
		person.email = null;	
		
		List<PersonInfo> persons = new ArrayList<>();
		persons.add(person);
		return persons;
	}
	
	
	
	@Test(expected = IllegalStateException.class)
	public void invalidConnection() {
		DeciTree<PersonInfo> tree = initializeInvalidConnection();
		tree.makeDecision();
	}
		
	
	
	private DeciTree<PersonInfo> initializeInvalidConnection() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsCpfChange();
		option.conn = invalidConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}	
	
	
	
	@Test
	public void cpfChange() {
		DeciTree<PersonInfo> tree = initializeCpfChange();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1766);
		assertTrue(result.getFailMessage().equals("CPF can't be changed"));
	}
		
	
	
	private DeciTree<PersonInfo> initializeCpfChange() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsCpfChange();
		option.conn = updateCpfChangeConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}
	
	
	
	private List<PersonInfo> buildPersonsCpfChange() {
		PersonInfo person = new PersonInfo();
		
		person.codOwner = 1;
		person.codPerson = 1;
		person.cpf = "38218232400";
		person.name = "name";
		person.codGender = 1;
		person.codEntityCateg = "C";
		person.birthDate = LocalDate.of(2018, 12, 1);
		person.email = null;	
		
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
		
		option.recordInfos = buildPersonsCpfAlreadyExist();
		option.conn = updateCpfAlreadyExistConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}
	
	
	
	private List<PersonInfo> buildPersonsCpfAlreadyExist() {
		PersonInfo person = new PersonInfo();
		
		person.codOwner = 1;
		person.codPerson = 1;
		person.cpf = "38218232400";
		person.name = "name";
		person.codGender = 1;
		person.codEntityCateg = "C";
		person.birthDate = LocalDate.of(2018, 12, 1);
		person.email = null;	
		
		List<PersonInfo> persons = new ArrayList<>();
		persons.add(person);
		return persons;
	}
	
	
	
	@Test
	public void cpfErasure() {
		DeciTree<PersonInfo> tree = initializeCpfErasure();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1768);
		assertTrue(result.getFailMessage().equals("CPF erasure is not allowed"));	
	}
		
	
	
	private DeciTree<PersonInfo> initializeCpfErasure() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsCpfErasure();
		option.conn = updateCpfErasureConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}
	
	
	
	private List<PersonInfo> buildPersonsCpfErasure() {
		PersonInfo person = new PersonInfo();
		
		person.codOwner = 1;
		person.codPerson = 1;
		person.cpf = null;
		person.name = "name";
		person.codGender = 1;
		person.codEntityCateg = "C";
		person.birthDate = LocalDate.of(2018, 12, 1);
		person.email = null;	
		
		List<PersonInfo> persons = new ArrayList<>();
		persons.add(person);
		return persons;
	}
	
	
	
	@Test
	public void emailBlankToFilled() {
		DeciTree<PersonInfo> tree = initializeEmailBlankToFilled();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertTrue(result.isSuccess());		
	}
		
	
	
	private DeciTree<PersonInfo> initializeEmailBlankToFilled() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsEmailBlankToFilled();
		option.conn = updateEmailBlankToFilledConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}
	
	
	
	private List<PersonInfo> buildPersonsEmailBlankToFilled() {
		PersonInfo person = new PersonInfo();
		
		person.codOwner = 1;
		person.codPerson = 1;
		person.cpf = "38218232400";
		person.name = "name";
		person.codGender = 1;
		person.codEntityCateg = "C";
		person.birthDate = LocalDate.of(2018, 12, 1);
		person.email = "email@test.com";	
		
		List<PersonInfo> persons = new ArrayList<>();
		persons.add(person);
		return persons;
	}
	
	
	
	@Test
	public void emailChange() {
		DeciTree<PersonInfo> tree = initializeEmailChange();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1767);
		assertTrue(result.getFailMessage().equals("e-mail can't be changed"));	
	}
		
	
	
	private DeciTree<PersonInfo> initializeEmailChange() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsEmailChange();
		option.conn = updateEmailChangeConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}
	
	
	
	private List<PersonInfo> buildPersonsEmailChange() {
		PersonInfo person = new PersonInfo();
		
		person.codOwner = 1;
		person.codPerson = 1;
		person.cpf = "38218232400";
		person.name = "name";
		person.codGender = 1;
		person.codEntityCateg = "C";
		person.birthDate = LocalDate.of(2018, 12, 1);
		person.email = "email@test.com";	
		
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
		
		option.recordInfos = buildPersonsEmailAlreadyExist();
		option.conn = updateEmailAlreadyExistConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}
	
	
	
	private List<PersonInfo> buildPersonsEmailAlreadyExist() {
		PersonInfo person = new PersonInfo();
		
		person.codOwner = 1;
		person.codPerson = 1;
		person.cpf = "38218232400";
		person.name = "name";
		person.codGender = 1;
		person.codEntityCateg = "C";
		person.birthDate = LocalDate.of(2018, 12, 1);
		person.email = "email@test.com";	
		
		List<PersonInfo> persons = new ArrayList<>();
		persons.add(person);
		return persons;
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
		
		option.recordInfos = buildPersonsEmailErasure();
		option.conn = dontExistConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}
	
	
	
	@Test
	public void emailErasure() {
		DeciTree<PersonInfo> tree = initializeEmailErasure();
		tree.makeDecision();
		DeciResult<PersonInfo> result = tree.getDecisionResult();		
		
		assertFalse(result.isSuccess());
		assertTrue(result.getFailCode() == 1770);
		assertTrue(result.getFailMessage().equals("e-mail erasure is not allowed"));	
	}
		
	
	
	private DeciTree<PersonInfo> initializeEmailErasure() {
		DeciTreeOption<PersonInfo> option = new DeciTreeOption<>();
		
		option.recordInfos = buildPersonsEmailErasure();
		option.conn = updateEmailErasureConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}
	
	
	
	private List<PersonInfo> buildPersonsEmailErasure() {
		PersonInfo person = new PersonInfo();
		
		person.codOwner = 1;
		person.codPerson = 1;
		person.cpf = "38218232400";
		person.name = "name";
		person.codGender = 1;
		person.codEntityCateg = "C";
		person.birthDate = LocalDate.of(2018, 12, 1);
		person.email = null;	
		
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
		option.conn = updateEmptyDocConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}
	
	
	
	private List<PersonInfo> buildPersonMissingFieldCodOwner() {
		PersonInfo person = new PersonInfo();		
		
		person.codOwner = -1;
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
		option.conn = updateEmptyDocConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}
	
	
	
	private List<PersonInfo> buildPersonMissingFieldName() {
		PersonInfo person = new PersonInfo();		
		
		person.codOwner = 1;
		person.codPerson = 1;
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
		option.conn = updateEmptyDocConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}
	
	
	
	private List<PersonInfo> buildPersonMissingFieldCodGender() {
		PersonInfo person = new PersonInfo();		
		
		person.codOwner = 1;
		person.codPerson = 1;
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
		option.conn = updateEmptyDocConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}
	
	
	
	private List<PersonInfo> buildPersonMissingFieldCodEntityCateg() {
		PersonInfo person = new PersonInfo();		
		
		person.codOwner = 1;
		person.codPerson = 1;
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
		
		option.recordInfos = buildPersonMissingFieldCodPerson();
		option.conn = updateEmptyDocConn;
		option.schemaName = DbSchema.getDefaultSchemaName();
		
		return new RootPersonUpdate(option);
	}
	
	
	
	private List<PersonInfo> buildPersonMissingFieldCodPerson() {
		PersonInfo person = new PersonInfo();		
		
		person.codOwner = 1;
		person.codPerson = -1;
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
