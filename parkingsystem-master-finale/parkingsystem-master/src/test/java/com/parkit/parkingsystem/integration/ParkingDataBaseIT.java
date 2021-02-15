package com.parkit.parkingsystem.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    @Mock
    private static InputReaderUtil inputReaderUtil;
    private ParkingService parkingService;
    

    @BeforeAll
    private static void setUp() throws Exception{
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = new DataBaseConfig();//dataBaseTestConfig;
        ticketDAO = new TicketDAO();
        inputReaderUtil = new InputReaderUtil();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;//new DataBaseConfig();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
    	parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
    	when(parkingService.getVehichleRegNumber()).thenReturn("AABBCC");
    }


    @Test
    public void testParkingACar() throws Exception{
    	
    	when(inputReaderUtil.readSelection()).thenReturn(1);
    	ParkingSpot parkingSpot = parkingService.getNextParkingNumberIfAvailable();
        if(parkingSpot !=null && parkingSpot.getId() > 0){
        	String vehicleRegNumber = parkingService.getVehichleRegNumber();
        	parkingSpot.setAvailable(false);
            parkingSpotDAO.updateParking(parkingSpot);//allot this parking space and mark it's availability as false
            Date inTime = new Date();
            Ticket ticket = new Ticket();
	        int ticketID = ticket.getNewTicketID();
	        ticket.setId(ticketID);
	        ticket.setParkingSpot(parkingSpot);
	        ticket.setVehicleRegNumber(vehicleRegNumber);
	        ticket.setPrice(0);
	        ticket.setInTime(inTime);
	        ticket.setOutTime(null);
	        ticketDAO = new TicketDAO();
	        ticketDAO.saveTicket(ticket);
	        Ticket regTicket;
	        regTicket = ticketDAO.getTicket(vehicleRegNumber);
	    	assertNotNull(regTicket);
	    	ParkingSpot regParkingSpot = regTicket.getParkingSpot();
	    	Connection con = null;
	    	DataBaseConfig dataBaseConfig = new DataBaseConfig(); 
	    	con = dataBaseConfig.getConnection();
	    	java.sql.Statement stmt = con.createStatement();
	    	ResultSet rs = stmt.executeQuery("SELECT AVAILABLE FROM parking where PARKING_NUMBER = "+ regParkingSpot.getId()  );
	    	int Available = 0;
	    	if (rs.next())
	    	{
	    		Available = rs.getInt("AVAILABLE");
	    	}
	    	
	    	assertEquals(0, Available);
	        }
        
      //TODO: check that a ticket is actualy saved in DB and Parking table is updated with availability
    }

    @Test
    public void testParkingLotExit() throws Exception{
    	 String vehicleRegNumber = parkingService.getVehichleRegNumber();
         Ticket ticket = ticketDAO.getTicket(vehicleRegNumber);
         Date outTime = new Date();
         ticket.setOutTime(outTime);
         FareCalculatorService fareCalculatorService = new FareCalculatorService();
         fareCalculatorService.calculateFare(ticket);
         if(ticketDAO.updateTicket(ticket)) {
             ParkingSpot parkingSpot = ticket.getParkingSpot();
             parkingSpot.setAvailable(true);
             parkingSpotDAO.updateParking(parkingSpot);
             System.out.println("Please pay the parking fare:" + ticket.getPrice());
             System.out.println("Recorded out-time for vehicle number:" + ticket.getVehicleRegNumber() + " is:" + outTime);
         }
    	
        
        Ticket registeredTicket = ticketDAO.getTicket(vehicleRegNumber);
        assertEquals(ticket.getVehicleRegNumber(), registeredTicket.getVehicleRegNumber()); 
        assertNotNull(registeredTicket.getPrice());
        assertNotNull(registeredTicket.getOutTime());
        Connection con = null;
    	DataBaseConfig dataBaseConfig = new DataBaseConfig(); 
    	con = dataBaseConfig.getConnection();
    	java.sql.Statement stmt = con.createStatement();
    	ResultSet rs = stmt.executeQuery("SELECT PRICE, OUT_TIME FROM ticket where ID = "+ registeredTicket.getId()  );
    	double Price= 0 ;Date Out_Time = null;
    	if (rs.next())
    	{
    		Price = rs.getDouble("PRICE");
    		Out_Time = rs.getTimestamp("OUT_TIME");

    	    
    	}
    	assertEquals(Price, registeredTicket.getPrice());
    	assertEquals(Out_Time, registeredTicket.getOutTime());
        
        //TODO: check that the fare generated and out time are populated correctly in the database
    }
    
    @Test
    public void getTicketsNumberTest() throws Exception{
    	String vehicleRegNumber = parkingService.getVehichleRegNumber();
    	int ticketsNumber = ticketDAO.getTicketsNumber(vehicleRegNumber);
    	assertNotNull(ticketsNumber);
    }
    
   
}
