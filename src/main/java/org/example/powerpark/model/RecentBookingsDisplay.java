package org.example.powerpark.model;

import java.util.Date;

public class RecentBookingsDisplay {
    private String stationName;
    private String userName;
    private Date bookingDate;

    public RecentBookingsDisplay(String stationName, String userName, Date bookingDate) {
        this.stationName = stationName;
        this.userName = userName;
        this.bookingDate = bookingDate;
    }

    public String getStationName() {
        return stationName;
    }

    public String getUserName() {
        return userName;
    }

    public Date getBookingDate() {
        return bookingDate;
    }
}

