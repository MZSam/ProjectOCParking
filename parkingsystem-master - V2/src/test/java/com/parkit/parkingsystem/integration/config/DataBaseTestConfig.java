package com.parkit.parkingsystem.integration.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
@ExtendWith(MockitoExtension.class)
public class DataBaseTestConfig extends DataBaseConfig {
    private static final DataBaseConfig    dataBaseConfig = new DataBaseConfig();
	@Mock
    private static ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();
	private ResultSet resultSet;
    private PreparedStatement ps;
    
    @Test
    public void getConnectionTest1() throws ClassNotFoundException, SQLException  {

    	Connection conn =  dataBaseConfig.getConnection();
    	assertEquals(true,conn.isValid(5));
    	try {
    	conn.prepareCall("test");
    	}
    	catch(Exception e)
    	{}
    	
    }
    
    
    @Test
    public void closeConnectionExceptionTest() throws ClassNotFoundException, SQLException{
    	Connection con = Mockito.mock(Connection.class);
    	Mockito.doThrow(new SQLException("Test")).when(con).close();
    	Assertions.assertThrows(SQLException.class, () -> {
    		
    		dataBaseConfig.closeConnection(con); 
    	  });

    	  
    
    }

    @Test
    public void closeConnectionTest() throws ClassNotFoundException, SQLException{
    	Connection con = dataBaseConfig.getConnection();
    	dataBaseConfig.closeConnection(con);  
    	assertEquals(true,con.isClosed());
    }
    
    @Test
    public void closeNullConnectionTest() throws ClassNotFoundException, SQLException{
    	Connection con = null ;
    	Assertions.assertDoesNotThrow(() -> dataBaseConfig.closeConnection(con));
    	
    }

    @Test
    public void closePreparedStatementT() throws ClassNotFoundException, SQLException {
    	Connection con = dataBaseConfig.getConnection();
    	PreparedStatement ps = con.prepareStatement("select * from prod");
    	dataBaseConfig.closePreparedStatement(ps);
    	assertEquals(true, ps.isClosed());
    	
    }
    
    
  

	@Test
    public void closeNullPreparedStatementTest() throws ClassNotFoundException, SQLException {
    	
    	PreparedStatement ps = null;
    	
    	Assertions.assertDoesNotThrow(() -> dataBaseConfig.closePreparedStatement(ps));
    	
    }
	
	@Test()
	public void closePreparedStatementExceptionTest() throws ClassNotFoundException, SQLException 
	{
		
    	ps =  Mockito.mock(PreparedStatement.class);
    	Mockito.doThrow(new SQLException()).when(ps).close();
       	Assertions.assertThrows(SQLException.class, () -> {
    		
    		dataBaseConfig.closePreparedStatement(ps);
    	  });
    	
	}
	

    @Test
    public void closeResultSetTest() throws ClassNotFoundException, SQLException {
    	Connection con = dataBaseConfig.getConnection();
    	PreparedStatement ps = con.prepareStatement(DBConstants.GET_NEXT_PARKING_SPOT);
        ps.setString(1, "1");
        ResultSet rs = ps.executeQuery();
        dataBaseConfig.closeResultSet(rs);
        assertEquals(true, rs.isClosed());
    }
    
    @Test
    public void closeNullResultSetTest() throws ClassNotFoundException, SQLException {
    	
    	ResultSet rs = null;
        
    	Assertions.assertDoesNotThrow(() -> dataBaseConfig.closeResultSet(rs));
    }
    
    @Test
    public void closeResultSetExceptionTest() throws ClassNotFoundException, SQLException {
    	
    	resultSet = Mockito.mock(ResultSet.class);
    	Mockito.doThrow(new SQLException("Test")).when(resultSet).close();
    	Assertions.assertThrows(SQLException.class, () -> {
    		
    		dataBaseConfig.closeResultSet(resultSet);
    	  });
    }
    
    
}
