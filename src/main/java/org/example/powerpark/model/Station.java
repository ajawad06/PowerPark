package org.example.powerpark.model;

import javafx.collections.ObservableList;
import org.example.powerpark.businesslogic.service;

public class Station {
    private int station_id;
    private String station_name;
    private String location;
    private double latitude;
    private double longitude;
    private String charger_type;
    private int total_slots;
    private int available_slots;
    public Station(){

    }
    public Station(int station_id,String station_name, String location, double latitude, double longitude, String charger_type, int total_slots,int available_slots) {
        this.station_id=station_id;
        this.station_name = station_name;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.charger_type = charger_type;
        this.total_slots = total_slots;
        this.available_slots = available_slots;
    }
    public Station(String station_name, String location, double latitude, double longitude, String charger_type, int total_slots) {
        this.station_name = station_name;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.charger_type = charger_type;
        this.total_slots = total_slots;
        this.available_slots = total_slots;
    }

    public int getStation_id() {
        return station_id;
    }

    public void setStation_id(int station_id) {
        this.station_id = station_id;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCharger_type() {
        return charger_type;
    }

    public void setCharger_type(String charger_type) {
        this.charger_type = charger_type;
    }

    public int getTotal_slots() {
        return total_slots;
    }

    public void setTotal_slots(int total_slots) {
        this.total_slots = total_slots;
    }

    public int getAvailable_slots() {
        return available_slots;
    }

    public void setAvailable_slots(int available_slots) {
        this.available_slots = available_slots;
    }
    public int getTotalStations(){
        service totalstations = new service();
        return totalstations.getTotalRows("station");
    }
    public String TopStation(){
        service topstation = new service();
        return topstation.getMostFreqStation();
    }
    public ObservableList<Station> getStations(){
        service getAllStations=new service();
        return getAllStations.getStations();
    }
    public String TopStation(String username){
        service topstation = new service();
        return topstation.getMostFreqStation(username);
    }
    @Override
    public String toString() {
        return station_name; // Or whatever field holds the station's name
    }
}
