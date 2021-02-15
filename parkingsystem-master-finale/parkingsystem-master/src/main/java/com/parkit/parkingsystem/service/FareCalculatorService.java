package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
        	if (ticket.getOutTime() != null)
        	{
        		throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString()); // à corriger
       
        	}
        	else
        	{
        		throw new IllegalArgumentException("Out time provided is incorrect:");
        	}
        }
        	

      /*  int inHour = ticket.getInTime();
        int outHour = ticket.getOutTime();*/

        //TODO: Some tests are failing here. Need to check if this logic is correct
        double duration = ((Math.abs(ticket.getOutTime().getTime() - ticket.getInTime().getTime()))/(60 * 1000))/60.00;

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
    
    public void calculateFareHalfHour(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
        	if (ticket.getOutTime() != null)
        	{
        		throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString()); // à corriger
       
        	}
        	else
        	{
        		throw new IllegalArgumentException("Out time provided is incorrect:");
        	}
        }
        	

      /*  int inHour = ticket.getInTime();
        int outHour = ticket.getOutTime();*/

        //TODO: Some tests are failing here. Need to check if this logic is correct
        double duration = ((Math.abs(ticket.getOutTime().getTime() - ticket.getInTime().getTime()))/(60 * 1000))/60.00;
        double freeDuration = 30;
        duration = ((duration * 60) - freeDuration)/60;
        if (duration <0)
        {
        	duration = 0;
        }
        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
    
    
    
    
    public void calculateFareReduction(Ticket ticket, boolean isFrequence){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
        	if (ticket.getOutTime() != null)
        	{
        		throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString()); // à corriger
       
        	}
        	else
        	{
        		throw new IllegalArgumentException("Out time provided is incorrect:");
        	}
        }
        	

      /*  int inHour = ticket.getInTime();
        int outHour = ticket.getOutTime();*/

        //TODO: Some tests are failing here. Need to check if this logic is correct
        double duration = ((Math.abs(ticket.getOutTime().getTime() - ticket.getInTime().getTime()))/(60 * 1000))/60.00;
        double price = 0;
        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
            	price = duration * Fare.CAR_RATE_PER_HOUR;
            	price = price - (0.05 * price);
                ticket.setPrice(price);
                break;
            }
            case BIKE: {
            	price = duration * Fare.BIKE_RATE_PER_HOUR;
            	price = price - (0.05 * price);
            	ticket.setPrice(price);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
    
    
}