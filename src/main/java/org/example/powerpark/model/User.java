package org.example.powerpark.model;
import javafx.collections.ObservableList;
import org.example.powerpark.businesslogic.service;

public class User extends Person{
    private String address;
    public User(){

    }
    public User(int id,String username, String password, String email, String phone_num, String address) {
        super( id,username, password, email, phone_num);
        this.address = address;
    }
    public User(String username, String password, String email, String phone_num, String address) {
        super( username, password, email, phone_num);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean Login(String username, String password, String role) {
        service userLog = new service();
        boolean val = userLog.Login(username, password, role);
        return val;
    }
    public boolean Signup(){
        service userSignup = new service();
        boolean val = userSignup.SignUp(this.username, this.password,this.email,this.phone_num,this.address);
        return val;
    }
    public int getTotalUsers(){
        service totalusers = new service();
        return totalusers.getTotalRows("user");
    }
    public String TopUser(){
        service topuser = new service();
        return topuser.getMostFreqUser();
    }
    public int getTotalBookings(int id){
        service totalBookings = new service();
        return totalBookings.getTotalBookings(id);
    }
    public int getUnpaidInvoices(int id){
        service unpaidInvoices = new service();
        return unpaidInvoices.getUnpaidInvoices(id);
    }
    public int getCurrentSpend(int id){
        service currspend = new service();
        return currspend.getAmountSpent(id);
    }

    public User fetchUserDetails(String username) {
        service db = new service();
        return db.fetchUserDetails(username);
    }

    public boolean editProfile(String role) {
        service db = new service();
        System.out.println("Role in editProfile: " + role);
        return db.updateUserProfile(this, role);
    }

    public ObservableList<viewInvoiceDisplay> viewInvoices(int id){
        service InvoiceDashboard = new service();
        return InvoiceDashboard.getInvoices(id);
    }
    public ObservableList<viewBookingsDisplay> viewBookings(int id){
        service BookingDashboard = new service();
        return BookingDashboard.getBookings(id);
    }
    public viewBookingsDisplay searchBooking(int id){
        service searchbooking=new service();
        return searchbooking.getBookingById(id);
    }
    public boolean cancelBooking(int id){
        service removebooking=new service();
        return removebooking.cancelBooking(id);
    }
    public viewInvoiceDisplay searchInvoice(int id){
        service searchinvoice=new service();
        return searchinvoice.getInvoiceById(id);
    }
    public boolean payInvoice(int id){
        service payment=new service();
        return payment.payInvoice(id);
    }
    public int bookSlot(int userid,int stationid,int slotid,java.sql.Date bookingdate){
        service db=new service();
        return db.bookSlot(userid,stationid,slotid,bookingdate);
    }

}

