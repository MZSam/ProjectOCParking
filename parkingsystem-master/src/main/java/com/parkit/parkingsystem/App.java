package com.parkit.parkingsystem;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.service.InteractiveShell;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
    private static final Logger logger = LogManager.getLogger("App");
   
    public static void main(String args[]) throws Exception{
        logger.info("Initializing Parking System");
        InputReaderUtil inputReaderUtil;
        ParkingService parkingService;
        ParkingSpotDAO parkingSpotDAO;
        TicketDAO ticketDAO;
        inputReaderUtil = new InputReaderUtil();
        parkingSpotDAO = new ParkingSpotDAO();
        ticketDAO = new TicketDAO();
        parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        InteractiveShell interactiveShell = new InteractiveShell(inputReaderUtil, parkingService);
        
        interactiveShell.loadInterface();
    }
}
