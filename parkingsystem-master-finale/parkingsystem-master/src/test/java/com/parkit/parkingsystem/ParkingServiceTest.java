package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {
	private ParkingService parkingService;   
    @Mock
    private Ticket ticket;
    @Mock
    private InputReaderUtil inputReaderUtil;
    @Mock
    private static ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();
    @Mock
    private static TicketDAO ticketDAO;
    @Mock
    private static ParkingSpot parkingSpot;
    @BeforeEach
    private void setUpPerTest() {
        try {
            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
            Ticket ticket = new Ticket();
            ticket.setInTime(new Date(System.currentTimeMillis() - (60*60*1000)));
            ticket.setParkingSpot(parkingSpot);
            ticket.setVehicleRegNumber("ABCDEF");
            parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("Failed to set up test mock objects");
        }
    }

    @Test
    public void processExitingVehicleHalfHourTest() throws Exception{
    	when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);
    	when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
    	when(parkingService.getVehichleRegNumber()).thenReturn("AABBCC"); 
    	Ticket ticket = new Ticket();
    	Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
    	ticket.setInTime(inTime);
    	ticket.setOutTime(outTime);
    	ParkingSpot parkingspot = new ParkingSpot(1,ParkingType.CAR,true);
    	ticket.setParkingSpot(parkingspot);
    	ticket.setVehicleRegNumber("ABCDEF");
    	when(ticketDAO.getTicket(any(String.class))).thenReturn(ticket);
        parkingService.processExitingVehicleHalfHour();
        
        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
    }
    
    @Test
    public void processExitingVehicleHalfHourExceptionTest() throws Exception{
    	
         Assertions.assertThrows(NullPointerException.class, () -> {
    		
        	 parkingService.processExitingVehicleHalfHour();
    	  });
    }
    @Test
    public void processExitingVehicleHalfHourElseTest() throws Exception{
    	when(parkingService.getVehichleRegNumber()).thenReturn("AABBCC"); 
    	Ticket ticket = new Ticket();
    	Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
    	ticket.setInTime(inTime);
    	ticket.setOutTime(outTime);
    	ParkingSpot parkingspot = new ParkingSpot(1,ParkingType.CAR,true);
    	ticket.setParkingSpot(parkingspot);
    	ticket.setVehicleRegNumber("ABCDEF");
    	when(ticketDAO.getTicket(any(String.class))).thenReturn(ticket);
    	when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(false);
    	parkingService.processExitingVehicleHalfHour();
    }
    	
    
    @Test
    public void processExitingVehicleElseTest() throws Exception{
    	when(parkingService.getVehichleRegNumber()).thenReturn("AABBCC"); 
    	Ticket ticket = new Ticket();
    	Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
    	ticket.setInTime(inTime);
    	ticket.setOutTime(outTime);
    	ParkingSpot parkingspot = new ParkingSpot(1,ParkingType.CAR,true);
    	ticket.setParkingSpot(parkingspot);
    	ticket.setVehicleRegNumber("ABCDEF");
    	when(ticketDAO.getTicket(any(String.class))).thenReturn(ticket);
    	when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(false);
    	parkingService.processExitingVehicle();
        
    }
    
    @Test
    public void processExitingVehicleTest() throws Exception{
    	when(parkingService.getVehichleRegNumber()).thenReturn("AABBCC"); 
    	Ticket ticket = new Ticket();
    	Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
    	ticket.setInTime(inTime);
    	ticket.setOutTime(outTime);
    	ParkingSpot parkingspot = new ParkingSpot(1,ParkingType.CAR,true);
    	ticket.setParkingSpot(parkingspot);
    	ticket.setVehicleRegNumber("ABCDEF");
    	when(ticketDAO.getTicket(any(String.class))).thenReturn(ticket);
    	when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
    	parkingService.processExitingVehicle();
    }
    
    @Test
    public void processExitingVehicleExceptionTest() throws Exception{
    	when(parkingService.getVehichleRegNumber()).thenThrow(NullPointerException.class); 
    		Assertions.assertThrows(NullPointerException.class, () -> {
    		
    		parkingService.processExitingVehicle();
    	  });
    	
    }
    
    @Test
    public void getNextParkingNumberIfAvailableTest() throws Exception{
        when(inputReaderUtil.readSelection()).thenReturn(1);
    	when(parkingSpotDAO.getNextAvailableSlot(any(ParkingType.class))).thenReturn(1);
    	parkingService.getNextParkingNumberIfAvailable();
    	verify(parkingSpotDAO, Mockito.times(1)).getNextAvailableSlot(any(ParkingType.class));
    }
    
    @Test
    public void processIncomingVehicleTest() throws Exception{
    	when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);
    	parkingSpot = new ParkingSpot(1,ParkingType.CAR,true);
      	when(parkingSpotDAO.getNextAvailableSlot(any(ParkingType.class))).thenReturn(1);
      	ticket = new Ticket();
      	Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
    	ticket.setInTime(inTime);
    	ticket.setOutTime(outTime);
      	when(parkingService.getVehichleRegNumber()).thenReturn("AABBCC");
    	when(inputReaderUtil.readSelection()).thenReturn(1);
        parkingService.processIncomingVehicle();
        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
    }
    
    @Test
    public void processIncomingVehicleTestException() throws Exception{
      	
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		
        	parkingService.processIncomingVehicle();
    	  });

    }
    

    
@Test  
 public void getVehichleTypeCarTest(){  
    when(inputReaderUtil.readSelection()).thenReturn(1);
    assertEquals(ParkingType.CAR, parkingService.getVehichleType());

}

@Test  
public void getVehichleTypeBikeTest(){  

   when(inputReaderUtil.readSelection()).thenReturn(2);
   assertEquals(ParkingType.BIKE, parkingService.getVehichleType());

}

@Test  
public void getVehichleTypeIllegalArgumentExceptionTest(){  
   when(inputReaderUtil.readSelection()).thenReturn(3);
   Assertions.assertThrows(IllegalArgumentException.class, () -> {
		
	   parkingService.getVehichleType();
	  });
	
}


@Test
public void getNextParkingNumberIfAvailableIllegalArgumentException() throws Exception{
	when(inputReaderUtil.readSelection()).thenReturn(1);
	when(parkingSpotDAO.getNextAvailableSlot(any(ParkingType.class))).thenThrow(new IllegalArgumentException());
	Assertions.assertThrows(IllegalArgumentException.class, () -> {
		
		parkingService.getNextParkingNumberIfAvailable();
		  });
		
}

@Test
public void getNextParkingNumberIfAvailableException() throws Exception{
  
  		when(inputReaderUtil.readSelection()).thenReturn(1);
  		when(parkingSpotDAO.getNextAvailableSlot(any(ParkingType.class))).thenThrow(new NullPointerException());
  		Assertions.assertThrows(Exception.class, () -> {
  			
  			parkingService.getNextParkingNumberIfAvailable();
  			  });
  			
}


@Test
public void getNextParkingNumberIfAvailableparkingNumberException() throws Exception{
  	
  		when(inputReaderUtil.readSelection()).thenReturn(1);
  		when(parkingSpotDAO.getNextAvailableSlot(any(ParkingType.class))).thenReturn(-1);
  		Assertions.assertThrows(Exception.class, () -> {
  			
  			parkingService.getNextParkingNumberIfAvailable();
  			  });
 }

@Test
public void processExitingVehicleElseTestHalfHour() throws Exception{
	when(parkingService.getVehichleRegNumber()).thenReturn("AABBCC"); 
	Ticket ticket = new Ticket();
	Date inTime = new Date();
    inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
    Date outTime = new Date();
	ticket.setInTime(inTime);
	ticket.setOutTime(outTime);
	ParkingSpot parkingspot = new ParkingSpot(1,ParkingType.CAR,true);
	ticket.setParkingSpot(parkingspot);
	ticket.setVehicleRegNumber("ABCDEF");
	when(ticketDAO.getTicket(any(String.class))).thenReturn(ticket);
	when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
	parkingService.processExitingVehicleHalfHour();
}

@Test
public void processExitingVehicleElseTestReduction() throws Exception{
	when(parkingService.getVehichleRegNumber()).thenReturn("AABBCC"); 
	Ticket ticket = new Ticket();
	Date inTime = new Date();
    inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
    Date outTime = new Date();
	ticket.setInTime(inTime);
	ticket.setOutTime(outTime);
	ParkingSpot parkingspot = new ParkingSpot(1,ParkingType.CAR,true);
	ticket.setParkingSpot(parkingspot);
	ticket.setVehicleRegNumber("ABCDEF");
	when(ticketDAO.getTicket(any(String.class))).thenReturn(ticket);
	when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
	parkingService.processExitingVehicleReduction();
    
}

@Test
public void processExitingVehicleIfTestReduction() throws Exception{
	when(parkingService.getVehichleRegNumber()).thenReturn("AABBCC"); 
	Ticket ticket = new Ticket();
	Date inTime = new Date();
    inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
    Date outTime = new Date();
	ticket.setInTime(inTime);
	ticket.setOutTime(outTime);
	ParkingSpot parkingspot = new ParkingSpot(1,ParkingType.CAR,true);
	ticket.setParkingSpot(parkingspot);
	ticket.setVehicleRegNumber("ABCDEF");
	when(ticketDAO.getTicket(any(String.class))).thenReturn(ticket);
	when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
	when(ticketDAO.getTicketsNumber(any(String.class))).thenReturn(3);
	parkingService.processExitingVehicleReduction();
	
}

@Test
public void processExitingVehicleupdateTicketElseTestReduction() throws Exception{
	when(parkingService.getVehichleRegNumber()).thenReturn("AABBCC"); 
	Ticket ticket = new Ticket();
	Date inTime = new Date();
    inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
    Date outTime = new Date();
	ticket.setInTime(inTime);
	ticket.setOutTime(outTime);
	ParkingSpot parkingspot = new ParkingSpot(1,ParkingType.CAR,true);
	ticket.setParkingSpot(parkingspot);
	ticket.setVehicleRegNumber("ABCDEF");
	when(ticketDAO.getTicket(any(String.class))).thenReturn(ticket);
	when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(false);
	parkingService.processExitingVehicleReduction();
	
}



@Test
public void processExitingVehicleuleExceptionTestReduction() throws Exception{
when(parkingService.getVehichleRegNumber()).thenThrow(new NullPointerException());
Assertions.assertThrows(NullPointerException.class, () -> {
	
	parkingService.processExitingVehicleReduction();
	  });
		

}

}
