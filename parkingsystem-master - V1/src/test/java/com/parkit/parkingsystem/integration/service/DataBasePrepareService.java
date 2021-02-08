package com.parkit.parkingsystem.integration.service;
import java.sql.Connection;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;

public class DataBasePrepareService {

    DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    

    
    @Test
    public void clearDataBaseEntries() throws SQLException{
        Connection connection = null;
        try{
            connection = dataBaseTestConfig.getConnection();

            //set parking entries to available
            connection.prepareStatement("update parking set available = true").execute();

            //clear ticket entries;
            connection.prepareStatement("truncate table ticket").execute();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            dataBaseTestConfig.closeConnection(connection);
        }
    }


   

}
