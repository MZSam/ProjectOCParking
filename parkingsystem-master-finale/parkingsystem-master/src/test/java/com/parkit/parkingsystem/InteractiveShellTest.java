package com.parkit.parkingsystem;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.InteractiveShell;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class InteractiveShellTest {
	
	private InteractiveShell interactiveShell;
	@Mock
	private InputReaderUtil inputReaderUtil = new InputReaderUtil();
	 @Mock
	private Ticket ticket;
	private ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO() ;
	@Mock
	private static TicketDAO ticketDAO;
	@Mock
	private static ParkingSpot parkingSpot;
	@Mock
    private ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
	 
	 @BeforeEach
	    private void setUpPerTest() {
	        try {
	            interactiveShell = new InteractiveShell(inputReaderUtil,parkingService);

	           ;
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw  new RuntimeException("Failed to set up test mock objects");
	        }
	    }
	
	@Test
	public void loadInterfaceIncomingVehicleTest() throws Exception{
		when(inputReaderUtil.readSelection()).thenReturn(1).thenReturn(3);
		interactiveShell.loadInterface();
		verify(parkingService, Mockito.times(1)).processIncomingVehicle();
	}
	
	@Test
	public void loadInterfaceExitingVehicleTest() throws Exception{
		when(inputReaderUtil.readSelection()).thenReturn(2).thenReturn(3);
		interactiveShell.loadInterface();
		verify(parkingService, Mockito.times(1)).processExitingVehicle();
		
		
	}
	
	
}
