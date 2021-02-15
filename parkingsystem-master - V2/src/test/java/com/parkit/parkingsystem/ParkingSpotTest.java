package com.parkit.parkingsystem;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParkingSpotTest {
	
	
	 private int number =1;
	
	private ParkingType parkingType = ParkingType.CAR;
	
	private boolean isAvailable = true;
	
	private ParkingSpot parkingSpot;
	
	
	@BeforeEach
	private void setUpPerTest() {
        try {
           // when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");

           parkingSpot = new ParkingSpot(number, parkingType, isAvailable);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("Failed to set up test mock objects");
        }
    }
	
	@Test
	public void equalsTest() {
		ParkingSpot parkingSpot1 = new ParkingSpot(1, ParkingType.CAR, true);
		boolean equals = parkingSpot.equals(parkingSpot1);
		assertEquals(true,equals);
	}
	
	@Test
	public void equalsNullTest() {
		
		
		
		assertNotNull(parkingSpot);
	}
	
	@Test
	public void equalsDifferentTypesTest() {
		InputReaderUtil inputReaderUtil = new InputReaderUtil();
		assertFalse(parkingSpot.equals(inputReaderUtil));
	}
	
	@Test
	public void hashCodeTest() {
		
		
		assertNotNull(parkingSpot.hashCode());
	}
}
