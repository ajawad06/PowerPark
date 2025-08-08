package org.example.powerpark.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.example.powerpark.model.*;

import javax.swing.*;
import java.io.IOException;

public class UserDashboardController {

    @FXML
    private Button BookSlotBtn;

    @FXML
    private Label CurrentSpend;

    @FXML
    private Button EditUserProfileBtn;

    @FXML
    private Button LogOutBtn;

    @FXML
    private TableView<Station> StationTable;

    @FXML
    private Label TotalBookings;

    @FXML
    private Label UnpaidInvoices;

    @FXML
    private TableColumn<Station,Integer> availableSlotsCol;

    @FXML
    private TableColumn<Station,String> chargerTypeCol;

    @FXML
    private Button myBookingBtn;

    @FXML
    private Button myInvoicesBtn;

    @FXML
    private TableColumn<Station,String> stationLocCol;

    @FXML
    private TableColumn<Station,String> stationNamecol;

    @FXML
    private Label username;

    @FXML
    private Button viewStationBtn;

    @FXML
    public void initialize(){
        username.setText(Session.getUsername());
        User u=new User();
        Station s=new Station();
        TotalBookings.setText(String.valueOf(u.getTotalBookings(Session.getUserId())));
        UnpaidInvoices.setText(String.valueOf(u.getUnpaidInvoices(Session.getUserId())));
        CurrentSpend.setText(String.valueOf(u.getCurrentSpend(Session.getUserId())));
        stationNamecol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getStation_name()));
        stationLocCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLocation()));
        chargerTypeCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCharger_type()));
        availableSlotsCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAvailable_slots()));
        StationTable.setItems(s.getStations());

    }

    @FXML
    void BookSlot(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/BookSlot.fxml"));
        Stage window = (Stage) BookSlotBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    void EditProfile(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/EditUserProfile.fxml"));
        Stage window = (Stage) EditUserProfileBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    void LogOut(ActionEvent event)throws IOException {
        Session.clear();
        JOptionPane.showMessageDialog(null, "Logout Successful");
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/Login.fxml"));
        Stage window = (Stage) LogOutBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    void viewBookings(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/MyBookings.fxml"));
        Stage window = (Stage) myBookingBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    void viewInvoices(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/MyInvoices.fxml"));
        Stage window = (Stage) myInvoicesBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    void viewStation(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/ViewStations.fxml"));
        Stage window = (Stage) viewStationBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

}


