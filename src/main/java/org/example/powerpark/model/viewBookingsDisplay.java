package org.example.powerpark.model;
import java.time.LocalTime;
import java.util.Date;

public class viewBookingsDisplay {
    private int bookingID;
    private String username;
    private String stationname;
    private LocalTime starttime;
    private LocalTime endtime;
    private Date booking_date;

    // Constructor
    public viewBookingsDisplay(int bookingID, String username, String stationname, LocalTime starttime, LocalTime endtime, Date booking_date) {
        this.bookingID = bookingID;
        this.username = username;
        this.stationname = stationname;
        this.starttime = starttime;
        this.endtime = endtime;
        this.booking_date = booking_date;
    }

    // Getters and setters for all fields
    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStationname() {
        return stationname;
    }

    public void setStationname(String stationname) {
        this.stationname = stationname;
    }

    public LocalTime getStarttime() {
        return starttime;
    }

    public void setStarttime(LocalTime starttime) {
        this.starttime = starttime;
    }

    public LocalTime getEndtime() {
        return endtime;
    }

    public void setEndtime(LocalTime endtime) {
        this.endtime = endtime;
    }

    public Date getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(Date booking_date) {
        this.booking_date = booking_date;
    }
}
