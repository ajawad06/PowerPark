package org.example.powerpark.model;

import org.example.powerpark.businesslogic.service;
public class Invoice {
    private int invoice_id;
    private int booking_id;
    private int amount;
    private String status;
    public Invoice(){
    }
    public Invoice(int invoice_id, int booking_id, int amount, String status) {
        this.invoice_id = invoice_id;
        this.booking_id = booking_id;
        this.amount = amount;
        this.status = status;
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
    public int TotalRevenue(){
        service totalamount = new service();
        return totalamount.getRevenue();
    }
    public boolean addInvoice(int bookingid,int amount, String status){
        service addInv = new service();
        return addInv.addInvoice(bookingid,amount,status);
    }
}
