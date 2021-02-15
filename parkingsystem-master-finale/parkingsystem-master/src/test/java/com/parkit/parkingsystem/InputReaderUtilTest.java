package com.parkit.parkingsystem;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;



public class InputReaderUtilTest {
	
	
	private InputReaderUtil inputReaderUtil= new InputReaderUtil();
	
	@Test
	public void readSelectionTest() {
	    
		
		System.setIn(new ByteArrayInputStream("5\r\n".getBytes()));
 	    assertEquals(5, inputReaderUtil.readSelection());
	}

	@Test
	public void readSelectionExceptionTest() {
		System.setIn(new ByteArrayInputStream("test\r\n".getBytes()));
		assertEquals(-1, inputReaderUtil.readSelection());
    		
	
	}
	
	@Test
	public void readVehicleRegistrationNumberTest() throws Exception {
		System.setIn(new ByteArrayInputStream("AABBCC\r\n".getBytes()));
		assertEquals("AABBCC", inputReaderUtil.readVehicleRegistrationNumber());
	}

	@Test
	public void readVehicleRegistrationNumberNullTest() throws Exception {
		System.setIn(null);
		Assertions.assertThrows(Exception.class, () -> {
    		
			inputReaderUtil.readVehicleRegistrationNumber();
    	  });
	}
	
	@Test
	public void readVehicleRegistrationNumberEmptyTest() throws Exception {
		System.setIn(new ByteArrayInputStream("".getBytes()));
		Assertions.assertThrows(Exception.class, () -> {
    		
			inputReaderUtil.readVehicleRegistrationNumber();
    	  });
	}


}
