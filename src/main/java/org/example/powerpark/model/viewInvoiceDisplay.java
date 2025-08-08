package org.example.powerpark.model;

public class viewInvoiceDisplay {
    private int invoice_id;
    private int booking_id;
    private int amount;
    private String status;
    private String username;
    private String station;

    public viewInvoiceDisplay(int invoice_id, int booking_id, int amount, String status, String username, String station) {
        this.invoice_id = invoice_id;
        this.booking_id = booking_id;
        this.amount = amount;
        this.status = status;
        this.username = username;
        this.station = station;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
