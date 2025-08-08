package org.example.powerpark.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.powerpark.model.*;


import javax.swing.*;
import java.io.IOException;

public class ManageStationsController {

    @FXML
    private Button AddstationBtn;

    @FXML
    private Button BackBtn;

    @FXML
    private TableColumn<Station, String> ChargerTypeCol;

    @FXML
    private TableColumn<Station, String> LocCol;

    @FXML
    private Button RemoveStationBtn;

    @FXML
    private TableView<Station> StationTable;

    @FXML
    private TableColumn<Station, Integer> TotalSlotsCol;

    @FXML
    private TextField searchBarstation;

    @FXML
    private TableColumn<Station, Integer> stationIDcol;

    @FXML
    private TableColumn<Station, String> stationNameCol;
    @FXML
    public void initialize(){
        Admin a=new Admin();
        stationIDcol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getStation_id()));
        stationNameCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getStation_name()));
        LocCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLocation()));
        ChargerTypeCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCharger_type()));
        TotalSlotsCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTotal_slots()));
        StationTable.setItems(a.getStations());
    }
    @FXML
    void AddStationScreen(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/AddStation.fxml"));
        Stage window = (Stage) AddstationBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    void previousScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/Admin_Dashboard.fxml"));
        Stage window = (Stage) BackBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    void removeStation(ActionEvent event) {
        Admin a = new Admin();
        String name = searchBarstation.getText();

        if (name.isEmpty()) {
            StationTable.setItems(a.getStations());
            JOptionPane.showMessageDialog(null, "No station selected for removal.");
        } else {
            Station foundStation = a.searchStation(name);

            if (foundStation != null) {
                StationTable.setItems(FXCollections.observableArrayList(foundStation));

                int option = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to remove this Station?",
                        "Confirm Station Removal",
                        JOptionPane.YES_NO_OPTION
                );

                if (option == JOptionPane.YES_OPTION) {
                    if (a.removeStation(name)) {
                        JOptionPane.showMessageDialog(null, "Station removed successfully.");
                        StationTable.setItems(a.getStations()); // Refresh table
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to remove the station.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Station removal cancelled.");
                }

            } else {
                StationTable.setItems(FXCollections.observableArrayList());
                JOptionPane.showMessageDialog(null, "Station with such name does not exist.");
            }
        }
    }

    @FXML
    void typeSearchStation(ActionEvent event) {
        Admin a=new Admin();
        String reqStation=searchBarstation.getText();
        if (reqStation.isEmpty()){
            StationTable.setItems(a.getStations());
        }else{
            Station foundStation=a.searchStation(reqStation);
            if (foundStation != null) {
                StationTable.setItems(FXCollections.observableArrayList(foundStation)); // Show only that user
            } else {
                StationTable.setItems(FXCollections.observableArrayList()); // Clear table if not found

            }
        }
    }

}
