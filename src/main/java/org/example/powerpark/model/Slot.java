package org.example.powerpark.model;

import javafx.collections.ObservableList;
import org.example.powerpark.businesslogic.service;

import java.time.LocalTime;

public class Slot {
    private int slotId;
    private String display;
    public Slot(){

    }
    public Slot(int slotId, LocalTime start, LocalTime end) {
        this.slotId = slotId;
        this.display = start.toString() + " – " + end.toString();
    }
    public int getSlotId() {
        return slotId;
    }
    @Override
    public String toString() {
        return display;
    }
    public ObservableList<Slot> getSlots(int stationid){
        service db=new service();
        return db.getSlots(stationid);
    }
}


