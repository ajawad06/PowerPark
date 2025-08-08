package org.example.powerpark.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import org.example.powerpark.businesslogic.service;

public class Admin extends Person{
    public Admin(){
    }
    public Admin( String username, String password, String email, String phone_num){
        super(username, password, email, phone_num);
    }

    public boolean Login(String username, String password, String role) {
        service adminLog = new service();
        boolean val = adminLog.Login(username, password, role);
        return val;
    }
    public ObservableList<RecentBookingsDisplay> getRecentBookings(){
        service recBookingDashboard = new service();
        return recBookingDashboard.getRecentBookings();
    }
    public ObservableList<User>  getUsers(){
        service getallusers=new service();
        return getallusers.getUsers();
    }
    public ObservableList<Station>  getStations(){
        service getAllStations=new service();
        return getAllStations.getStations();
    }

    public User searchUser(String username){
        service searchforUser=new service();
        return searchforUser.getUserByUsername(username);
    }
    public boolean removeUser(String username){
        service removeUser=new service();
        return removeUser.removeUser(username);
    }
    public Station searchStation(String name){
        service searchforStation=new service();
        return searchforStation.getStationByName(name);
    }
    public boolean removeStation(String name){
        service removeStation=new service();
        return removeStation.removeStation(name);
    }
    public boolean addStation(String name, String location, double latitude, double longitude, String charger, int slots) {
        service addStation = new service();
        try {
            return addStation.addStationinDB(name, location, latitude, longitude, charger, slots);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public ObservableList<viewBookingsDisplay> viewBookings(){
        service BookingDashboard = new service();
        return BookingDashboard.getBookings();
    }
    public viewBookingsDisplay searchBooking(int id){
        service searchBooking=new service();
        return searchBooking.getBookingById(id);
    }
    public boolean cancelBooking(int id){
        service removebooking=new service();
        return removebooking.cancelBooking(id);
    }
    public ObservableList<viewInvoiceDisplay> viewInvoices(){
        service InvoiceDashboard = new service();
        return InvoiceDashboard.getInvoices();
    }

    public Admin getAdminDetails(String username) {
        service db = new service();
        return db.fetchAdminDetails(username);
    }
    public boolean editProfile(String role) {
        service db = new service();
        return db.updateAdminProfile(this, role);
    }

}
