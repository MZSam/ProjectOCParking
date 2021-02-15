package com.parkit.parkingsystem.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import com.parkit.parkingsystem.config.DataBaseConfig;

public class Ticket {
	private int id;
    private ParkingSpot parkingSpot;
    private String vehicleRegNumber;
    private double price;
    private Date inTime;
    private Date outTime;

    public int getId() {
        return id;
    }

    public int getNewTicketID() throws ClassNotFoundException, SQLException
    {
    	Connection con = null;
    	DataBaseConfig dataBaseConfig = new DataBaseConfig(); 
    	con = dataBaseConfig.getConnection();
    	java.sql.Statement stmt = con.createStatement();
    	ResultSet rs = stmt.executeQuery("SELECT Max(ID) as ticketID FROM ticket");
    	int ticketID = 0;
    	if (rs.next())
    	{
    	    ticketID = rs.getInt("ticketID");

    	    
    	}
    	return ticketID + 1;
    	
    }
    public void setId(int id) {
        this.id = id;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }
}
