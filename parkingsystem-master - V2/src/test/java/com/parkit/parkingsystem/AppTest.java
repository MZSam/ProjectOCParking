package com.parkit.parkingsystem;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.service.InteractiveShell;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.ByteArrayInputStream;

@ExtendWith(MockitoExtension.class)
public class AppTest {
	 @Mock
	 private InputReaderUtil inputReaderUtil = new InputReaderUtil();
	  @Mock
	 private Ticket ticket;
	 private ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO() ;
	 @Mock
	 private static TicketDAO ticketDAO;
	 @Mock
     private ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
	 @Mock
     private InteractiveShell interactiveShell = new InteractiveShell(inputReaderUtil, parkingService);
    
    
    
    @Test
    public void mainTest() throws Exception
    {
    	System.setIn(new ByteArrayInputStream("3\r\n".getBytes()));
    	String[] test = {"test"};
    	App.main(test);
    	
    }
}
