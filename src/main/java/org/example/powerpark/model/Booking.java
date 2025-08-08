package org.example.powerpark.model;
import org.example.powerpark.businesslogic.service;

import java.util.Date;

public class Booking {
    private int booking_id;
    private int user_id;
    private int station_id;
    private int slot_id;
    private Date booking_date;
    public Booking(){

    }

    public Booking(int user_id, int station_id, int slot_id, Date booking_date) {
        this.user_id = user_id;
        this.station_id = station_id;
        this.slot_id = slot_id;
        this.booking_date = booking_date;
    }
public boolean isSlotAvailable(int stationId,int slotId,java.sql.Date bookingDate){
    service db = new service();
    return db.isSlotAvailable(stationId, slotId, bookingDate);
}
    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getSlot_id() {
        return slot_id;
    }

    public void setSlot_id(int slot_id) {
        this.slot_id = slot_id;
    }

    public int getStation_id() {
        return station_id;
    }

    public void setStation_id(int station_id) {
        this.station_id = station_id;
    }

    public Date getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(Date booking_date) {
        this.booking_date = booking_date;
    }
}
